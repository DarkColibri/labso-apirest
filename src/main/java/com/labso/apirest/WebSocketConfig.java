package com.labso.apirest;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		// URL ACCESS
		registry.addEndpoint("/chat-websocket")
		// ORIGEN
		// CORS
		.setAllowedOrigins("http://localhost:4200")
		// Permite con socket utilizar protocolo HTTP para conectarnos al servidor Websocket
		.withSockJS();
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		// ENVIAMOS MENSAJE
		// HABILITAMOS BROKER con el sufijo /chat/
		// Habilita la ruta para escuhar en el @SendTo["/chat/mensaje"]
		// Todo nomnbre de EVENTO tiene que tener el /chat
		registry.enableSimpleBroker("/chat/");
		// DESTINO DEL MENSAJE, Cuando enviamos un mensaje, donde lo vamos a reoger.
		registry.setApplicationDestinationPrefixes("/api");
	}

}
