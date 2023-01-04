package com.ean.promo.backend.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "role", schema = "adm")
@Getter 
@Setter 
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Role {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "idrole", unique = true, nullable = false)
	private Integer idrole;
	
	@Column(name = "role", nullable = false)
	@NotBlank(message = "{nb.required}")
	@Size(min = 3, max = 45, message = "{nb.size}")
	private String role;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@NotNull
	@Column(name = "enabled", nullable = false)
	private boolean enabled;
	
	@CreatedDate
	@Column(name = "create_at")
	private Timestamp create_at;
		
	@LastModifiedDate
	@Column(name = "last_change_at")
	private Timestamp last_change_at;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="idusuario_auditoria", nullable = false)
	private Usuario usuario;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "role") //EAGER para que pueda traer los datos por relacion
	private Set<UsuarioRole> userRole = new HashSet<UsuarioRole>();
	
}



