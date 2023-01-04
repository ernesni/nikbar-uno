package com.ean.promo.ui.dialogs;

import com.ean.promo.backend.entity.Usuario;
import com.ean.promo.backend.service.impl.MiUsuarioServiceImpl;
import com.ean.promo.ui.util.UIUtils;
import com.ean.promo.ui.util.css.LumoStyles;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.data.binder.BeanValidationBinder;

//@CssImport(value = "./styles/components/dialog.css", themeFor = "vaadin-*-overlay", include = "")
public class RestartPassDialog extends Dialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 66835524649468708L;

	private MiUsuarioServiceImpl usuarioService;

	private Button cancelButton, saveButton;
	
	private Usuario usuario;
	
	private BeanValidationBinder<Usuario> binderNewPass, binderConfirmPass;
	
	private ConfirmationDialog confirmationDialog = null;
	
	private PasswordField passwordFieldOldPass, passwordFieldNewPass, passwordFieldConfirmPass;
	
	private boolean isRestartAutomatic;

	public RestartPassDialog(Usuario usuario, MiUsuarioServiceImpl usuarioService, boolean isRestartAutomatic) {
		getElement().setAttribute("theme", "custom-dialog");
		this.usuario = usuario;
		this.usuarioService = usuarioService;
		this.isRestartAutomatic = isRestartAutomatic;
		setCloseOnEsc(false);
		setCloseOnOutsideClick(false);

		//createHeader();
		createContent();
		createFooter();
		
		setHeaderTitle("Cambiar contraseña");
		
		open();
	}

	private void createContent() {
		FormLayout form = new FormLayout();
		form.addClassNames(LumoStyles.Padding.Bottom.XL, LumoStyles.Padding.Left.XL, LumoStyles.Padding.Right.XL);
		form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
				new FormLayout.ResponsiveStep("600px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP));
		
		form.setMaxWidth("400px");
		
		passwordFieldOldPass = new PasswordField("Contraseña");
		passwordFieldOldPass.setPlaceholder("Ingrese su contraseña actual");
		passwordFieldOldPass.setClearButtonVisible(true);
		passwordFieldOldPass.setRequired(true);
		passwordFieldOldPass.setWidthFull();
		
		passwordFieldOldPass.setValue("");
		
		passwordFieldNewPass = new PasswordField("Contraseña nueva");
		passwordFieldNewPass.setPlaceholder("Ingrese su nueva contraseña");
		passwordFieldNewPass.setClearButtonVisible(true);
		passwordFieldNewPass.setRequired(true);
		passwordFieldNewPass.setWidthFull();
		
		binderNewPass = new BeanValidationBinder<>(Usuario.class);
		binderNewPass.setBean(new Usuario());
		binderNewPass.bind(passwordFieldNewPass, "password");
		
		passwordFieldConfirmPass = new PasswordField("Confirmar contraseña");
		passwordFieldConfirmPass.setPlaceholder("Confirme su nueva contraseña");
		passwordFieldConfirmPass.setClearButtonVisible(true);
		passwordFieldConfirmPass.setRequired(true);
		passwordFieldConfirmPass.setWidthFull();
		
		binderConfirmPass = new BeanValidationBinder<>(Usuario.class);
		binderConfirmPass.setBean(new Usuario());
		binderConfirmPass.bind(passwordFieldConfirmPass, "password");
		
		form.add(passwordFieldOldPass, passwordFieldNewPass, passwordFieldConfirmPass);
		form.setColspan(passwordFieldOldPass, 2);
		form.setColspan(passwordFieldNewPass, 2);
		form.setColspan(passwordFieldConfirmPass, 2);

		add(form);
	}

	private void createFooter() {
		cancelButton = new Button("Cancelar", new Icon(VaadinIcon.CLOSE_CIRCLE), event -> {
			if (isRestartAutomatic) {
				UI.getCurrent().getPage().executeJs("location.assign('logout')");
			}else {
				close();
			}
			
		});
		cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

		saveButton = new Button("Guardar", new Icon(VaadinIcon.CHECK), event -> {
			if (controlar()) {
				confirmationDialog = new ConfirmationDialog("Confirmar operación",
						"Está seguro que desea guardar las modificaciones realizadas?", ev -> {
							guardar(confirmationDialog);
						},
						action -> {
							saveButton.setEnabled(true);
							confirmationDialog.close();
						});
			}
		});
		saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		saveButton.addClickShortcut(Key.ENTER);

		getFooter().add(cancelButton);
		getFooter().add(saveButton);
	}
	
	private boolean controlar() {
		boolean salida = false;
		
		if (UIUtils.isMatchedPass(passwordFieldOldPass.getValue(), usuario.getPassword())) {
			if (binderNewPass.validate().isOk()) {
				if (binderConfirmPass.validate().isOk()) {
					if (passwordFieldNewPass.getValue().equals(passwordFieldConfirmPass.getValue())) {
						if (passwordFieldOldPass.getValue().equals(passwordFieldNewPass.getValue())) {
							UIUtils.createNotificationError("La nueva contraseña no puede ser igual a la contraseña actual");
						}else {
							salida = controlarRepetido();
						}
					}else {
						UIUtils.createNotificationError("Su nueva contraseña no coincide con la confirmación de la misma");
					}
				}else {
					UIUtils.createNotificationError("La confirmación de su contraseña nueva no tiene el formato requerido");
				}
			}else {
				UIUtils.createNotificationError("Su contraseña nueva no tiene el formato requerido");
			}
		}else {
			UIUtils.createNotificationError("Su contraseña actual no coincide");
		}
		
		return salida;
	}
	
	public void guardar(ConfirmationDialog confirmationDialog) {
		
		usuario.setPassword(UIUtils.generatePass(passwordFieldNewPass.getValue()));
		usuario.setContrasena(passwordFieldNewPass.getValue());

		usuarioService.refreshPass(usuario);
		
		UIUtils.showNotification("Modificaciones confirmadas!.", Notification.Position.BOTTOM_END);
		confirmationDialog.close();
		close();
	}
	
	private boolean controlarRepetido() {
		boolean salida = false;
		Usuario usuarioControl = usuarioService.findExistUsuarioChangePass(usuario.getIdusuario(), passwordFieldNewPass.getValue());
		if (usuarioControl == null) {
			salida = true;
		}else {
			UIUtils.createNotificationError("Ya existe un usuario registrado con esta contraseña!");
			passwordFieldNewPass.focus();
		}
		return salida;
	}
	
}
