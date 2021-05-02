package com.labso.apirest.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.labso.apirest.models.entity.Factura;

public interface IFacturaDao extends CrudRepository<Factura, Long>{

}
