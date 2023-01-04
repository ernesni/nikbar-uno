package com.ean.promo.views.control;

import static com.ean.promo.ui.util.UIUtils.IMG_PATH_NO_IMAGE;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ean.promo.backend.entity.ComprobanteUsuario;
import com.ean.promo.backend.service.impl.ComprobanteUsuarioServiceImpl;
import com.ean.promo.ui.components.FlexBoxLayout;
import com.ean.promo.ui.components.detailsdrawer.DetailsDrawer;
import com.ean.promo.ui.components.detailsdrawer.DetailsDrawerFooterCRUD;
import com.ean.promo.ui.components.detailsdrawer.DetailsDrawerHeader;
import com.ean.promo.ui.dialogs.ConfirmationDialog;
import com.ean.promo.ui.dialogs.VerImagen;
import com.ean.promo.ui.layout.size.Horizontal;
import com.ean.promo.ui.layout.size.Top;
import com.ean.promo.ui.util.Constante;
import com.ean.promo.ui.util.UIUtils;
import com.ean.promo.ui.util.css.BoxSizing;
import com.ean.promo.ui.util.css.FlexDirection;
import com.ean.promo.ui.util.css.LumoStyles;
import com.ean.promo.views.MainView;
import com.ean.promo.views.SplitViewFrame;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.converter.StringToBooleanConverter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;

@Route(value = Constante.PAGE_CONTROL_USUARIO, layout = MainView.class)
@PageTitle(Constante.TITLE_CONTROL_USUARIO)
@RolesAllowed({"ADMIN", "SUPERVISOR"})
public class ControlUsuarioView extends SplitViewFrame {
	
	private ComprobanteUsuarioServiceImpl comprobanteUsuarioService;
	
	private BeanValidationBinder<ComprobanteUsuario> binder;
	
	private RadioButtonGroup<String> chequeado, aprobado;
	private IntegerField cuponField;
	private ComboBox<String> comboBoxMensaje;
	
	private boolean isdeleteImage;
	private Grid<ComprobanteUsuario> grid;

	private List<String> messages;
	
	private DetailsDrawerFooterCRUD footer;

	private DetailsDrawer detailsDrawer;
	private DetailsDrawerHeader detailsDrawerHeader;
	
	private ConfirmationDialog confirmationDialog = null;
	
	private Image image;

	public ControlUsuarioView(@Autowired ComprobanteUsuarioServiceImpl comprobanteUsuarioService) {
		this.comprobanteUsuarioService = comprobanteUsuarioService;

		messages = new ArrayList<String>();
		messages.add("");
		messages.add("OK");
		messages.add("Pendiente");
		messages.add("No compró el producto");
		messages.add("No se visualiza número de factura");
		messages.add("No coincide número de factura");
		messages.add("No coincide el Nro de documento con el de la factura");
		messages.add("Documento Inválido");
				
		setViewContent(createContent());
		setViewDetails(createDetailsDrawer());

	}

	private Component createContent() {
		FlexBoxLayout content = new FlexBoxLayout(createGrid());
		content.setBoxSizing(BoxSizing.BORDER_BOX.getValue());
		content.setHeightFull();
		content.setPadding(Horizontal.RESPONSIVE_X, Top.RESPONSIVE_X);
		content.setFlexDirection(FlexDirection.COLUMN.getValue());
		return content;
	}
	
	private Grid<ComprobanteUsuario> createGrid() {
		
		grid = new Grid<>();
		grid.addSelectionListener(event -> event.getFirstSelectedItem().ifPresent(this::showDetails));
		grid.setItems(comprobanteUsuarioService.findAllComprobanteUsuarioByChequeadoFalse());
		grid.setHeightFull();
		grid.setColumnReorderingAllowed(true);
		grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
		
		grid.addColumn(ComprobanteUsuario::getIdcomprobante).setFlexGrow(0).setFrozen(true).setHeader("ID").setSortProperty("idcomprobante").setTextAlign(ColumnTextAlign.END);
		grid.addColumn(ComprobanteUsuario::getNumero_comprobante).setHeader("Comprobante N°").setTextAlign(ColumnTextAlign.START).setSortProperty("numero_comprobante");
		grid.addColumn(cu -> cu.getUsuario().getUsuarioInfo().getFirstNombreFirstApellido()).setHeader("Usuario").setTextAlign(ColumnTextAlign.START);
		grid.addColumn(cu -> cu.getGrupo().getNombre()).setHeader("Grupo").setTextAlign(ColumnTextAlign.START).setSortProperty("grupo.nombre");
	
		for (Column<ComprobanteUsuario> column : grid.getColumns()) {
			column.setAutoWidth(true);
			column.setResizable(true);
		}
				
		return grid;
	}
	
