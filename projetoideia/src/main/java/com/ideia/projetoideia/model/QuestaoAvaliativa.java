package com.ideia.projetoideia.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "tb_questao_avaliativa")
@Data
public class QuestaoAvaliativa {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	@Column(nullable = false, name = "nota_maxima")
	private Float notaMax;
	
	@Column(nullable = false)
	private String questao;
	
	@Column(nullable = false)
	private Integer enumeracao;
	
	@Enumerated(EnumType.STRING)
	private TipoQuestaoAvaliativa tipoQuestaoAvaliativa;
	
	@ManyToOne
	@JoinColumn(name = "competicao_fk")
	private Competicao competicaoCadastrada;
	
	@OneToMany(mappedBy = "questaoAvaliativa", cascade = CascadeType.REMOVE)
	@JsonIgnore
	private List<AvaliacaoPitch> avaliacaoPitch = new ArrayList<>();
	
}
