package com.labso.apirest.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.labso.apirest.models.entity.Mensaje;


public interface IChatDao extends JpaRepository <Mensaje, Long> {

	 public List<Mensaje> findFirst10ByOrderByFechaDesc();
}
