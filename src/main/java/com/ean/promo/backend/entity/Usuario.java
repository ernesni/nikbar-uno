package com.ean.promo.backend.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuario", schema = "adm")
@Getter 
@Setter 
@NoArgsConstructor
//Para no tener que cargar manualmente la fecha en que se crea un usuario usamos esta anotacion
@EntityListeners(AuditingEntityListener.class)
public class Usuario {

	@Id
	@Column(name = "idusuario", unique = true, nullable = false)
	@Min(value = 100000, message = "{nb.min}")
	@Max(value = 100000000, message = "{nb.max}")
	private int idusuario;

	@Column(name = "usuario", unique = true, nullable = false)
	@NotBlank(message = "{nb.required}")
	@Size(min = 4, max = 45, message = "{nb.size}")
	private String username;
	
	@Column(name = "contrasena", unique = true, nullable = false)
	@NotBlank(message = "{nb.required}")
	@Size(min = 4, max = 60, message = "{nb.size}")
	private String contrasena;

	@Column(name = "password", unique = true, nullable = false, length = 60)
	@NotBlank(message = "{nb.required}")
	//@ValidPassword(message = "{nb.valid}")
	private String password;

	@NotNull
	@Column(name = "enabled", nullable = false)
	private boolean enabled;
	
	//Fecha de creación del usuario
	@CreatedDate
	@Column(name = "create_at")
	private Timestamp create_at;
		
	@LastModifiedDate
	@Column(name = "last_change_at")
	private Timestamp last_change_at;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "usuario") 
	private Set<Role> roles = new HashSet<Role>();

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user") // EAGER para que pueda traer los datos por relacion
	private Set<UsuarioRole> userRole = new HashSet<UsuarioRole>();
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "usuario", cascade = CascadeType.ALL) 
	private Set<UsuarioInfo> usuarioInfos = new HashSet<UsuarioInfo>();
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "usuario") 
	private Set<Grupo> grupos = new HashSet<Grupo>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario") 
	private Set<ComprobanteUsuario> comprobanteUsuarios = new HashSet<ComprobanteUsuario>();
	
	public UsuarioInfo getUsuarioInfo() {
		return usuarioInfos.stream().findFirst().get();
	}

}
