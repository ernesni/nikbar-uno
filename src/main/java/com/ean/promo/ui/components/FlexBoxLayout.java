package com.ean.promo.ui.components;

import java.util.ArrayList;

import com.ean.promo.ui.layout.size.Size;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.theme.lumo.Lumo;

public class FlexBoxLayout extends FlexLayout {

	private static final long serialVersionUID = -581669322740742260L;
	//Usar siempre como parametro LumoUtility......
	public static final String BACKGROUND_COLOR = "background-color";
	public static final String BORDER_RADIUS = "border-radius";
	public static final String BOX_SHADOW = "box-shadow";
	public static final String BOX_SIZING = "box-sizing";
	public static final String DISPLAY = "display";
	public static final String FLEX_DIRECTION = "flex-direction";
	public static final String FLEX_WRAP = "flex-wrap";
	public static final String MAX_WIDTH = "max-width";
	public static final String OVERFLOW = "overflow";
	public static final String POSITION = "position";

	private ArrayList<Size> spacings;

	public FlexBoxLayout(Component... components) {
		super(components);
		spacings = new ArrayList<>();
	}

	public void setBackgroundColor(String value) {
		getStyle().set(BACKGROUND_COLOR, value);
	}

	public void setBackgroundColor(String value, String theme) {
		getStyle().set(BACKGROUND_COLOR, value);
		setTheme(theme);
	}

	public void removeBackgroundColor() {
		getStyle().remove(BACKGROUND_COLOR);
	}

	public void setBorderRadius(String radius) {
		getStyle().set(BORDER_RADIUS, radius);
	}

	public void removeBorderRadius() {
		getStyle().remove(BORDER_RADIUS);
	}

	public void setBoxSizing(String sizing) {
		getStyle().set(BOX_SIZING, sizing);
	}

	public void removeBoxSizing() {
		getStyle().remove(BOX_SIZING);
	}

	public void setDisplay(String display) {
		getStyle().set(DISPLAY, display);
	}

	public void removeDisplay() {
		getStyle().remove(DISPLAY);
	}

	public void setFlex(String value, Component... components) {
		for (Component component : components) {
			component.getElement().getStyle().set("flex", value);
		}
	}

	public void setFlexBasis(String value, Component... components) {
		for (Component component : components) {
			component.getElement().getStyle().set("flex-basis", value);
		}
	}
	
	public void setFlexDirection(String direction) {
		getStyle().set(FLEX_DIRECTION, direction);
	}

	public void removeFlexDirection() {
		getStyle().remove(FLEX_DIRECTION);
	}

	public void setFlexShrink(String value, Component... components) {
		for (Component component : components) {
			component.getElement().getStyle().set("flex-shrink", value);
		}
	}

	public void setFlexWrap(String wrap) {
		getStyle().set(FLEX_WRAP, wrap);
	}

	public void removeFlexWrap() {
		getStyle().remove(FLEX_WRAP);
	}

	public void setMargin(Size... sizes) {
		for (Size size : sizes) {
			for (String attribute : size.getMarginAttributes()) {
				getStyle().set(attribute, size.getVariable());
			}
		}
	}

	public void removeMargin() {
		getStyle().remove("margin");
		getStyle().remove("margin-bottom");
		getStyle().remove("margin-left");
		getStyle().remove("margin-right");
		getStyle().remove("margin-top");
	}

	public void setMaxWidth(String value) {
		getStyle().set(MAX_WIDTH, value);
	}

	public void removeMaxWidth() {
		getStyle().remove(MAX_WIDTH);
	}

	public void setOverflow(String overflow) {
		getStyle().set(OVERFLOW, overflow);
	}

	public void removeOverflow() {
		getStyle().remove(OVERFLOW);
	}

	public void setPadding(Size... sizes) {
		removePadding();
		for (Size size : sizes) {
			for (String attribute : size.getPaddingAttributes()) {
				getStyle().set(attribute, size.getVariable());
			}
		}
	}

	public void removePadding() {
		getStyle().remove("padding");
		getStyle().remove("padding-bottom");
		getStyle().remove("padding-left");
		getStyle().remove("padding-right");
		getStyle().remove("padding-top");
	}

	public void setPosition(String position) {
		getStyle().set(POSITION, position);
	}

	public void removePosition() {
		getStyle().remove(POSITION);
	}

	public void setShadow(String shadow) {
		getStyle().set(BOX_SHADOW, shadow);
	}

	public void removeShadow() {
		getStyle().remove(BOX_SHADOW);
	}

	public void setSpacing(Size... sizes) {
		// Remove old styles (if applicable)
		for (Size spacing : spacings) {
			removeClassName(spacing.getSpacingClassName());
		}
		spacings.clear();

		// Add new
		for (Size size : sizes) {
			addClassName(size.getSpacingClassName());
			spacings.add(size);
		}
	}

	public void setTheme(String theme) {
		if (Lumo.DARK.equals(theme)) {
			getElement().setAttribute("theme", "dark");
		} else {
			getElement().removeAttribute("theme");
		}
	}
}
