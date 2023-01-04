package com.ean.promo.backend.service;

import com.ean.promo.backend.entity.UsuarioInfo;

public interface UsuarioInfoService {
	
	public abstract UsuarioInfo findByIdUsuario(int id);
	
	public abstract UsuarioInfo addUsuarioInfo(UsuarioInfo usuarioInfo);

}
