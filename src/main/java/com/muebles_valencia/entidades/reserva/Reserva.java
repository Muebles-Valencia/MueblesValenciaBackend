package com.muebles_valencia.entidades.reserva;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "reservas")
public class Reserva implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "codigo_reserva")
	private Integer codigo_reserva;
	
	@Column(name = "nombre_cliente_reserva")
	private String nombre_cliente_reserva;
	
	@Column(name = "nombre_producto_reserva")
	private String nombre_producto_reserva;
	
	@Column(name = "cedula_cliente_reserva")
	private String cedula_cliente_reserva;
	
	@Column(name = "fecha_creacion_reserva")
	private String fecha_creacion_reserva;
	
	@Column(name = "fecha_recoger_reserva")
	private String fecha_recoger_reserva;
	
	@Column(name = "foto_producto_reserva")
	private String foto_producto_reserva;
	
	@Column(name = "estado_reserva")
	private String estado_reserva;
	
	public Reserva() {
		super();
	}

	public Reserva(Integer codigo_reserva, String nombre_cliente_reserva, String nombre_producto_reserva,
			String cedula_cliente_reserva, String fecha_creacion_reserva, String fecha_recoger_reserva,
			String foto_producto_reserva, String estado_reserva) {
		super();
		this.codigo_reserva = codigo_reserva;
		this.nombre_cliente_reserva = nombre_cliente_reserva;
		this.nombre_producto_reserva = nombre_producto_reserva;
		this.cedula_cliente_reserva = cedula_cliente_reserva;
		this.fecha_creacion_reserva = fecha_creacion_reserva;
		this.fecha_recoger_reserva = fecha_recoger_reserva;
		this.foto_producto_reserva = foto_producto_reserva;
		this.estado_reserva = estado_reserva;
	}

	public String getEstado_reserva() {
		return estado_reserva;
	}

	public void setEstado_reserva(String estado_reserva) {
		this.estado_reserva = estado_reserva;
	}

	public Integer getCodigo_reserva() {
		return codigo_reserva;
	}

	public void setCodigo_reserva(Integer codigo_reserva) {
		this.codigo_reserva = codigo_reserva;
	}

	public String getNombre_cliente_reserva() {
		return nombre_cliente_reserva;
	}

	public void setNombre_cliente_reserva(String nombre_cliente_reserva) {
		this.nombre_cliente_reserva = nombre_cliente_reserva;
	}

	public String getCedula_cliente_reserva() {
		return cedula_cliente_reserva;
	}

	public void setCedula_cliente_reserva(String cedula_cliente_reserva) {
		this.cedula_cliente_reserva = cedula_cliente_reserva;
	}

	public String getFecha_creacion_reserva() {
		return fecha_creacion_reserva;
	}

	public void setFecha_creacion_reserva(String fecha_creacion_reserva) {
		this.fecha_creacion_reserva = fecha_creacion_reserva;
	}

	public String getFecha_recoger_reserva() {
		return fecha_recoger_reserva;
	}

	public void setFecha_recoger_reserva(String fecha_recoger_reserva) {
		this.fecha_recoger_reserva = fecha_recoger_reserva;
	}

	public String getFoto_producto_reserva() {
		return foto_producto_reserva;
	}

	public void setFoto_producto_reserva(String foto_producto_reserva) {
		this.foto_producto_reserva = foto_producto_reserva;
	}

	public String getNombre_producto_reserva() {
		return nombre_producto_reserva;
	}

	public void setNombre_producto_reserva(String nombre_producto_reserva) {
		this.nombre_producto_reserva = nombre_producto_reserva;
	}

}
