package com.ean.promo.backend.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ean.promo.backend.entity.Grupo;
import com.ean.promo.backend.repository.GrupoRepository;
import com.ean.promo.backend.service.GrupoService;

@Service("grupoService")
public class GrupoServiceImpl implements GrupoService{
	
	@Autowired
	@Qualifier("grupoRepository")
	private GrupoRepository grupoRepository;
	
	@Override
	public List<Grupo> findByIdUsuario(int idUsuario) {
		return grupoRepository.searchByIdUsuario(idUsuario);
	}
	
	@Override
	public List<Grupo> findByCinBeneficiario(int cin) {
		return grupoRepository.searchByCinBeneficiario(cin);
	}
	
	@Override
	public List<Grupo> findAllGrupo() {
		return grupoRepository.findAll(Sort.by(Sort.Direction.ASC, "nombre"));
	}

	@Override
	@Transactional
	public Grupo addGrupo(Grupo grupo) {
		grupoRepository.save(grupo);
		return null;
	}

	@Override
	public Grupo searchByNombre(String nombre) {
		return grupoRepository.findByNombre(nombre);
	}

}
