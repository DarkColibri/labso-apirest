package com.labso.apirest.controllers;

import java.util.Date;
//import java.util.Random;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.labso.apirest.models.entity.Mensaje;
import com.labso.apirest.models.services.IChatService;

//import com.labso.apirest.models.entity.Mensajete;
//import com.labso.apirest.models.services.IChatService;


@Controller
public class ChatRestController {
	
private String[] colores = {"red", "green", "blue", "magenta", "purple", "orange"};
	
	@Autowired
	private IChatService chatService;
	
	@Autowired
	private SimpMessagingTemplate webSocket;
	
	// POST /app/mensajes
	// Dónde se envía el mensaje
	@MessageMapping("/mensaje")
	@SendTo("/chat/mensaje") // EVENTO donde se recibe la respuesta. Hay que subscribirse a este evento para escuchar los mensajes.
	public Mensaje recibeMensaje(Mensaje mensaje) {
		mensaje.setFecha(new Date().getTime());
		
//		mensaje.setTexto("Recibido por el broker: " + mensaje.getTexto());
		
		if(mensaje.getTipo().equals("NUEVO_USUARIO")) {
			mensaje.setColor(colores[new Random().nextInt(colores.length)]);
			mensaje.setTexto("nuevo usuario");
		} else {
			chatService.guardar(mensaje);
		}
		
		// El mensaje se envía al EVENTO("/chat/mensaje/")
		// Lo reciven todos los clientes subscritos a este evento.
		return mensaje;
	}

	@MessageMapping("/escribiendo")
	@SendTo("/chat/escribiendo")
	public String estaEscribiendo(String username) {
		return username.concat(" está escribiendo ...");
	}
	
	@MessageMapping("/historial")
	public void historial(String clienteId){
		webSocket.convertAndSend("/chat/historial/" + clienteId, chatService.obtenerUltimos10Mensajes());
	}

}