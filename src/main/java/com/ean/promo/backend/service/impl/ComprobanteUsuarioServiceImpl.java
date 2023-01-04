package com.ean.promo.backend.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ean.promo.backend.entity.ComprobanteUsuario;
import com.ean.promo.backend.repository.ComprobanteUsuarioRepository;
import com.ean.promo.backend.service.ComprobanteUsuarioService;

@Service("comprobanteUsuarioService")
public class ComprobanteUsuarioServiceImpl implements ComprobanteUsuarioService{
	
	@Autowired
	@Qualifier("comprobanteUsuarioRepository")
	private ComprobanteUsuarioRepository comprobanteUsuarioRepository;
	
	@Override
	public List<Object[]> findByIdUsuario(int idUsuario) {
		return comprobanteUsuarioRepository.searchByIdUsuario(idUsuario);
	}
	
	@Override
	public List<ComprobanteUsuario> findAllComprobanteUsuario() {
		return comprobanteUsuarioRepository.findAll(Sort.by(Sort.Direction.ASC, "numero_comprobante"));
	}

	@Override
	@Transactional
	public ComprobanteUsuario addComprobanteUsuario(ComprobanteUsuario comprobanteUsuario) {
		comprobanteUsuarioRepository.save(comprobanteUsuario);
		return null;
	}

	@Override
	public ComprobanteUsuario findExistsComprobanteByNumero(String comprobante) {
		return comprobanteUsuarioRepository.searchIfExistsByNumero(comprobante);
	}

	@Override
	public List<ComprobanteUsuario> findAllComprobanteUsuarioByChequeadoFalse() {
		return comprobanteUsuarioRepository.findByChequeadoFalse();
	}

}
