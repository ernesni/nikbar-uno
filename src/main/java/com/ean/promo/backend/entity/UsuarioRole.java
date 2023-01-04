package com.ean.promo.backend.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuario_role", uniqueConstraints = @UniqueConstraint(
		columnNames = {"idrole", "idusuario"}), schema = "adm")
@Getter 
@Setter 
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class UsuarioRole {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "user_role_id", unique = true, nullable = false)
	private Integer userRoleId;
	
	@CreatedDate
	@Column(name = "create_at")
	private Timestamp create_at;
		
	@LastModifiedDate
	@Column(name = "last_change_at")
	private Timestamp last_change_at;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="idusuario_auditoria", nullable = false)
	private Usuario usuario;
	
	@ManyToOne(fetch = FetchType.EAGER) //EAGER para que pueda traer los datos por relacion
	@JoinColumn(name = "idusuario", nullable = false)
	private Usuario user;
	
	@ManyToOne(fetch = FetchType.EAGER) //EAGER para que pueda traer los datos por relacion
	@JoinColumn(name = "idrole", nullable = false)
	private Role role;

	public UsuarioRole(Usuario user, Role role) {
		super();
		this.user = user;
		this.role = role;
	}
	
}



