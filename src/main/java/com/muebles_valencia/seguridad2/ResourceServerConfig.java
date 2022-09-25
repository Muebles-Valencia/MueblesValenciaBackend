package com.muebles_valencia.seguridad2;

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

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/clientes/**", "/producto/listaProductos", "/carritoCompras/**",
						"/favoritos/**", "/PDF/pdf/generate","/proximos/listaProximos")
				.permitAll()
//============
				.antMatchers(HttpMethod.GET, "/producto/**", "/proveedores/**", "/categorias/**",
						"/categorias/**", "/facturas/consultarFactura/**",
						"/facturas/facturasCliente/**", "/reserva/buscarReservaIndividual/**" , "/pdf/generate","/proximos/buscar/**")
				.permitAll().antMatchers(HttpMethod.POST, "/oauth/token").permitAll()
				.antMatchers(HttpMethod.POST, "/carritoCompras", "/producto/filtrar/**" , "/facturas/registrarFactura/**","/proximos/**").permitAll()
				.antMatchers(HttpMethod.DELETE, "/carritoCompras/**", "/favoritos/**", "/reserva/eliminarReserva/**","/proximos/eliminarProximos/**")
				.permitAll().antMatchers(HttpMethod.PUT, "/carritoCompras/**", "/categorias/**" , "/reserva/editarReserva/**" , "/administradores/**").permitAll()
				.antMatchers(HttpMethod.POST, "/favoritos").permitAll()
//=============
				.antMatchers(HttpMethod.GET, "/producto/**", "/proveedores/**", "/categorias/**", "/categorias/**",
						"/facturas/consultarFactura/**", "/facturas/facturasCliente/**",
						"/reserva/buscarReservaIndividual/**", "/pdf/generate", "/clientes/recuperarContraseña/**" , "/clientes/compararCodigo/**")
				.permitAll().antMatchers(HttpMethod.POST, "/oauth/token").permitAll()
				.antMatchers(HttpMethod.POST, "/carritoCompras", "/producto/filtrar/**",
						"/facturas/registrarFactura/**")
				.permitAll()
				.antMatchers(HttpMethod.DELETE, "/carritoCompras/**", "/favoritos/**", "/reserva/eliminarReserva/**")
				.permitAll()
				.antMatchers(HttpMethod.PUT, "/carritoCompras/**", "/categorias/**", "/reserva/editarReserva/**",
						"/administradores/**" , "/clientes/actualizarContraseña/**")
				.permitAll().antMatchers(HttpMethod.POST, "/favoritos").permitAll()
//====================
				.antMatchers(HttpMethod.POST, "/producto/filtrarNombre/**", "/clientes/**",
						"producto/reservarProducto/**")
				.permitAll().antMatchers("/api/clientes/**").hasRole("ADMIN").anyRequest().authenticated().and().cors()
				.configurationSource(corsConfigurationSource());

	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://localhost:3000",
				"http://localhost:8080", "http://localhost:8089" , "https://proyecto-mv.pages.dev" , "https://muebleriaback.herokuapp.com"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		configuration.setAllowCredentials(true);
		configuration.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization", "Access-Control-Allow-Origin"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter() {
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(
				new CorsFilter(corsConfigurationSource()));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;

	}
}
