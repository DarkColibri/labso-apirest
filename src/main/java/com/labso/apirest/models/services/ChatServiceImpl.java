package com.labso.apirest.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.labso.apirest.models.dao.IChatDao;
import com.labso.apirest.models.entity.Mensaje;


@Service
public class ChatServiceImpl implements IChatService{
	
	@Autowired
	private IChatDao chatDao;

	@Override
	public List<Mensaje> obtenerUltimos10Mensajes() {
		return chatDao.findFirst10ByOrderByFechaDesc();
	}

	@Override
	public Mensaje guardar(Mensaje mensaje) {
		return chatDao.save(mensaje);
	}

}
