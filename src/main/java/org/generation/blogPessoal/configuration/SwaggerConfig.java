package org.generation.blogPessoal.configuration;

import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;

@Configuration
public class SwaggerConfig {
	@Bean 
	/*indica ao Spring que ele deve invocar aquele método e gerenciar o objeto
	 * retornado por ele, ou seja, o objeto vai poder ser injetado em qualquer ponto da minha
	 * aplicação
	 */
	public OpenAPI springBlogPessoalOpenAPI() {
		//OpenAPI, gera a documentação no Swagger utilizando a especificação OpenAPI
		return new OpenAPI()
				.info(new Info()
						.title("Projeto Blog")
						.description("Projeto Blog Pessoal - Generation Brasil")
						.version("v0.0.1")
				//aqui foram inseridas informações sobre a API
				.license(new License()
						.name("Generation Brasil")
						.url("https://brazil.generation.org/"))
				//aqui foram informações referentes a licença da API
				.contact(new Contact()
						.name("Jennifer Sicherolli")
						.url("https://github.com/jheesicherolli")
						.email("jennifer.sicherolli@gmail.com")))
				//informações de contato da pessoa Desenvolvedora
				.externalDocs(new ExternalDocumentation()
						.description("GitHub")
						.url("https://github.com/jheesicherolli"));
	             //informações referrentes a Documentações Externas(GitHub, Gitpage, etc.)
	}
	
	@Bean
	 public OpenApiCustomiser customerGlobalHeaderOpenApiCustomiser() {
		/*OpenApiCustomiser permite personalizar o Swagger, baseado na
Especificação OpenAPI. O Método acima, personaliza todas as mensagens HTTP
Responses (Respostas das requisições) do Swagger.*/
		
		return openApi -> {
/* Cria um Objeto da Classe OpenAPI, que gera a documentação no Swagger utilizando a especificação OpenAPI.
*/
			openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations().forEach(operation-> {
/*Cria um primeiro looping que fará a leitura de todos os recursos (Paths) 
 * através do Método getPaths(), que retorna o caminho de cada endpoint. Na sequência, 
 * cria um segundo looping que Identificará qual Método HTTP (Operations), está sendo
 * executado em cada endpoint através do Método readOperations(). Para cada Método,
 * todas as mensagens serão lidas e substituídas pelas novas mensagens.*/
				
				ApiResponses apiResponses = operation.getResponses();
	/*Cria um Objeto da Classe ApiResponses, que receberá as Respostas HTTP de
cada endpoint (Paths) através do método getResponses().*/
				
				apiResponses.addApiResponse("200", createApiResponse("Sucesso!"));
				apiResponses.addApiResponse("201", createApiResponse("Objeto Persistido!"));
				apiResponses.addApiResponse("204", createApiResponse("Objeto Excluído!"));
				apiResponses.addApiResponse("400", createApiResponse("Erro na Requisição!"));
				apiResponses.addApiResponse("401", createApiResponse("Acesso Não Autorizado!"));
				apiResponses.addApiResponse("404", createApiResponse("Objeto Não Encontrado!"));
				apiResponses.addApiResponse("500", createApiResponse("Erro na Aplicação!"));
/*Adiciona as novas Respostas no endpoint, substituindo as atuais e 
 *acrescentando as demais, através do Método addApiResponse(), identificadas pelo
 *HTTP Status Code (200, 201 e etc).
*/
			}));


		};
		
	}

	private ApiResponse createApiResponse(String message) {
		/*O Método createApiResponse() adiciona uma descrição (Mensagem),
em cada Resposta HTTP*/
		
		return new ApiResponse().description(message);
	}

}
