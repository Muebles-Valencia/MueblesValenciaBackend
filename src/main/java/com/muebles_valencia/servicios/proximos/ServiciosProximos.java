package com.muebles_valencia.servicios.proximos;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import com.muebles_valencia.entidades.proximos.Proximos;

public interface ServiciosProximos {
	
	public Iterable<Proximos>findAll();
	public Proximos save(Proximos proximos);
	public Page<Proximos> findAll(Pageable pageable);
	public Optional<Proximos> findById(Integer codigo);
	public void deleteById(Integer codigo);
}
