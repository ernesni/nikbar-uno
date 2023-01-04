package com.ean.promo.backend.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ean.promo.backend.entity.Usuario;

@Repository("usuarioRepository")
public interface UsuarioRepository extends JpaRepository<Usuario, Serializable>{
	
	Usuario findByUsernameIgnoreCase(String username);
	
	public abstract Usuario findByUsername(String username);
	
	@Query("select u from Usuario u where u.idusuario = :idUsuario or u.username = :username or u.contrasena = :pass ") 
	public Usuario searchIfExists(@Param("idUsuario") int idUsuario, @Param("username") String username, @Param("pass") String pass);
	
	@Query("select u from Usuario u where u.idusuario != :idUsuario and u.contrasena = :pass ") 
	public Usuario searchIfExistsChangePass(@Param("idUsuario") int idUsuario, @Param("pass") String pass);
	
}