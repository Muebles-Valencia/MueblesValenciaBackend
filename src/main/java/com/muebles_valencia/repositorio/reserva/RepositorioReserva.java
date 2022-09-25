package com.muebles_valencia.repositorio.reserva;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.muebles_valencia.entidades.reserva.Reserva;

@Repository
public interface RepositorioReserva extends JpaRepository<Reserva, Integer>{

}
