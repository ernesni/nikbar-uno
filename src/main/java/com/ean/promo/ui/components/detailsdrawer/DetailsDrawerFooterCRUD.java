package com.ean.promo.ui.components.detailsdrawer;

import com.ean.promo.ui.components.FlexBoxLayout;
import com.ean.promo.ui.layout.size.Horizontal;
import com.ean.promo.ui.layout.size.Right;
import com.ean.promo.ui.layout.size.Vertical;
import com.ean.promo.ui.util.UIUtils;
import com.ean.promo.ui.util.css.LumoStyles;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.shared.Registration;

public class DetailsDrawerFooterCRUD extends FlexBoxLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4293836806946989965L;
	private Button save;
    private Button cancel;
    private Button delete;

    public DetailsDrawerFooterCRUD() {
        setBackgroundColor(LumoStyles.Color.Contrast._5);
        setPadding(Horizontal.RESPONSIVE_L, Vertical.S);
        setSpacing(Right.S);
        setWidthFull();

        save = UIUtils.createPrimaryButton("Guardar");
        cancel = UIUtils.createTertiaryButton("Cancelar");
        delete = UIUtils.createDeleteButton("Borrar");
        
        /*add(delete, cancel, save);
        setAlignSelf(Alignment.START, delete);
        setAlignSelf(Alignment.END, cancel);
        setAlignSelf(Alignment.END, save);*/
        
        
		// Botones
		HorizontalLayout layout = new HorizontalLayout();
		layout.setWidth("100%");
		//layout.setPadding(true);
		//layout.getStyle().set("background-color", "#EFF2FB");

		HorizontalLayout layoutCancel = new HorizontalLayout(delete);
		layoutCancel.setWidth("100%");
		layoutCancel.setJustifyContentMode(JustifyContentMode.START);

		HorizontalLayout layoutSave = new HorizontalLayout(cancel, save);
		layoutSave.setWidth("100%");
		// the default is JustifyContentMode.START
		layoutSave.setJustifyContentMode(JustifyContentMode.END);

		layout.add(layoutCancel, layoutSave);
		
		add(layout);
    }

    public Registration addSaveListener(
            ComponentEventListener<ClickEvent<Button>> listener) {
        return save.addClickListener(listener);
    }

    public Registration addCancelListener(
            ComponentEventListener<ClickEvent<Button>> listener) {
        return cancel.addClickListener(listener);
    }
    
    public Registration addDeleteListener(
            ComponentEventListener<ClickEvent<Button>> listener) {
        return delete.addClickListener(listener);
    }

	public Button getDelete() {
		return delete;
	}

	public Button getSave() {
		return save;
	}
	
	
}
