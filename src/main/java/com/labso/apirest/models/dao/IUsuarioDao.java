package com.labso.apirest.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.labso.apirest.models.entity.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Long>{
	
	// JPA: A través del nombre del método (Query method name) se ejecutará la consulta JPQL;
	// SELECT u FROM Usuario WHERE u.username=?
	public Usuario findByUsername(String username);
	
	@Query("select u from Usuario u where u.username=?1")
	public Usuario findByUsername2(String username);
	
	// SELECT u FROM Usuario WHERE u.username=? AND u.email =?
	//	public Usuario findByUsernameAndEmail(String username, String email);
		

}
