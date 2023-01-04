package com.ean.promo.ui.dialogs;

import com.ean.promo.backend.entity.ComprobanteUsuario;
import com.ean.promo.ui.util.Constante;
import com.ean.promo.ui.util.UIUtils;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class ComprobanteSelectDialog extends Dialog{
	
	private Button cancelBtn, saveBtn;
	
	RadioButtonGroup<String> opcion;
	
	public ComprobanteSelectDialog() {

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

		
		opcion = new RadioButtonGroup<>();
		opcion.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
		opcion.setLabel("Elija el tipo de grupo");
		opcion.setItems("Propios", "De terceros");
		opcion.setRequired(true);
      
		FormLayout form = new FormLayout();
		form.addClassNames(LumoUtility.Padding.Bottom.LARGE, LumoUtility.Padding.Horizontal.LARGE, LumoUtility.Padding.Top.SMALL);
		form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
				new FormLayout.ResponsiveStep("600px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP),
				new FormLayout.ResponsiveStep("800px", 3, FormLayout.ResponsiveStep.LabelsPosition.TOP),
				new FormLayout.ResponsiveStep("1024px", 4, FormLayout.ResponsiveStep.LabelsPosition.TOP));
		
		//form.setColspan(ciudad, 2);
		
		
		form.add(opcion);

        add(form);
	}

	private void createFooter() {
		cancelBtn = new Button("Cancelar", new Icon(VaadinIcon.CLOSE_CIRCLE), event -> {
			close();
		});
		cancelBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

		saveBtn = new Button("OK", event -> {
			if (!opcion.isEmpty()) {
				if (opcion.getValue().equals("Propios")) {
					saveBtn.getUI().ifPresent(ui -> {
						ui.navigate(Constante.PAGE_MIS_GRUPOS);
					});
				}else if (opcion.getValue().equals("De terceros")) {
					saveBtn.getUI().ifPresent(ui -> {
						ui.navigate(Constante.PAGE_TERCEROS_GRUPOS);
					});
				}
				close();
				UIUtils.createNotificationCenter("Debe hacer click sobre el grupo al cual le desea asignar un comprobante!!.");
			}else {
				UIUtils.createNotificationError("Debe seleccionar un grupo!.");
				saveBtn.setEnabled(true);
			}
		});

		saveBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		saveBtn.setDisableOnClick(true);
		
		getFooter().add(cancelBtn);
		getFooter().add(saveBtn);
		
	}
	
}
