package com.ean.promo.backend.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ean.promo.backend.entity.ComprobanteBeneficiario;
import com.ean.promo.backend.repository.ComprobanteBeneficiarioRepository;
import com.ean.promo.backend.service.ComprobanteBeneficiarioService;

@Service("comprobanteBeneficiarioService")
public class ComprobanteBeneficiarioServiceImpl implements ComprobanteBeneficiarioService{
	
	@Autowired
	@Qualifier("comprobanteBeneficiarioRepository")
	private ComprobanteBeneficiarioRepository comprobanteBeneficiarioRepository;
	
	@Override
	public List<ComprobanteBeneficiario> findByIdBeneficiario(int idBeneficiario) {
		return comprobanteBeneficiarioRepository.searchByIdBeneficiario(idBeneficiario);
	}
	
	@Override
	public List<ComprobanteBeneficiario> findAllComprobanteBeneficiario() {
		return comprobanteBeneficiarioRepository.findAll(Sort.by(Sort.Direction.ASC, "numero_comprobante"));
	}

	@Override
	@Transactional
	public ComprobanteBeneficiario addComprobanteBeneficiario(ComprobanteBeneficiario comprobanteBeneficiario) {
		comprobanteBeneficiarioRepository.save(comprobanteBeneficiario);
		return null;
	}
	
	@Override
	public ComprobanteBeneficiario findExistsComprobanteByNumero(String comprobante) {
		return comprobanteBeneficiarioRepository.searchIfExistsByNumero(comprobante);
	}
	
	@Override
	public List<ComprobanteBeneficiario> findAllComprobanteBeneficiarioByChequeadoFalse() {
		return comprobanteBeneficiarioRepository.findByChequeadoFalse();
	}

}
