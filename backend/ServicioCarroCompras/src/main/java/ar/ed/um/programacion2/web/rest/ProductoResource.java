package ar.ed.um.programacion2.web.rest;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.ed.um.programacion2.domain.Producto;
import ar.ed.um.programacion2.service.ConexionService;

@RestController
@RequestMapping("/api/producto")
public class ProductoResource {
	@Resource
	ConexionService conexionService;
	
    private final Logger log = LoggerFactory.getLogger(ProductoResource.class);
    
    /**
     * GET listarProvincias
     */
    @GetMapping("/listar-productos")
    public String listarProductos() {
    	List<Producto> productos = this.conexionService.obtenerProductos();
    	productos.forEach(System.out::println);
    	String resultado = this.conexionService.prueba();
        return "listarProvincias " + resultado;
    }
    
    @GetMapping("/lista-productos")
    public List<Producto> listarProd() {
    	
    	List<Producto> productos = this.conexionService.obtenerProductos();
        
    	return productos;
    }
    
}