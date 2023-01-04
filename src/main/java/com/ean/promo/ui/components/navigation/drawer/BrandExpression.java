package com.ean.promo.ui.components.navigation.drawer;

import com.ean.promo.ui.util.UIUtils;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;

public class BrandExpression extends Div {

	private static final long serialVersionUID = 1434934559439176651L;

	private String CLASS_NAME = "brand-expression";

	private Image logo;
	private Label title;

	public BrandExpression(String text) {
		setClassName(CLASS_NAME);

		logo = new Image(UIUtils.IMG_PATH + "logos/logo.png", "");
		logo.setAlt(text + " logo");
		logo.setClassName(CLASS_NAME + "__logo");
		
		logo.addClickListener(e ->
			logo.getUI().ifPresent(ui -> {
				String url = "http://www.nikbarofficial.com/";
				UI.getCurrent().getPage().open(url, "_blank");//_self para que sea en la misma pestaña
			})
		);
	

		title = UIUtils.createXLargeLabel(text);
		title.addClassName(CLASS_NAME + "__title");

		add(logo, title);
	}

}
