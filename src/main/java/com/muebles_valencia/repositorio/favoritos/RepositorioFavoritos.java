package com.muebles_valencia.repositorio.favoritos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.muebles_valencia.entidades.favoritos.Favoritos;
import com.muebles_valencia.entidades.producto.Producto;
@Repository
public interface RepositorioFavoritos extends JpaRepository<Favoritos, Integer> {
 
}
