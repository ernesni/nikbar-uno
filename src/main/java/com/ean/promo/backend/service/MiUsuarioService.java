package com.ean.promo.backend.service;

import java.util.List;

import com.ean.promo.backend.entity.Usuario;

public interface MiUsuarioService {

	public abstract List<Usuario> findAllUsuario();
	public abstract Usuario findByUsernameIgnoreCase(String username);
	
	public abstract Usuario findExistUsuario(int idusuario, String username, String password);
	
	public abstract Usuario findExistUsuarioChangePass(int idusuario, String password);
	
	public abstract Usuario addUsuario(Usuario usuario);
	
	public abstract Usuario refreshPass(Usuario usuario);

}
