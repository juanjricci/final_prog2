package ar.ed.um.programacion2.service.impl;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import ar.ed.um.programacion2.config.ConexionConfiguration;
import ar.ed.um.programacion2.domain.Producto;
import ar.ed.um.programacion2.service.ConexionService;

@Service
@Transactional
public class ConexionServiceImpl implements ConexionService {

    private final Logger log = LoggerFactory.getLogger(ConexionServiceImpl.class);
	public String prueba(){
    	return "prueba";
    }
	
@Resource
ConexionConfiguration conexionConfiguration;
	
public List<Producto> obtenerProductos(){
    	
    	String url = conexionConfiguration.getUrl();//"http://127.0.1.1:8080/api/provincias";
    	String token = conexionConfiguration.getToken(); //"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTYzNDA3Mzk0OX0.gt7S1Ouy4epIbpob65NbFXy7uQo3aRuawATCR7Vl6i9LpBMY1LYgq5iborAGjlmQeoencjoL8hyon4clc159UQ";//conexionConfiguration.getToken();     	
    	// RestTemplate es una clase que se encarga de conectarse a un servicio web. es decir nos va a ayduar a hacer peticiones a una url
    	RestTemplate template = new RestTemplate();
    	// EL http entity necesita como parametro headers, lo defnimos aca (no explican mucho mas sobre esto)
    	HttpHeaders headers = new HttpHeaders();
    	//le damos el touken para estar autorizados
    	headers.setBearerAuth(token);
    	//Definimos el tipode de contenido que en nuestro caso es json
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	//aca definimos el entity que vamos a necesitar como parametro en la funcion exchange
    	HttpEntity<String> entity = new HttpEntity<>(headers);
    	
    	//Esta es la funcion en la cual cargamos los parametros, (la url, el metodo http, 
    	//el httpEntity(no explico mucho sobre esto solo que hay que hacerlo), 
    	//y por ultimo como nos va a devolver una lsita de objetos, etnocnes le deciomos el tipo de objeto en el que quiero es Provincia
    	//entonces lo va a guardar en un arreglo de provincias.
    	ResponseEntity<Producto[]> resultado = null; // lo definimos afuera del try, porque si lo definieramos adentro solo viviria en el catch y no fuera
    	try {
    	resultado = template.exchange(url , HttpMethod.GET, entity, Producto[].class); //Con Provincia[].class le decimos que los json que va a traer van a tener la forma de la clase provincia 
    	//Ahora la respuesta de la funcion anterior la tengo que guardar en algun lado, entonces la guardamos en un ResponseEntity
    	}
    	catch (Exception ex) {
    		//TRACE -> DEBUG -> WARN -> INFO (NIVELES DE INFORMACION)
    		log.debug(ex.getMessage());
    		ex.printStackTrace();
    	}
    	// El llamado puede fallar, entonces por recomendacion habria que atraparlo en una esepcion  
    	List<Producto> productos = Arrays.asList(resultado.getBody());
    	
    	return productos;
    }
}
