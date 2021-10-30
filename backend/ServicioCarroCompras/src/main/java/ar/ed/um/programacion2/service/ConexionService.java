package ar.ed.um.programacion2.service;

import java.util.List;

import ar.ed.um.programacion2.domain.Producto;

public interface ConexionService {
	
	public String prueba();

	public List<Producto> obtenerProductos();
}