package com.labso.apirest.auth;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

// CONFIGURACION SERVIDOR DE RECURSOS
// Se encarga de dar acceso a los clientes a los recursos de nuestra aplicacion,
// Siempre que el TOKEn que se esta enviando el cliente en las cabeceras sea un token válido.

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	

	@Override
	public void configure(HttpSecurity http) throws Exception {
		// ACCESO A LAS RUTAS PUBLICAS.
		http.authorizeRequests().antMatchers(HttpMethod.GET, 
				"/api/clientes",
				"/api/clientes/page/**",
				"/api/uploads/img/**",
				"/images/**",
				"/chat-websocket/**",
				"/api/mensaje/**",
				"/api/escribiendo/**",
				"/api/historial/**").permitAll()
		/* 
		 * ACCESOS POR ROLES
		 * .antMatchers(HttpMethod.GET, "/api/clientes/{id}").hasAnyRole("USER", "ADMIN")
		 * .antMatchers(HttpMethod.POST, "/api/clientes/upload").hasAnyRole("USER", "ADMIN")
		 * .antMatchers(HttpMethod.POST, "/api/clientes").hasRole("ADMIN")
		 * .antMatchers("/api/clientes/**").hasRole("ADMIN")
		*/
		
		// RUTAS AUTENTICADAS
		.anyRequest().authenticated()
		.and().cors().configurationSource(corsConfigurationSource());
	}
	
	// CONFIGURA EL CORSS
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
		config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		config.setAllowCredentials(true);
		config.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}
	
	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter(){
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(corsConfigurationSource()));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	}

	
}
