package com.ean.promo.backend.service;

import java.util.List;

import com.ean.promo.backend.entity.ComprobanteUsuario;

public interface ComprobanteUsuarioService {
	
	public abstract List<ComprobanteUsuario> findAllComprobanteUsuario();
	
	public abstract List<ComprobanteUsuario> findAllComprobanteUsuarioByChequeadoFalse();
	
	public abstract List<Object[]> findByIdUsuario(int idUsuario);
	
	public abstract ComprobanteUsuario addComprobanteUsuario(ComprobanteUsuario comprobanteUsuario);
	
	public abstract ComprobanteUsuario findExistsComprobanteByNumero(String comprobante);

}
