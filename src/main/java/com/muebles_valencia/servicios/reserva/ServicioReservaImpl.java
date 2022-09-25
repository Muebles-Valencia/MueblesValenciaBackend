package com.muebles_valencia.servicios.reserva;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muebles_valencia.entidades.reserva.Reserva;
import com.muebles_valencia.repositorio.reserva.RepositorioReserva;

@Service
public class ServicioReservaImpl implements ServicioReserva{

	@Autowired
	private RepositorioReserva repoReserva;
	
	@Override
	public Reserva save(Reserva reserva) {
		return repoReserva.save(reserva);
	}

	@Override
	public List<Reserva> findAll() {
		return repoReserva.findAll();
	}

	@Override
	public Optional<Reserva> findById(int codigo) {
		return repoReserva.findById(codigo);
	}

	@Override
	public void deleteById(int codigo) {
		repoReserva.deleteById(codigo);
	}

}
