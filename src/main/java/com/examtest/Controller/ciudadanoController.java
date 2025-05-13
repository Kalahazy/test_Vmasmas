package com.examtest.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examtest.Model.ciudadano;
import com.examtest.Model.domicilio;
import com.examtest.Repository.ciudadanoRepository;
import com.examtest.Repository.domicilioRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/ciudadanos")
public class ciudadanoController {
	
	@Autowired	//Inyectar el Repository de ciudadano
	private ciudadanoRepository ciudadanoRepository;
	@Autowired	//Inyectar el Repository de domicilio
	private domicilioRepository domicilioRepository;
	
// ~~~~~ POST ~~~~~
//	@PostMapping
//	public ciudadano addCiudadano(@Valid @RequestBody ciudadano data) {
//		return ciudadanoRepository.save(data);
//	}
	@PostMapping
    public ResponseEntity<?> addCiudadano(@Valid @RequestBody ciudadano data) {
		// Domicilios no son null ni estan vacios
        if (data.getDomicilios() != null && !data.getDomicilios().isEmpty()) {
            for (domicilio domicilio : data.getDomicilios()) {	// Iterar cada domicilio dentro de los domicilios del objeto data
                domicilio.setCiudadano(data);	// Correlacionar "manualmente" cada domicilio "actual" con su respectivo ciudadano
            }
        }
        // Regresa 201 y guarda ciudadano asi como sus domicilios, también lo retorna en el body
        return ResponseEntity.status(HttpStatus.CREATED).body( ciudadanoRepository.save(data) );
    }
	
// ~~~~~ GET ~~~~~
	@GetMapping
	public List<ciudadano> getAllCiudadanos(){	// Obtener todos los datos de los ciudadanos que hay en la DB
		return ciudadanoRepository.findAll();
	}
//	@GetMapping("/{id}")
//	public ciudadano getCiudadanoById(@PathVariable Long id){
//		return ciudadanoRepository.findById(id)
//				.orElseThrow( () -> new RuntimeException("No se encontró el ciudadano con el ID: " + id) );
//	}
	@GetMapping("/{id}") 
    public ResponseEntity<?> getCiudadanoById( @PathVariable Long id ) {
        Optional<ciudadano> ciudadano = ciudadanoRepository.findById(id);
        if ( ciudadano.isEmpty() ) {	//	Id de ciudadano está vacio
        	return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null); // Regresa http 204
        }
        return ResponseEntity.ok( ciudadano.get() );	// Regresa los datos del ciudadano solicitado por su Id
    }
	
