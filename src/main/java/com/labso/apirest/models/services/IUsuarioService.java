package com.labso.apirest.models.services;

import com.labso.apirest.models.entity.Usuario;

public interface IUsuarioService {

	public Usuario findByUsername(String username);
}
