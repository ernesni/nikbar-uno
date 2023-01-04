package com.ean.promo.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter 
@NoArgsConstructor
@AllArgsConstructor
public class ComprobanteModel {
	
	private String numero_comprobante;
	private String nombre;
	private String mensaje;
	private int cupon;
	private boolean chequeado;
	private boolean aprobado;
	
}
