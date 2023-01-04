package com.ean.promo.backend.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ean.promo.backend.entity.UsuarioInfo;

@Repository("usuarioInfoRepository")
public interface UsuarioInfoRepository extends JpaRepository<UsuarioInfo, Serializable>{
	
	@Query(value = "select * from adm.usuario_info ui where fk_usuario = :idUsuario", nativeQuery = true) 
	public UsuarioInfo searchByIdUsuario(@Param("idUsuario") int idUsuario);

}
