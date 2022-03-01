package com.formacionspring.app.service;

import java.util.List;

import com.formacionspring.app.entity.Producto;

public interface ProductoService {
	
	public List<Producto> findAll();
	public Producto findById(Long id);
	Producto save(Producto producto);
	public void deleteById(Long id);

}
