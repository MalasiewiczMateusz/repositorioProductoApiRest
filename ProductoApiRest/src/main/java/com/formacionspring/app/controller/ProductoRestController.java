package com.formacionspring.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import com.formacionspring.app.entity.Producto;
import com.formacionspring.app.service.ProductoService;

@RestController
@RequestMapping("/api")
public class ProductoRestController {

	@Autowired
	private ProductoService servicio;
	
	@GetMapping({"/productos","/todos"})
	public List<Producto> index(){
		return servicio.findAll();
	}
	
	@GetMapping("/productos/{id}")
	public ResponseEntity<?>findProductoById(@PathVariable Long id){
		Producto p= null;
		Map<String,Object>response= new HashMap<>();
		
		try {
			p=servicio.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta a la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(p==null) {
			response.put("mensaje", "El producto ID: ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		
		
		return new ResponseEntity<Producto>(p,HttpStatus.OK);
	}
	
	@PostMapping("/producto/guardarProducto")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> SaveProducto(@RequestBody Producto producto)
	{
	Map<String, Object> response = new HashMap<>();

	try {
	servicio.save(producto);
	} catch (DataAccessException e) {



	response.put("mensaje", "Error al realizar la insert a la base de datos");
	response.put("error", e.getMessage().concat(": ".concat(e.getMostSpecificCause().getMessage())));

	return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
	}

	response.put("mensaje", "¡El producto ha sido creado con exito!");
	response.put("producto",producto);

	return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}
	
	
	@PutMapping("/producto/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> upDateProducto(@RequestBody Producto producto, @PathVariable Long id) {
		Producto productoActual = servicio.findById(id);
	Map<String, Object> response = new HashMap<>();
	if (productoActual == null) {
	response.put("mensaje", "El producto ID: ".concat(id.toString().concat(" no existe en la base de datos")));
	return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
	}
	try {
		productoActual.setId(id);
		productoActual.setCodigo(producto.getCodigo());
		productoActual.setTipo(producto.getTipo());
		productoActual.setCantidad(producto.getCantidad());
		productoActual.setPrecio(producto.getPrecio());
		productoActual.setMarca(producto.getMarca());
		productoActual.setFechaIngreso(producto.getFechaIngreso());
		productoActual.setDescripcion(producto.getDescripcion());
		
		servicio.save(productoActual);
		
	} catch (DataAccessException e) { 
	response.put("mensaje", "Error al realizar la actualización a la base de datos");
	response.put("error", e.getMessage().concat(": ".concat(e.getMostSpecificCause().getMessage()))); 
	return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	response.put("mensaje", "¡El producto ha sido actualizado con exito!");
	response.put("producto", productoActual); 
	
	
	return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	
	
	@DeleteMapping("/producto/{id}")
	public ResponseEntity<?> deleteProducto(@PathVariable Long id) {

	Producto productoDelete = servicio.findById(id);
	Map<String, Object> response = new HashMap<>();

	if(productoDelete == null) {
	response.put("mensaje", "Error: no se pudo eliminar el producto con ID: "+id);
	return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
	}

	try {
	servicio.deleteById(id);
	} catch (DataAccessException e) {



	response.put("mensaje", "Error al eliminar la información en la base de datos");
	response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));



	return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	response.put("producto", productoDelete);
	response.put("mensaje", "El cliente ha sido eliminado con éxito");

	return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	
}
