package com.capgemini;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class imagenConfigurer implements WebMvcConfigurer{

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		WebMvcConfigurer.super.addResourceHandlers(registry);
		
		// con esto registramos el recurso que hemos creado para que muestre imagenes
		registry.addResourceHandler("/Recursos/**").addResourceLocations("file:"+"/home/curso/Mascota/Recursos/");
	}
	
}
