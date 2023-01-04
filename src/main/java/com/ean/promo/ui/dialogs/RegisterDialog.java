package com.ean.promo.ui.dialogs;

import com.ean.promo.backend.entity.Usuario;
import com.ean.promo.backend.entity.UsuarioRole;
import com.ean.promo.backend.service.impl.MiUsuarioServiceImpl;
import com.ean.promo.ui.util.UIUtils;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class RegisterDialog extends Dialog{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 410498140151991109L;

	private MiUsuarioServiceImpl miUsuarioService;
	
	private Button cancelBtn, saveBtn;
	
	private BeanValidationBinder<Usuario> binder;
	private ConfirmationDialog confirmationDialog = null;
	private PasswordField passwordField, confirmPasswordField;
	private IntegerField ciField;
	private TextField usuarioField;
	
	public RegisterDialog(MiUsuarioServiceImpl miUsuarioService) {
		this.miUsuarioService = miUsuarioService;
		getElement().setAttribute("theme", "custom-dialog");

		setCloseOnEsc(false);
		setCloseOnOutsideClick(false);

		createContent();
		createFooter();
		
		setHeaderTitle("Registro de Usuario");
		
		open();
	}

	private void createContent() {
		Usuario usuario = new Usuario();
		ciField = new IntegerField();
    	ciField.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);
    	ciField.setAutoselect(true);
    	ciField.setLabel("C.I. N°");
        
        usuarioField = new TextField("Usuario");
        passwordField = new PasswordField("Contraseña");
        confirmPasswordField = new PasswordField("Confirmar contraseña");
        
        binder = new BeanValidationBinder<>(Usuario.class);
		binder.setBean(usuario);

		binder.bind(ciField, "idusuario");
		binder.bind(usuarioField, "username");
		binder.bind(passwordField, "contrasena");
      
    	FormLayout form = new FormLayout();
		form.addClassNames(LumoUtility.Padding.Bottom.LARGE, LumoUtility.Padding.Horizontal.LARGE, LumoUtility.Padding.Top.SMALL);
		form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
				new FormLayout.ResponsiveStep("600px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP));
        
		form.add(ciField, usuarioField , passwordField, confirmPasswordField);
		//form.setColspan(ciudad, 2);

        add(form);
	}

	private void createFooter() {
		cancelBtn = new Button("Cancelar", new Icon(VaadinIcon.CLOSE_CIRCLE), event -> {
			close();
		});
		cancelBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

		saveBtn = new Button("GUARDAR", event -> {
			if (binder.validate().isOk()) {
				if (passwordField.getValue().equals(confirmPasswordField.getValue())) {
					confirmationDialog = new ConfirmationDialog("Confirmar operación",
							"Está seguro que desea registrarse con estos datos?", accion -> {
								guardar(confirmationDialog);
							},
							action -> {
								saveBtn.setEnabled(true);
								confirmationDialog.close();
							});
				}else {
					UIUtils.createNotificationError("Las contraseñas no son iguales!");
					saveBtn.setEnabled(true);
				}
			}else {
				saveBtn.setEnabled(true);
			}
		});
		// saveButton.setIconAfterText(true);
		saveBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		saveBtn.setDisableOnClick(true);
		
		getFooter().add(cancelBtn);
		getFooter().add(saveBtn);
		
	}
	
	public void guardar(ConfirmationDialog confirmationDialog) {
		Usuario usuarioBean = binder.getBean();
		usuarioBean.setPassword(UIUtils.generatePass(passwordField.getValue()));
		usuarioBean.setEnabled(true);
		Usuario usuarioControl = controlar(usuarioBean);
		if(usuarioControl == null) {
			
			miUsuarioService.addUsuario(usuarioBean);

			UIUtils.createNotificationSuccess("Se han registrado sus credenciales!.");
			this.close();
		}else {
			if (usuarioControl.getIdusuario() == usuarioBean.getIdusuario()) {
				UIUtils.createNotificationError("Ya existe un usuario registrado con este N° de C.I.!");
				ciField.focus();
			}else if (usuarioControl.getUsername().equals(usuarioBean.getUsername())) {
				UIUtils.createNotificationError("Ya existe un usuario registrado con este nombre de usuario!");
				usuarioField.focus();
			}else if (usuarioControl.getContrasena().equals(usuarioBean.getContrasena())) {
				UIUtils.createNotificationError("Ya existe un usuario registrado con esta contraseña!");
				passwordField.focus();
			}
			saveBtn.setEnabled(true);
			
		}
		confirmationDialog.close();
	}
	
	private Usuario controlar(Usuario user) {
		
		return miUsuarioService.findExistUsuario(user.getIdusuario(), user.getUsername(), user.getContrasena());
	}

}
