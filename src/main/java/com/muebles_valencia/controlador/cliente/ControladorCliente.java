package com.muebles_valencia.controlador.cliente;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
/**
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
**/
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.muebles_valencia.entidades.cliente.Cliente;
import com.muebles_valencia.entidades.cliente.ClienteDTO;
import com.muebles_valencia.repositorio.cliente.RepositorioCliente;
import com.muebles_valencia.servicios.cliente.ServicioCliente;
import com.muebles_valencia.servicios.correo.EnviadorDeCorreos;

@RestController
@CrossOrigin(origins = {"https://muebles-valencia.pages.dev", "https://muebleriaback.herokuapp.com",
		"http://localhost:3000", "http://localhost:8080" })
@RequestMapping("/clientes")
public class ControladorCliente {

	@Autowired
	private ServicioCliente servicioClientes;

	@Autowired
	private RepositorioCliente repoCliente;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private EnviadorDeCorreos enviarCorreo;

	// Registrar un nuevo usuario
	// http://localhost:8080/clientes
	@PostMapping
	public ResponseEntity<?> registrarUsuario(@RequestBody Cliente cliente) {
		cliente.setEstado_cliente(1);
		cliente.setContraseña_cliente(passwordEncoder.encode(cliente.getContraseña_cliente()));
		List<Cliente> clientes = repoCliente.findAll();
		System.out.println("CORREO " + cliente.getCorreo());
		for (Cliente cliente2 : clientes) {
			if(cliente2.getCedula_cliente().equalsIgnoreCase(cliente.getCedula_cliente()) || cliente2.getCorreo().equalsIgnoreCase(cliente.getCorreo())) {
				return ResponseEntity.status(HttpStatus.CONFLICT).build();
			}
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(servicioClientes.save(cliente));
	}
	
	@PostMapping("/registrarCliente")
	public ResponseEntity<ClienteDTO> registrarClienteDTO(@RequestBody ClienteDTO clientedto) {
		clientedto.setEstado_cliente(1);
		return new ResponseEntity<>(servicioClientes.crearCliente(clientedto),HttpStatus.CREATED);
	}
	
	// http://localhost:8080/clientes/iniciarSesion/j@gmail.com/123
	// Iniciar sesion
	@PostMapping("/iniciarSesion/{correo}/{password}")
	public ResponseEntity<?> inicioSesion(@PathVariable(value = "correo") String correo,
			@PathVariable(value = "password") String password) {
		Optional<Cliente> oCliente = Optional.ofNullable(servicioClientes.iniciarSesion(correo, password));
		if (!oCliente.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(servicioClientes.iniciarSesion(correo, password));
	}

	// Consultar usuario en la base de datos
	@GetMapping("/buscarCliente/{id}")
	public ResponseEntity<?> consultarUsuarios(@PathVariable(value = "id") String identificacion) {
		Cliente cli = null;
		List<Cliente> clientes = servicioClientes.findAll();
		System.out.println("LISTA C " + clientes);
		for (Cliente cliente : clientes) {
			if (cliente.getCedula_cliente().equalsIgnoreCase(identificacion)) {
				cli = new Cliente();
				cli.setNombre(cliente.getNombre());
				cli.setApellido_cliente(cliente.getApellido_cliente());
				cli.setCedula_cliente(cliente.getCedula_cliente());
				cli.setCelular_cliente(cliente.getCelular_cliente());
				cli.setCorreo(cliente.getCorreo());
				cli.setFecha_nacimin_cliente(cliente.getFecha_nacimin_cliente());
				cli.setContraseña_cliente(cliente.getContraseña_cliente());
				cli.setCodigo_cliente(cliente.getCodigo_cliente());
			}
		}
		return ResponseEntity.ok(cli);
	}

	// Editar usuario
	@PutMapping("/{id}")
	public ResponseEntity<?> actualizarUsuario(@RequestBody Cliente usuarioActualizado,
			@PathVariable(value = "id") String identificacion) {

		Optional<Cliente> oUsuario = servicioClientes.findById(identificacion);
		
		if (!oUsuario.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		System.out.println("nombreeeee"+usuarioActualizado.getNombre());
		if(usuarioActualizado.getNombre().equals("") || usuarioActualizado.getNombre() == null) {
			oUsuario.get().setNombre(oUsuario.get().getNombre());
			System.out.println("no encontro el nombre"); 	
		}else {
			oUsuario.get().setNombre(usuarioActualizado.getNombre());
		}
		if(usuarioActualizado.getApellido_cliente().equalsIgnoreCase("") || usuarioActualizado.getApellido_cliente() == null) {
			oUsuario.get().setApellido_cliente(oUsuario.get().getApellido_cliente());
		}else {
			oUsuario.get().setApellido_cliente(usuarioActualizado.getApellido_cliente());
		}
			
		if(usuarioActualizado.getCorreo().equalsIgnoreCase("") || usuarioActualizado.getCorreo() == null) {
			oUsuario.get().setCorreo(oUsuario.get().getCorreo());
		}else {
			oUsuario.get().setCorreo(usuarioActualizado.getCorreo());
		}
		
		if(usuarioActualizado.getCelular_cliente().equalsIgnoreCase("") || usuarioActualizado.getCelular_cliente() == null) {
			oUsuario.get().setCelular_cliente(oUsuario.get().getCelular_cliente());
		}else {
			oUsuario.get().setCelular_cliente(usuarioActualizado.getCelular_cliente());
		}
		
		if(usuarioActualizado.getFecha_nacimin_cliente().equalsIgnoreCase("") || usuarioActualizado.getFecha_nacimin_cliente() == null) {
			oUsuario.get().setFecha_nacimin_cliente(usuarioActualizado.getFecha_nacimin_cliente());
		}else {
			oUsuario.get().setFecha_nacimin_cliente(usuarioActualizado.getFecha_nacimin_cliente());
		}

		if(usuarioActualizado.getContraseña_cliente().equalsIgnoreCase("") || usuarioActualizado.getContraseña_cliente() == null) {
			oUsuario.get().setContraseña_cliente((oUsuario.get().getContraseña_cliente()));
		}else {
			oUsuario.get().setContraseña_cliente(passwordEncoder.encode(usuarioActualizado.getContraseña_cliente()));
		}
		
		oUsuario.get().setCedula_cliente(usuarioActualizado.getCedula_cliente());
		oUsuario.get().setCodigo_cliente(usuarioActualizado.getCodigo_cliente());
		oUsuario.get().setCarrito(usuarioActualizado.getCarrito());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(servicioClientes.save(oUsuario.get()));
	}

	// Eliminar usuario
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminarUsuario(@PathVariable(value = "id") String identificacion) {

		if (!servicioClientes.findById(identificacion).isPresent()) {
			return ResponseEntity.notFound().build();
		}

		servicioClientes.deleteById(identificacion);
		return ResponseEntity.ok().build();
	}

	// Consultar todos los usuarios
	@GetMapping("/listaClientes")
	public List<ClienteDTO> consultarTodosClientes() {
		return servicioClientes.obtenerTodosLosClientes();

	}
	
	String codigo = "";
	
	Optional<Cliente> clienteBuscado = null;
	
	@GetMapping("/recuperarContraseña/{correo}")
	private void recuperarContraseña(@PathVariable(value = "correo")String correo){
		String code = enviarCorreo.sendEmailResetPassword(correo);
		Optional<Cliente> cli = repoCliente.findByCorreo(correo);
		clienteBuscado = cli;
		codigo = code;
	}
	
	@GetMapping("/compararCodigo/{codigo}")
	private HttpStatus compararCodigos(@PathVariable(value = "codigo")String codigoEnviado){
		String codigoGenerado = codigo;
		System.out.println("CODIGO GENERADO " + codigoGenerado);
		if(codigoEnviado.equalsIgnoreCase(codigoGenerado)) {
			System.out.println("ENTRO A LA COMPARACION ");
			return HttpStatus.ACCEPTED;
		}
		return HttpStatus.NOT_ACCEPTABLE;
	}
	
	@PutMapping("/actualizarContraseña/{contraseña}")
	private ResponseEntity<?> actualizarContraseña(@PathVariable(value = "contraseña")String contraseña){
		List<Cliente> clientes = servicioClientes.findAll();
		System.out.println("CONTRASEÑA " + contraseña);
		for (Cliente cliente : clientes) {
			if(cliente.getCedula_cliente().equalsIgnoreCase(clienteBuscado.get().getCedula_cliente())) {
				clienteBuscado.get().setNombre(cliente.getNombre());
				clienteBuscado.get().setApellido_cliente(cliente.getApellido_cliente());
				clienteBuscado.get().setCedula_cliente(cliente.getCedula_cliente());
				clienteBuscado.get().setCelular_cliente(cliente.getCelular_cliente());
				clienteBuscado.get().setCodigo_cliente(cliente.getCodigo_cliente());
				clienteBuscado.get().setContraseña_cliente(passwordEncoder.encode(contraseña));
				clienteBuscado.get().setCorreo(cliente.getCorreo());
				clienteBuscado.get().setEstado_cliente(1);
				clienteBuscado.get().setFecha_nacimin_cliente(cliente.getFecha_nacimin_cliente());
				clienteBuscado.get().setRoles(cliente.getRoles());
				servicioClientes.save(clienteBuscado.get());
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(clienteBuscado.get());
			}
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(clienteBuscado.get());
	}
/**
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<Cliente> clientes = StreamSupport.stream(servicioClientes.findAll().spliterator(), false)
				.collect(Collectors.toList());
		Cliente cliente = null;
		System.out.println("LISTA CLIENTES " +clientes);
		for (Cliente cli : clientes) {
			if (cli.getCorreo().equalsIgnoreCase(username)) {
				cliente = new Cliente();
				cliente.setCorreo(cli.getCorreo());
				cliente.setContraseña_cliente(cli.getContraseña_cliente());
			}
		}
		return new User(cliente.getCorreo(), cliente.getContraseña_cliente(),buildgrante(cliente.getEstado_cliente()));
	}
	
	public List<GrantedAuthority> buildgrante(int estado){
		
		String[] roles = {"CLIENTE" , "EMPLEADO" , "ADMIN" };
		List<GrantedAuthority> auths = new ArrayList<>();
		
		for(int i = 0 ; i < estado ; i++) {
			auths.add(new SimpleGrantedAuthority(roles[i]));
		}
		
		return auths;
	}
	**/
}
