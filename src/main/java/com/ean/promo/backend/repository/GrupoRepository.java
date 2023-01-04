package com.ean.promo.backend.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ean.promo.backend.entity.Grupo;

@Repository("grupoRepository")
public interface GrupoRepository extends JpaRepository<Grupo, Serializable>{
	
	@Query("select g from Grupo g where g.usuario.idusuario = :idUsuario "
			+ "order by nombre") 
	public List<Grupo> searchByIdUsuario(@Param("idUsuario") int idUsuario);
	
	@Query(value = "select * from adm.grupo g, adm.beneficiario b "
			+ "where g.idgrupo = b.fk_grupo and b.cin = :cin "
			+ "order by g.nombre" , nativeQuery = true) 
	public List<Grupo> searchByCinBeneficiario(@Param("cin") int cin);
	
	@Query("select g from Grupo g where lower(g.nombre) = lower(:nombre)") 
	public abstract Grupo findByNombre(@Param("nombre") String nombre);
	
}
