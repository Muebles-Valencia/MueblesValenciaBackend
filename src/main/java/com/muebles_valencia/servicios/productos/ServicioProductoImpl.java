package com.muebles_valencia.servicios.productos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.muebles_valencia.entidades.producto.Producto;
import com.muebles_valencia.repositorio.producto.RepositorioProducto;

@Service
public class ServicioProductoImpl implements ServicioProducto {
 

	@Autowired
	private RepositorioProducto productoRepositorio;
	

	@Override
	@Transactional(readOnly = true)
	public Iterable<Producto> findAll(){
		return productoRepositorio.findAll();
	}
	
	@Override
	@Transactional
	public Producto save(Producto producto) {		
		return productoRepositorio.save(producto)  ;
	}
	
	@Transactional(readOnly = true)
	public Optional<Producto> findById(int codigo) {
		return productoRepositorio.findById(codigo);
	}


	@Override
	@Transactional
	public void deleteById(int codigo) {
		productoRepositorio.deleteById(codigo);
	}

	@Transactional(readOnly = true)
	public List<Producto> filtrarPrecio(float minimo, float maximo) {
		List<Producto> productos = productoRepositorio.findAll();
		Optional<Producto> prod = null;
		Producto producto = null;
		ArrayList<Producto> prods2 = new ArrayList<Producto>();;
		for (Producto products : productos) {
			if(products.getPrecio_producto() >= minimo && products.getPrecio_producto() <= maximo) {
				prod = productoRepositorio.findById(products.getCodigo_producto());
				if(prod.isPresent()) {
					System.out.println(prods2);
					producto = new Producto();
					producto.setCodigo_producto(prod.get().getCodigo_producto());
					producto.setFoto_producto(prod.get().getFoto_producto());
					producto.setCantidad_producto(prod.get().getCantidad_producto());
					producto.setNombre_proveedor_producto(prod.get().getNombre_proveedor_producto());
					producto.setId_categoria(prod.get().getId_categoria());
					producto.setDescripcion_producto(prod.get().getDescripcion_producto());
					producto.setNombre_producto(prod.get().getNombre_producto());
					producto.setPrecio_producto(prod.get().getPrecio_producto());
					System.out.println(producto.getNombre_producto());
					prods2.add(producto);
					System.out.println(prods2);
				}else {
					prod = findById(0);
				}
			}else {
				prod = findById(0);
			}
		}
		return prods2;
	}
	@SuppressWarnings("null")
	@Transactional(readOnly = true)
	public List<Producto> filtrarCategoria(int idCategoria){
		System.out.println("ANTES DE LA BUSQUEDA DEL REPOSITORIO");
		List<Producto> productos = productoRepositorio.findAll();
		System.out.println(productos);
		System.out.println("ID QUE MANDO: " + idCategoria);
		Optional<Producto> prod = null;
		Producto producto = null;
		ArrayList<Producto> prods = new ArrayList<Producto>();
		for (Producto products : productos) {
			if(products.getId_categoria().getId_categoria() == idCategoria) {
				prod = productoRepositorio.findById(products.getCodigo_producto());
				if(prod.isPresent()) {
					producto = new Producto();
					producto.setCodigo_producto(prod.get().getCodigo_producto());
					producto.setDescripcion_producto(prod.get().getDescripcion_producto());
					producto.setNombre_producto(prod.get().getNombre_producto());
					producto.setPrecio_producto(prod.get().getPrecio_producto());
					System.out.println(producto.getNombre_producto());
					prods.add(producto);
					System.out.println(prods);
				}else {
					producto = new Producto();
					producto.setCodigo_producto(null);
					producto.setDescripcion_producto(null);
					producto.setNombre_producto(null);
					producto.setPrecio_producto(0);
					System.out.println("No se encontro");
				}
			}else {
				prod = findById(0);
			}
		}
		return prods;
	}

}
