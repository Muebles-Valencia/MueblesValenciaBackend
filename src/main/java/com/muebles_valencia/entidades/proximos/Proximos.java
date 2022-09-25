package com.muebles_valencia.entidades.proximos;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="proximos")
public class Proximos implements Serializable {
	
	
	private static final long serialVersionUID = 2L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "codigo")
	private Integer codigo;
	
	@Column(name = "nombre_producto")
	private String nombre_producto;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "imagen_producto")
	private String imagen_producto;
	
	@Column(name = "precioProducto")
	private float precioProducto;

	public Proximos(Integer codigo, String nombre_producto, String descripcion, String imagen_producto,
			float precioProducto) {
		super();
		this.codigo = codigo;
		this.nombre_producto = nombre_producto;
		this.descripcion = descripcion;
		this.imagen_producto = imagen_producto;
		this.precioProducto = precioProducto;
	}

	public Proximos() {
		
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getNombre_producto() {
		return nombre_producto;
	}

	public void setNombre_producto(String nombre_producto) {
		this.nombre_producto = nombre_producto;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getImagen_producto() {
		return imagen_producto;
	}

	public void setImagen_producto(String imagen_producto) {
		this.imagen_producto = imagen_producto;
	}

	public float getPrecioProducto() {
		return precioProducto;
	}

	public void setPrecioProducto(float precioProducto) {
		this.precioProducto = precioProducto;
	}

	@Override
	public String toString() {
		return "Proximos [codigo=" + codigo + ", nombre_producto=" + nombre_producto + ", descripcion=" + descripcion
				+ ", imagen_producto=" + imagen_producto + ", precioProducto=" + precioProducto + "]";
	}
	
	
}
