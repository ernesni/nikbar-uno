package com.ean.promo.ui.dialogs;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.shared.Registration;

public class ConfirmationDialog extends Dialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6194660480267567337L;
	private Label question;
	private Button cancelButton, saveButton;

	public ConfirmationDialog() {
		getElement().setAttribute("theme", "custom-dialog");

		setCloseOnEsc(false);
		setCloseOnOutsideClick(false);

		createContent();
		createFooter();
	}
	
	public ConfirmationDialog(String title, String content, ComponentEventListener<ClickEvent<Button>> listenerSave, 
			ComponentEventListener<ClickEvent<Button>> listenerCancel) {
		this();
		setHeaderTitle(title);
		setQuestion(content);
		addConfirmationListener(listenerSave);
		addCancelationListener(listenerCancel);
		
		open();
	}
	
	public void setQuestion(String question) {
		this.question.setText(question);
	}

	public void addConfirmationListener(ComponentEventListener<ClickEvent<Button>> listener) {
		saveButton.addClickListener(listener);
	}
	
	public void addCancelationListener(ComponentEventListener<ClickEvent<Button>> listener) {
		cancelButton.addClickListener(listener);
	}

	private void createContent() {
		question = new Label();
		VerticalLayout content = new VerticalLayout();
		content.add(question);
		content.setPadding(true);
		add(content);
	}

	private void createFooter() {
		cancelButton = new Button("Cancelar", new Icon(VaadinIcon.CLOSE_CIRCLE));
		cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

		saveButton = new Button("SI");
		// saveButton.setIconAfterText(true);
		saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		saveButton.setDisableOnClick(true);

		getFooter().add(cancelButton);
		getFooter().add(saveButton);
	}

	public Registration addSaveListener(
            ComponentEventListener<ClickEvent<Button>> listener) {
        return saveButton.addClickListener(listener);
    }
	
	public Registration addCancelListener(
            ComponentEventListener<ClickEvent<Button>> listener) {
        return cancelButton.addClickListener(listener);
    }

}
