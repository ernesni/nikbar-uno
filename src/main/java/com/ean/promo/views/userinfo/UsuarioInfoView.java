package com.ean.promo.views.userinfo;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;

import com.ean.promo.backend.entity.Ciudad;
import com.ean.promo.backend.entity.Departamento;
import com.ean.promo.backend.entity.Usuario;
import com.ean.promo.backend.entity.UsuarioInfo;
import com.ean.promo.backend.service.impl.CiudadServiceImpl;
import com.ean.promo.backend.service.impl.DepartamentoServiceImpl;
import com.ean.promo.backend.service.impl.MiUsuarioServiceImpl;
import com.ean.promo.backend.service.impl.UsuarioInfoServiceImpl;
import com.ean.promo.security.SecurityUtils;
import com.ean.promo.ui.components.FlexBoxLayout;
import com.ean.promo.ui.dialogs.ConfirmationDialog;
import com.ean.promo.ui.layout.size.Horizontal;
import com.ean.promo.ui.layout.size.Uniform;
import com.ean.promo.ui.util.Constante;
import com.ean.promo.ui.util.UIUtils;
import com.ean.promo.ui.util.css.FlexDirection;
import com.ean.promo.ui.util.css.LumoStyles;
import com.ean.promo.views.MainView;
import com.ean.promo.views.ViewFrame;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle(Constante.TITLE_USUARIO_INFO)
@Route(value = Constante.PAGE_USUARIO_INFO, layout = MainView.class)
@RolesAllowed("USER")
public class UsuarioInfoView extends ViewFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6819230989956352746L;
	private UsuarioInfoServiceImpl usuarioInfoService;
	private CiudadServiceImpl ciudadService;
	private List<Departamento> departamentos;
	private List<Ciudad> ciudades;
	private Usuario usuario;
	private ConfirmationDialog confirmationDialog = null;
	
	private BeanValidationBinder<UsuarioInfo> binder;
	private TextField telefono2;
	private RadioButtonGroup<String> opcion_telefono2;

    public UsuarioInfoView(@Autowired UsuarioInfoServiceImpl usuarioInfoService, @Autowired DepartamentoServiceImpl departamentoService, 
    		@Autowired CiudadServiceImpl ciudadService, @Autowired MiUsuarioServiceImpl usuarioService) {

    	this.usuarioInfoService = usuarioInfoService;
    	this.ciudadService = ciudadService;
    	
    	final String username = SecurityUtils.getUsername();
		usuario = username != null ? usuarioService.findByUsernameIgnoreCase(username) : null;
    	
    	departamentos = departamentoService.findAllDepartamento();
    	ciudades = new ArrayList<Ciudad>();
    	
    	setId("usuario-info");
		setViewContent(createContent());
        
    }
    
    private Component createContent() {
	
		FlexBoxLayout content = new FlexBoxLayout(createForm());
		content.setFlexDirection(FlexDirection.COLUMN.getValue());
		content.setMargin(Horizontal.AUTO);
		content.setMaxWidth("840px");
		content.setHeightFull();
		content.setPadding(Uniform.RESPONSIVE_L);
		return content;
	}
    
    private Component createForm() {
    	UsuarioInfo usuarioInfo = null;
    	if (usuario != null) {
    		usuarioInfo = usuarioInfoService.findByIdUsuario(usuario.getIdusuario());	
		}
    	
    	/*IntegerField ciField = new IntegerField();
    	ciField.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);
    	ciField.setAutoselect(true);
    	ciField.setLabel("C.I. N°");
    	ciField.setReadOnly(true);*/
    	
    	TextField ciField = new TextField();
    	ciField.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);
    	ciField.setLabel("C.I. N°");
    	ciField.setReadOnly(true);
    	
        TextField apellido = new TextField("Apellidos");
        TextField nombre = new TextField("Nombres");
        TextField telefono1 = new TextField("Teléfono principal N°");
        telefono1.setAllowedCharPattern("[\\d\\-+()]");
        telefono2 = new TextField("Teléfono secundario N°");
        telefono2.setAllowedCharPattern("[\\d\\-+()]");
        
        RadioButtonGroup<String> opcion_telefono1 = new RadioButtonGroup<>();
        opcion_telefono1.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        opcion_telefono1.setLabel("Opciones Teléfono principal");
        opcion_telefono1.setItems("Llamadas y whatsapp", "Solo whatsapp", "Solo llamadas");
        
        opcion_telefono2 = new RadioButtonGroup<>();
        opcion_telefono2.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        opcion_telefono2.setLabel("Opciones Teléfono secundario");
        opcion_telefono2.setItems("Llamadas y whatsapp", "Solo whatsapp", "Solo llamadas");
        
        RadioButtonGroup<String> sexo = new RadioButtonGroup<>();
        sexo.setLabel("Sexo");
        sexo.setItems("Masculino", "Femenino");
        
        EmailField emailField = new EmailField();
        emailField.setLabel("email");
        emailField.setErrorMessage("Ingrese un email válido");
        emailField.setClearButtonVisible(true);
        
        int charLimit = 300;

        TextArea direccion = new TextArea();
        direccion.setLabel("Dirección completa");
        direccion.setMaxLength(charLimit);
        //direccion.setValueChangeMode(ValueChangeMode.EAGER);
        /*direccion.addValueChangeListener(e -> {
            e.getSource()
                    .setHelperText(e.getValue().length() + "/" + charLimit);
        });*/
        
        ComboBox<Departamento> departamento = new ComboBox<>("Departamento");
        departamento.setItems(departamentos);
        departamento.setItemLabelGenerator(Departamento::getDepartamento);
        
        ComboBox<Ciudad> ciudad = new ComboBox<>("Ciudad");
        ciudad.setItems(ciudades);
        ciudad.setItemLabelGenerator(Ciudad::getCiudad);
        
        departamento.addValueChangeListener(dep -> {
			ciudad.clear();
			ciudades.clear();
			if (dep.getValue()!= null) {
				ciudades = ciudadService.findByDepartamento(dep.getValue().getIddepartamento());
			}
			ciudad.setItems(ciudades);
		});
        
        if (usuarioInfo != null) {
			departamento.setValue(usuarioInfo.getCiudad().getDepartamento());
			ciudad.clear();
			ciudades.clear();
			ciudades = ciudadService.findByDepartamento(departamento.getValue().getIddepartamento());
			ciudad.setItems(ciudades);
		}
        
        if (usuarioInfo == null) {
			usuarioInfo = new UsuarioInfo();
		}
        
        binder = new BeanValidationBinder<>(UsuarioInfo.class);
		binder.setBean(usuarioInfo);

		//binder.bind(ciField, "idusuario_info");
		binder.bind(nombre, "nombre");
		binder.bind(apellido, "apellido");
		binder.bind(telefono1, "telefono1");
		binder.bind(telefono2, "telefono2");
		binder.bind(sexo, "sexo");
		binder.bind(emailField, "email");
		binder.bind(opcion_telefono1, "opcion_telefono1");
		binder.bind(opcion_telefono2, "opcion_telefono2");
		binder.bind(direccion, "direccion");

		binder.bind(ciudad, "ciudad");
		
		HorizontalLayout hl = createFooter();
		hl.addClassNames(LumoStyles.Padding.Bottom.XL);
      
    	FormLayout form = new FormLayout();
		form.addClassNames(LumoStyles.Padding.Bottom.XL, LumoStyles.Padding.Horizontal.L, LumoStyles.Padding.Top.S);
		form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
				new FormLayout.ResponsiveStep("600px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP),
				new FormLayout.ResponsiveStep("800px", 3, FormLayout.ResponsiveStep.LabelsPosition.TOP),
				new FormLayout.ResponsiveStep("1024px", 4, FormLayout.ResponsiveStep.LabelsPosition.TOP));
        
		form.add(ciField, nombre , apellido, sexo, emailField, departamento, ciudad, telefono1, opcion_telefono1, telefono2, 
        		opcion_telefono2, direccion, hl);

		form.setColspan(emailField, 2);
		form.setColspan(direccion, 4);
		form.setColspan(hl, 4);

        ciField.setValue(UIUtils.convertInt(usuario.getIdusuario()));

        return form;
        
    }
    
    private HorizontalLayout createFooter() {
    	
    	Button cancelButton = new Button("Cancelar", new Icon(VaadinIcon.CLOSE_CIRCLE));
    	cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

    	cancelButton.addClickListener(e ->
    		cancelButton.getUI().ifPresent(ui ->
    	           ui.navigate(""))
    	);
    	
		

		Button saveButton = new Button("Guardar", new Icon(VaadinIcon.CHECK_CIRCLE_O), 
				event -> {
					if (binder.validate().isOk()) {
						if(controlarTelefono2()) {
							confirmationDialog = new ConfirmationDialog("Confirmar operación",
									"Está seguro que desea guardar las modificaciones realizadas?", action -> {
										guardar(confirmationDialog);
									},
									action -> {
										//saveBtn.setEnabled(true);
										confirmationDialog.close();
									});	
						}
					}
				});
		// saveButton.setIconAfterText(true);
		saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		//saveButton.addClickShortcut(Key.ENTER);

		//layout.getStyle().set("background-color", "#EFF2FB");

		HorizontalLayout layoutSave = new HorizontalLayout();
		layoutSave.setSizeFull();
		//layoutSave.setPadding(true);
		layoutSave.setMargin(true);
		layoutSave.add(cancelButton, saveButton);

		layoutSave.setJustifyContentMode(JustifyContentMode.END);


		return layoutSave;
	}
    
    private boolean controlarTelefono2() {
    	boolean salida = false;
    	if (telefono2.isEmpty() && opcion_telefono2.isEmpty()) {
			salida = true;
		}else if (!telefono2.isEmpty() && !opcion_telefono2.isEmpty()) {
			salida = true;
		}else {
			UIUtils.createNotificationError("Si ingresó un teléfono secundario debe seleccionar la opción del teléfono secundario y viceversa!.");
		}
    	return salida;
    }
    
    public void guardar(ConfirmationDialog confirmationDialog) {
		UsuarioInfo usuarioInfoBean = binder.getBean();
		usuarioInfoBean.setUsuario(usuario);
		usuarioInfoBean.setIdusuario_info(usuario.getIdusuario());
		if (!telefono2.isEmpty() && !opcion_telefono2.isEmpty()) {
			usuarioInfoBean.setTelefono2(telefono2.getValue());
			usuarioInfoBean.setOpcion_telefono2(opcion_telefono2.getValue());
		}
		
		usuarioInfoService.addUsuarioInfo(usuarioInfoBean);
		confirmationDialog.getUI().ifPresent(ui ->
        ui.navigate(""));

		UIUtils.createNotificationError("Se ha registrado la información del Usuario!.");
		confirmationDialog.close();
	}

}