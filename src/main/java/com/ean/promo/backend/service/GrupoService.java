package com.ean.promo.backend.service;

import java.util.List;

import com.ean.promo.backend.entity.Grupo;

public interface GrupoService {
	
	public abstract List<Grupo> findAllGrupo();
	
	public abstract List<Grupo> findByIdUsuario(int idUsuario);
	
	public abstract List<Grupo> findByCinBeneficiario(int cin);
	
	public abstract Grupo addGrupo(Grupo grupo);
	
	public abstract Grupo searchByNombre(String nombre);

}
