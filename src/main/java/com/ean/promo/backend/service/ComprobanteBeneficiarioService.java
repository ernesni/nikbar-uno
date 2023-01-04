package com.ean.promo.backend.service;

import java.util.List;

import com.ean.promo.backend.entity.ComprobanteBeneficiario;

public interface ComprobanteBeneficiarioService {
	
	public abstract List<ComprobanteBeneficiario> findAllComprobanteBeneficiario();
	
	public abstract List<ComprobanteBeneficiario> findAllComprobanteBeneficiarioByChequeadoFalse();
	
	public abstract List<ComprobanteBeneficiario> findByIdBeneficiario(int idBeneficiario);
	
	public abstract ComprobanteBeneficiario addComprobanteBeneficiario(ComprobanteBeneficiario comprobanteBeneficiario);
	
	public abstract ComprobanteBeneficiario findExistsComprobanteByNumero(String comprobante);

}
