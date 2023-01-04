package com.ean.promo.backend.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ean.promo.backend.entity.Departamento;

@Repository("departamentoRepository")
public interface DepartamentoRepository extends JpaRepository<Departamento, Serializable>{

}
