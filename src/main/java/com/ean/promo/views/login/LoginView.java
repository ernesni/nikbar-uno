package com.ean.promo.views.login;

import org.springframework.beans.factory.annotation.Autowired;

import com.ean.promo.backend.service.impl.MiUsuarioServiceImpl;
import com.ean.promo.security.AuthenticatedUser;
import com.ean.promo.ui.dialogs.RegisterDialog;
import com.ean.promo.ui.util.Constante;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle(Constante.TITLE_LOGIN)
@Route(value = Constante.PAGE_LOGIN)
@CssImport(value="./themes/nikbar/views/login-view.css", themeFor="vaadin-login-overlay-wrapper")
public class LoginView extends LoginOverlay implements BeforeEnterObserver {

	private static final long serialVersionUID = 2123138672270010877L;
	private final AuthenticatedUser authenticatedUser;
	private MiUsuarioServiceImpl miUsuarioService;
    
    public LoginView(AuthenticatedUser authenticatedUser, @Autowired MiUsuarioServiceImpl miUsuarioService) {
        this.authenticatedUser = authenticatedUser;
        this.miUsuarioService = miUsuarioService;
        addClassName("login-view");
		setI18n(createEspanishI18n());
		setAction("login");
	}
	
	private LoginI18n createEspanishI18n() {
        final LoginI18n i18n = LoginI18n.createDefault();
        
    	/*H1 title = new H1();
        title.getStyle().set("color", "var(--lumo-base-color)");
        Icon icon = VaadinIcon.VAADIN_H.create();
        icon.setSize("30px");
        icon.getStyle().set("top", "-4px");
        title.add(icon);
        title.add(new Text("  BASE"));
        setTitle(title);
      	setDescription("Sistema Base");*/
      	//setForgotPasswordButtonVisible(false);
        setTitle("");
        setDescription("");
        
        i18n.getForm().setUsername("Usuario");
        i18n.getForm().setTitle("Ingrese su cuenta");
        i18n.getForm().setSubmit("Entrar");
        i18n.getForm().setPassword("Contraseña");
        i18n.getForm().setForgotPassword("Registrarse");
        
        i18n.getErrorMessage().setTitle("Usuario/contraseña inválidos");
        i18n.getErrorMessage()
            .setMessage("Confirme su usuario y contraseña e intente nuevamente.");
        
        addForgotPasswordListener(event -> {
        	new RegisterDialog(miUsuarioService);
		});
        
        
        return i18n;
    }

	/*@Override
	public void afterNavigation(AfterNavigationEvent event) {
		setError(
			event.getLocation().getQueryParameters().getParameters().containsKey(
				"error"));
	}*/

    

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (authenticatedUser.get().isPresent()) {
            // Already logged in
            setOpened(false);
            event.forwardTo("");
        }else {
        	setOpened(true);
        	// Prevent the example from stealing focus when browsing the documentation
            getElement().setAttribute("no-autofocus", "");
        }

        setError(event.getLocation().getQueryParameters().getParameters().containsKey("error"));
    }
}