	private void showDetails(ComprobanteUsuario comprobanteUsuario) {
		
		isdeleteImage = false;
		detailsDrawerHeader.setTitle("Comprobante N°: " + comprobanteUsuario.getNumero_comprobante());
		detailsDrawer.setContent(createDetails(comprobanteUsuario));
		
		detailsDrawer.show();
	}

	private DetailsDrawer createDetailsDrawer() {
		detailsDrawer = new DetailsDrawer(DetailsDrawer.Position.RIGHT);

		// Header
		detailsDrawerHeader = new DetailsDrawerHeader("");
		detailsDrawerHeader.addCloseListener(buttonClickEvent -> detailsDrawer.hide());
		detailsDrawer.setHeader(detailsDrawerHeader);

		// Footer
		footer = new DetailsDrawerFooterCRUD();
		footer.addSaveListener(e -> {
			if (binder.validate().isOk()) {
				if(controlar()) {
					confirmationDialog = new ConfirmationDialog("Confirmar operación",
							"Está seguro que desea guardar las modificaciones realizadas?", event -> {
								guardar(confirmationDialog);
							},
							action -> {
								//saveBtn.setEnabled(true);
								confirmationDialog.close();
							});
				}
			}
		});
		footer.getDelete().setVisible(false);
		/*footer.addDeleteListener(e -> {
			confirmationDialog = new ConfirmationDialog("Confirmar operación",
					"Está seguro que desea eliminar este registro de forma permanente?", event -> {
						delete(confirmationDialog);
					},
					action -> {
						//saveBtn.setEnabled(true);
						confirmationDialog.close();
					});
		});*/
		
		footer.addCancelListener(e -> {
			detailsDrawer.hide();
		});
		
		detailsDrawer.setFooter(footer);

		return detailsDrawer;
	}

	@Transactional
	public void guardar(ConfirmationDialog confirmationDialog) {
		ComprobanteUsuario comprobanteUsuarioBean = binder.getBean();
		comprobanteUsuarioService.addComprobanteUsuario(comprobanteUsuarioBean);
		
		updateList();
		UIUtils.showNotification("Modificaciones confirmadas!.", Notification.Position.BOTTOM_END);
		detailsDrawer.hide();
		confirmationDialog.close();	
	}
	
	public void updateList() {
		grid.setItems(comprobanteUsuarioService.findAllComprobanteUsuarioByChequeadoFalse());
	}
	
	private FormLayout createDetails(ComprobanteUsuario comprobanteUsuario) {
		
		// Form layout
		FormLayout form = new FormLayout();
				form.addClassNames(LumoStyles.Padding.Bottom.L, LumoStyles.Padding.Horizontal.L, LumoStyles.Padding.Top.S);
				form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
						new FormLayout.ResponsiveStep("21em", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP));

