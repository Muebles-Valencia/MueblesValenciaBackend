package com.muebles_valencia.controlador.categoria;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.muebles_valencia.entidades.categoria.Categoria;
import com.muebles_valencia.entidades.cliente.Cliente;
import com.muebles_valencia.entidades.producto.Producto;
import com.muebles_valencia.repositorio.producto.RepositorioProducto;
import com.muebles_valencia.servicios.categoria.ServicioCategoria;

@RestController
@CrossOrigin(origins = {"https://proyecto-mv.pages.dev", "https://muebleriaback.herokuapp.com",
		"http://localhost:3000", "http://localhost:8080"})
@RequestMapping("/categorias")
public class ControladorCategoria {
	
	@Autowired
	private ServicioCategoria servicioCategorias;
	
	@Autowired
	private RepositorioProducto repoProd;
	
	// Registrar una nueva categoria
	//http://localhost:8080/categorias
	@PostMapping
	public ResponseEntity<?> registrarUsuario(@RequestBody Categoria categoria) {
		categoria.setNombre_categoria(categoria.getNombre_categoria().toLowerCase());
		return ResponseEntity.status(HttpStatus.CREATED).body(servicioCategorias.save(categoria));
	}
	
	// Consultar categoria
	//http://localhost:8080/categorias/buscarCategoria/{codigo}
	@GetMapping("/buscarCategoria/{codigo}")
	public List<?> consultarCategoria(@PathVariable(value = "codigo") Integer codigo) {
		List<Producto> productos = repoProd.findAll();
		List<Producto> prods2 = new ArrayList<>();
		for (Producto producto : productos) {
			if(producto.getId_categoria().getId_categoria() == codigo) {
				prods2.add(producto);
			}
		}
		return prods2;
	}
		
	@GetMapping("/buscarCategoriaCodigo/{codigo}")
	public Categoria consultarCategoriaCodigo(@PathVariable(value = "codigo") Integer codigo) {
		Optional<Categoria> c = servicioCategorias.findById(codigo);
		if(!c.isPresent()) {
			return null;
		}
		return c.get();
	}
	// Editar categoria
	//localhost:8080/categorias/actualizarCategoria/{codigo}
	@PutMapping("/actualizarCategoria/{codigo}")
	public ResponseEntity<?> actualizarCategoria(@RequestBody Categoria categoriaActualizado,@PathVariable(value = "codigo") Integer codigo) {
		
		Optional<Categoria> oCategoria = servicioCategorias.findById(codigo);
		if (!oCategoria.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		BeanUtils.copyProperties(categoriaActualizado, oCategoria.get());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(servicioCategorias.save(oCategoria.get()));
	}
	
	//Eliminar categoria
	//http://localhost:8080/categorias/eliminarCategoria/{codigo}
	@DeleteMapping("/eliminarCategoria/{codigo}")
	public ResponseEntity<?> eliminarCategoria(@PathVariable(value = "codigo")Integer codigo){
		
		if(!servicioCategorias.findById(codigo).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		servicioCategorias.deleteById(codigo);
		return ResponseEntity.ok().build();
	}
	
	//Listar categorias totales
	@GetMapping("/listarCategorias")
	public List<Categoria> listarCategorias(){
		List<Categoria> categorias = StreamSupport.stream(servicioCategorias.findAll().spliterator(),false).collect(Collectors.toList());
		return categorias;
	}
}
