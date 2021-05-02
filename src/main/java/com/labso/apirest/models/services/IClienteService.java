package com.labso.apirest.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.labso.apirest.models.entity.Cliente;
import com.labso.apirest.models.entity.Factura;
import com.labso.apirest.models.entity.Producto;
import com.labso.apirest.models.entity.Region;

public interface IClienteService {

	public List<Cliente> findAll();
	
	// Paginación
	public Page<Cliente> findAll(Pageable pageable);
	
	public Cliente findById(Long id);
	
	public Cliente save(Cliente cliente);
	
	public void delete(Long id);
	
	public List<Region> findAllRegiones();
	
	public Factura findFacturaById(Long id);
	
	public Factura saveFactura(Factura factura);
	
	public void deleteFacturaById(Long id);
	
	public List<Producto> findProductoByNombre(String term);

}
