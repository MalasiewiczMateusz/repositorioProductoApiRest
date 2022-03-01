package com.formacionspring.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.formacionspring.app.dao.ProductoDAO;
import com.formacionspring.app.entity.Producto;

@Service
public class ProductoServiceImpl implements ProductoService {
	
	@Autowired
	private ProductoDAO productoDAO;

	@Override
	@Transactional(readOnly=true)
	public List<Producto> findAll() {
		
		return (List<Producto>) productoDAO.findAll();
	}

	@Override
	public Producto findById(Long id) {
		
		return productoDAO.findById(id).orElse(null);
	}

	@Override
	public Producto save(Producto producto) {
		
		return productoDAO.save(producto);
	}

	@Override
	public void deleteById(Long id) {
		productoDAO.deleteById(id);
		
	}

}
