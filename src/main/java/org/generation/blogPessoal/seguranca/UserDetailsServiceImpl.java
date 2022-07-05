package org.generation.blogPessoal.seguranca;

import java.util.Optional;

import org.generation.blogPessoal.model.Usuario;
import org.generation.blogPessoal.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
//demonstra que é uma classe de serviço, ou seja implementa regras de negócio da aplicação
public class UserDetailsServiceImpl implements UserDetailsService
/**
 *  Classe UserDetailsServiceImpl 
 * 
 *  Implementa a interface UserDetailsService, que é responsável por recuperar os dados
 *  do usuário no Banco de Dados pelo usuário e converter em um objeto da Classe 
 *  UserDetailsImpl.
 * 
 *  Por se tratar de uma implementação de uma interface, a classe deve ter em seu nome o 
 *  sufixo Impl para indicar que se trata de uma implementação.
 */
{

	@Autowired 
	private UsuarioRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		/**
		 * Sobrescreve (@Override) o método loadUserByUsername.
		 * 
		 * A implementação de autenticação chama o método loadUserByUsername(String username),
		 * para obter os dados de um usuário com um determinado nome de usuário. 
		 * O nome do usuário deve ser único. O usuário retornado por este método é um objeto
		 * da classe UserDetailsImpl. 
		 */
		Optional <Usuario > user = userRepository.findByUsuario(userName);
		/**
		 * Para buscar o usuário no Banco de dados, utilizaremos o método findByUsuario,
		 * que foi assinado na interface UsuarioRepository
		 */
		user.orElseThrow(()-> new UsernameNotFoundException(userName + "not found."));
		
		/**
		 * Se o usuário não existir, o método lança uma Exception do tipo UsernameNotFoundException.
		 */ 
		
		return user.map(UserDetailsImpl ::new).get();
		//significa que vai ser entregue um novo UserDetails
		//.get = pra conseguir extrair o que tem dentro do objeto
		
		/**
		 * Retorna um objeto do tipo UserDetailsImpl criado com os dados recuperados do
		 * Banco de dados.
		 * 
		 * O operador :: faz parte de uma expressão que referencia um método, complementando
		 * uma função lambda. Neste exemplo, o operador faz referência ao construtor da 
		 * Classe UserDetailsImpl. 
		 */
	}
}
