package org.generation.blogPessoal.seguranca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SuppressWarnings("deprecation")
@EnableWebSecurity
//habilita a configuração de Web security, configuração padrão do Spring Security na nosso api.
public class BasicSecurityConfig extends WebSecurityConfigurerAdapter {
/*Basic Security Config (é uma camada) = configura todos os acessos, verifica acesso por acesso, 
para saber se é seguro ou não*/

	@Autowired //são anotações 
	private UserDetailsService userDetailsService;
	//UserDetailsService injetamos de dentro de uma classe que existe dentro de WebSecurityConfigurerAdapter
	
	/**
	 * A annotation @Autowired insere uma Injeção de Dependência. 
	 * 
	 * Como iremos utilizar os usuários salvos no nosso Banco de dados,
	 * na tabela tb_usuarios, para efetuar o login na api precisamos injetar 
	 * um objeto da Interface UserDetailsService que será implementada na 
	 * Classe UserDetailsServiceImpl que fará o acesso ao nosso Banco de dados
	 * para recuperar os dados do usuário.
	 */
	
	@Override
	//sobrescreve o método que tem dentro de UserDetailService, o método configure
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	                                                            //throws é uma tratativa de erro
	/**
	 *  Sobrescreve (@Override) o primeiro método Configure, que tem a função 
	 *  de criar uma nova instância da Classe AuthenticationManagerBuilder e 
	 *  define que o login será efetuado através dos usuários criados no Banco de dados.
	 *  Para recuperar os dados do usuário no Banco de Dados utilizaremos a Interface 
	 *  UserDetailsService.
	 *  Outra alternativa de login seria acriação de um usuário em memória, que veremos nas
	 *  próximas sessões.
	 *  
	 *  O método é do tipo protected por definição da classe.
	 * 
	 *  Lembrete:
	 * 
	 *  1) public: permite acesso a qualquer código externo a classe.
	 *  2) protected: permite acesso às classes filhas, mas proíbe a qualquer 
	 *     outro acesso externo.
	 *  3) private: proíbe qualquer acesso externo à própria classe, inclusive 
	 *     das classes filhas.
	 */
	{
		auth.userDetailsService(userDetailsService); //auth é um objeto
		
		/**
		 *  O objeto auth registra e cria uma nova instância do objeto userDetailsService
		 *  da interface UserDetailsService implementada na Classe UserDetailsServiceImpl
		 *  para recuperar os dados dos usuários gravados no Banco de dados.
		 */
		
		auth.inMemoryAuthentication()
		    .withUser("root")
		    .password(passwordEncoder().encode("root"))
		    .authorities("ROLE_USER");
	}
	
	@Bean 
	public PasswordEncoder passwordEncoder() {
		/**
		 *  A annotation @Bean transforma a instância retornada pelo método como um 
		 *  objeto gerenciado pelo Spring, desta forma, ele pode ser injetado em qualquer
		 *  classe, a qualquer momento que você precisar sem a necessidade de usar a 
		 *  annotation @Autowired
		 */
		return new BCryptPasswordEncoder();
		/*O método BCryptPasswordEncoder é responsável por criptografar a senha do usuário utilizando o
		 *  hash Bcrypt.
		 */
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
	//configure = nome do método
		                         //http = nome do objeto
		http.authorizeRequests()
		.antMatchers("/usuarios/logar").permitAll()
		.antMatchers("/usuarios/cadastrar").permitAll() //usuarios/logar = endpoint...
		/*essa configuração serve para liberar endpoints, ou seja liberar alguns caminhos dentro do meu controller 
		 * para que o client tenha acesso sem precisar passar uma chave em token.
		 * No modo geral estou permitindo usuários a se cadastrar e logar
		 *  
		 *  Outra explicação:
		 * HttpMethod.OPTIONS -> O parâmetro HttpMethod.OPTIONS permite que 
		 * o cliente (frontend), possa descobrir quais são as opções de 
		 * requisição permitidas para um determinado recurso em um servidor. 
		 * Nesta implementação, está sendo liberada todas as opções das 
		 * requisições através do método permitAll().
		 */
		.anyRequest().authenticated()
		/*todas as outras requisições deveram ser autenticadas*/
		.and().httpBasic()
		//vamos utilizar o padrão basic security pra gerar a chave token
        .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //SessionCreationPolicy : tipo da policy
        //sessionManagement(gerenciador de sessões) : indica qual é o tipo de sessão que vai ser utilizada,   
        /*
         * Uma das finalidades de uma API Rest é que seja STATELESS, ou seja não guarda sessão nenhuma, com isso deixamos 
         * explicito para a IDE, que a sessão a ser guardada é STATELESS
         * 
         * sessionCreationPolicy(SessionCreationPolicy.STATELESS) -> Define
		 * como o Spring Secuiryt irá criar (ou não) as sessões
		 * 
		 * 
		 * STATELESS -> Nunca será criada uma sessão, ou seja, basta enviar
		 * o token através do cabeçalho da requisição que a mesma será processada.
        */
         .and().cors()
         //habilita o cors
         /*cors -> O compartilhamento de recursos de origem cruzada (CORS) surgiu 
		 * porquê os navegadores não permitem solicitações feitas por um domínio
		 * (endereço) diferente daquele de onde o site foi carregado. Desta forma o 
		 * Frontend da aplicação, por exemplo, obrigatoriamente teria que estar 
		 * no mesmo domínio que o Backend. Habilitando o CORS, o Spring desabilita 
		 * esta regra e permite conexões de outros domínios.
         */
         .and().csrf().disable();
		  /*CSRF: O cross-site request forgery (falsificação de 
	       * solicitação entre sites), é um tipo de ataque no qual comandos não 
		   * autorizados são transmitidos a partir de um usuário em quem a 
		   * aplicação web confia. 
		   * 
		   * csrf().disabled() -> Esta opção de proteção é habilitada por padrão no 
		   * Spring Security, entretanto precisamos desabilitar, caso contrário, todos 
		   * os endpoints que respondem ao verbo POST não serão executados.
		   * 
		   */
	}
	
	
}
