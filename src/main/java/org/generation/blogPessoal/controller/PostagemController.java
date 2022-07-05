package org.generation.blogPessoal.controller;
//controller = uma classe

import java.util.List;

import javax.validation.Valid;

import org.generation.blogPessoal.model.Postagem;
import org.generation.blogPessoal.repository.PostagemRepository;
import org.generation.blogPessoal.repository.TemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
//mostra que se trata de uma classe controladota da API (onde ficam os endpoints).

@RequestMapping("/postagens")
//Mostra e define por qual URI a classe será acessada.
// indica um endpoint

@CrossOrigin(origins = "*", allowedHeaders = "*") 
//aceita requisições de qualquer origem, ou seja permite que requisições de outras portas sejam aceitas na minha aplicação.
public class PostagemController {

	@Autowired
	//funciona como injeção de dependência, transferindo a responsabilidade
	private PostagemRepository repository;
	
	@Autowired
	private TemaRepository temaRepository;
	
	@GetMapping
	//GetMapping indica o verbo que pode ser utilizado no endpoint
	//PostMapping para postar, criação
	//PutMapping atualiza
	//DeleteMapping deleta 
	//Mapping é para mapear essa função
	
	public ResponseEntity<List<Postagem>> getAll()
	//GetAll é só um nome que dou para minha fnução buscar meus dados, poderia buscaPostagem
	//list serve para pegar uma postagem ou uma lista de postagem
	{
		return ResponseEntity.ok(repository.findAll());
		
	}
	

	@GetMapping("/{id}") //é um caminho específico de pesquisa
	public ResponseEntity<Postagem> getById(@PathVariable Long id)/*@PathVariable = prepara pra receber um valor da minha variável, no caso do ID na minha URI(URL)*/
	/*ResponseEntity: significa representar toda a resposta HTTP*/
	{
		return repository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				//.map blinda a função de trazer ou não um resultado
				//.ok significa que deu tudo certo
		        .orElse(ResponseEntity.notFound().build());
		        //.orElse é basicamente uma correção caso não encontre nada
		
		
	}
	
	@GetMapping("/titulo/{titulo}")
	/*recebe duas barras para não confundir o backend,resultando em uma duplicidade de endpoint, 
	pois a API entende que depois de uma barra vem o último dado*/
	
	public ResponseEntity <List<Postagem>> GetByTitulo(@PathVariable String titulo){
		return ResponseEntity.ok(repository.findAllByTituloContainingIgnoreCase(titulo));
		//retornar tudo que contém em título ignorando se é minúscula ou maiúscula.
	}

	
	@PostMapping
	public ResponseEntity<Postagem> adicionaPostagem(@Valid @RequestBody Postagem postagem)
	//RequestBory = corpo da requisição                                    //esse postagem é um objeto
	{
		if (temaRepository.existsById(postagem.getTema().getId()))
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(postagem));
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

	}
	
	@PutMapping
	public ResponseEntity<Postagem> atualizarPostagem(@Valid @RequestBody Postagem postagem){
		
		if (repository.existsById(postagem.getId())){
		
			if (temaRepository.existsById(postagem.getTema().getId()))
		return ResponseEntity.status(HttpStatus.OK).body(repository.save(postagem));
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity <?> deletePostagem(@PathVariable Long id)
	// quando é void e não deve retornar nada, por isso não é usado o return
	{
		return repository.findById(id)
				.map(resposta -> {
					repository.deleteById(id);
					return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
				})
				.orElse(ResponseEntity.notFound().build());
	}
	
}
