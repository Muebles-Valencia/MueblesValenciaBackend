package com.muebles_valencia.servicios.reserva;

import java.util.List;
import java.util.Optional;

import com.muebles_valencia.entidades.reserva.Reserva;

public interface ServicioReserva{
	
	public Reserva save(Reserva reserva);
	
	public List<Reserva> findAll();
	
	public Optional<Reserva> findById(int codigo);
	
	public void deleteById(int codigo);
	
}
