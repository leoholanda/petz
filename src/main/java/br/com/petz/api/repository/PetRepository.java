package br.com.petz.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.petz.api.domain.Pet;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

	Optional<Pet> findByNome(String nome);

}
