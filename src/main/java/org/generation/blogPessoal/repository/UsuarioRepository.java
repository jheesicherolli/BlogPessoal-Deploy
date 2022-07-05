package org.generation.blogPessoal.repository;

import java.util.List;
import java.util.Optional;

import org.generation.blogPessoal.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository 
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	public Optional<Usuario>findByUsuario(String usuario);
	//optional pode trazer valores nulo      //esse usuario se refere ao atributo "usuario" dado na model
	
	public List<Usuario> findAllByNomeContainingIgnoreCase(@Param("nome") String nome);

}
