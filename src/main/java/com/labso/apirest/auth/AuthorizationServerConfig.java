package com.labso.apirest.auth;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;


@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	@Qualifier("authenticationManager")
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private InfoAdicionalToken infoAdicionalToken;

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		// ACCESO A SPRING SECURITY
		// GENERAR EL TOKEN.
		// AKI ACCEDEN 
		security.tokenKeyAccess("permitAll()")
		// ACCESO A LA RUTA DE CHEKEO DEL TOKEN
		// AKI SOLO ENTRAN LOS AUTENTICADOS
		.checkTokenAccess("isAuthenticated()");
	}

	// CONFIGURACION DE LA AUTENTICACION DEL CLIENTE (LA APLICACIÓN)
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		// NOMBRE
		clients.inMemory().withClient("angularapp")
		// CONTRASEÑA ENCRIPTADA
		.secret(passwordEncoder.encode("12345"))
		// ALCANCE, PERMISOS DEL CLIENTE, LA APLICACIÓN
		.scopes("read", "write")
		// COMO SE VA A OBTENER EL TOKEN,
		.authorizedGrantTypes("password", "refresh_token")
		// CADUCIDAD DEL TOKEN
		.accessTokenValiditySeconds(3600)
		// TIEMPO EXPIRACION DEL VALID TOKEN
		.refreshTokenValiditySeconds(3600);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(infoAdicionalToken, accessTokenConverter()));
		
		endpoints.authenticationManager(authenticationManager)
		.tokenStore(tokenStore())
		.accessTokenConverter(accessTokenConverter())
		.tokenEnhancer(tokenEnhancerChain);
	}

	@Bean
	public JwtTokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

	// IMPLEMENTACION PARA QUE EL CÓDIGO SECRETO SEA A TRAVÉS DE RCA
	// SISTEMA CRIPTOGRFICO DE CLAVE PLUBLICA Y PRIVADA PARA CIFRAR COMO PARA FIRMAR DIGITALMENTE.
	// PRIVADA: FIRMA TOKEN
	// PUBLICA: PARA VALIDAR TOKEN
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
		jwtAccessTokenConverter.setSigningKey(JwtConfig.RSA_PRIVADA);
		jwtAccessTokenConverter.setVerifierKey(JwtConfig.RSA_PUBLICA);
		return jwtAccessTokenConverter;
	}
	

}
