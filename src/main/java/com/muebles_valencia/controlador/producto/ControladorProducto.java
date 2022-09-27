package com.muebles_valencia.controlador.producto;

import com.muebles_valencia.entidades.categoria.Categoria;
import com.muebles_valencia.entidades.cliente.Cliente;
import com.muebles_valencia.entidades.producto.Producto;
import com.muebles_valencia.entidades.reserva.Reserva;
import com.muebles_valencia.repositorio.categoria.RepositorioCategoria;
import com.muebles_valencia.repositorio.cliente.RepositorioCliente;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.BeanUtils;

//import javax.persistence.criteria.Path;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.muebles_valencia.servicios.categoria.ServicioCategoria;
import com.muebles_valencia.servicios.cliente.ServicioCliente;
import com.muebles_valencia.servicios.correo.EnviadorDeCorreos;
import com.muebles_valencia.servicios.productos.ServicioProducto;
import com.muebles_valencia.servicios.reserva.ServicioReserva;
import com.muebles_valencia.servicios.reserva.ServicioReservaImpl;

@RestController
@CrossOrigin(origins = { "https://proyecto-mv.pages.dev", "https://muebleriaback.herokuapp.com",
		"http://localhost:3000", "http://localhost:8080" })
@RequestMapping("/producto")
public class ControladorProducto {

	@Autowired
	private ServicioProducto servicioProducto;

	@Autowired
	private ServicioCategoria servicioCategoria;

	@Autowired
	private EnviadorDeCorreos enviarCorreo;

	@Autowired
	private RepositorioCategoria repoCategoria;

	@Autowired
	private ServicioReserva servicioReserva;

	@Autowired
	private RepositorioCliente repoCliente;

	// Registrar un producto
	// http://localhost:8080/productos
	@PostMapping
	public ResponseEntity<Producto> registrarProducto(@RequestBody Producto producto) {

//		if(!foto.isEmpty()) {

//			String ruta="C://Temp//uploads";
		Path directorioImagenes = Paths.get("src//main//resources//static/upload");

//			//String rutaAbsoluta=directorioImagenes.toFile().getAbsolutePath();
//			System.out.println("semetio");
//		
//			try {
//				System.out.println("se metio en foto");
//				byte[] byt=foto.getBytes();
//				Path rutaCompleta= Paths.get(ruta + "//" + foto.getOriginalFilename());
//				Files.write(rutaCompleta, byt);
//				producto.setFoto(foto.getOriginalFilename());
//				
//			} catch (Exception e) {
//				System.out.println("se nose metio ");
//			}

		return ResponseEntity.status(HttpStatus.CREATED).body(servicioProducto.save(producto));
	}

