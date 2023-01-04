package com.ean.promo.ui.util;

import java.util.Locale;

import org.springframework.data.domain.Sort;

public class Constante {
	public static final Locale APP_LOCALE = Locale.US;

	public static final String PAGE_ROOT = "";
	public static final String PAGE_LOGIN = "login";
	public static final String PAGE_USUARIO_INFO = "usuario_info";
	public static final String PAGE_MIS_GRUPOS = "mis_grupos";
	public static final String PAGE_TERCEROS_GRUPOS = "grupos_de_terceros";
	public static final String PAGE_COMPROBANTE = "comprobante";
	public static final String PAGE_CONTROL_USUARIO = "control_usuario";
	public static final String PAGE_CONTROL_BENEFICIARIO = "control_beneficiario";

	public static final String TITLE_USUARIO_INFO = "Usuario Información";
	public static final String TITLE_MIS_GRUPOS = "Mis Grupos";
	public static final String TITLE_TERCEROS_GRUPOS = "Grupos de terceros";
	public static final String TITLE_COMPROBANTE = "Comprobantes";
	public static final String TITLE_CONTROL_USUARIO = "Control usuario";
	public static final String TITLE_CONTROL_BENEFICIARIO = "Control beneficiario";
	
	public static final String TITLE_HOME = "Welcome";
	public static final String TITLE_LOGIN = "NikBar login";
	
	public static final String TITLE_LOGOUT = "Logout";
	public static final String TITLE_NOT_FOUND = "Page was not found";
	public static final String TITLE_ACCESS_DENIED = "Access denied";

	public static final String[] ORDER_SORT_FIELDS = {"dueDate", "dueTime", "id"};
	public static final Sort.Direction DEFAULT_SORT_DIRECTION = Sort.Direction.ASC;

	public static final String VIEWPORT = "width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes";

	// Mutable for testing.
	public static int NOTIFICATION_DURATION = 4000;
	//Nombre que sale en el menú del proyecto, al lado del logo
	public static String NAME_PROYECT_SHORT = "NIKBAR";
	
	public static String[] meses = new String[] {"Enero", "Febrero", "Marzo", "Abril", 
			"Mayo", "Junio", "Julio", "Agosto", "Septiembre", 
			"Octubre", "Noviembre", "Diciembre"};
	
	public static String COLOR_ROJO = "#E74C3C";
	public static String COLOR_AMARILLO = "#F1C40F";
	public static String COLOR_VERDE = "#2ECC71";
	
	public static String URL_FOTO_MONITOR = "C:/apache-tomcat-9.0.55/webapps/images/Nik-Bar-Fotos/";

	
}