// ~~~~~ PUT ~~~~~
//	@PutMapping("/{id}")
//	public ciudadano updateCiudadano(@PathVariable Long id, @RequestBody ciudadano detailsCiudadano){
//		ciudadano ciudadanoUpd = ciudadanoRepository.findById(id)
//		.orElseThrow( () -> new RuntimeException("No se encontró el ciudadano con el ID: " + id) );
//		
//		ciudadanoUpd.setNombre(detailsCiudadano.getNombre());
//		ciudadanoUpd.setFecha_nacimiento(detailsCiudadano.getFecha_nacimiento());
//		ciudadanoUpd.setCurp(detailsCiudadano.getCurp());
//		ciudadanoUpd.setCelular(detailsCiudadano.getCelular());
//		
//		return ciudadanoRepository.save(ciudadanoUpd);
//	}
	@PutMapping("/{id}")
    public ResponseEntity<?> updateCiudadano(@PathVariable Long id, @Valid @RequestBody ciudadano detCiudadano){

        Optional<ciudadano> ciudadano = ciudadanoRepository.findById(id);
        
        if ( ciudadano.isEmpty() ) {
        	return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null); // Regresa http 204
        }

        ciudadano ciudadanoUpd = ciudadano.get();	// Ciudadano si existe -> se puede actualizar

        // Actualizar solo los detalles del ciudadano
        ciudadanoUpd.setNombre(detCiudadano.getNombre());
        ciudadanoUpd.setFecha_nacimiento(detCiudadano.getFecha_nacimiento());
        ciudadanoUpd.setCurp(detCiudadano.getCurp());
        ciudadanoUpd.setCelular(detCiudadano.getCelular());

        try {
            return ResponseEntity.ok( ciudadanoRepository.save(ciudadanoUpd) );	// Guardar el ciudadano actualizado y retornar 200 OK
        } catch (Exception e) {	// Capturar posibles errores al actualizar
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put( "mensaje", "Error al actualizar el ciudadano con ID " + id + ": " + e.getMessage() );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);	// Regresa 500 (asumiendo algún fallo del server) y body con el error
        }
    }
	@PutMapping("/{id}/domicilios")
    public ResponseEntity<?> updateCiudadanoDomicilios(@PathVariable Long id, @Valid @RequestBody List<domicilio> updatedDomicilios) {
        Optional<ciudadano> ciudadano = ciudadanoRepository.findById(id);
        if ( ciudadano.isEmpty() ) {
        	return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);	// Regresa http 204
        	// Map<String, String> responseBody = new HashMap<>();
            // responseBody.put("mensaje", "No se encontró el ciudadano con el ID: " + id);
            // return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        }
        
        List<domicilio> updatedList = new ArrayList<>();	//Lista domicilios actualizados
        List<String> errors = new ArrayList<>();	//Lista mensajes error
        
        for (domicilio domicilioUpdt : updatedDomicilios) {	//Iterando en cada domicilio dentro de la lista recibida en el cuerpo
        	
        	// Domicilio actual del bucle sin ID
            if ( domicilioUpdt.getId() == null ) {
                errors.add("Error: Domicilio en la lista sin ID proporcionado, no se puede actualizar un domicilio sin su ID.");
                continue; // Saltar a la siguiente iteración
            }
            
            Optional<domicilio> existDomicilio = domicilioRepository.findById(domicilioUpdt.getId());
            
            //Domicilio actual del bucle no encontrado
            if ( existDomicilio.isEmpty() ) {
                errors.add("Error: Domicilio con ID " + domicilioUpdt.getId() + " no encontrado en la base de datos.");
                continue;
            }
            
            domicilio domicilioUpd = existDomicilio.get(); // Guardar el domicilio actual del bucle como domicilio a actualizar
            
            //Asegurar que el domicilio actual del bucle pertenece al ciudadano del id en el path
            if ( !domicilioUpd.getCiudadano().getId().equals(id) ) {
                 errors.add("Error: No se permite actualizar; domicilio con ID " + domicilioUpdt.getId() + " no pertenece al ciudadano con ID " + id);
                 continue;
            }

            //Actualizar los campos del domicilio
            domicilioUpd.setCalle(domicilioUpdt.getCalle());
            domicilioUpd.setNumero(domicilioUpdt.getNumero());
            domicilioUpd.setColonia(domicilioUpdt.getColonia());
            domicilioUpd.setCiudad(domicilioUpdt.getCiudad());
            domicilioUpd.setEstado(domicilioUpdt.getEstado());
            domicilioUpd.setCp(domicilioUpdt.getCp());
            
            // Guardar el domicilio actualizado en la base de datos
            try {
                 updatedList.add( domicilioRepository.save(domicilioUpd) );	//Agregar el domicilio a la lista de domicilios actualizados correctamente
            } catch (Exception e) {	// Capturar posibles errores al guardar
                 errors.add( "Error al guardar el domicilio con ID " + domicilioUpdt.getId() + ": " + e.getMessage() );	//Agregar el error a la lista de errores
            }
        }
        
        if ( !errors.isEmpty() ) {	// Lista de errores no vacia, actualización "parcial"
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Procesamiento de domicilios completado. Algunos domicilios no pudieron ser actualizados.");
            response.put("domiciliosActualizadosExitosamente", updatedList);	// Lista de domicilios actualizados
            response.put("erroresEnDomicilios", errors);	// Lista de errores
            return ResponseEntity.ok(response); // Devuelve un 200 OK y el mapa con el msj general y las listas de errores
        } else {	// Todos los domicilios se actualizaron correctamente
            return ResponseEntity.ok(updatedList); // 200 OK y lista de domicilios actualizados
        }
        
    }
	
// ~~~~~ DELETE ~~~~~
//	@DeleteMapping("/{id}")
//	public String deleteCiudadano(@PathVariable Long id) {
//		ciudadano ciudadanoDel = ciudadanoRepository.findById(id)
//				.orElseThrow( () -> new RuntimeException("No se encontró el ciudadano con el ID: " + id) );
//		
//		ciudadanoRepository.delete(ciudadanoDel);
//		return "El ciudadano " + ciudadanoDel.getNombre() + " fue elminidado correctamente";
//	}
	@DeleteMapping("/{id}") 
    public ResponseEntity<?> deleteCiudadano( @PathVariable Long id ) {
        Optional<ciudadano> ciudadano = ciudadanoRepository.findById(id);
        if ( ciudadano.isEmpty() ) {
        	return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);	// Regresa http 204
        }
        ciudadano ciudadanoDel = ciudadano.get();	// Ciudadano encontrado por id, guardamos en ciudadanoDel

        try {
            ciudadanoRepository.delete(ciudadanoDel);	//Funcion que elimina al ciudadano encontrado con el id
            Map<String, String> successResponse = new HashMap<>();
            successResponse.put("mensaje", "El ciudadano " + ciudadanoDel.getNombre() + " fue eliminado correctamente.");
            return ResponseEntity.ok(successResponse);	// 200 OK y mensaje de confirmación
        } catch (Exception e) {	// Capturar posibles errores al actualizar
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("mensaje", "Error al eliminar el ciudadano con ID " + id + ": " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);	// Regresa 500 (asumiendo algún fallo del server) y body con el error
        }
    }

}
