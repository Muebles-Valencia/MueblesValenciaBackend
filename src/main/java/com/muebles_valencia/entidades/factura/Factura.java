package com.muebles_valencia.entidades.factura;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.muebles_valencia.entidades.administrador.Administrador;
import com.muebles_valencia.entidades.carritoCompra.CarritoCompra;
import com.muebles_valencia.entidades.cliente.Cliente;
import com.muebles_valencia.entidades.producto.Producto;

@Entity
@Table(name = "facturas")
public class Factura implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codigo_factura")
	private Integer codigo_factura;
	
	@Column(name = "cantidad_producto")
	private String cantidad_producto;
	
	@Column(name = "fecha_factura")
	private String fecha_factura;
	
	@Column(name = "nombre_cliente")
	private String nombre_cliente;
	
	@Column(name = "cedula_cliente")
	private String cedula_cliente;
	
	@Column(name = "telefono_cliente")
	private String telefono_cliente;

	@Column(name = "nombre_producto")
	private String nombre_producto;
	
	@Column(name = "total_a_pagar")
	private String total_a_pagar;
	
	
	@OneToOne(cascade = CascadeType.ALL)
	@JsonIgnore
	@JoinColumn(name = "id_productos")
	private Producto id_productos;
	
	public Factura() {
	
	}

	public Factura(Integer codigo_factura, String cantidad_producto, String fecha_factura, String nombre_cliente,
			String cedula_cliente, String telefono_cliente, String nombre_producto,
			String total_a_pagar, Producto id_productos) {
		super();
		this.codigo_factura = codigo_factura;
		this.cantidad_producto = cantidad_producto;
		this.fecha_factura = fecha_factura;
		this.nombre_cliente = nombre_cliente;
		this.cedula_cliente = cedula_cliente;
		this.telefono_cliente = telefono_cliente;
		this.nombre_producto = nombre_producto;
		this.total_a_pagar = total_a_pagar;
		this.id_productos = id_productos;
	
	}

	public String getCedula_cliente() {
		return cedula_cliente;
	}

	public void setCedula_cliente(String cedula_cliente) {
		this.cedula_cliente = cedula_cliente;
	}

	public Integer getCodigo_factura() {
		return codigo_factura;
	}

	public void setCodigo_factura(Integer codigo_factura) {
		this.codigo_factura = codigo_factura;
	}

	public void setCantidad_producto(String cantidad_producto) {
		this.cantidad_producto = cantidad_producto;
	}

	public String getCantidad_producto() {
		return cantidad_producto;
	}

	public String getFecha_factura() {
		return fecha_factura;
	}

	public void setFecha_factura(String fecha_factura) {
		this.fecha_factura = fecha_factura;
	}

	public String getNombre_cliente() {
		return nombre_cliente;
	}

	public void setNombre_cliente(String nombre_cliente) {
		this.nombre_cliente = nombre_cliente;
	}

	public String getTelefono_cliente() {
		return telefono_cliente;
	}

	public void setTelefono_cliente(String telefono_cliente) {
		this.telefono_cliente = telefono_cliente;
	}

	public String getNombre_producto() {
		return nombre_producto;
	}

	public void setNombre_producto(String nombre_producto) {
		this.nombre_producto = nombre_producto;
	}

	public String getTotal_a_pagar() {
		return total_a_pagar;
	}

	public void setTotal_a_pagar(String total_a_pagar) {
		this.total_a_pagar = total_a_pagar;
	}

	public Producto getId_productos() {
		return id_productos;
	}

	public void setId_productos(Producto id_productos) {
		this.id_productos = id_productos;
	}
	
}
