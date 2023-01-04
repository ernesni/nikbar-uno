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
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.ean.promo.ui.util.Constante;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "comprobante_beneficiario", schema = "adm")
@Getter 
@Setter 
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ComprobanteBeneficiario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idcomprobante_beneficiario", unique = true, nullable = false)
	private int idcomprobante;

	@NotBlank(message = "{nb.blank}")
	@Size(min = 2, max = 150, message = "{nb.size}")
	@Column(name = "imagen_name", unique = true, nullable = false)
	private String name;
	
	@NotBlank(message = "{nb.blank}")
	@Size(min = 2, max = 5, message = "{nb.size}")
	@Column(name = "imagen_extension", nullable = false)
	private String extension;
	
	@NotBlank(message = "{nb.blank}")
	@Size(min = 2, max = 15, message = "{nb.size}")
	@Column(name = "numero_comprobante", nullable = false)
	private String numero_comprobante;
	
	@DecimalMin(value = "0", inclusive = true, message = "{nb.min}")
	@DecimalMax(value = "100", inclusive = true, message = "{nb.max}")
	@Column(name = "cupon")
	private int cupon;
	
	@NotNull
	@Column(name = "chequeado", nullable = false)
	private boolean chequeado;
	
	@NotNull
	@Column(name = "aprobado", nullable = false)
	private boolean aprobado;
	
	@Column(name = "mensaje")
	@Size(min = 2, max = 50, message = "{nb.size}")
	private String mensaje;
	
	//Fecha de creación del usuario
	@CreatedDate
	@Column(name = "create_at")
	private Timestamp create_at;
		
	@LastModifiedDate
	@Column(name = "last_change_at")
	private Timestamp last_change_at;
		    
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="fk_beneficiario", nullable = false)
	private Beneficiario beneficiario;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="fk_grupo", nullable = false)
	private Grupo grupo;
	
	public String getUrl() {
		return Constante.URL_FOTO_MONITOR + name + extension;
	}
	
}
