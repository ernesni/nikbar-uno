package com.ean.promo.ui.dialogs;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import com.ean.promo.backend.entity.ComprobanteBeneficiario;
import com.ean.promo.backend.entity.ComprobanteUsuario;
import com.ean.promo.backend.entity.Grupo;
import com.ean.promo.backend.entity.Usuario;
import com.ean.promo.backend.service.impl.ComprobanteBeneficiarioServiceImpl;
import com.ean.promo.backend.service.impl.ComprobanteUsuarioServiceImpl;
import com.ean.promo.ui.util.UIUtils;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.dom.DomEvent;
import com.vaadin.flow.dom.DomEventListener;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class ComprobanteUsuarioDialog extends Dialog{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7238587507732249881L;
	private ComprobanteUsuarioServiceImpl comprobanteUsuarioService;
	private ComprobanteBeneficiarioServiceImpl comprobanteBeneficiarioService;
	private Usuario usuario;
	private Grupo grupo;
	
	private Button cancelBtn, saveBtn;
	
	private BeanValidationBinder<ComprobanteUsuario> binder;
	private ConfirmationDialog confirmationDialog = null;
	
	private MemoryBuffer buffer;
	private Upload upload;
	private boolean hayFoto = false;
	private TextField numerotextField;

	
	public ComprobanteUsuarioDialog(ComprobanteUsuarioServiceImpl comprobanteUsuarioService, 
			Usuario usuario, Grupo grupo, ComprobanteBeneficiarioServiceImpl comprobanteBeneficiarioService) {
		this.comprobanteUsuarioService = comprobanteUsuarioService;
		this.usuario = usuario;
		this.grupo = grupo;
		this.comprobanteBeneficiarioService = comprobanteBeneficiarioService;

		getElement().setAttribute("theme", "custom-dialog");

		setCloseOnEsc(false);
		setCloseOnOutsideClick(false);

		createContent();
		createFooter();
		
		setHeaderTitle("Nuevo Comprobante");
		
		open();
	}

	private void createContent() {
		ComprobanteUsuario comprobanteUsuario = new ComprobanteUsuario();

		/*IntegerField numeroField = new IntegerField();
		numeroField.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);
		numeroField.setAutoselect(true);
		numeroField.setLabel("Número del comprobante");
		numeroField.setHelperText("Formato: 001-001-1234567");*/
		
		numerotextField = new TextField();
		numerotextField.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);
		numerotextField.setPattern("[0-9]{3}[-][0-9]{3}[-][0-9]{7}$");
		numerotextField.setHelperText("Formato: 001-001-1234567");
		numerotextField.setLabel("Número del comprobante");
		numerotextField.setRequired(true);
		numerotextField.setErrorMessage("Formato incorrecto");
        
        binder = new BeanValidationBinder<>(ComprobanteUsuario.class);
		binder.setBean(comprobanteUsuario);

		//binder.bind(numerotextField, "numero_comprobante");
		
		buffer = new MemoryBuffer();
		upload = new Upload(buffer);
		upload.setMaxFiles(1);
		int maxFileSizeInBytes = 10 * 1024 * 1024; // 10MB
		upload.setMaxFileSize(maxFileSizeInBytes);
		upload.setAcceptedFileTypes("image/jpeg", "image/jpg", "image/png", "image/gif");

		upload.addSucceededListener(event -> {
			hayFoto = true;
		});

		upload.addFileRejectedListener(event -> {
			// error en el formato del archivo
			upload.setDropLabel(new Label("Tipo de Archivo incorrecto"));
		});

		upload.getElement().addEventListener("upload-abort", new DomEventListener() {

			@Override
			public void handleEvent(DomEvent event) {
				// cancel upload
				//imageBytes = null;
				//buffer = null;
				hayFoto = false;
			}
		});
      
		FormLayout form = new FormLayout();
		form.addClassNames(LumoUtility.Padding.Bottom.LARGE, LumoUtility.Padding.Horizontal.LARGE, LumoUtility.Padding.Top.SMALL);
		form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
				new FormLayout.ResponsiveStep("600px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP),
				new FormLayout.ResponsiveStep("800px", 3, FormLayout.ResponsiveStep.LabelsPosition.TOP),
				new FormLayout.ResponsiveStep("1024px", 4, FormLayout.ResponsiveStep.LabelsPosition.TOP));
		
		//form.setColspan(ciudad, 2);
		
		
		form.add(numerotextField, upload);

        add(form);
	}

	private void createFooter() {
		cancelBtn = new Button("Cancelar", new Icon(VaadinIcon.CLOSE_CIRCLE), event -> {
			close();
		});
		cancelBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

		saveBtn = new Button("GUARDAR", event -> {
			if (binder.validate().isOk()) {
				if (numerotextField.getValue().length() == 15 && !numerotextField.isInvalid()) {
					if (hayFoto) {
						if (checkExists(numerotextField.getValue())) {
							UIUtils.createNotificationError("Ya fue registrado un comprobante con ese número!.");
							saveBtn.setEnabled(true);
						}else {
							confirmationDialog = new ConfirmationDialog("Confirmar operación",
									"Está seguro que desea guardar este comprobante?", accion -> {
										guardar(confirmationDialog);
									},
									action -> {
										saveBtn.setEnabled(true);
										confirmationDialog.close();
									});
						}
						
					}else {
						UIUtils.createNotificationError("Debe seleccionar una imagen!.");
						saveBtn.setEnabled(true);
					}
				}else {
					UIUtils.createNotificationError("Debe cargar el número de comprobante correctamente!.");
					saveBtn.setEnabled(true);
					numerotextField.focus();
				}
					
			}else {
				saveBtn.setEnabled(true);
			}
		});

		saveBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		saveBtn.setDisableOnClick(true);
		
		getFooter().add(cancelBtn);
		getFooter().add(saveBtn);
		
	}
	
	public void guardar(ConfirmationDialog confirmationDialog) {
		ComprobanteUsuario comprobanteUsuarioBean = binder.getBean();
		comprobanteUsuarioBean.setNumero_comprobante(numerotextField.getValue());
		comprobanteUsuarioBean.setUsuario(usuario);
		comprobanteUsuarioBean.setGrupo(grupo);
		comprobanteUsuarioBean.setAprobado(false);
		comprobanteUsuarioBean.setChequeado(false);
		comprobanteUsuarioBean.setCupon(0);
		comprobanteUsuarioBean.setMensaje("Pendiente");
		comprobanteUsuarioBean.setExtension(".jpg");
		comprobanteUsuarioBean.setName("idUsuario_" + usuario.getIdusuario() + "_grupo_" + grupo.getIdgrupo() + "_numero_comprobante_" + comprobanteUsuarioBean.getNumero_comprobante());
		
		try {
			BufferedImage imagen = ImageIO.read(buffer.getInputStream());

			File outputfile = new File(comprobanteUsuarioBean.getUrl());
			ImageIO.write(imagen, "jpg", outputfile);
			
			
			if (checkFoto(comprobanteUsuarioBean)) {
				comprobanteUsuarioService.addComprobanteUsuario(comprobanteUsuarioBean);
				
				UIUtils.createNotificationSuccess("Se ha registrado el Comprobante!.");
				this.close();
				confirmationDialog.close();
			}else {
				UIUtils.createNotificationError("Hubo un problema al cargar la imagen.");
			}
			
			/*System.out.println("getUrl: " + comprobanteUsuarioBean.getUrl());
			System.out.println("buffer: " + buffer.getInputStream().toString());
			System.out.println("outputfile: " + outputfile.toString());
			System.out.println("buffer info: " + buffer.getFileName());
			int i = buffer.getFileName().lastIndexOf(".");
			String extension = buffer.getFileName().substring(i);
			System.out.println("buffer info: " + extension);*/
			
		} catch (IOException e) {
			UIUtils.createNotificationError("Hubo un problema al cargar el comprobante.");
		}
		
	}
	
	private boolean checkFoto(ComprobanteUsuario comprobanteUsuario) {
		boolean salida = false;
		byte[] bytes;
		try {
			bytes = Files.readAllBytes(Paths.get(comprobanteUsuario.getUrl()));
			if (bytes != null) {
				salida = true;
			}
		} catch (IOException e) {
			System.err.println("Error: " + e.getMessage() + e.getCause());
		}

		return salida;
	}
	
	private boolean checkExists(String numero) {
		ComprobanteUsuario comprobanteUsuario = comprobanteUsuarioService.findExistsComprobanteByNumero(numero);
		
		if (comprobanteUsuario == null) {
			ComprobanteBeneficiario comprobanteBeneficiario = comprobanteBeneficiarioService.findExistsComprobanteByNumero(numero);
			if (comprobanteBeneficiario == null) {
				return false;
			}else {
				return true;
			}
		}else {
			return true;
		}
	}
	
}
