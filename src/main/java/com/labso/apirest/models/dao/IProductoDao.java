package com.labso.apirest.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.labso.apirest.models.entity.Producto;

public interface IProductoDao extends CrudRepository<Producto, Long>{

	@Query("select p from Producto p where p.nombre like %?1%")
	public List<Producto> findByNombre(String term);
	
	// Busca en la columna Nombre y que contenga el string
	public List<Producto> findByNombreContainingIgnoreCase(String term);
	
	// 
	public List<Producto> findByNombreStartingWithIgnoreCase(String term);
}
