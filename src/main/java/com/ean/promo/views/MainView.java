package com.ean.promo.views;

import java.util.Arrays;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ean.promo.backend.entity.Usuario;
import com.ean.promo.backend.service.impl.MiUsuarioServiceImpl;
import com.ean.promo.security.AuthenticatedUser;
import com.ean.promo.ui.components.FlexBoxLayout;
import com.ean.promo.ui.components.navigation.bar.AppBar;
import com.ean.promo.ui.components.navigation.bar.TabBar;
import com.ean.promo.ui.components.navigation.drawer.NaviDrawer;
import com.ean.promo.ui.components.navigation.drawer.NaviItem;
import com.ean.promo.ui.components.navigation.drawer.NaviMenu;
import com.ean.promo.ui.util.UIUtils;
import com.ean.promo.ui.util.css.Display;
import com.ean.promo.ui.util.css.Overflow;
import com.ean.promo.views.comprobantes.ComprobanteView;
import com.ean.promo.views.control.ControlBeneficiarioView;
import com.ean.promo.views.control.ControlUsuarioView;
import com.ean.promo.views.grupo.MisGruposView;
import com.ean.promo.views.grupo.TercerosGruposView;
import com.ean.promo.views.home.Home;
import com.ean.promo.views.userinfo.UsuarioInfoView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.ErrorHandler;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import com.vaadin.flow.theme.lumo.Lumo;

//@CssImport(value="./themes/nikbar/components/grid.css", themeFor = "vaadin-grid")
public class MainView extends FlexBoxLayout implements RouterLayout, AfterNavigationObserver {
	
	private static final long serialVersionUID = 6200179564842965579L;
	private AuthenticatedUser authenticatedUser;
    private AccessAnnotationChecker accessChecker;

	private MiUsuarioServiceImpl usuarioService;
	private static final Logger log = LoggerFactory.getLogger(MainView.class);
	private static final String CLASS_NAME = "root";

	//private Div appHeaderOuter;

	private FlexBoxLayout row;
	private NaviDrawer naviDrawer;
	private FlexBoxLayout column;

	private Div appHeaderInner;
	private Main viewContainer;
	//private Div appFooterInner;

	//private Div appFooterOuter;

	private TabBar tabBar;
	private boolean navigationTabs = false;
	private AppBar appBar;

	public MainView(@Autowired MiUsuarioServiceImpl usuarioService, 
			AuthenticatedUser authenticatedUser, AccessAnnotationChecker accessChecker) {
		VaadinSession.getCurrent().setErrorHandler((ErrorHandler) errorEvent -> {
			log.error("Uncaught UI exception", errorEvent.getThrowable());
			Notification.show("We are sorry, but an internal error occurred");
		});
		
		this.usuarioService = usuarioService;
		
		this.authenticatedUser = authenticatedUser;
        this.accessChecker = accessChecker;

		addClassName(CLASS_NAME);
		setFlexDirection(com.ean.promo.ui.util.css.FlexDirection.COLUMN.getValue());
		setSizeFull();

		// Initialise the UI building blocks
		initStructure();

		// Populate the navigation drawer
		initNaviItems();

		// Configure the headers and footers (optional)
		initHeadersAndFooters();
		
	}
	
	/**
	 * Initialise the required components and containers.
	 */
	private void initStructure() {
		naviDrawer = new NaviDrawer();

		viewContainer = new Main();
		viewContainer.addClassName(CLASS_NAME + "__view-container");
		UIUtils.setDisplay(Display.FLEX.getValue(), viewContainer);
		UIUtils.setFlexGrow(1, viewContainer);
		UIUtils.setOverflow(Overflow.HIDDEN.getValue(), viewContainer);

		column = new FlexBoxLayout(viewContainer);
		column.addClassName(CLASS_NAME + "__column");		
		column.setFlexDirection(com.ean.promo.ui.util.css.FlexDirection.COLUMN.getValue());
		column.setFlexGrow(1, viewContainer);
		column.setOverflow(Overflow.HIDDEN.getValue());

		row = new FlexBoxLayout(naviDrawer, column);
		row.addClassName(CLASS_NAME + "__row");
		row.setFlexGrow(1, column);
		row.setOverflow(Overflow.HIDDEN.getValue());
		add(row);
		setFlexGrow(1, row);

	}

	/**
	 * Initialise the navigation items.
	 */
	private void initNaviItems() {
		NaviMenu menu = naviDrawer.getMenu();
		menu.addNaviItem(VaadinIcon.HOME, "Inicio", Home.class);
		
		if (accessChecker.hasAccess(UsuarioInfoView.class)) {
			menu.addNaviItem(VaadinIcon.USER_CARD, "Usuario Info", UsuarioInfoView.class);
        }
		
		if (accessChecker.hasAccess(MisGruposView.class)) {
			// GRUPO
			NaviItem item_grupo = menu.addNaviItem(VaadinIcon.USERS, "Grupos", null);
			
			if (accessChecker.hasAccess(MisGruposView.class)) {
				menu.addNaviItem(item_grupo, "Propios", MisGruposView.class);
			}

			if (accessChecker.hasAccess(TercerosGruposView.class)) {
				menu.addNaviItem(item_grupo, "De terceros", TercerosGruposView.class);
			}
			menu.closeSub(item_grupo);
		}
		
		if (accessChecker.hasAccess(ComprobanteView.class)) {
            menu.addNaviItem(VaadinIcon.INVOICE, "Comprobantes", ComprobanteView.class);
        }
		
		if (accessChecker.hasAccess(ControlUsuarioView.class)) {
			menu.addNaviItem(VaadinIcon.COG_O, "Control usuario", ControlUsuarioView.class);
        }
		
		if (accessChecker.hasAccess(ControlBeneficiarioView.class)) {
			menu.addNaviItem(VaadinIcon.COGS, "Control beneficiario", ControlBeneficiarioView.class);
        }
				
	}

