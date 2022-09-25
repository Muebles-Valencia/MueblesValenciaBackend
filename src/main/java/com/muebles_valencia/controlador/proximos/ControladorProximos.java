package com.muebles_valencia.controlador.proximos;

import java.util.List;
import java.util.Optional;

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

import com.muebles_valencia.entidades.producto.Producto;
import com.muebles_valencia.entidades.proximos.Proximos;
import com.muebles_valencia.servicios.proximos.ServiciosProximos;

@RestController
@CrossOrigin(origins = {"https://proyecto-mv.pages.dev", "https://muebleriaback.herokuapp.com",
		"http://localhost:3000", "http://localhost:8080"})
@RequestMapping("/proximos")
public class ControladorProximos {
	
	@Autowired
	private ServiciosProximos proximosServicie;
	
	@PostMapping
	public ResponseEntity<?> registerProximos(@RequestBody Proximos proximos){
		return ResponseEntity.status(HttpStatus.CREATED).body(proximosServicie.save(proximos));
	}
	
	@GetMapping("/listaProximos")
	public List<Proximos> listarProductos(){
		List<Proximos>proximos = null;
		try {
			proximos=(List<Proximos>) proximosServicie.findAll();
			
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		return proximos;
	}
	
	@DeleteMapping("/eliminarProximos/{codigo}")
	public ResponseEntity<?> eliminarCarritoCompra(@PathVariable(value = "codigo")Integer codigo){
		
		if(!proximosServicie.findById(codigo).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		proximosServicie.deleteById(codigo);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/actualizarProximos/{codigo}")
	public ResponseEntity<?> actualizarCarritoCompra(@RequestBody Proximos proximos,@PathVariable(value = "codigo") Integer codigo) {
		
		Optional<Proximos> proximo2 = proximosServicie.findById(codigo);
		if (!proximo2.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		BeanUtils.copyProperties(proximos, proximo2.get());
		
				
		return ResponseEntity.status(HttpStatus.CREATED).body(proximosServicie.save(proximo2.get()));
	}
	
	@GetMapping("/buscar/{id}")
	public Proximos consultarProductos(@PathVariable(value = "id") int codigo) {

		Optional<Proximos> proximo =proximosServicie .findById(codigo);
		if (!proximo.isPresent()) {
			return proximo.get();
		}

		return proximo.get();
	}

}
