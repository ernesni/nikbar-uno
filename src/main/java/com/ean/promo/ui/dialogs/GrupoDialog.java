package com.ean.promo.ui.dialogs;

import com.ean.promo.backend.entity.Beneficiario;
import com.ean.promo.backend.entity.Grupo;
import com.ean.promo.backend.entity.Usuario;
import com.ean.promo.backend.service.impl.GrupoServiceImpl;
import com.ean.promo.ui.util.UIUtils;
import com.ean.promo.views.grupo.MisGruposView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class GrupoDialog extends Dialog{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8801178601515880341L;
	private GrupoServiceImpl grupoService;
	private Usuario usuario;
	private MisGruposView grupoCardView;
	
	private TextField nombreField;
	private Integrante integrante1, integrante2, integrante3;
	
	private Button cancelBtn, saveBtn;
	
	private BeanValidationBinder<Grupo> binder;
	private ConfirmationDialog confirmationDialog = null;

	
	public GrupoDialog(GrupoServiceImpl grupoService, Usuario usuario, MisGruposView grupoCardView) {
		this.grupoService = grupoService;
		this.usuario = usuario;
		this.grupoCardView = grupoCardView;
		getElement().setAttribute("theme", "custom-dialog");

		setCloseOnEsc(false);
		setCloseOnOutsideClick(false);

		createContent();
		createFooter();
		
		setHeaderTitle("Nuevo Grupo");
		
		open();
	}

	private void createContent() {
		Grupo grupo = new Grupo();
		nombreField = new TextField("Nombre del Grupo");
        
        binder = new BeanValidationBinder<>(Grupo.class);
		binder.setBean(grupo);

		binder.bind(nombreField, "nombre");
      
		FormLayout form = new FormLayout();
		form.addClassNames(LumoUtility.Padding.Bottom.LARGE, LumoUtility.Padding.Horizontal.LARGE, LumoUtility.Padding.Top.SMALL);
		form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
				new FormLayout.ResponsiveStep("600px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP),
				new FormLayout.ResponsiveStep("800px", 3, FormLayout.ResponsiveStep.LabelsPosition.TOP),
				new FormLayout.ResponsiveStep("1024px", 4, FormLayout.ResponsiveStep.LabelsPosition.TOP));
        
		
		//form.setColspan(ciudad, 2);
		
		integrante1 = new Integrante(1); 
		integrante2 = new Integrante(2);
		integrante3 = new Integrante(3);
		
		
		form.add(nombreField, integrante1.details, integrante2.details, integrante3.details);

        add(form);
	}

	private void createFooter() {
		cancelBtn = new Button("Cancelar", new Icon(VaadinIcon.CLOSE_CIRCLE), event -> {
			close();
		});
		cancelBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

		saveBtn = new Button("GUARDAR", event -> {
			
			if (binder.validate().isOk() && integrante1.binder.validate().isOk() && 
					integrante2.binder.validate().isOk() && integrante3.binder.validate().isOk()) {
				if(checkGrupoRepetido(nombreField.getValue())) {
					UIUtils.createNotificationError("Ya tiene un grupo registrado con ese nombre");
				}else {
					confirmationDialog = new ConfirmationDialog("Confirmar operación",
							"Está seguro que desea guardar este grupo?", accion -> {
								guardar(confirmationDialog);
							},
							action -> {
								saveBtn.setEnabled(true);
								confirmationDialog.close();
							});	
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
		Grupo grupoBean = binder.getBean();
		grupoBean.setUsuario(usuario);

		Beneficiario beneficiarioBean1 = integrante1.binder.getBean();
		beneficiarioBean1.setGrupo(grupoBean);
		
		grupoBean.getBeneficiarios().add(integrante1.getIntegranteBean(grupoBean));
		grupoBean.getBeneficiarios().add(integrante2.getIntegranteBean(grupoBean));
		grupoBean.getBeneficiarios().add(integrante3.getIntegranteBean(grupoBean));
			
		grupoService.addGrupo(grupoBean);
		
		UIUtils.createNotificationSuccess("Se ha registrado el Grupo!.");
		grupoCardView.updateList();
		this.close();
		confirmationDialog.close();
	}
	
	private class Integrante {
		private BeanValidationBinder<Beneficiario> binder;
		private IntegerField ciField;
		private TextField nombreField, apellidoField;
		private Details details;
		private Beneficiario beneficiario;
		
		Integrante(int index){
			beneficiario = new Beneficiario();
			ciField = new IntegerField("C.I. N°");
			ciField.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);
			ciField.setAutoselect(true);
			nombreField = new TextField("Nombres");
			apellidoField = new TextField("Apellidos");
			
			binder = new BeanValidationBinder<>(Beneficiario.class);
			binder.setBean(beneficiario);
			
			binder.bind(ciField, "cin");
			binder.bind(nombreField, "nombre");
			binder.bind(apellidoField, "apellido");
			
			FormLayout form = new FormLayout();
			form.addClassNames(LumoUtility.Padding.Bottom.LARGE, LumoUtility.Padding.Horizontal.LARGE, LumoUtility.Padding.Top.SMALL);
			form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
					new FormLayout.ResponsiveStep("600px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP),
					new FormLayout.ResponsiveStep("800px", 3, FormLayout.ResponsiveStep.LabelsPosition.TOP),
					new FormLayout.ResponsiveStep("1024px", 4, FormLayout.ResponsiveStep.LabelsPosition.TOP));


			form.add(ciField, nombreField, apellidoField);

			details = new Details("Beneficiario N° " + index, form);
			details.setOpened(true);

		}
		
		private Beneficiario getIntegranteBean(Grupo grupoBean) {
			Beneficiario beneficiarioBean = binder.getBean();
			beneficiarioBean.setGrupo(grupoBean);
			return beneficiarioBean;
		}
	}
	
	private boolean checkGrupoRepetido(String nombre) {
		return grupoService.searchByNombre(nombre) != null;
	}
}
