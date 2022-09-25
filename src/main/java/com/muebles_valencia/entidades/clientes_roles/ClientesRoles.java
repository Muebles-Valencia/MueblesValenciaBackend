package com.muebles_valencia.entidades.clientes_roles;
/**
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "clientes_roles")
public class ClientesRoles implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "puente")
	private Integer puente;
	
	@Column(name = "cedula_cliente")
	private String cedula_cliente;
	
	@Column(name = "id")
	private int id;

	public ClientesRoles(Integer puente, String cedula_cliente, int id) {
		super();
		this.puente = puente;
		this.cedula_cliente = cedula_cliente;
		this.id = id;
	}

	public ClientesRoles() {
		super();
	}

	public Integer getPuente() {
		return puente;
	}

	public void setPuente(Integer puente) {
		this.puente = puente;
	}

	public String getCedula_cliente() {
		return cedula_cliente;
	}

	public void setCedula_cliente(String cedula_cliente) {
		this.cedula_cliente = cedula_cliente;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
**/