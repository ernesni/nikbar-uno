package com.ean.promo.backend.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
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
import javax.validation.constraints.Size;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "grupo", schema = "adm")
@Getter 
@Setter 
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Grupo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idgrupo", unique = true, nullable = false)
	private int idgrupo;

	@Column(name = "nombre", unique = true, nullable = false)
	@NotBlank(message = "{nb.blank}")
	@Size(min = 4, max = 40, message = "{nb.size}")
	private String nombre;
	
	//Fecha de creación del usuario
	@CreatedDate
	@Column(name = "create_at")
	private Timestamp create_at;
		
	@LastModifiedDate
	@Column(name = "last_change_at")
	private Timestamp last_change_at;
		    
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="fk_usuario", nullable = false)
	private Usuario usuario;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "grupo", cascade = CascadeType.ALL) 
	private Set<Beneficiario> beneficiarios = new HashSet<Beneficiario>();
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "grupo", cascade = CascadeType.ALL) 
	private Set<ComprobanteUsuario> comprobanteUsuarios = new HashSet<ComprobanteUsuario>();
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "grupo", cascade = CascadeType.ALL) 
	private Set<ComprobanteBeneficiario> comprobanteBeneficiarios = new HashSet<ComprobanteBeneficiario>();
}
