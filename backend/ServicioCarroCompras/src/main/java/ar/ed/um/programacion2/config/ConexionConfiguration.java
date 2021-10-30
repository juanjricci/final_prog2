package ar.ed.um.programacion2.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;


@Configuration
@Data
public class ConexionConfiguration {
	@Value("${parametros.endpoint.url}")
	protected String url;
		
	@Value("${parametros.endpoint.token}")
	protected String token;
}


