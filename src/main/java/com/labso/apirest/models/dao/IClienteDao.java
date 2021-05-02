package com.labso.apirest.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.labso.apirest.models.entity.Cliente;
import com.labso.apirest.models.entity.Region;

public interface IClienteDao extends JpaRepository<Cliente, Long> {

	// Consulta Region del cliente

	// Mapeamos a una consulta query personalizada.
	@Query("from Region") // --> equivale a "SELECT * FROM Region"
	public List<Region> findAllRegiones();
}