	/**
	 * Configure the app's inner and outer headers and footers.
	 */
	private void initHeadersAndFooters() {
		
		Optional<Usuario> maybeUser = authenticatedUser.get();
        if (maybeUser.isPresent()) {
            Usuario usuario = maybeUser.get();

    		appBar = new AppBar(authenticatedUser, "", usuario, usuarioService);


            /*Avatar avatar = new Avatar(user.getNombre(), null);//user.getProfilePictureUrl());
            avatar.addClassNames("me-xs");

            ContextMenu userMenu = new ContextMenu(avatar);
            userMenu.setOpenOnClick(true);
            userMenu.addItem("Logout", e -> {
                authenticatedUser.logout();
            });

            Span name = new Span(user.getNombre());
            name.addClassNames("font-medium", "text-s", "text-secondary");

            layout.add(avatar, name);*/
        } else {
            /*Anchor loginLink = new Anchor("login", "Sign in");
            layout.add(loginLink);*/
        }

		/*final String username = SecurityUtils.getUsername();
		Usuario usuario = username != null ? usuarioRepository.findByUsernameIgnoreCase(username) : null;
		appBar = new AppBar("", usuario, usuarioService);*/

		// Tabbed navigation
		if (navigationTabs) {
			tabBar = new TabBar();
			UIUtils.setTheme(Lumo.DARK, tabBar);

			// Shift-click to add a new tab
			for (NaviItem item : naviDrawer.getMenu().getNaviItems()) {
				item.addClickListener(e -> {
					if (e.getButton() == 0 && e.isShiftKey()) {
						tabBar.setSelectedTab(tabBar.addClosableTab(item.getText(), item.getNavigationTarget()));
					}
				});
			}
			appBar.getAvatar().setVisible(false);
			setAppHeaderInner(tabBar, appBar);

			// Default navigation
		} else {
			UIUtils.setTheme(Lumo.DARK, appBar);
			setAppHeaderInner(appBar);
			//setAppFooterInner(new Label("Hola"));
		}
	}

	/*private void setAppHeaderOuter(Component... components) {
		if (appHeaderOuter == null) {
			appHeaderOuter = new Div();
			appHeaderOuter.addClassName("app-header-outer");
			getElement().insertChild(0, appHeaderOuter.getElement());
		}
		appHeaderOuter.removeAll();
		appHeaderOuter.add(components);
	}*/

	private void setAppHeaderInner(Component... components) {
		if (appHeaderInner == null) {
			appHeaderInner = new Div();
			appHeaderInner.addClassName("app-header-inner");
			column.getElement().insertChild(0, appHeaderInner.getElement());
		}
		appHeaderInner.removeAll();
		appHeaderInner.add(components);
	}

	/*private void setAppFooterInner(Component... components) {
		if (appFooterInner == null) {
			appFooterInner = new Div();
			appFooterInner.addClassName("app-footer-inner");
			column.getElement().insertChild(column.getElement().getChildCount(), appFooterInner.getElement());
		}
		appFooterInner.removeAll();
		appFooterInner.add(components);
	}

	private void setAppFooterOuter(Component... components) {
		if (appFooterOuter == null) {
			appFooterOuter = new Div();
			appFooterOuter.addClassName("app-footer-outer");
			getElement().insertChild(getElement().getChildCount(), appFooterOuter.getElement());
		}
		appFooterOuter.removeAll();
		appFooterOuter.add(components);
	}*/

	@Override
	public void showRouterLayoutContent(HasElement content) {
		this.viewContainer.getElement().appendChild(content.getElement());
	}

	public NaviDrawer getNaviDrawer() {
		return naviDrawer;
	}

	public static MainView get() {
		return (MainView) UI.getCurrent().getChildren().filter(component -> component.getClass() == MainView.class)
				.findFirst().get();
	}

	public AppBar getAppBar() {
		return appBar;
	}

	@Override
	public void afterNavigation(AfterNavigationEvent event) {
		if (navigationTabs) {
			afterNavigationWithTabs(event);
		} else {
			afterNavigationWithoutTabs(event);
		}
	}

	private void afterNavigationWithTabs(AfterNavigationEvent e) {
		NaviItem active = getActiveItem(e);
		if (active == null) {
			if (tabBar.getTabCount() == 0) {
				tabBar.addClosableTab("", Home.class);
			}
		} else {
			if (tabBar.getTabCount() > 0) {
				tabBar.updateSelectedTab(active.getText(), active.getNavigationTarget());
			} else {
				tabBar.addClosableTab(active.getText(), active.getNavigationTarget());
			}
		}
		appBar.getMenuIcon().setVisible(false);
	}

	private NaviItem getActiveItem(AfterNavigationEvent e) {
		for (NaviItem item : naviDrawer.getMenu().getNaviItems()) {
			if (item.isHighlighted(e)) {
				return item;
			}
		}
		return null;
	}

	private void afterNavigationWithoutTabs(AfterNavigationEvent e) {
		NaviItem active = getActiveItem(e);
		if (active != null) {
			getAppBar().setTitle(active.getText());
		}
	}

}