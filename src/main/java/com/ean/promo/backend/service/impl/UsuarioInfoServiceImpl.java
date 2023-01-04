package com.ean.promo.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ean.promo.backend.entity.UsuarioInfo;
import com.ean.promo.backend.repository.UsuarioInfoRepository;
import com.ean.promo.backend.service.UsuarioInfoService;

@Service("usuarioInfoService")
public class UsuarioInfoServiceImpl implements UsuarioInfoService{
	
	@Autowired
	@Qualifier("usuarioInfoRepository")
	private UsuarioInfoRepository usuarioInfoRepository;

	@Override
	public UsuarioInfo findByIdUsuario(int id) {
		return usuarioInfoRepository.searchByIdUsuario(id);
	}
	
	@Override
	public UsuarioInfo addUsuarioInfo(UsuarioInfo usuarioInfo) {
		return usuarioInfoRepository.save(usuarioInfo);
	}

}
