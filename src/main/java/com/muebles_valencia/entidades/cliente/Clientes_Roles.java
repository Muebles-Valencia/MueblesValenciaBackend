package com.muebles_valencia.entidades.cliente;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "clientes_roles")
public class Clientes_Roles implements Serializable{

	private static final long serialVersionUID = -6844377848096971714L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "puente")
	private Integer puente;
	
	@Column(name = "cedula_cliente")
	private Integer cedula_cliente;
	
	@Column(name = "id")
	private String id;

	public Clientes_Roles() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Clientes_Roles(Integer puente, Integer cedula_cliente, String id) {
		super();
		this.puente = puente;
		this.cedula_cliente = cedula_cliente;
		this.id = id;
	}

	public Integer getPuente() {
		return puente;
	}

	public void setPuente(Integer puente) {
		this.puente = puente;
	}

	public Integer getCedula_cliente() {
		return cedula_cliente;
	}

	public void setCedula_cliente(Integer cedula_cliente) {
		this.cedula_cliente = cedula_cliente;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
}
		