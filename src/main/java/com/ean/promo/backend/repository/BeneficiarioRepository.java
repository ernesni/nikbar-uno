package com.ean.promo.backend.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ean.promo.backend.entity.Beneficiario;

@Repository("beneficiarioRepository")
public interface BeneficiarioRepository extends JpaRepository<Beneficiario, Serializable>{
	
	@Query("select b from Beneficiario b where b.grupo.idgrupo = :idGrupo "
			+ "order by nombre") 
	public List<Beneficiario> searchByIdGrupo(@Param("idGrupo") int idGrupo);
	
	
	@Query(value = "select * from adm.beneficiario b where b.fk_grupo = :idGrupo and b.cin = :cin", nativeQuery = true) 
	public Beneficiario searchByIdGrupoAndCinBeneficiario(@Param("idGrupo") int idGrupo, @Param("cin") int cin);
}
