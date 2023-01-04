package com.ean.promo.backend.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ean.promo.backend.entity.ComprobanteBeneficiario;

@Repository("comprobanteBeneficiarioRepository")
public interface ComprobanteBeneficiarioRepository extends JpaRepository<ComprobanteBeneficiario, Serializable>{
	
	@Query("select cb from ComprobanteBeneficiario cb where cb.beneficiario.idbeneficiario = :idBeneficiario "
			+ "order by numero_comprobante") 
	public List<ComprobanteBeneficiario> searchByIdBeneficiario(@Param("idBeneficiario") int idBeneficiario);
	
	@Query("select cb from ComprobanteBeneficiario cb where cb.numero_comprobante = :numero ") 
	public ComprobanteBeneficiario searchIfExistsByNumero(@Param("numero") String numero);
	
	public List<ComprobanteBeneficiario> findByChequeadoFalse();
	
}
