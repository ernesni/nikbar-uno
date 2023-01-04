package com.ean.promo.backend.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
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
@Table(name = "usuario_info", schema = "adm")
@Getter 
@Setter 
@NoArgsConstructor
//Para no tener que cargar manualmente la fecha en que se crea un usuario usamos esta anotacion
@EntityListeners(AuditingEntityListener.class)
public class UsuarioInfo {

	@Id
	@Column(name = "idusuario_info", unique = true, nullable = false)
	@Min(value = 100000, message = "{nb.min}")
	@Max(value = 100000000, message = "{nb.max}")
	private int idusuario_info;
 
	@Column(name = "nombre", nullable = false)
	@NotBlank(message = "{nb.blank}")
	@Size(min = 3, max = 40, message = "{nb.size}")
	private String nombre;

	@Column(name = "apellido", nullable = false)
	@NotBlank(message = "{nb.blank}")
	@Size(min = 3, max = 40, message = "{nb.size}")
	private String apellido;
	
	@Column(name = "email", nullable = false)
	@NotBlank(message = "{nb.required}")
	@Email
	private String email;

	@Column(name = "sexo", nullable = false)
	@NotBlank(message = "{nb.blank}")
	private String sexo;
	
	@Column(name = "telefono1", nullable = false)
	@NotBlank(message = "{nb.blank}")
	@Size(min = 6, max = 30, message = "{nb.size}")
	private String telefono1;
	
	@Column(name = "opcion_telefono1", nullable = false)
	@NotBlank(message = "{nb.blank}")
	@Size(min = 6, max = 30, message = "{nb.size}")
	private String opcion_telefono1;
	
	@Column(name = "telefono2")
	//@Size(min = 6, max = 30, message = "{nb.size}")
	private String telefono2;
	
	@Column(name = "opcion_telefono2")
	@Size(min = 6, max = 30, message = "{nb..size}")
	private String opcion_telefono2;

	@Column(name = "direccion", nullable = false)
	@NotBlank(message = "{nb.blank}")
	@Size(min = 8, max = 300, message = "{nb.size}")
	private String direccion;
	
	//Fecha de creación del usuario
	@CreatedDate
	@Column(name = "create_at")
	private Timestamp create_at;
		
	@LastModifiedDate
	@Column(name = "last_change_at")
	private Timestamp last_change_at;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="fk_usuario", nullable = false)
	private Usuario usuario;
			
	@NotNull(message = "{nb.blank}")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="fk_ciudad", nullable = false)
	private Ciudad ciudad;
	
	public String getInitials() {
		return (nombre.substring(0, 1) + apellido.substring(0, 1)).toUpperCase();
	}

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