	// Listar Productos
	// http://localhost:8080/producto/listaProductos
	@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:8080", "https://pruebadespliege.pages.dev",
			"https://muebleriaback.herokuapp.com", "null", "**" })
	@GetMapping("/listaProductos")
	public List<Producto> listarProductos() {
		List<Producto> productos = null;
		try {
			productos = (List<Producto>) servicioProducto.findAll();

		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		return productos;
	}
	
	@GetMapping("/listaProductosPrincipal")
	public List<Producto> listarProductosPrincipal() {
		List<Producto> productos = (List<Producto>) servicioProducto.findAll();
		List<Producto> prods = new ArrayList<>();
		
		for (Producto producto : productos) {
			if(producto.getEstado().equalsIgnoreCase("1")) {
				prods.add(producto);
			}
		}
		return prods;
	}

	// Reservar Producto
	// http://localhost:8080/producto/reservarProducto/{correo}/{nombre}
	@PostMapping("/reservarProducto/{correo}/{nombre}/{fechaRecoger}")
	public Producto reservarProducto(@PathVariable(value = "correo") String correo,
			@PathVariable(value = "nombre") String nombreCliente,
			@PathVariable(value = "fechaRecoger") String fechaRecoger, @RequestBody Producto prodReservar) {
		try {
			List<Cliente> clientes = repoCliente.findAll();
			for (Cliente cliente : clientes) {
				if (cliente.getCorreo().equalsIgnoreCase(correo)) {
					Reserva reserva = new Reserva();
					reserva.setCedula_cliente_reserva(cliente.getCedula_cliente());
					Date fecha = new Date();
					int mes = fecha.getMonth() + 1;
					int dia = fecha.getDate();
					int anio = 2022;
					String fechaFinal = dia + "--" + mes + "--" + anio;
					reserva.setFecha_creacion_reserva(fechaFinal);
					reserva.setFecha_recoger_reserva(fechaRecoger);
					reserva.setFoto_producto_reserva(prodReservar.getFoto_producto());
					reserva.setNombre_cliente_reserva(nombreCliente);
					reserva.setNombre_producto_reserva(prodReservar.getNombre_producto());
					reserva.setEstado_reserva("pendiente");
					servicioReserva.save(reserva);
					enviarCorreo.sendEmail(correo, nombreCliente, prodReservar);
					enviarCorreo.sendEmailAdmin(
							"Hola orlando o a quien llegue si le ha llegado este correo es porque el cliente "
									+ cliente.getNombre()
									+ "ha reservado un producto, el cual se anexara en el mensaje, por favor guardarlo hasta la fecha "
									+ fechaFinal + ", El numero de cedula del cliente es "
									+ cliente.getCedula_cliente(),
							prodReservar);
				}
			}

		} catch (Exception e) {
			System.out.println("ERROR CONTROLADOR " + e);
		}

		return null;

	}

	// Buscar productos
	// http://localhost:8080/producto/buscar/<codigo_producto>
	@GetMapping("/buscar/{id}")
	public Producto consultarProductos(@PathVariable(value = "id") int codigo) {

		Optional<Producto> producto = servicioProducto.findById(codigo);
		if (!producto.isPresent()) {
			return producto.get();
		}

		return producto.get();
	}

	// EditarProductos
	// http://localhost:8080/productos/<codigo_productos>
	@PutMapping("/actualizar/{id}")
	public ResponseEntity<Producto> actualizarPoductos(@RequestBody Producto productoActualizar,
			@PathVariable(value = "id") int codigo) {
		Optional<Producto> producto = servicioProducto.findById(codigo);
		if (!producto.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		BeanUtils.copyProperties(productoActualizar, producto.get());

		return ResponseEntity.status(HttpStatus.CREATED).body(servicioProducto.save(producto.get()));

	}
	
	@PutMapping("/habilitarProducto/{id}")
	public ResponseEntity<Producto> habilitarProducto(@PathVariable(value = "id") int codigo) {
		Optional<Producto> producto = servicioProducto.findById(codigo);
		System.out.println("ENTRA A LA FUNCION " + producto.get());
		if (!producto.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		if(producto.get().getEstado().equalsIgnoreCase("1")) {
			producto.get().setEstado("2");
		}else {
			producto.get().setEstado("1");
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(servicioProducto.save(producto.get()));

	}

	// Filtrar por precio
	// http://localhost:8080/producto/filtrar/{minimo}/{maximo}
	@GetMapping("/filtrar/{minimo}/{maximo}")
	public List<Producto> filtrarPorPrecio(@PathVariable(value = "minimo") float minimo,
			@PathVariable(value = "maximo") float maximo) {
		List<Producto> productos2 = servicioProducto.filtrarPrecio(minimo, maximo);
		System.out.println("MINIMO: " + minimo);
		System.out.println("MAXIMO: " + maximo);
		if (productos2.isEmpty()) {
			return null;
		}
		System.out.println("PRODS " + productos2);
		return productos2;
	}

	// Filtrar por Nombre
	@PostMapping("/filtrarNombre/{nombre}")
	public List<Producto> filtrarCategoria(@PathVariable(value = "nombre") String nombre) {
		List<Producto> productos = new ArrayList<>();
		List<Producto> listaCategorias = new ArrayList<Producto>();

		System.out.println("ENTRA EN CONTROLADOR PRODUCTO " + nombre);
		productos = (List<Producto>) servicioProducto.findAll();

		for (Producto producto : productos) {
			System.out.println(producto.getNombre_producto());
			System.out.println(nombre);
			if (producto.getNombre_producto().toLowerCase().contains(nombre.toLowerCase())) {

				listaCategorias.add(producto);

				System.out.println("entro en el contains" + listaCategorias);
			}
		}
		if (listaCategorias.isEmpty()) {
			System.out.println("PRODUCTOS VACIOS");
			return listaCategorias;
		}
		System.out.println(listaCategorias);
		return listaCategorias;
	}

	// Consultar categoria de un producto
	@GetMapping("/consultarCategoria/{categoria}")
	public Categoria consultarCategoria(@PathVariable(value = "categoria") int categoria) {
		Categoria categoriaEncontrada = null;
		List<Categoria> categorias = StreamSupport.stream(servicioCategoria.findAll().spliterator(), false)
				.collect(Collectors.toList());

		for (Categoria cat : categorias) {
			if (cat.getId_categoria() == categoria) {
				categoriaEncontrada = new Categoria();
				categoriaEncontrada.setId_categoria(cat.getId_categoria());
				categoriaEncontrada.setNombre_categoria(cat.getNombre_categoria());
			}
		}
		return categoriaEncontrada;
	}

	@PostMapping("/img")
	public ResponseEntity<Producto> guardarImg(@RequestParam("file") MultipartFile foto, Producto producto) {

		if (!foto.isEmpty()) {

			String ruta = "C://Temp//uploads";
			// Path directorioImagenes= Paths.get("src//main//resources//static/upload");

			// String rutaAbsoluta=directorioImagenes.toFile().getAbsolutePath();
			System.out.println("semetio");

			try {
				System.out.println("se metio en foto");
				byte[] byt = foto.getBytes();
				Path rutaCompleta = Paths.get(ruta + "//" + foto.getOriginalFilename());
				Files.write(rutaCompleta, byt);
				producto.setFoto_producto(foto.getOriginalFilename());

			} catch (Exception e) {
				System.out.println("se nose metio ");
			}
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(servicioProducto.save(producto));

	}
}
