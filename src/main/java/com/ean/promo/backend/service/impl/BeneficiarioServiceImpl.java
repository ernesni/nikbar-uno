package com.ean.promo.backend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ean.promo.backend.entity.Beneficiario;
import com.ean.promo.backend.repository.BeneficiarioRepository;
import com.ean.promo.backend.service.BeneficiarioService;

@Service("beneficiarioService")
public class BeneficiarioServiceImpl implements BeneficiarioService{
	
	@Autowired
	@Qualifier("beneficiarioRepository")
	private BeneficiarioRepository beneficiarioRepository;

	@Override
	public List<Beneficiario> findByIdGrupo(int idGrupo) {
		return beneficiarioRepository.searchByIdGrupo(idGrupo);
	}

	@Override
	public Beneficiario findByIdGrupoAndCinBeneficiario(int idGrupo, int cin) {
		return beneficiarioRepository.searchByIdGrupoAndCinBeneficiario(idGrupo, cin);
	}

}
