package com.ean.promo.backend.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ean.promo.backend.entity.Ciudad;

@Repository("ciudadRepository")
public interface CiudadRepository extends JpaRepository<Ciudad, Serializable>{
	
	@Query("select c from Ciudad c where c.departamento.iddepartamento = :idDepartamento "
			+ "order by ciudad") 
	public List<Ciudad> searchByDepartamento(@Param("idDepartamento") int idDepartamento);
	
}
