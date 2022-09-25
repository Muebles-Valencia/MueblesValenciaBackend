package com.muebles_valencia.servicios.proximos;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.muebles_valencia.entidades.proximos.Proximos;
import com.muebles_valencia.repositorio.proximos.RepositorioProximos;
@Service
public class ServicioProximoImpl implements ServiciosProximos {
	
	@Autowired
	private RepositorioProximos proximosR;
	
	@Override
	public Iterable<Proximos> findAll() {
		
		return proximosR.findAll();
	}

	@Override
	public Proximos save(Proximos proximos) {
		return proximosR.save(proximos);
	}

	@Override
	public Page<Proximos> findAll(Pageable pageable) {
		
		return proximosR.findAll(pageable);
	}

	@Override
	public Optional<Proximos> findById(Integer codigo) {
		
		return proximosR.findById(codigo);
	}

	@Override
	public void deleteById(Integer codigo) {
		proximosR.deleteById(codigo);
		
	}

}
