package br.com.petz.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.petz.api.domain.Pet;
import br.com.petz.api.repository.PetRepository;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/pet")
public class PetController {
	
	@Autowired
	private PetRepository petRepository;
	/**
	 * Lista todos as pets
	 * @return
	 */
	@GetMapping
	@ApiOperation(value = "Lista todos os pets cadastrados")
	public ResponseEntity<List<Pet>> buscarTodos() {
		List<Pet> pets = petRepository.findAll();
		return ResponseEntity.ok(pets);
	}
	
	/**
	 * Busca pet pelo id
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	@ApiOperation(value = "Busca pet pelo ID")
	public ResponseEntity<Pet> buscarPorId(@PathVariable Long id) {
		Optional<Pet> pet = petRepository.findById(id);
		if(pet.isPresent()) {
			return ResponseEntity.ok(pet.get());
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"Identificador não encontrado na base de dados");
		}
	}
	
	/**
	 * Cadastra pet
	 * @param pet
	 * @return
	 */
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Cadastra pet")
	public Pet cadastrar(@Valid @RequestBody Pet pet) {
		return petRepository.save(pet);
	}
	
	/**
	 * Atualiza dados do pet
	 * @param id
	 * @param pet
	 * @return
	 */
	@PutMapping("/{id}")
	@ApiOperation(value = "Atualiza dados do pet pelo id")
	public ResponseEntity<Pet> atualizar(@PathVariable Long id, @RequestBody Pet pet) {
		Optional<Pet> buscaPet = petRepository.findById(id);
		
		buscaPet.get().setNome(pet.getNome());
		buscaPet = Optional.ofNullable(petRepository.save(buscaPet.get()));
		
		return ResponseEntity.ok(buscaPet.get());
	}
	
	/**
	 * Exclui pet
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Exclui pet")
	public ResponseEntity<Pet> excluir(@PathVariable Long id) {
		Optional<Pet> petExistente = petRepository.findById(id);
		
		if(petExistente.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"Pet não encontrado");
		}
		
		petRepository.delete(petExistente.get());
		return ResponseEntity.ok(petExistente.get()); 
	}

}
