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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "beneficiario", schema = "adm")
@Getter 
@Setter 
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Beneficiario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idbeneficiario", unique = true, nullable = false)
	private int idbeneficiario;
	
	@Column(name = "cin", nullable = false)
	@Min(value = 100000, message = "{nb.min}")
	@Max(value = 100000000, message = "{nb.max}")
	private int cin;

	@Column(name = "nombre", nullable = false)
	@NotBlank(message = "{nb.blank}")
	@Size(min = 3, max = 40, message = "{nb.size}")
	private String nombre;

	@Column(name = "apellido", nullable = false)
	@NotBlank(message = "{nb.blank}")
	@Size(min = 3, max = 40, message = "{nb.size}")
	private String apellido;
	
	//Fecha de creación del usuario
	@CreatedDate
	@Column(name = "create_at")
	private Timestamp create_at;
		
	@LastModifiedDate
	@Column(name = "last_change_at")
	private Timestamp last_change_at;
		    
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="fk_grupo", nullable = false)
	private Grupo grupo;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "beneficiario") 
	private Set<ComprobanteBeneficiario> comprobanteBeneficiarios = new HashSet<ComprobanteBeneficiario>();
	
	public String getNombreCompleto() {
		return nombre + " " + apellido;
	}

	public String getFirstNombreFirstApellido() {
		String salida;
		int indice = nombre.indexOf(" ");
		if (indice == -1) {
			salida = nombre; 
		}else {
			salida = nombre.substring(0, indice);
		}
		indice = apellido.indexOf(" ");
		if (indice == -1) {
			salida += " " + apellido; 
		}else {
			salida += " " + apellido.substring(0, indice);
		}
		
		return salida;
	}
}

