package com.ean.promo.views.comprobantes;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;

import com.ean.promo.backend.entity.Usuario;
import com.ean.promo.backend.model.ComprobanteModel;
import com.ean.promo.backend.service.impl.ComprobanteBeneficiarioServiceImpl;
import com.ean.promo.backend.service.impl.ComprobanteUsuarioServiceImpl;
import com.ean.promo.backend.service.impl.GrupoServiceImpl;
import com.ean.promo.backend.service.impl.MiUsuarioServiceImpl;
import com.ean.promo.backend.service.impl.UsuarioInfoServiceImpl;
import com.ean.promo.security.SecurityUtils;
import com.ean.promo.ui.components.FlexBoxLayout;
import com.ean.promo.ui.dialogs.ComprobanteSelectDialog;
import com.ean.promo.ui.layout.size.Horizontal;
import com.ean.promo.ui.layout.size.Top;
import com.ean.promo.ui.util.Constante;
import com.ean.promo.ui.util.UIUtils;
import com.ean.promo.ui.util.css.BoxSizing;
import com.ean.promo.ui.util.css.FlexDirection;
import com.ean.promo.views.MainView;
import com.ean.promo.views.ViewFrame;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle(Constante.TITLE_COMPROBANTE)
@Route(value = Constante.PAGE_COMPROBANTE, layout = MainView.class)
@RolesAllowed("USER")
@JsModule("@vaadin/vaadin-lumo-styles/badge.js")
public class ComprobanteView extends ViewFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 541387084961559016L;
	private GrupoServiceImpl grupoService;
	private UsuarioInfoServiceImpl usuarioInfoService;
	private ComprobanteUsuarioServiceImpl comprobanteUsuarioService;
    private ComprobanteBeneficiarioServiceImpl comprobanteBeneficiarioService;
	private Usuario usuario;

	private Grid<ComprobanteModel> grid;
	private List<ComprobanteModel> comprobantes;
    
    private FlexBoxLayout content;

	public ComprobanteView(@Autowired GrupoServiceImpl grupoService, @Autowired UsuarioInfoServiceImpl usuarioInfoService,  
			@Autowired ComprobanteUsuarioServiceImpl comprobanteUsuarioService, @Autowired ComprobanteBeneficiarioServiceImpl comprobanteBeneficiarioService,
			@Autowired MiUsuarioServiceImpl usuarioService) {
		
		this.grupoService = grupoService;
		this.usuarioInfoService = usuarioInfoService;
		this.comprobanteUsuarioService = comprobanteUsuarioService;
		this.comprobanteBeneficiarioService = comprobanteBeneficiarioService;
		
		final String username = SecurityUtils.getUsername();
		usuario = username != null ? usuarioService.findByUsernameIgnoreCase(username) : null;
		
		comprobantes = convertObjectToComprobanteModel(comprobanteUsuarioService.findByIdUsuario(usuario.getIdusuario()));
		
		setViewContent(createContent());
		
	}
	
	private Component createContent() {
		content = new FlexBoxLayout(createNew(), createGrid());
		content.setBoxSizing(BoxSizing.BORDER_BOX.getValue());
		content.setPadding(Horizontal.RESPONSIVE_X, Top.RESPONSIVE_X);
		content.setFlexDirection(FlexDirection.COLUMN.getValue());
		return content;
	}
	
	private Grid<ComprobanteModel> createGrid() {
		grid = new Grid<>();
		grid.setId("card-list-view");
		grid.addClassName("card-list-view");
		
		grid.setItems(comprobantes);
		
        grid.setHeight("100%");
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS);
        grid.addComponentColumn(grupo -> createCard(grupo));
        
        grid.setAllRowsVisible(true);

        return grid;
	}
	
	private Component createNew() {
		Button cuponBtn = UIUtils.createButton("Cargar comprobante", VaadinIcon.PLUS_CIRCLE, ButtonVariant.LUMO_PRIMARY);
		cuponBtn.addClickListener(clickEvent -> {
			if (usuarioInfoService.findByIdUsuario(usuario.getIdusuario()) == null) {
	    		UIUtils.createNotificationError("Primero debe completar sus datos antes de poder cargar un comprobante!.");
			}else {
				if (grupoService.findByIdUsuario(usuario.getIdusuario()).isEmpty()) {
					UIUtils.createNotificationError("Primero debe crear su propio grupo!.");
				}else {
					new ComprobanteSelectDialog();
					//Grupo grupo = new Grupo();
					//new ComprobanteUsuarioDialog(comprobanteUsuarioService, usuario, grupo, comprobanteBeneficiarioService);
					//new ComprobanteBeneficiarioDialog(comprobanteBeneficiarioService, beneficiarioService, usuario, grupo, comprobanteUsuarioService);	
				}
			}
		});
		
		
        return cuponBtn;

	}
	
	private HorizontalLayout createCard(ComprobanteModel comprobanteModel) {
        HorizontalLayout card = new HorizontalLayout();
        card.addClassName("card");
        card.setSpacing(false);
        card.getThemeList().add("spacing-s");
        
        Image foto = UIUtils.getFoto(2);
        
        
        VerticalLayout description = new VerticalLayout();
        description.addClassName("description");
        description.setSpacing(false);
        description.setPadding(false);

        Span grupoSpan = new Span("Grupo: " + comprobanteModel.getNombre());
        grupoSpan.addClassName("repositora");
        
        Span comprobaneSpan = new Span("Comprobante N°: " + comprobanteModel.getNumero_comprobante());
        comprobaneSpan.addClassName("integrante");
        
        HorizontalLayout header = new HorizontalLayout();
        header.addClassName("header");
        header.setSpacing(false);
        header.getThemeList().add("spacing-s");

        //header.add(comprobaneSpan, grupoSpan);

        Span mensajeSpan = new Span(
        		"Control: " + comprobanteModel.getMensaje());
       
        mensajeSpan.addClassName("contenido");
        
        if (!comprobanteModel.isChequeado()) {
        	description.add(comprobaneSpan, grupoSpan, mensajeSpan);
		}else if (comprobanteModel.isAprobado()) {
			description.add(comprobaneSpan, grupoSpan, createControl(mensajeSpan, comprobanteModel.getCupon()));
		}else {
			description.add(comprobaneSpan, grupoSpan, mensajeSpan);
		}
        
        HorizontalLayout hlCupon = new HorizontalLayout();

        Icon CuponIcon = null;
        
        if (!comprobanteModel.isChequeado()) {
        	CuponIcon = VaadinIcon.QUESTION_CIRCLE.create();
        	CuponIcon.setColor("#85C1E9");
		}else if (comprobanteModel.isAprobado()) {
			CuponIcon = VaadinIcon.CHECK_CIRCLE.create();
        	CuponIcon.setColor("#82E0AA");
		}else {
			CuponIcon = VaadinIcon.CLOSE_CIRCLE.create();
        	CuponIcon.setColor("#F1948A");
		}
        CuponIcon.setSize("32px");
        CuponIcon.addClassName("quiebre");
        
        hlCupon.add(CuponIcon);
        hlCupon.setAlignItems(Alignment.START);
        card.add(foto, description, CuponIcon, hlCupon);
        return card;
    }
	
	private List<ComprobanteModel> convertObjectToComprobanteModel(List<Object[]> lista) {
		List<ComprobanteModel> tabla = new ArrayList<ComprobanteModel>();
		lista.stream().forEach(
				e -> {
					ComprobanteModel cm = new ComprobanteModel(e[0].toString(), e[1].toString(), e[2].toString(), 
							(int)e[3], (boolean)e[4], (boolean)e[5]);
					tabla.add(cm);
				}
			);
		
		return tabla;
	}
	
	private Component createControl(Span span, int cupones) {

        HorizontalLayout hl = new HorizontalLayout(span, UIUtils.createCupon3(cupones));

		return hl;
	}

}