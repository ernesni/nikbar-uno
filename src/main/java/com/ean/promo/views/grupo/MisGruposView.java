package com.ean.promo.views.grupo;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;

import com.ean.promo.backend.entity.Beneficiario;
import com.ean.promo.backend.entity.ComprobanteBeneficiario;
import com.ean.promo.backend.entity.ComprobanteUsuario;
import com.ean.promo.backend.entity.Grupo;
import com.ean.promo.backend.entity.Usuario;
import com.ean.promo.backend.service.impl.ComprobanteBeneficiarioServiceImpl;
import com.ean.promo.backend.service.impl.ComprobanteUsuarioServiceImpl;
import com.ean.promo.backend.service.impl.GrupoServiceImpl;
import com.ean.promo.backend.service.impl.MiUsuarioServiceImpl;
import com.ean.promo.backend.service.impl.UsuarioInfoServiceImpl;
import com.ean.promo.security.SecurityUtils;
import com.ean.promo.ui.components.FlexBoxLayout;
import com.ean.promo.ui.dialogs.ComprobanteUsuarioDialog;
import com.ean.promo.ui.dialogs.GrupoDialog;
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
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

@PageTitle(Constante.TITLE_MIS_GRUPOS)
@Route(value = Constante.PAGE_MIS_GRUPOS, layout = MainView.class)
@RolesAllowed("USER")
@JsModule("@vaadin/vaadin-lumo-styles/badge.js")
public class MisGruposView extends ViewFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -706893504131835237L;
	private GrupoServiceImpl grupoService;
	private UsuarioInfoServiceImpl usuarioInfoService;
    private Grid<Grupo> grid = new Grid<>();
    private List<Grupo> grupos;
    private Usuario usuario;

    private ComprobanteUsuarioServiceImpl comprobanteUsuarioService;
    private ComprobanteBeneficiarioServiceImpl comprobanteBeneficiarioService;
    
    private FlexBoxLayout content;

	public MisGruposView(@Autowired GrupoServiceImpl grupoService, @Autowired UsuarioInfoServiceImpl usuarioInfoService, 
			@Autowired MiUsuarioServiceImpl miUsuarioService, @Autowired ComprobanteUsuarioServiceImpl comprobanteUsuarioService, 
			@Autowired ComprobanteBeneficiarioServiceImpl comprobanteBeneficiarioService) {
		this.grupoService = grupoService;
		this.usuarioInfoService = usuarioInfoService;
		this.comprobanteUsuarioService = comprobanteUsuarioService;
		this.comprobanteBeneficiarioService = comprobanteBeneficiarioService;
		
		final String username = SecurityUtils.getUsername();
		usuario = username != null ? miUsuarioService.findByUsernameIgnoreCase(username) : null;
    	
    	grupos = grupoService.findByIdUsuario(usuario.getIdusuario());
		
		setViewContent(createContent());
	}
	
	private Component createContent() {
		content = new FlexBoxLayout(createNew(), createGrid());
		content.setBoxSizing(BoxSizing.BORDER_BOX.getValue());
		content.setHeightFull();
		content.setPadding(Horizontal.RESPONSIVE_X, Top.RESPONSIVE_X);
		content.setFlexDirection(FlexDirection.COLUMN.getValue());
		//content.addClassNames(LumoStyles.Padding.Bottom.XL);
		if (UIUtils.isMobileDevice()) {
			content.getStyle().set("padding-bottom", "60px");
		}
		return content;
	}
	
	private Grid<Grupo> createGrid() {
		grid = new Grid<>();
		grid.setId("card-list-view");
		grid.addClassName("card-list-view");
		grid.addSelectionListener(event -> event.getFirstSelectedItem().ifPresent(this::showDetails));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS);
        grid.addComponentColumn(grupo -> createCard(grupo));
        //.setFooter(String.format("%s total groups", grupos.size()));
        
        grid.setItems(grupos);
       
        return grid;
	}
	
	public void updateList() {
    	grupos = grupoService.findByIdUsuario(usuario.getIdusuario());
    	grid.setItems(grupos);
    }
	
	private void showDetails(Grupo grupo) {
    	new ComprobanteUsuarioDialog(comprobanteUsuarioService, usuario, grupo, comprobanteBeneficiarioService);
    }

	private Component createNew() {
		Button cuponBtn = UIUtils.createButton("Crear un grupo", VaadinIcon.PLUS_CIRCLE, ButtonVariant.LUMO_SUCCESS);
		
		cuponBtn.addClickListener(clickEvent -> {
			if (usuarioInfoService.findByIdUsuario(usuario.getIdusuario()) == null) {
				UIUtils.createNotificationError("Primero debe completar sus datos antes de poder crear un grupo!.");
			}else {
				new GrupoDialog(grupoService, usuario, this);
			}
		});
		
        return cuponBtn;

	}
	
	private HorizontalLayout createCard(Grupo grupo) {
        HorizontalLayout card = new HorizontalLayout();
        card.addClassName("card");
        card.setSpacing(false);
        card.getThemeList().add("spacing-s");
        
        Image foto = UIUtils.getFoto(1);
        
        
        VerticalLayout description = new VerticalLayout();
        description.addClassName("description");
        description.setSpacing(false);
        description.setPadding(false);

        description.add(createHeader(grupo));
        List<Beneficiario> beneficiarios = grupo.getBeneficiarios().stream()
        		.sorted(Comparator.comparing(Beneficiario::getFirstNombreFirstApellido))
                .collect(Collectors.toList());
        
        description.add(createIntegranteYo(grupo));
        
        for (Beneficiario b: beneficiarios) {
        	description.add(createIntegrante(b));
			
		}
       
        card.add(foto, description);
        return card;
    }

	private Component createHeader(Grupo grupo) {
		Map<String, String> map = activo(grupo);
		boolean activo = Boolean.valueOf(map.get("activo"));
		int cupones = Integer.valueOf(map.get("cupones"));

		FormLayout form = new FormLayout();
		form.addClassNames(LumoUtility.Padding.End.SMALL);
		form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 4, FormLayout.ResponsiveStep.LabelsPosition.TOP),
				new FormLayout.ResponsiveStep("500px", 4, FormLayout.ResponsiveStep.LabelsPosition.TOP));
		form.setMaxWidth("500px");
		
        Span grupoSpan = new Span(grupo.getNombre());
        grupoSpan.getElement().getStyle().set("font-size", "24px");
        grupoSpan.getElement().getStyle().set("fontWeight", "bold");
        grupoSpan.getElement().getStyle().set("font-style", "italic");
        grupoSpan.getElement().getStyle().set("color", "#1A5276");  
        
        Icon cuponIcon = null;
        
        form.add(grupoSpan);
        form.setColspan(grupoSpan, 2);
        
        if(activo) {
        	cuponIcon = VaadinIcon.CHECK_CIRCLE.create();
    		cuponIcon.setColor("#82E0AA");
    		form.add(cuponIcon);
        }else {
        	cuponIcon = VaadinIcon.CLOSE_CIRCLE.create();
        	cuponIcon.setColor("#F1948A");
        
        	form.add(cuponIcon);
        }
        if (cupones > 0) {
        	form.add(UIUtils.createCupon3(cupones));
		}
        
        return form;
	}
	
	private Component createIntegranteYo(Grupo grupo) {
    	FormLayout form = new FormLayout();
    	form.addClassNames(LumoUtility.Padding.End.SMALL);
		form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 4, FormLayout.ResponsiveStep.LabelsPosition.TOP),
				new FormLayout.ResponsiveStep("500px", 4, FormLayout.ResponsiveStep.LabelsPosition.TOP));
		form.setMaxWidth("500px");
        Span integrante  = new Span("YO");
        integrante.addClassName("integrante");
        //integrante.getElement().getStyle().set("fontWeight", "bold");
        Icon registradoIcon = null;
        
        form.add(integrante);
        form.setColspan(integrante, 2);
        
        if(grupo.getComprobanteUsuarios().isEmpty()) {
        	registradoIcon = VaadinIcon.CLOSE_CIRCLE.create();
            registradoIcon.setColor("#F1948A");
            form.add(registradoIcon);
        }else {
        	
        	int totalCupones = grupo.getComprobanteUsuarios().stream()
			.filter( b -> b.isAprobado() ).collect(Collectors.toList())
			.stream().mapToInt(ComprobanteUsuario::getCupon).sum();

        	if(totalCupones > 0) {
        		registradoIcon = VaadinIcon.CHECK_CIRCLE.create();
                registradoIcon.setColor("#82E0AA");
                form.add(registradoIcon, UIUtils.createCupon3(totalCupones));
            }else {
            	registradoIcon = VaadinIcon.CLOSE_CIRCLE.create();
                registradoIcon.setColor("#F1948A");
                form.add(registradoIcon);
            }
        }

		return form;
	}
    
	private Component createIntegrante(Beneficiario beneficiario) {
		FormLayout form = new FormLayout();
		form.addClassNames(LumoUtility.Padding.End.SMALL);
		form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 4, FormLayout.ResponsiveStep.LabelsPosition.TOP),
				new FormLayout.ResponsiveStep("500px", 4, FormLayout.ResponsiveStep.LabelsPosition.TOP));
		form.setMaxWidth("500px");
        Span integrante  = new Span(beneficiario.getFirstNombreFirstApellido());
        integrante.addClassName("integrante");
        integrante.getElement().getStyle().set("fontWeight", "bold");
        Icon registradoIcon = null;
        
        form.add(integrante);
        form.setColspan(integrante, 2);
        //hlIntegrante.setFlexGrow(0.8, integrante);
        //hlIntegrante.setAlignSelf(Alignment.START, integrante);

        if(beneficiario.getComprobanteBeneficiarios().isEmpty()) {
        	registradoIcon = VaadinIcon.CLOSE_CIRCLE.create();
            registradoIcon.setColor("#F1948A");
            form.add(registradoIcon);
        }else {
        	
        	int totalCupones = beneficiario.getComprobanteBeneficiarios().stream()
			.filter( b -> b.isAprobado() ).collect(Collectors.toList())
			.stream().mapToInt(ComprobanteBeneficiario::getCupon).sum();

        	if(totalCupones > 0) {
        		registradoIcon = VaadinIcon.CHECK_CIRCLE.create();
                registradoIcon.setColor("#82E0AA");
                HorizontalLayout hl = UIUtils.createCupon3(totalCupones);
                form.add(registradoIcon, hl);
            }else {
            	registradoIcon = VaadinIcon.CLOSE_CIRCLE.create();
                registradoIcon.setColor("#F1948A");
                form.add(registradoIcon);
            }
        }

		return form;
	}
	
	private Map<String, String> activo(Grupo grupo) {
		boolean activo = true;
		int totalCupones = 0;
		
		if(!grupo.getComprobanteUsuarios().isEmpty()) {
			for(Beneficiario beneficiario: grupo.getBeneficiarios()) {
        		if(beneficiario.getComprobanteBeneficiarios().isEmpty()) {
        			activo = false;
        			break;
        		}else {
        			int totalBeneficiario = beneficiario.getComprobanteBeneficiarios().stream()
                			.filter( bene -> bene.isAprobado() ).collect(Collectors.toList())
                			.stream().mapToInt(ComprobanteBeneficiario::getCupon).sum();
                    if(totalBeneficiario > 0) {
                    	//activo = true;
                    }else {
                       	activo = false;
                       	break;
                    }
        		}
        	}
		}else {
        	activo = false;
        }
		
		 if(!grupo.getComprobanteUsuarios().isEmpty()) {
	        	int totalUsuario = grupo.getComprobanteUsuarios().stream()
	        			.filter( b -> b.isAprobado() ).collect(Collectors.toList())
	        			.stream().mapToInt(ComprobanteUsuario::getCupon).sum();
	        	totalCupones += totalUsuario;
		 }
		 if(!grupo.getComprobanteBeneficiarios().isEmpty()) {
	        	int totalBeneficiario = grupo.getComprobanteBeneficiarios().stream()
	        			.filter( b -> b.isAprobado() ).collect(Collectors.toList())
	        			.stream().mapToInt(ComprobanteBeneficiario::getCupon).sum();
	        	totalCupones += totalBeneficiario;
		 }
		
        Map<String, String> map = new HashMap<String, String>();
        map.put("activo", String.valueOf(activo));
        map.put("cupones", String.valueOf(totalCupones));
		
        /*if(!grupo.getComprobanteUsuarios().isEmpty()) {
        	int totalUsuario = grupo.getComprobanteUsuarios().stream()
        			.filter( b -> b.isAprobado() ).collect(Collectors.toList())
        			.stream().mapToInt(ComprobanteUsuario::getCupon).sum();
        	totalCupones += totalUsuario;
            if(totalUsuario > 0) {
            	for(Beneficiario beneficiario: grupo.getBeneficiarios()) {
            		if(beneficiario.getComprobanteBeneficiarios().isEmpty()) {
            			activo = false;
            		}else {
            			int totalBeneficiario = beneficiario.getComprobanteBeneficiarios().stream()
                    			.filter( bene -> bene.isAprobado() ).collect(Collectors.toList())
                    			.stream().mapToInt(ComprobanteBeneficiario::getCupon).sum();
            			totalCupones += totalBeneficiario;
                        if(totalBeneficiario > 0) {
                        	//activo = true;
                        }else {
                           	activo = false;
                        }
            		}
            	}
            }else {
            	activo = false;
            }
        }else {
        	activo = false;
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put("activo", String.valueOf(activo));
        map.put("cupones", String.valueOf(totalCupones));*/

		return map;
	}

}
