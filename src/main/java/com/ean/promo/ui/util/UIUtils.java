package com.ean.promo.ui.util;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ean.promo.backend.entity.Usuario;
import com.ean.promo.ui.util.css.LumoStyles;
import com.ean.promo.ui.util.css.WhiteSpace;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.WebBrowser;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class UIUtils {

	public static final String IMG_PATH = "images/";
	public static final String IMG_PATH_NO_IMAGE = "images/default/noimage.png";
	public static final String IMG_PATH_NO_FOTO = "images/default/no_foto_user.png";

	public static final String[] colores = new String[] { "RED", "YELLOW", "GREEN", "CRIMSON", "FIREBRICK", "DARKRED",
			"HOTPINK", "DEEPPINK", "MEDIUMVIOLETRED", "PALEVIOLETRED", "TOMATO", "ORANGERED", "DARKORANGE", "ORANGE",
			"FUCHSIA", "MAGENTA", "MEDIUMORCHID", "MEDIUMPURPLE", "REBECCAPURPLE", "BLUEVIOLET", "DARKVIOLET",
			"DARKORCHID", "DARKMAGENTA", "PURPLE", "INDIGO", "SLATEBLUE", "DARKSLATEBLUE", "MEDIUMSLATEBLUE",
			"MEDIUMSEAGREEN", "SEAGREEN", "FORESTGREEN", "GREEN", "DARKGREEN", "YELLOWGREEN", "OLIVEDRAB", "OLIVE",
			"DARKOLIVEGREEN", "DARKSEAGREEN", "LIGHTSEAGREEN", "DARKCYAN", "TEAL", "DODGERBLUE", "CORNFLOWERBLUE",
			"MEDIUMSLATEBLUE", "ROYALBLUE", "BLUE", "MEDIUMBLUE", "DARKBLUE", "NAVY", "MIDNIGHTBLUE", "GOLDENROD",
			"DARKGOLDENROD", "PERU", "CHOCOLATE", "SADDLEBROWN", "SIENNA", "BROWN", "MAROON", "GRAY", "DIMGRAY",
			"LIGHTSLATEGRAY", "SLATEGRAY", "DARKSLATEGRAY", "BLACK" };

	public static final String[] colores_verdes = { "GREENYELLOW", "CHARTREUSE", "LAWNGREEN", "LIME", "LIMEGREEN",
			"PALEGREEN", "LIGHTGREEN", "MEDIUMSPRINGGREEN", "SPRINGGREEN", "MEDIUMSEAGREEN", "SEAGREEN", "FORESTGREEN",
			"GREEN", "DARKGREEN", "YELLOWGREEN", "OLIVEDRAB", "OLIVE", "DARKOLIVEGREEN", "MEDIUMAQUAMARINE",
			"DARKSEAGREEN", "LIGHTSEAGREEN", "DARKCYAN", "TEAL" };

	public static final String[] colores_purpuras = { "LAVENDER", "THISTLE", "PLUM", "VIOLET", "ORCHID", "FUCHSIA",
			"MAGENTA", "MEDIUMORCHID", "MEDIUMPURPLE", "REBECCAPURPLE", "BLUEVIOLET", "DARKVIOLET", "DARKORCHID",
			"DARKMAGENTA", "PURPLE", "INDIGO", "SLATEBLUE", "DARKSLATEBLUE", "MEDIUMSLATEBLUE" };

	public static final String[] colores_azules = { "AQUA", "CYAN", "LIGHTCYAN", "PALETURQUOISE", "AQUAMARINE",
			"TURQUOISE", "MEDIUMTURQUOISE", "DARKTURQUOISE", "CADETBLUE", "STEELBLUE", "LIGHTSTEELBLUE", "POWDERBLUE",
			"LIGHTBLUE", "SKYBLUE", "LIGHTSKYBLUE", "DEEPSKYBLUE", "DODGERBLUE", "CORNFLOWERBLUE", "MEDIUMSLATEBLUE",
			"ROYALBLUE", "BLUE", "MEDIUMBLUE", "DARKBLUE", "NAVY", "MIDNIGHTBLUE" };

	public static final String[] colores_usuario = { "#FF4000", "#0080FF", "#FACC2E", "#FA58D0", "#A4A4A4", "#74DF00" };

	/**
	 * Thread-unsafe formatters.
	 */
	private static final ThreadLocal<DecimalFormat> decimalFormat = ThreadLocal
			.withInitial(() -> new DecimalFormat("###,###.00", DecimalFormatSymbols.getInstance(Locale.US)));
	
	/*private static final ThreadLocal<DateTimeFormatter> dateFormat = ThreadLocal
			.withInitial(() -> DateTimeFormatter.ofPattern("MMM dd, YYYY"));*/

	/* ==== BUTTONS ==== */

	// Styles

	public static Button createPrimaryButton(String text) {
		return createButton(text, ButtonVariant.LUMO_PRIMARY);
	}

	public static Button createPrimaryButton(VaadinIcon icon) {
		return createButton(icon, ButtonVariant.LUMO_PRIMARY);
	}

	public static Button createPrimaryButton(String text, VaadinIcon icon) {
		return createButton(text, icon, ButtonVariant.LUMO_PRIMARY);
	}

	public static Button createTertiaryButton(String text) {
		return createButton(text, ButtonVariant.LUMO_TERTIARY);
	}

	public static Button createDeleteButton(String text) {
		return createButton(text, ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_ERROR);
	}

	public static Button createTertiaryButton(VaadinIcon icon) {
		return createButton(icon, ButtonVariant.LUMO_TERTIARY);
	}

	public static Button createTertiaryButton(String text, VaadinIcon icon) {
		return createButton(text, icon, ButtonVariant.LUMO_TERTIARY);
	}

	public static Button createTertiaryInlineButton(String text) {
		return createButton(text, ButtonVariant.LUMO_TERTIARY_INLINE);
	}

	public static Button createTertiaryInlineButton(VaadinIcon icon) {
		return createButton(icon, ButtonVariant.LUMO_TERTIARY_INLINE);
	}

	public static Button createTertiaryInlineButton(String text, VaadinIcon icon) {
		return createButton(text, icon, ButtonVariant.LUMO_TERTIARY_INLINE);
	}

	public static Button createSuccessButton(String text) {
		return createButton(text, ButtonVariant.LUMO_SUCCESS);
	}

	public static Button createSuccessButton(VaadinIcon icon) {
		return createButton(icon, ButtonVariant.LUMO_SUCCESS);
	}

	public static Button createSuccessButton(String text, VaadinIcon icon) {
		return createButton(text, icon, ButtonVariant.LUMO_SUCCESS);
	}

	public static Button createSuccessPrimaryButton(String text) {
		return createButton(text, ButtonVariant.LUMO_SUCCESS, ButtonVariant.LUMO_PRIMARY);
	}

	public static Button createSuccessPrimaryButton(VaadinIcon icon) {
		return createButton(icon, ButtonVariant.LUMO_SUCCESS, ButtonVariant.LUMO_PRIMARY);
	}

	public static Button createSuccessPrimaryButton(String text, VaadinIcon icon) {
		return createButton(text, icon, ButtonVariant.LUMO_SUCCESS, ButtonVariant.LUMO_PRIMARY);
	}

	public static Button createSuccessPrimaryButtonIcon(VaadinIcon icon) {
		return createButton(icon, ButtonVariant.LUMO_SUCCESS, ButtonVariant.LUMO_PRIMARY);
	}

	public static Button createErrorButton(String text) {
		return createButton(text, ButtonVariant.LUMO_ERROR);
	}

	public static Button createErrorButton(VaadinIcon icon) {
		return createButton(icon, ButtonVariant.LUMO_ERROR);
	}

	public static Button createErrorButton(String text, VaadinIcon icon) {
		return createButton(text, icon, ButtonVariant.LUMO_ERROR);
	}

	public static Button createErrorPrimaryButton(String text) {
		return createButton(text, ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_PRIMARY);
	}

	public static Button createErrorPrimaryButton(VaadinIcon icon) {
		return createButton(icon, ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_PRIMARY);
	}

	public static Button createErrorPrimaryButton(String text, VaadinIcon icon) {
		return createButton(text, icon, ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_PRIMARY);
	}

	public static Button createContrastButton(String text) {
		return createButton(text, ButtonVariant.LUMO_CONTRAST);
	}

	public static Button createContrastButton(VaadinIcon icon) {
		return createButton(icon, ButtonVariant.LUMO_CONTRAST);
	}

	public static Button createContrastButton(String text, VaadinIcon icon) {
		return createButton(text, icon, ButtonVariant.LUMO_CONTRAST);
	}

	public static Button createContrastPrimaryButton(String text) {
		return createButton(text, ButtonVariant.LUMO_CONTRAST, ButtonVariant.LUMO_PRIMARY);
	}

	public static Button createContrastPrimaryButton(VaadinIcon icon) {
		return createButton(icon, ButtonVariant.LUMO_CONTRAST, ButtonVariant.LUMO_PRIMARY);
	}

	public static Button createPrimaryPrimaryButton(String text, VaadinIcon icon) {
		return createButton(text, icon, ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_PRIMARY);
	}

	public static Button createPrimaryPrimaryButton(String text) {
		return createButton(text, ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_PRIMARY);
	}

	public static Button createPrimaryPrimaryIconButton(VaadinIcon icon) {
		return createButton(icon, ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_PRIMARY);
	}

	public static Button createPrimaryContrastButton(String text, VaadinIcon icon) {
		return createButton(text, icon, ButtonVariant.LUMO_CONTRAST, ButtonVariant.LUMO_PRIMARY);
	}

	public static Button createPrimarySuccesButton(String text, VaadinIcon icon) {
		return createButton(text, icon, ButtonVariant.LUMO_SUCCESS, ButtonVariant.LUMO_PRIMARY);
	}

	// Size

	public static Button createSmallButton(String text) {
		return createButton(text, ButtonVariant.LUMO_SMALL);
	}

	public static Button createSmallButton(VaadinIcon icon) {
		return createButton(icon, ButtonVariant.LUMO_SMALL);
	}

	public static Button createSmallButton(String text, VaadinIcon icon) {
		return createButton(text, icon, ButtonVariant.LUMO_SMALL);
	}

	public static Button createLargeButton(String text) {
		return createButton(text, ButtonVariant.LUMO_LARGE);
	}

	public static Button createLargeButton(VaadinIcon icon) {
		return createButton(icon, ButtonVariant.LUMO_LARGE);
	}

	public static Button createLargeButton(String text, VaadinIcon icon) {
		return createButton(text, icon, ButtonVariant.LUMO_LARGE);
	}

	// Text

	public static Button createButton(String text, ButtonVariant... variants) {
		Button button = new Button(text);
		button.addThemeVariants(variants);
		button.getElement().setAttribute("aria-label", text);
		return button;
	}

	// Icon

	public static Button createButton(VaadinIcon icon, ButtonVariant... variants) {
		Button button = new Button(new Icon(icon));
		button.addThemeVariants(variants);
		return button;
	}

	// Text and icon

	public static Button createButton(String text, VaadinIcon icon, ButtonVariant... variants) {
		Icon i = new Icon(icon);
		i.getElement().setAttribute("slot", "prefix");
		Button button = new Button(text, i);
		button.addThemeVariants(variants);
		return button;
	}

	/* ==== TEXTFIELDS ==== */

	public static TextField createSmallTextField() {
		TextField textField = new TextField();
		textField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
		return textField;
	}

	/* ==== LABELS ==== */

	public static Label createLabel(FontSize size, TextColor color, String text) {
		Label label = new Label(text);
		setFontSize(size.getValue(), label);
		setTextColor(color, label);
		return label;
	}

	/*public static Label createLabel(FontSize size, String text) {
		return createLabel(size, TextColor.BODY, text);
	}

	public static Label createLabel(TextColor color, String text) {
		return createLabel(FontSize.M, color, text);
	}

	public static Label createH1Label(String text) {
		Label label = new Label(text);
		label.addClassName(LumoStyles.Heading.H1);
		return label;
	}

	public static Label createH2Label(String text) {
		Label label = new Label(text);
		label.addClassName(LumoStyles.Heading.H2);
		return label;
	}*/

	public static Label createXLargeLabel(String text) {
		Label label = new Label(text);
		label.addClassName(LumoUtility.FontSize.XLARGE);
		return label;
	}

	public static Label createH4Label(String text) {
		Label label = new Label(text);
		label.addClassName(LumoStyles.Heading.H4);
		return label;
	}

	/*public static Label createH5Label(String text) {
		Label label = new Label(text);
		label.addClassName(LumoStyles.Heading.H5);
		return label;
	}

	public static Label createH6Label(String text) {
		Label label = new Label(text);
		label.addClassName(LumoStyles.Heading.H6);
		return label;
	}*/

	/* === MISC === */

	/*
	 * public static String formatAddress(Address address) { return
	 * address.getStreet() + "\n" + address.getCity() + ", " + address.getCity() +
	 * " " + address.getZip(); }
	 */

	public static Button createFloatingActionButton(VaadinIcon icon) {
		Button button = createPrimaryButton(icon);
		button.addThemeName("fab");
		return button;
	}

	/*public static FlexLayout createPhoneLayout() {
		TextField prefix = new TextField();
		prefix.setValue("+358");
		prefix.setWidth("80px");

		TextField number = new TextField();
		// number.setValue(DummyData.getPhoneNumber());
		number.setValue("0981 123456");

		FlexBoxLayout layout = new FlexBoxLayout(prefix, number);
		layout.setFlexGrow(1, number);
		layout.setSpacing(Right.S);
		return layout;
	}*/

	/* === NUMBERS === */

	public static String formatAmount(Double amount) {
		return decimalFormat.get().format(amount);
	}

	public static String formatAmount(int amount) {
		return decimalFormat.get().format(amount);
	}

	/*public static Label createAmountLabel(double amount) {
		Label label = createH5Label(formatAmount(amount));
		label.addClassName(LumoStyles.FontFamily.MONOSPACE);
		return label;
	}*/

	public static String formatUnits(int units) {
		return NumberFormat.getIntegerInstance().format(units);
	}

	/*public static Label createUnitsLabel(int units) {
		Label label = new Label(formatUnits(units));
		label.addClassName(LumoStyles.FontFamily.MONOSPACE);
		return label;
	}

	/* === ICONS === */

	/*public static Icon createPrimaryIcon(VaadinIcon icon) {
		Icon i = new Icon(icon);
		setTextColor(TextColor.PRIMARY, i);
		return i;
	}

	public static Icon createSecondaryIcon(VaadinIcon icon) {
		Icon i = new Icon(icon);
		setTextColor(TextColor.SECONDARY, i);
		return i;
	}

	public static Icon createTertiaryIcon(VaadinIcon icon) {
		Icon i = new Icon(icon);
		setTextColor(TextColor.TERTIARY, i);
		return i;
	}

	public static Icon createDisabledIcon(VaadinIcon icon) {
		Icon i = new Icon(icon);
		setTextColor(TextColor.DISABLED, i);
		return i;
	}

	public static Icon createSuccessIcon(VaadinIcon icon) {
		Icon i = new Icon(icon);
		setTextColor(TextColor.SUCCESS, i);
		return i;
	}

	public static Icon createErrorIcon(VaadinIcon icon) {
		Icon i = new Icon(icon);
		setTextColor(TextColor.ERROR, i);
		return i;
	}

	public static Icon createSmallIcon(VaadinIcon icon) {
		Icon i = new Icon(icon);
		i.addClassName(IconSize.S.getClassName());
		return i;
	}

	public static Icon createLargeIcon(VaadinIcon icon) {
		Icon i = new Icon(icon);
		i.addClassName(IconSize.L.getClassName());
		return i;
	}

	// Combinations

	public static Icon createIcon(IconSize size, TextColor color, VaadinIcon icon) {
		Icon i = new Icon(icon);
		i.addClassNames(size.getClassName());
		setTextColor(color, i);
		return i;
	}*/

	/* === NOTIFICATIONS === */

	public static void showNotification(String text) {
		Notification.show(text, 3000, Notification.Position.BOTTOM_CENTER);
	}

	public static void showNotification(String text,
			com.vaadin.flow.component.notification.Notification.Position position) {
		Notification.show(text, 3000, position);
	}

	/* === CSS UTILITIES === */

	/*public static void setAlignItems(AlignItems alignItems, Component... components) {
		for (Component component : components) {
			component.getElement().getStyle().set("align-items", alignItems.getValue());
		}
	}

	public static void setAlignSelf(AlignSelf alignSelf, Component... components) {
		for (Component component : components) {
			component.getElement().getStyle().set("align-self", alignSelf.getValue());
		}
	}*/

	public static void setBackgroundColor(String backgroundColor, Component... components) {
		for (Component component : components) {
			component.getElement().getStyle().set("background-color", backgroundColor);
		}
	}

	/*public static void setBorderRadius(BorderRadius borderRadius, Component... components) {
		for (Component component : components) {
			component.getElement().getStyle().set("border-radius", borderRadius.getValue());
		}
	}

	public static void setBoxSizing(BoxSizing boxSizing, Component... components) {
		for (Component component : components) {
			component.getElement().getStyle().set("box-sizing", boxSizing.getValue());
		}
	}*/

	public static void setColSpan(int span, Component... components) {
		for (Component component : components) {
			component.getElement().setAttribute("colspan", Integer.toString(span));
		}
	}

	public static void setDisplay(String display, Component... components) {
		for (Component component : components) {
			component.getElement().getStyle().set("display", display);
		}
	}

	public static void setFlexGrow(double flexGrow, Component... components) {
		for (Component component : components) {
			component.getElement().getStyle().set("flex-grow", String.valueOf(flexGrow));
		}
	}

	public static void setFlexShrink(double flexGrow, Component... components) {
		for (Component component : components) {
			component.getElement().getStyle().set("flex-shrink", String.valueOf(flexGrow));
		}
	}

	/*public static void setFlexWrap(FlexWrap flexWrap, Component... components) {
		for (Component component : components) {
			component.getElement().getStyle().set("flex-wrap", flexWrap.getValue());
		}
	}*/

	public static void setFontSize(String fontSize, Component... components) {
		for (Component component : components) {
			component.getElement().getStyle().set("font-size", fontSize);
		}
	}

	/*public static void setFontWeight(FontWeight fontWeight, Component... components) {
		for (Component component : components) {
			component.getElement().getStyle().set("font-weight", fontWeight.getValue());
		}
	}

	public static void setJustifyContent(JustifyContent justifyContent, Component... components) {
		for (Component component : components) {
			component.getElement().getStyle().set("justify-content", justifyContent.getValue());
		}
	}

	public static void setLineHeight(LineHeight lineHeight, Component... components) {
		for (Component component : components) {
			component.getElement().getStyle().set("line-height", lineHeight.getValue());
		}
	}*/

	public static void setLineHeight(String value, Component... components) {
		for (Component component : components) {
			component.getElement().getStyle().set("line-height", value);
		}
	}

	public static void setMaxWidth(String value, Component... components) {
		for (Component component : components) {
			component.getElement().getStyle().set("max-width", value);
		}
	}

	public static void setOverflow(String overflow, Component... components) {
		for (Component component : components) {
			component.getElement().getStyle().set("overflow", overflow);
		}
	}

	/*public static void setPointerEvents(PointerEvents pointerEvents, Component... components) {
		for (Component component : components) {
			component.getElement().getStyle().set("pointer-events", pointerEvents.getValue());
		}
	}

	public static void setShadow(Shadow shadow, Component... components) {
		for (Component component : components) {
			component.getElement().getStyle().set("box-shadow", shadow.getValue());
		}
	}

	public static void setTextAlign(TextAlign textAlign, Component... components) {
		for (Component component : components) {
			component.getElement().getStyle().set("text-align", textAlign.getValue());
		}
	}*/

	public static void setTextColor(TextColor textColor, Component... components) {
		for (Component component : components) {
			component.getElement().getStyle().set("color", textColor.getValue());
		}
	}

	/*public static void setTextOverflow(TextOverflow textOverflow, Component... components) {
		for (Component component : components) {
			component.getElement().getStyle().set("text-overflow", textOverflow.getValue());
		}
	}*/

	public static void setTheme(String theme, Component... components) {
		for (Component component : components) {
			component.getElement().setAttribute("theme", theme);
		}
	}

	public static void setTooltip(String tooltip, Component... components) {
		for (Component component : components) {
			component.getElement().setProperty("title", tooltip);
		}
	}

	public static void setWhiteSpace(WhiteSpace whiteSpace, Component... components) {
		for (Component component : components) {
			component.getElement().setProperty("white-space", whiteSpace.getValue());
		}
	}

	public static void setWidth(String value, Component... components) {
		for (Component component : components) {
			component.getElement().getStyle().set("width", value);
		}
	}

	/* === ACCESSIBILITY === */

	public static void setAriaLabel(String value, Component... components) {
		for (Component component : components) {
			component.getElement().setAttribute("aria-label", value);
		}
	}

	/* Mis funciones */
	public static void cargarFoto(Usuario usuario, Image avatar) {
		String src = (IMG_PATH_NO_FOTO);
		/*if (usuario.getFoto() != null) {
			StreamResource sr = new StreamResource("user", () -> {
				return new ByteArrayInputStream(usuario.getFoto());
			});
			sr.setContentType("image/png");
			avatar.setSrc(sr);
		} else {
			avatar.setSrc(src);
		}*/
		avatar.setSrc(src);
	}

	public static Image cargarFotoSinResize(byte[] img, boolean nuevo) {
		Image imagen = new Image();
		String src = (IMG_PATH_NO_IMAGE);
		if (!nuevo) {
			if (img != null) {
				StreamResource sr = new StreamResource("foto", () -> {
					return new ByteArrayInputStream(img);
				});
				sr.setContentType("image/png");
				imagen.setSrc(sr);
			} else {
				imagen.setSrc(src);
			}
		} else {
			imagen.setSrc(src);
		}
		return imagen;
	}

	public static Image cargarFoto(byte[] img, boolean nuevo, String size) {
		Image imagen = new Image();
		String src = (IMG_PATH_NO_IMAGE);
		if (!nuevo) {
			if (img != null) {
				StreamResource sr = new StreamResource("foto", () -> {
					return new ByteArrayInputStream(img);
				});
				sr.setContentType("image/png");
				imagen.setSrc(sr);
			} else {
				imagen.setSrc(src);
			}
		} else {
			imagen.setSrc(src);
		}

		imagen.setWidth(size);
		imagen.setHeight(size);

		return imagen;

	}

	public static Image cargarLogo(byte[] img, String size) {
		Image imagen = new Image();
		String src = (IMG_PATH_NO_IMAGE);

		if (img != null) {
			StreamResource sr = new StreamResource("logo", () -> {
				return new ByteArrayInputStream(img);
			});
			sr.setContentType("image/png");
			imagen.setSrc(sr);
		} else {
			imagen.setSrc(src);
		}

		imagen.setWidth(size);
		imagen.setHeight(size);

		return imagen;

	}

	public static String generatePass(String valor) {
		BCryptPasswordEncoder pe = new BCryptPasswordEncoder();
		return pe.encode(valor);
	}

	public static boolean isMatchedPass(String pass, String encodedPass) {
		BCryptPasswordEncoder pe = new BCryptPasswordEncoder();
		boolean isPasswordMatch = pe.matches(pass, encodedPass);
		return isPasswordMatch;
	}

	/* Para obtener la fecha actual */
	public static Date getDate() {
		Calendar fecha = Calendar.getInstance();
		Date date = new Date(fecha.getTimeInMillis());
		return date;
	}

	public static Timestamp getTimeStamp() {
		Calendar fecha = Calendar.getInstance();
		Timestamp timestamp = new Timestamp(fecha.getTimeInMillis());
		return timestamp;
	}

	/* Para obtener el dia actual */
	public static int getDia() {
		Calendar fecha = Calendar.getInstance();
		int dia = fecha.get(Calendar.DAY_OF_MONTH);
		return dia;
	}

	/* Para obtener el dia de una fecha */
	public static int getDia(Date fechaDate) {
		Calendar fecha = Calendar.getInstance();
		fecha.setTime(fechaDate);
		int dia = fecha.get(Calendar.DAY_OF_MONTH);
		return dia;
	}

	/* Para obtener el mes actual */
	public static int getMes() {
		Calendar fecha = Calendar.getInstance();
		int mes = fecha.get(Calendar.MONTH) + 1;
		return mes;
	}

	/* Para obtener el año actual */
	public static int getAnio() {
		Calendar fecha = Calendar.getInstance();
		int anio = fecha.get(Calendar.YEAR);
		return anio;
	}

	/* Para obtener el mes de la Lista */
	public static int getMesLista(String mes) {
		int salida = 0;
		for (int i = 0; i < Constante.meses.length; i++) {
			if (Constante.meses[i].equals(mes)) {
				salida = i + 1;
				break;
			}
		}

		return salida;
	}

	/*
	 * Para obtener la primera palabra de un String (Usada principalmente para
	 * obtener el primer nombre)
	 */
	public static String getFirstPalabra(String str) {

		int position = str.indexOf(" ");
		if (position == -1) {
			return str;
		} else {
			return str.substring(0, position);
		}

	}

	/* para devolver un double con 2 decimales */
	public static double getDoubleTwoDecimal(double valor) {
		/*
		 * DecimalFormat df = new DecimalFormat("0.00");
		 * df.setRoundingMode(RoundingMode.DOWN); return df.format(valor);
		 */

		BigDecimal bd = new BigDecimal(valor).setScale(2, RoundingMode.DOWN);
		double val = bd.doubleValue();
		return val;
	}

	// Menu
	public static HorizontalLayout createMenuItem(VaadinIcon icon, String text) {
		HorizontalLayout hl = new HorizontalLayout();
		hl.add(new Icon(icon), new H5(text));
		hl.setDefaultVerticalComponentAlignment(Alignment.CENTER);
		return hl;
	}

	// Check si un usuario posee o no un role
	public static boolean checkRole(String role) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) auth.getPrincipal();

		List<String> allowedRoles = Arrays.asList(role);
		return userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.anyMatch(allowedRoles::contains);

		/*
		 * Get the username of the logged in user: getPrincipal() Get the password of
		 * the authenticated user: getCredentials() Get the assigned roles of the
		 * authenticated user: getAuthorities() Get further details of the authenticated
		 * user: getDetails()
		 */
	}

	// Check si un usuario posee o no alguno de los roles
	public static boolean checkRoles(List<String> roles) {
		boolean salida = false;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) auth.getPrincipal();

		boolean control = false;
		for (String role : roles) {
			List<String> allowedRoles = Arrays.asList(role);
			control = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
					.anyMatch(allowedRoles::contains);
			if (control) {
				salida = true;
				break;
			}
		}
		return salida;
	}

	/*public static void nota(String text) {
		Notification notification = new Notification();

		Label label = new Label(text);

		Button btnClose = UIUtils.createPrimaryPrimaryIconButton(VaadinIcon.CLOSE);
		btnClose.addClickListener(e -> {
			notification.close();
		});

		notification.add(label, btnClose);

		label.getStyle().set("margin-right", "0.5rem");
		btnClose.getStyle().set("margin-right", "0.5rem");

		notification.open();
	}*/

	/*public static void notificacionClose(String text, NotificationVariant notificationVariant) {
		Notification notification = new Notification();

		Label label = new Label(text);
		notification.addThemeVariants(notificationVariant);

		Button btnClose = UIUtils.createPrimaryPrimaryIconButton(VaadinIcon.CLOSE);
		btnClose.addClickListener(e -> {
			notification.close();
		});

		notification.add(label, btnClose);

		label.getStyle().set("margin-right", "0.5rem");
		btnClose.getStyle().set("margin-right", "0.5rem");

		notification.open();
	}*/

	/*public static void Noti(String mensaje) {
		Notification notification = new Notification();
		notification.setText(mensaje);
		notification.setDuration(3000);
		notification.addThemeVariants(NotificationVariant.LUMO_PRIMARY);
		notification.open();
	}*/

	/*public static void Noti(String mensaje, NotificationVariant notificationVariant) {
		Notification notification = new Notification();
		notification.setText(mensaje);
		notification.setDuration(3000);
		notification.addThemeVariants(notificationVariant);
		notification.open();
	}*/

	/*public static void NotiError(String mensaje) {
		Notification notification = new Notification();
		notification.setText(mensaje);
		notification.setDuration(3000);
		notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
		notification.open();
	}*/

	/*public static void NotiSucess(String mensaje) {
		Notification notification = new Notification();
		notification.setText(mensaje);
		notification.setDuration(3000);
		notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
		notification.setPosition(Notification.Position.BOTTOM_END);
		notification.open();
	}*/

	// diferencia entre fechas
	public static int diferenciasDeFechas(Date fechaInicial, Date fechaFinal) {
		long fechaInicialMs = fechaInicial.getTime();
		long fechaFinalMs = fechaFinal.getTime();
		long diferencia = fechaFinalMs - fechaInicialMs;
		double dias = Math.floor(diferencia / (1000 * 60 * 60 * 24));
		return ((int) dias);
	}

	public static Div getGsSuffix() {
		Div gsSuffix = new Div();
		gsSuffix.setText("" + "₲ Gs.");

		return gsSuffix;
	}
	
	public static Image getFoto(int linea) {
		Image foto = new Image();
		//String src = (IMG_PATH_NO_IMAGE);
		List<String> srcs = new ArrayList<String>();
		if(linea == 1) {
			srcs.add("images/productos/n1500/bananaIce7.png");
			srcs.add("images/productos/n1500/blueberryIce1.png");
			srcs.add("images/productos/n1500/coolMint3.png");
			srcs.add("images/productos/n1500/grapeIce1.png");
		}else if(linea == 2) {
			srcs.add("images/productos/n600/BananaIce6.png");
			srcs.add("images/productos/n600/BlueberryIce.png");
			srcs.add("images/productos/n600/CoolMint2.png");
			srcs.add("images/productos/n600/GrapeICe.png");
		}
		
		int min = 0;
		int max = srcs.size() - 1;

		Random random = new Random();

		int value = random.nextInt(max + min) + min;
		
		String src = srcs.get(value);	

		

		/*if (img != null) {
			StreamResource sr = new StreamResource("foto", () ->  {
		        return new ByteArrayInputStream(img);
		    });
		    sr.setContentType("image/png");
		    foto.setSrc(sr);
		}else {
			foto.setSrc(src);
		}*/
		foto.setSrc(src);
		
		return foto;

	}
	
	public static boolean isMobileDevice() {
	    WebBrowser webBrowser = VaadinSession.getCurrent().getBrowser();
	    return webBrowser.isAndroid() || webBrowser.isIPhone() || webBrowser.isWindowsPhone();
	}
	
	// Convierte un int a un String con separador de miles
	public static String convertInt(int number) {
		if (number > 0) {
			return NumberFormat.getNumberInstance(Locale.getDefault()).format(number);
		}else {
			return "0";
		}
	}
	
	public static HorizontalLayout createCupon3(int cantidad) {
    	HorizontalLayout hl = new HorizontalLayout();
    	hl.setMargin(false);
    	hl.setPadding(false);
    	Span numberOfNotifications = new Span(String.valueOf(cantidad));
    	
    	numberOfNotifications.getElement().getThemeList().addAll(
                Arrays.asList("badge", "#6200ee", "primary", "small", "pill"));
        numberOfNotifications.getStyle().set("position", "absolute")
                .set("transform", "translate(80%, 0%)");

        Icon icon = new Icon(VaadinIcon.MEDAL);
        icon.setColor("hsl(214, 100%, 48%)");
        hl.add(icon, numberOfNotifications);
        
        return hl;
    }
	
	public static void createNotificationError(String mensaje) {
		Notification notification = Notification.show(mensaje);
		notification.setPosition(Notification.Position.BOTTOM_END);
		notification.addThemeVariants(NotificationVariant.LUMO_ERROR);	
	}
	
	public static void createNotificationSuccess(String mensaje) {
		Notification notification = Notification.show(mensaje);
		notification.setPosition(Notification.Position.BOTTOM_END);
		notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);	
	}
	
	public static void createNotificationCenter(String mensaje) {
		
		Notification notification = new Notification();

		Label label = new Label(mensaje);
		notification.addThemeVariants(NotificationVariant.LUMO_PRIMARY);
		notification.setPosition(Notification.Position.MIDDLE);

		Button btnClose = UIUtils.createPrimaryPrimaryIconButton(VaadinIcon.CLOSE);
		btnClose.addClickListener(e -> {
			notification.close();
		});

		notification.add(label, btnClose);

		label.getStyle().set("margin-right", "0.5rem");
		btnClose.getStyle().set("margin-right", "0.5rem");

		notification.open();
	}
	

}
