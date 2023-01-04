package com.ean.promo.backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="prueba", schema="adm")
@Getter 
@Setter 
@NoArgsConstructor
public class Prueba {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "idprueba")
	private Integer idprueba;

	@Column(name = "dato")
	private String dato;

}