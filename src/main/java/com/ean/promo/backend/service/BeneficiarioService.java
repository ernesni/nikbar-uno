package com.ean.promo.backend.service;

import java.util.List;

import com.ean.promo.backend.entity.Beneficiario;

public interface BeneficiarioService {
	
	public abstract List<Beneficiario> findByIdGrupo(int idGrupo);
	
	public abstract Beneficiario findByIdGrupoAndCinBeneficiario(int idGrupo, int cin);

}
