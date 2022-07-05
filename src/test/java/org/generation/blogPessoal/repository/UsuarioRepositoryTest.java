package org.generation.blogPessoal.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.generation.blogPessoal.model.Usuario;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
/*indica que é uma classe de SpringBootTest, 
 * caso a porta 8080 esteja sendo ocupada ele cria outra porta de entrada automaticamente
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//indica que tem apenas um único ciclo de vida, após a execução ela é quebrada, pra depois poder ser construída de novo
public class UsuarioRepositoryTest {


  @Autowired
  private UsuarioRepository usuarioRepository;
  
  @BeforeAll
   void start() {
	  usuarioRepository.deleteAll();
	  //apaga todos os registros do banco de dados antes de iniciar os testes

	  

		usuarioRepository.save(new Usuario(0L, "João da Silva", "joao@email.com.br", "13465278", "https://i.imgur.com/FETvs2O.jpg"));
		
		usuarioRepository.save(new Usuario(0L, "Manuela da Silva", "manuela@email.com.br", "13465278", "https://i.imgur.com/NtyGneo.jpg"));
		
		usuarioRepository.save(new Usuario(0L, "Adriana da Silva", "adriana@email.com.br", "13465278", "https://i.imgur.com/mB3VM2N.jpg"));

        usuarioRepository.save(new Usuario(0L, "Paulo Antunes", "paulo@email.com.br", "13465278", "https://i.imgur.com/JR7kUFU.jpg"));
      //Persiste (Grava) 4 Objetos Usuario no Banco de dados

  }
  
  @Test 
  @DisplayName("Retorna 1 usuário")
  public void deveRetornaUmUsuario() {

	  Optional<Usuario> usuario = usuarioRepository.findByUsuario("joao@email.com.br");
	//  Executa o método findByUsuario para buscar um usuario pelo nome (joao@email.com.br)

	  
			  assertTrue(usuario.get().getUsuario().equals("joao@gmail.com"));
			  //assertTrue = retorna a verificação do usuario, se for verdadeira retorna true, se não volta com false, falhando o teste
  }
  
  @Test
  @DisplayName("Retorna 3 usuários")
  public void deveRetornarTresUsuarios() {
	  List <Usuario> listaDeUsuarios = usuarioRepository.findAllByNomeContainingIgnoreCase("Silva");
	  /*Executa o método findAllByNomeContainingIgnoreCase para buscar todos os usuarios cujo nome 
	   * contenha a palavra "Silva"
	   */
      assertEquals(3,listaDeUsuarios.size()); 
      /*verifica o tamanho da lista, no exemplo é 3 e só será verdadeira se retornar 
      os 3 usuários cujo nome possua a palavra Silva, se não for verdadeira o teste falha. 
      */ 
	  //size retorna o tamanho da lista
      
      
	  assertTrue(listaDeUsuarios.get(0).getNome().equals("João da Silva"));
	  assertTrue(listaDeUsuarios.get(1).getNome().equals("Manuela da Silva"));
	  assertTrue(listaDeUsuarios.get(2).getNome().equals("Adriana da Silva"));
	  /*serão todos verificados, se a sua posição na lista e os usuários estiverem certos a ação é verdadeira e o teste passa, 
	   * caso contrário o teste falha
	   */
	  
	  /** EX:
		 *  Verifica se a afirmação: "É verdade que a busca retornou na segunda posição da Lista a usuaria 
		 * Manuela da Silva" é verdadeira
		 * Se for verdadeira, o teste passa, senão o teste falha.
		 */


  }
  
  @AfterAll
  public void end() {
	  usuarioRepository.deleteAll();
  }

} 