package com.ean.promo.backend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ean.promo.backend.entity.Ciudad;
import com.ean.promo.backend.repository.CiudadRepository;
import com.ean.promo.backend.service.CiudadService;

@Service("ciudadService")
public class CiudadServiceImpl implements CiudadService{
	
	@Autowired
	@Qualifier("ciudadRepository")
	private CiudadRepository ciudadRepository;

	@Override
	public List<Ciudad> findByDepartamento(int idDepartamento) {
		return ciudadRepository.searchByDepartamento(idDepartamento);
	}

}
