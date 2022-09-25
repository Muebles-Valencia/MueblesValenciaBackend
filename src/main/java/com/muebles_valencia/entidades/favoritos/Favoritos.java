package com.muebles_valencia.entidades.favoritos;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "favoritos")
public class Favoritos implements Serializable {
	
		private static final long serialVersionUID = 2L;
		
		@Id
		@Column(name = "codigo")
		private Integer codigo;
		
		@Column(name = "nombreProducto")
		private String nombreProducto;
		
		@Column(name = "descripcionProducto")
		private String descripcionProducto;
		
		@Column(name = "imagenProducto")
		private String imagenProducto;
		
		@Column(name = "precioProducto")
		private float precioProducto;
		
		@Column(name = "cantidadCart")
		private int cantidadCart ;
		
		@Column(name = "precioTotal")
		private float precioTotal;

		public Favoritos() {
		
		}

		public Favoritos(Integer codigoFavorito, String nombreProducto, String descripcionProducto,
				String imagenProducto, float precioProducto, int cantidadCart, float precioTotal) {
			super();
			this.codigo = codigoFavorito;
			this.nombreProducto = nombreProducto;
			this.descripcionProducto = descripcionProducto;
			this.imagenProducto = imagenProducto;
			this.precioProducto = precioProducto;
			this.cantidadCart = cantidadCart;
			this.precioTotal = precioTotal;
		}

		
		
		
		
		public Integer getCodigo() {
			return codigo;
		}

		public void setCodigo(Integer codigo) {
			this.codigo = codigo;
		}

		public String getNombreProducto() {
			return nombreProducto;
		}

		public void setNombreProducto(String nombreProducto) {
			this.nombreProducto = nombreProducto;
		}

		public String getDescripcionProducto() {
			return descripcionProducto;
		}

		public void setDescripcionProducto(String descripcionProducto) {
			this.descripcionProducto = descripcionProducto;
		}

		public String getImagenProducto() {
			return imagenProducto;
		}

		public void setImagenProducto(String imagenProducto) {
			this.imagenProducto = imagenProducto;
		}

		public float getPrecioProducto() {
			return precioProducto;
		}

		public void setPrecioProducto(float precioProducto) {
			this.precioProducto = precioProducto;
		}

		public int getCantidadCart() {
			return cantidadCart;
		}

		public void setCantidadCart(int cantidadCart) {
			this.cantidadCart = cantidadCart;
		}

		public float getPrecioTotal() {
			return precioTotal;
		}

		public void setPrecioTotal(float precioTotal) {
			this.precioTotal = precioTotal;
		}

		
	}