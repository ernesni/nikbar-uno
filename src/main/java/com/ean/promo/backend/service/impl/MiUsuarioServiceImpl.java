package com.ean.promo.backend.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ean.promo.backend.entity.Role;
import com.ean.promo.backend.entity.Usuario;
import com.ean.promo.backend.entity.UsuarioRole;
import com.ean.promo.backend.repository.RoleRepository;
import com.ean.promo.backend.repository.UsuarioRepository;
import com.ean.promo.backend.repository.UsuarioRoleRepository;
import com.ean.promo.backend.service.MiUsuarioService;

@Service("MiUsuarioService")
public class MiUsuarioServiceImpl implements MiUsuarioService{
	
	@Autowired
	@Qualifier("usuarioRepository")
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	@Qualifier("usuarioRoleRepository")
	private UsuarioRoleRepository usuarioRoleRepository;
	
	@Autowired
	@Qualifier("roleRepository")
	private RoleRepository roleRepository;
	
	@Override
	public List<Usuario> findAllUsuario() {
		return usuarioRepository.findAll(Sort.by(Sort.Direction.ASC, "username"));
	}
	
	@Override
	public Usuario findByUsernameIgnoreCase(String username) {
		return usuarioRepository.findByUsernameIgnoreCase(username);
	}

	@Override
	public Usuario findExistUsuario(int idusuario, String username, String password) {
		return usuarioRepository.searchIfExists(idusuario, username, password);
	}
	
	@Override
	@Transactional
	public Usuario addUsuario(Usuario usuario) {
		Usuario user = usuarioRepository.save(usuario);
		Role role = roleRepository.findByRole("USER");
		UsuarioRole usuarioRole = new UsuarioRole(user, role);
		usuarioRole.setUsuario(user);
		usuarioRoleRepository.save(usuarioRole);
		return user;
	}
	
	@Override
	public Usuario refreshPass(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}

	@Override
	public Usuario findExistUsuarioChangePass(int idusuario, String password) {
		return usuarioRepository.searchIfExistsChangePass(idusuario, password);
	}
	

}
