package com.formacionspring.app.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.formacionspring.app.entity.Producto;


@Repository
public interface ProductoDAO  extends CrudRepository<Producto, Long>{

}
