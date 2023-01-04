package com.ean.promo.views.home;

import java.sql.Date;

import javax.annotation.security.PermitAll;

import org.springframework.beans.factory.annotation.Autowired;

import com.ean.promo.backend.repository.UsuarioRepository;
import com.ean.promo.backend.service.impl.MiUsuarioServiceImpl;
import com.ean.promo.ui.components.FlexBoxLayout;
import com.ean.promo.ui.components.ListItem;
import com.ean.promo.ui.layout.size.Horizontal;
import com.ean.promo.ui.layout.size.Uniform;
import com.ean.promo.ui.layout.size.Vertical;
import com.ean.promo.ui.util.Constante;
import com.ean.promo.ui.util.css.FlexDirection;
import com.ean.promo.views.MainView;
import com.ean.promo.views.ViewFrame;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle(Constante.TITLE_HOME)
@Route(value = "", layout = MainView.class)
@PermitAll
public class Home extends ViewFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8771089316183293363L;

	public Home(@Autowired UsuarioRepository usuarioRepository, @Autowired MiUsuarioServiceImpl usuarioService) {
		setId("home");
		setViewContent(createContent());
		
		/*final String username = SecurityUtils.getUsername();
		Usuario usuario = username != null ? usuarioRepository.findByUsernameIgnoreCase(username) : null;
		
		if (usuario.isRestartPass()) {
			//Tiene que restaurar su pass
			new RestartPassDialog(usuario, usuarioService, true);
		}else {
			int diferencia = diferenciasDeFechas(usuario.getLast_change_password(), UIUtils.getDate());
			if (diferencia > 90) {
				//Tiene que restaurar su pass
				new RestartPassDialog(usuario, usuarioService, true);
			}
		}*/
	}

	public static int diferenciasDeFechas(Date fechaInicial, Date fechaFinal) {
		long fechaInicialMs = fechaInicial.getTime();
		long fechaFinalMs = fechaFinal.getTime();
		long diferencia = fechaFinalMs - fechaInicialMs;
		double dias = Math.floor(diferencia / (1000 * 60 * 60 * 24));
		return ((int) dias);
	}
	
	private Component createContent() {
		
		H3 titulo = new H3("Detalles de uso de la aplicación");
		VerticalLayout vl = new VerticalLayout(titulo);
		
		createFila("Registre sus datos haciendo click en el menú Usuario Info.", vl);
		createFila("Crée sus grupos haciendo click en el menú Grupo, luego en el sub menú Propios y después haciendo click "
				+ "en el botón Crear un grupo.", vl);
		createFila("Una vez creado un Grupo no se va a poder eliminar o modificar alguno de los integrantes.", vl);
		createFila("Registre sus comprobantes haciendo click en el menú Grupo, luego en el sub menú Propios y después "
				+ "haciendo click sobre el grupo al cual le desea asignar un comprobante.", vl);
		createFila("Vea los grupos de terceros de los que forma parte haciendo click en el menú Grupo, luego en el sub "
				+ "menú De terceros.", vl);
		createFila("Registre sus comprobantes en los grupos de terceros de los que forma parte haciendo click en el menú "
				+ "Grupo, luego en el sub menú De terceros y después haciendo click sobre el grupo al cual le desea "
				+ "asignar un comprobante.", vl);
		createFila("Vea el estado de sus comprobantes haciendo click en el menú Comprobantes.", vl);
		
		/*Html intro = new Html("<p>Esta es una plicación receptiva. " +
		" <b>Maneja los satélites del Super Más</b>.</p>");*/


		FlexBoxLayout content = new FlexBoxLayout(vl);
		content.setFlexDirection(FlexDirection.COLUMN.getValue());
		content.setMargin(Horizontal.AUTO);
		content.setMaxWidth("840px");
		content.setPadding(Uniform.RESPONSIVE_L);
		return content;
	}
	
	private void createFila(String str, VerticalLayout vl) {
		Icon i = new Icon(VaadinIcon.ANGLE_DOUBLE_RIGHT);
		ListItem item = new ListItem(i, str);
		item.setPadding(Vertical.XS);
		vl.add(item);
	}

}

