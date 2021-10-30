package ar.ed.um.programacion2.domain;

import lombok.Data;

@Data
public class Producto {
	private Long id;

 	private String nombre;
    
    private String descripcion;

    private Long precio;

    private Long cantVendida;

}
