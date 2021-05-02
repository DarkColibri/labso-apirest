package com.labso.apirest.models.services;

import java.util.List;

import com.labso.apirest.models.entity.Mensaje;


public interface IChatService {

	public List<Mensaje> obtenerUltimos10Mensajes();
	public Mensaje guardar(Mensaje mensaje);
}
