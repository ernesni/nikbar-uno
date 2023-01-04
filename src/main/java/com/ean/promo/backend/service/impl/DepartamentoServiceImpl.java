package com.ean.promo.backend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ean.promo.backend.entity.Departamento;
import com.ean.promo.backend.repository.DepartamentoRepository;
import com.ean.promo.backend.service.DepartamentoService;

@Service("departamentoService")
public class DepartamentoServiceImpl implements DepartamentoService{
	
	@Autowired
	@Qualifier("departamentoRepository")
	private DepartamentoRepository departamentoRepository;

	@Override
	public List<Departamento> findAllDepartamento() {
		return departamentoRepository.findAll(Sort.by(Sort.Direction.ASC, "departamento"));
	}

}