		TextField numeroField = new TextField();
		numeroField.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);
		numeroField.setAutoselect(true);
		numeroField.setLabel("N° de Comprobante");
		numeroField.setReadOnly(true);
		numeroField.setValue(comprobanteUsuario.getNumero_comprobante());
				
		cuponField = new IntegerField();
		cuponField.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);
		cuponField.setAutoselect(true);
		cuponField.setLabel("Cupones");
		
		chequeado = new RadioButtonGroup<String>();
		chequeado.setLabel("Chequeado");
		chequeado.setItems("SI", "NO");
		
		aprobado = new RadioButtonGroup<String>();
		aprobado.setLabel("Aprobado");
		aprobado.setItems("SI", "NO");
		
		comboBoxMensaje = new ComboBox<String>("Mensaje");
		comboBoxMensaje.setPlaceholder("Seleccione un Mensaje");
		comboBoxMensaje.setItems(messages);
		comboBoxMensaje.setClearButtonVisible(true);
		comboBoxMensaje.setRequired(true);
		comboBoxMensaje.setMinWidth("200px");
		
		chequeado.addValueChangeListener(ch -> {
			if(ch.getValue().equals("SI")) {
				aprobado.setEnabled(true);
				comboBoxMensaje.setEnabled(true);
			}else {
				aprobado.setValue("NO");
				cuponField.setValue(0);
				aprobado.setEnabled(false);
				cuponField.setEnabled(false);
				comboBoxMensaje.setValue("Pendiente");
				comboBoxMensaje.setEnabled(false);
			}
		});
		
		aprobado.addValueChangeListener(ch -> {
			if(ch.getValue().equals("SI")) {
				cuponField.setEnabled(true);
				cuponField.focus();
				comboBoxMensaje.setValue("OK");
				comboBoxMensaje.setEnabled(false);
			}else {
				comboBoxMensaje.setValue("Pendiente");
				comboBoxMensaje.setEnabled(true);
				cuponField.setValue(0);
				cuponField.setEnabled(false);
			}
		});
		
		binder = new BeanValidationBinder<>(ComprobanteUsuario.class);
		binder.setBean(comprobanteUsuario);
		
		binder.bind(cuponField, "cupon");
		binder.forField(chequeado).withConverter(new StringToBooleanConverter("Please boolean value", "SI", "NO"))
		.bind("chequeado");
		binder.forField(aprobado).withConverter(new StringToBooleanConverter("Please boolean value", "SI", "NO"))
		.bind("aprobado");
		
		binder.bind(comboBoxMensaje, "mensaje");
		
		image = createFoto(comprobanteUsuario);
		isdeleteImage = true;
		form.add(numeroField, chequeado, aprobado, cuponField, comboBoxMensaje, image);
		form.setColspan(image, 2);

		form.setColspan(numeroField, 2);
		form.setColspan(comboBoxMensaje, 2);
		
		return form;
	}
	
	private Image createFoto(ComprobanteUsuario comprobanteUsuario) {
		
		Image foto = null;
		byte[] bytes;
		try {
			bytes = Files.readAllBytes(Paths.get(comprobanteUsuario.getUrl()));
			foto = cargarFoto(bytes, false, "250px");
			foto.addClassNames(LumoStyles.Padding.Bottom.L, LumoStyles.Padding.Top.S);

			if (!isdeleteImage) {
				createContextMenu(foto, comprobanteUsuario);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return foto;
	}
	
	private void createContextMenu(Image foto, ComprobanteUsuario comprobanteUsuario) {
		ContextMenu contextMenu = new ContextMenu();
		contextMenu.setTarget(foto);
		contextMenu.addItem("Ver Foto", e -> {
			new VerImagen("Comprobante N°: " + comprobanteUsuario.getNumero_comprobante(),
					UIUtils.cargarFotoSinResize(getByte(comprobanteUsuario.getUrl()), false));
		});
	}
	
	public static Image cargarFoto(byte[] img, boolean nuevo, String size) {
		Image imagen = new Image();
		String src = (IMG_PATH_NO_IMAGE);
		if (!nuevo) {
			if (img != null) {
				StreamResource sr = new StreamResource("foto", () -> {
					return new ByteArrayInputStream(img);
				});
				sr.setContentType("image/jpg");
				imagen.setSrc(sr);
			} else {
				imagen.setSrc(src);
			}
		} else {
			imagen.setSrc(src);
		}

		imagen.setWidth(size);
		imagen.setHeight(size);

		return imagen;

	}
	
	private byte[] getByte(String filePath) {
		byte[] bytes = null;
		try {
			bytes = Files.readAllBytes(Paths.get(filePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bytes;
	}
	
	private boolean controlar() {
		boolean salida = false;
		if(chequeado.getValue().equals("SI")) {
			if (aprobado.getValue().equals("SI")) {
				if (cuponField.getValue() > 0) {
					salida = true;
				}else {
					UIUtils.createNotificationError("Debe completar la cantidad de cupones!.");
					cuponField.focus();
				}
			}else {
				if(!comboBoxMensaje.getValue().equals("OK") && !comboBoxMensaje.getValue().equals("Pendiente")) {
					salida = true;	
				}else {
					UIUtils.createNotificationError("Debe cambiar el mensaje para saber por qué no se aprobó el comprobante!.");
				}
			}
		}else {
			UIUtils.createNotificationError("No ha chequeado el comprobante aún!.");
		}
		return salida;
	}
	
}