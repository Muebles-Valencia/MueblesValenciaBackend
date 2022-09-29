package com.muebles_valencia.controlador.administrador;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.muebles_valencia.entidades.administrador.Administrador;
import com.muebles_valencia.entidades.cliente.Cliente;
import com.muebles_valencia.servicios.administrador.ServicioAdministrador;
import com.muebles_valencia.servicios.cliente.ServicioCliente;

@RestController
@CrossOrigin(origins = { "https://muebles-valencia.pages.dev", "https://muebleriaback.herokuapp.com",
		"http://localhost:3000", "http://localhost:8080", "null" })
@RequestMapping("/administradores")
public class ControladorAdministrador {

	@Autowired
	private ServicioAdministrador servicioAdministradores;

	@Autowired
	private ServicioCliente servicioCli;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	// Crear un nuevo usuario
	@PostMapping("/registrarAdministrador")
	public ResponseEntity<?> registrarAdministrador(@RequestBody Administrador admin) {
		List<Cliente> clientes = servicioCli.findAll();
		Cliente cli = new Cliente();
		cli.setNombre(admin.getNombre_administrador());
		cli.setCorreo(admin.getCorreo_administrador());
		cli.setContraseña_cliente(passwordEncoder.encode(admin.getContraseña_administrador()));
		cli.setEstado_cliente(2);
		cli.setCedula_cliente(admin.getNombre_administrador());
		servicioCli.save(cli);
		return ResponseEntity.status(HttpStatus.CREATED).body(servicioAdministradores.save(admin));
	}

	// http://localhost:8080/usuarios/iniciarSesion/j@gmail.com/123
	// Iniciar sesion
	@PostMapping("/iniciarSesion/{correo}/{password}")
	public ResponseEntity<?> inicioSesion(@PathVariable(value = "correo") String correo,
			@PathVariable(value = "password") String password) {
		Optional<Administrador> oAdmin = Optional.ofNullable(servicioAdministradores.iniciarSesion(correo, password));
		if (!oAdmin.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(oAdmin);
	}

	// Consultar usuario en la base de datos
	@GetMapping("/consultarAdministrador/{id}")
	public ResponseEntity<?> consultarAdministrador(@PathVariable(value = "id") int codigo) {
		Optional<Administrador> oAdmin = servicioAdministradores.findById(codigo);
		if (!oAdmin.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(oAdmin);
	}

	// Editar usuario
	@PutMapping("/actualizarAdministrador/{id}/{correoComparar}")
	public ResponseEntity<?> actualizarAdministrador(@RequestBody Administrador adminActualizado,
			@PathVariable(value = "id") int codigo, @PathVariable(value = "correoComparar") String correoComparar) {

		Optional<Administrador> oAdmin = servicioAdministradores.findById(codigo);
		if (!oAdmin.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		List<Cliente> clientes = servicioCli.findAll();
		// Este metodo sirve para modificar el objeto que queramos completo sin
		// necesidad de ir propiedad por propiedad
		// BeanUtils.copyProperties(usuarioActualizado, oUsuario.get());

		oAdmin.get().setNombre_administrador(adminActualizado.getNombre_administrador());
		oAdmin.get().setCorreo_administrador(adminActualizado.getCorreo_administrador());
		oAdmin.get().setCodigo_administrador(oAdmin.get().getCodigo_administrador());
		oAdmin.get()
				.setContraseña_administrador(passwordEncoder.encode(adminActualizado.getContraseña_administrador()));
		System.out.println("OADMIN " + oAdmin.get().getCorreo_administrador() + "CORREO ACTUALIZAR "
				+ adminActualizado.getCorreo_administrador());
		for (Cliente cliente : clientes) {
			if (cliente.getCorreo().equalsIgnoreCase(correoComparar)) {
				System.out.println("ENTRA AL CONDICIONAL");
				Optional<Cliente> oUsuario = servicioCli.findById(cliente.getCedula_cliente());

				oUsuario.get().setNombre(adminActualizado.getNombre_administrador());
				oUsuario.get().setCedula_cliente(cliente.getCedula_cliente());
				oUsuario.get().setCorreo(adminActualizado.getCorreo_administrador());
				oUsuario.get().setEstado_cliente(2);
				oUsuario.get()
						.setContraseña_cliente(passwordEncoder.encode(adminActualizado.getContraseña_administrador()));

				servicioCli.save(oUsuario.get());
			}
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(servicioAdministradores.save(oAdmin.get()));
	}

	// Eliminar usuario
	@DeleteMapping("/eliminarAdministrador/{id}")
	public ResponseEntity<?> eliminarAdministrador(@PathVariable(value = "id") int codigo) {
		Optional<Administrador> admin = servicioAdministradores.findById(codigo);
		Optional<Cliente> admin2 = servicioCli.findById(admin.get().getNombre_administrador());
		if (!admin.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		servicioCli.deleteById(admin2.get().getCedula_cliente());
		servicioAdministradores.deleteById(codigo);
		return ResponseEntity.ok().build();
	}

	// Consultar todos los administradores
	@GetMapping("/consultarTodosLosAdministradores")
	public List<Administrador> consultarTodosAdministradores() {
		List<Administrador> administradores = StreamSupport
				.stream(servicioAdministradores.findAll().spliterator(), false).collect(Collectors.toList());
		return administradores;

	}
}
