package com.ean.promo.ui.components.navigation.tab;

import com.ean.promo.ui.util.UIUtils;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;

public class ClosableNaviTab extends NaviTab {

	private static final long serialVersionUID = -8726133365466178339L;
	private final Button close;

	public ClosableNaviTab(String label,
	                       Class<? extends Component> navigationTarget) {
		super(label, navigationTarget);
		getElement().setAttribute("closable", true);

		close = UIUtils.createButton(VaadinIcon.CLOSE, ButtonVariant.LUMO_TERTIARY_INLINE);
		// ButtonVariant.LUMO_SMALL isn't small enough.
		UIUtils.setFontSize(FontSize.XXSMALL, close);
		add(close);
	}

	public Button getCloseButton() {
		return close;
	}
}
