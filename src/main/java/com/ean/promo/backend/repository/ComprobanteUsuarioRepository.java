package com.ean.promo.backend.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ean.promo.backend.entity.ComprobanteUsuario;

@Repository("comprobanteUsuarioRepository")
public interface ComprobanteUsuarioRepository extends JpaRepository<ComprobanteUsuario, Serializable>{
	
	@Query(value = "select cu.numero_comprobante, g.nombre, cu.mensaje, cu.cupon, cu.chequeado, cu.aprobado from adm.comprobante_usuario cu, adm.usuario u, adm.grupo g "
			+ "where cu.fk_usuario = u.idusuario and cu.fk_grupo = g.idgrupo and u.idusuario = :idUsuario "
			+ "union all "
			+ "select cb.numero_comprobante, g.nombre, cb.mensaje, cb.cupon, cb.chequeado, cb.aprobado from adm.comprobante_beneficiario cb, adm.beneficiario b, adm.grupo g "
			+ "where cb.fk_beneficiario = b.idbeneficiario and cb.fk_grupo = g.idgrupo and b.cin = :idUsuario "
			+ "order by numero_comprobante, nombre", nativeQuery = true) 
	public List<Object[]> searchByIdUsuario(@Param("idUsuario") int idUsuario);
	
	@Query("select cu from ComprobanteUsuario cu where cu.numero_comprobante = :numero ") 
	public ComprobanteUsuario searchIfExistsByNumero(@Param("numero") String numero);
	
	public List<ComprobanteUsuario> findByChequeadoFalse();
	
}
