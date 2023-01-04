package com.ean.promo.ui.dialogs;

import com.ean.promo.ui.components.FlexBoxLayout;
import com.ean.promo.ui.layout.size.Horizontal;
import com.ean.promo.ui.layout.size.Vertical;
import com.ean.promo.ui.util.FontSize;
import com.ean.promo.ui.util.TextColor;
import com.ean.promo.ui.util.UIUtils;
import com.ean.promo.ui.util.css.FlexDirection;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class VerImagen extends Dialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2445786217496200739L;
	private Label titleLabel;
	private FlexBoxLayout content;
	private Button cancelButton;

	public VerImagen() {
		getElement().setAttribute("theme", "custom-dialog");

		setCloseOnEsc(false);
		setCloseOnOutsideClick(false);

		createHeader();
		createContent();
		createFooter();
	}
	
	public VerImagen(String title, Component content) {
		this();
		setTitle(title);
		setContent(content);
		
		open();
	}

	public void setTitle(String title) {
		this.titleLabel.setText(title);
	}

	public void setContent(Component component) {
		content.add(component);
	}

	private void createHeader() {
		this.titleLabel = UIUtils.createLabel(FontSize.XL, TextColor.SECONDARY, "");
		titleLabel.getStyle().set("font-weight", "bold");

		HorizontalLayout header = new HorizontalLayout();
		header.add(this.titleLabel);
		header.setWidthFull();
		header.setPadding(true);
		header.setAlignItems(Alignment.CENTER);
		add(header);
	}

	private void createContent() {
		content = new FlexBoxLayout();
		content.setFlexDirection(FlexDirection.COLUMN.getValue());
		content.setMargin(Horizontal.AUTO, Vertical.RESPONSIVE_L);
		//content.setMaxWidth("840px");
		add(content);
	}

	private void createFooter() {
		cancelButton = new Button("Salir", new Icon(VaadinIcon.CLOSE_CIRCLE), event -> {
			close();
		});
		cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

		// Botones
		HorizontalLayout layout = new HorizontalLayout();
		layout.setWidth("100%");
		layout.setPadding(true);

		HorizontalLayout layoutCancel = new HorizontalLayout(cancelButton);
		layoutCancel.setWidth("100%");
		layoutCancel.setJustifyContentMode(JustifyContentMode.START); 

		layout.add(layoutCancel);

		add(layout);
	}

}
