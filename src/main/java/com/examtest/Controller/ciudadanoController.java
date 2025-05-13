package com.examtest.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examtest.Model.ciudadano;
import com.examtest.Repository.ciudadanoRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/ciudadanos")
public class ciudadanoController {
	
	@Autowired
	private ciudadanoRepository ciudadanoRepository;
	
// ~~~~~ POST ~~~~~
	@PostMapping
	public ciudadano addCiudadano(@Valid @RequestBody ciudadano data) {
		return ciudadanoRepository.save(data);
	}
	
// ~~~~~ GET ~~~~~
	@GetMapping
	public List<ciudadano> getAllCiudadanos(){
		return ciudadanoRepository.findAll();
	}
	@GetMapping("/{id}")
	public ciudadano getCiudadanoById(@PathVariable Long id){
		return ciudadanoRepository.findById(id)
				.orElseThrow( () -> new RuntimeException("No se encontró el ciudadano con el ID: " + id) );
	}
	
// ~~~~~ PUT ~~~~~
	@PutMapping("/{id}")
	public ciudadano updateCiudadano(@PathVariable Long id, @RequestBody ciudadano detailsCiudadano){
		ciudadano ciudadanoUpd = ciudadanoRepository.findById(id)
		.orElseThrow( () -> new RuntimeException("No se encontró el ciudadano con el ID: " + id) );
		
		ciudadanoUpd.setNombre(detailsCiudadano.getNombre());
		ciudadanoUpd.setFechaNacimiento(detailsCiudadano.getFechaNacimiento());
		ciudadanoUpd.setCurp(detailsCiudadano.getCurp());
		ciudadanoUpd.setCelular(detailsCiudadano.getCelular());
		
		return ciudadanoRepository.save(ciudadanoUpd);
	}
	
// ~~~~~ DELETE ~~~~~
	@DeleteMapping("/{id}")
	public String deleteCiudadano(@PathVariable Long id) {
		ciudadano ciudadanoDel = ciudadanoRepository.findById(id)
				.orElseThrow( () -> new RuntimeException("No se encontró el ciudadano con el ID: " + id) );
		
		ciudadanoRepository.delete(ciudadanoDel);
		return "El ciudadano " + ciudadanoDel.getNombre() + " fue elminidado correctamente";
	}

}
