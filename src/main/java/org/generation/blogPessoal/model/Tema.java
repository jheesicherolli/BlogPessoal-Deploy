package org.generation.blogPessoal.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table (name = "tb_tema")
public class Tema {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "O atributo Descrição é obrigatório e não pode conter espaços em branco")
	private String descricao;
	
	@OneToMany (mappedBy = "tema", cascade = CascadeType.REMOVE)
	//está sendo mapeado o atributo tema da tabela de postagem
	//CascadeType.REMOVE = indica que se uma categoria for atualizada, todos os produtos atrelados a ela vão ser removidos
	
	/*Cascade = caso alteremos algo referente ao tema, todas as postagens referentes 
	 * aquele tema sofrerão ateração, ex: se deletar algum tema, todas as postagens referentes a aquele tema
	 * também serão apagadas*/
	@JsonIgnoreProperties("tema")
	private List<Postagem> postagem;
	//listar todos os itens que estão na minha postagem, fazendo uma fusão dos dados
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public List<Postagem> getPostagem() {
		return postagem;
	}
	public void setPostagem(List<Postagem> postagem) {
		this.postagem = postagem;
	}

}
