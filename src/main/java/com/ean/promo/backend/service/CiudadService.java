package com.ean.promo.backend.service;

import java.util.List;

import com.ean.promo.backend.entity.Ciudad;

public interface CiudadService {
	
	public abstract List<Ciudad> findByDepartamento(int idDepartamento);

}
