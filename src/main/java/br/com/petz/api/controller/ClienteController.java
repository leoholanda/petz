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

import br.com.petz.api.domain.Cliente;
import br.com.petz.api.repository.ClienteRepository;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
	
	@Autowired
	private ClienteRepository clienteRepository;
	/**
	 * Lista todos as clientes
	 * @return
	 */
	@GetMapping
	@ApiOperation(value = "Lista todos os clientes cadastrados")
	public ResponseEntity<List<Cliente>> buscarTodos() {
		List<Cliente> clientes = clienteRepository.findAll();
		return ResponseEntity.ok(clientes);
	}
	
	/**
	 * Busca cliente pelo id
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	@ApiOperation(value = "Busca cliente pelo ID")
	public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id) {
		Optional<Cliente> cliente = clienteRepository.findById(id);
		if(cliente.isPresent()) {
			return ResponseEntity.ok(cliente.get());
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"Identificador não encontrado na base de dados");
		}
	}
	
	/**
	 * Cadastra cliente
	 * @param cliente
	 * @return
	 */
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Cadastra cliente")
	public Cliente cadastrar(@Valid @RequestBody Cliente cliente) {
		Optional<Cliente> clienteExistente = clienteRepository.findByNome(cliente.getNome());
		
		if(clienteExistente.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Existe outro cliente cadastrado com o mesmo nome");
		}
		return clienteRepository.save(cliente);
	}
	
	/**
	 * Atualiza dados do cliente
	 * @param id
	 * @param cliente
	 * @return
	 */
	@PutMapping("/{id}")
	@ApiOperation(value = "Atualiza dados do cliente pelo id")
	public ResponseEntity<Cliente> atualizar(@PathVariable Long id, @RequestBody Cliente cliente) {
		Optional<Cliente> buscaCliente = clienteRepository.findById(id);
		
		buscaCliente.get().setNome(cliente.getNome());
		buscaCliente = Optional.ofNullable(clienteRepository.save(buscaCliente.get()));
		
		return ResponseEntity.ok(buscaCliente.get());
	}
	
	/**
	 * Exclui cliente
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Exclui cliente")
	public ResponseEntity<Cliente> excluir(@PathVariable Long id) {
		Optional<Cliente> clienteExistente = clienteRepository.findById(id);
		
		if(clienteExistente.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"Cliente não encontrado");
		}
		
		clienteRepository.delete(clienteExistente.get());
		return ResponseEntity.ok(clienteExistente.get()); 
	}

}
