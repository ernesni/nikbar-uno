package com.ean.promo.views;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.html.Div;

public class ViewFrame extends Composite<Div> implements HasStyle {

	private static final long serialVersionUID = 1127041346814914353L;

	private String CLASS_NAME = "view-frame";

	private Div header;
	private Div content;
	private Div footer;

	public ViewFrame() {
		setClassName(CLASS_NAME);

		header = new Div();
		header.setClassName(CLASS_NAME + "__header");

		content = new Div();
		content.setClassName(CLASS_NAME + "__content");

		footer = new Div();
		footer.setClassName(CLASS_NAME + "__footer");

		getContent().add(header, content, footer);
	}

	/**
	 * Sets the header slot's components.
	 */
	public void setViewHeader(Component... components) {
		header.removeAll();
		header.add(components);
	}

	/**
	 * Sets the content slot's components.
	 */
	public void setViewContent(Component... components) {
		content.removeAll();
		content.add(components);
	}

	/**
	 * Sets the footer slot's components.
	 */
	public void setViewFooter(Component... components) {
		footer.removeAll();
		footer.add(components);
	}

	@Override
	protected void onAttach(AttachEvent attachEvent) {
		super.onAttach(attachEvent);
		MainView.get().getAppBar().reset();
	}

}
