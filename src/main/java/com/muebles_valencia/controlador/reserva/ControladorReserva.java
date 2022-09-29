package com.muebles_valencia.controlador.reserva;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

import com.muebles_valencia.entidades.cliente.Cliente;
import com.muebles_valencia.entidades.factura.Factura;
import com.muebles_valencia.entidades.reserva.Reserva;
import com.muebles_valencia.servicios.reserva.ServicioReserva;

@RestController
@CrossOrigin(origins = { "https://muebles-valencia.pages.dev", "https://muebleriaback.herokuapp.com",
		"http://localhost:3000", "http://localhost:8080"})
@RequestMapping("/reserva")
public class ControladorReserva {

	@Autowired
	private ServicioReserva servicioReserva;

	@PostMapping("/registrarReserva")
	private ResponseEntity<Reserva> registrarReserva(@RequestBody Reserva reserva) {
		reserva.setEstado_reserva("pendiente");
		return ResponseEntity.status(HttpStatus.CREATED).body(servicioReserva.save(reserva));
	}

	@GetMapping("/buscarReservas/{cedula}")
	private List<Reserva> buscarReservas(@PathVariable(value = "cedula") String cedula) {
		List<Reserva> reservas = servicioReserva.findAll();
		List<Reserva> reservasCliente = new ArrayList<>();

		for (Reserva reserva : reservas) {
			if (reserva.getCedula_cliente_reserva().equalsIgnoreCase(cedula)) {
				reservasCliente.add(reserva);
			}
		}

		return reservasCliente;
	}

	@GetMapping("/buscarReservaIndividual/{id}")
	private Reserva buscarReservaIndividual(@PathVariable(value = "id") int codigo) {
		Optional<Reserva> reserva = servicioReserva.findById(codigo);

		if (!reserva.isPresent()) {
			return reserva.get();
		}
		System.out.println("FACTURA EN CONSULTAR " + reserva.get());
		return reserva.get();
	}

	@PutMapping("/editarReserva/{id}")
	private ResponseEntity<Reserva> editarReserva(@PathVariable(value = "id") int codigo,
			@RequestBody Reserva reserva) {

		List<Reserva> reservas = servicioReserva.findAll();
		for (Reserva reserva2 : reservas) {
			if (reserva2.getCodigo_reserva() == codigo) {
				reserva.setFecha_creacion_reserva(reserva2.getFecha_creacion_reserva());
				reserva2.setCedula_cliente_reserva(reserva.getCedula_cliente_reserva());
				reserva2.setCodigo_reserva(reserva2.getCodigo_reserva());
				reserva2.setFecha_recoger_reserva(reserva.getFecha_recoger_reserva());
				reserva2.setFoto_producto_reserva(reserva.getFoto_producto_reserva());
				reserva2.setNombre_cliente_reserva(reserva.getNombre_cliente_reserva());

				return ResponseEntity.status(HttpStatus.CREATED).body(servicioReserva.save(reserva2));
			}
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@GetMapping("/listarTodasReservas")
	private List<Reserva> listarTodasReservas() {
		return servicioReserva.findAll();
	}

	// Eliminar usuario
	@PutMapping("/cambiarEstadoReserva/{id}")
	public ResponseEntity<?> cambiarEstadoReserva(@PathVariable(value = "id") int codigo) {
		List<Reserva> reservas = servicioReserva.findAll();
		for (Reserva reserva : reservas) {
			if (reserva.getCodigo_reserva() == codigo) {
				if(reserva.getEstado_reserva().equalsIgnoreCase("pendiente")) {
					reserva.setFecha_creacion_reserva(reserva.getFecha_creacion_reserva());
					reserva.setCedula_cliente_reserva(reserva.getCedula_cliente_reserva());
					reserva.setCodigo_reserva(reserva.getCodigo_reserva());
					reserva.setFecha_recoger_reserva(reserva.getFecha_recoger_reserva());
					reserva.setFoto_producto_reserva(reserva.getFoto_producto_reserva());
					reserva.setNombre_cliente_reserva(reserva.getNombre_cliente_reserva());
					reserva.setEstado_reserva("expirado");
					return ResponseEntity.status(HttpStatus.CREATED).body(servicioReserva.save(reserva));
				}else if(reserva.getEstado_reserva().equalsIgnoreCase("expirado")){
					reserva.setFecha_creacion_reserva(reserva.getFecha_creacion_reserva());
					reserva.setCedula_cliente_reserva(reserva.getCedula_cliente_reserva());
					reserva.setCodigo_reserva(reserva.getCodigo_reserva());
					reserva.setFecha_recoger_reserva(reserva.getFecha_recoger_reserva());
					reserva.setFoto_producto_reserva(reserva.getFoto_producto_reserva());
					reserva.setNombre_cliente_reserva(reserva.getNombre_cliente_reserva());
					reserva.setEstado_reserva("pendiente");
					return ResponseEntity.status(HttpStatus.CREATED).body(servicioReserva.save(reserva));
				}
			}
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@GetMapping("/listarReservasPendientes")
	private List<Reserva> listarReservasPendientes() {
		List<Reserva> reserva = servicioReserva.findAll();
		List<Reserva> reservasPendientes = new ArrayList<>();
		
		try {
			for (Reserva reserva2 : reserva) {
				if (reserva2.getEstado_reserva().equalsIgnoreCase("pendiente")) {
					reservasPendientes.add(reserva2);
				}
			}
		} catch (Exception e) {
			System.out.println("ERROR " + e);
		}

		System.out.println("RESERVA EN CONSULTAR PENDIENTE " + reservasPendientes);
		if(reservasPendientes.isEmpty()) {
			Reserva res = new Reserva();
			reservasPendientes.add(res);
			return reservasPendientes;
		}
		return reservasPendientes;
	}

	@GetMapping("/listarReservasExpiradas")
	private List<Reserva> listarReservasExpiradas() {
		List<Reserva> reserva = servicioReserva.findAll();
		List<Reserva> reservasExpiradas = new ArrayList<>();

		for (Reserva reserva2 : reserva) {
			if (reserva2.getEstado_reserva().equalsIgnoreCase("expirado")) {
				reservasExpiradas.add(reserva2);
			}
		}

		System.out.println("RESERVA EN CONSULTAR PENDIENTE " + reservasExpiradas);
		return reservasExpiradas;
	}

	@GetMapping("/reservasPendientesCliente/{cedula}")
	private List<Reserva> buscarReservasPendientesCliente(@PathVariable(value = "cedula") String cedula) {
		List<Reserva> reservas = servicioReserva.findAll();
		List<Reserva> reservasClientePendiente = new ArrayList<>();

		for (Reserva reserva : reservas) {
			if (reserva.getCedula_cliente_reserva().equalsIgnoreCase(cedula)
					&& reserva.getEstado_reserva().equalsIgnoreCase("pendiente")) {
				reservasClientePendiente.add(reserva);
			}
		}

		return reservasClientePendiente;
	}
	
	@GetMapping("/reservasExpiradasCliente/{cedula}")
	private List<Reserva> buscarReservasExpiradasCliente(@PathVariable(value = "cedula") String cedula) {
		List<Reserva> reservas = servicioReserva.findAll();
		List<Reserva> reservasClientePendiente = new ArrayList<>();

		for (Reserva reserva : reservas) {
			if (reserva.getCedula_cliente_reserva().equalsIgnoreCase(cedula)
					&& reserva.getEstado_reserva().equalsIgnoreCase("expirado")) {
				reservasClientePendiente.add(reserva);
			}
		}

		return reservasClientePendiente;
	}
	

}
