package com.ideia.projetoideia.model;

import java.io.File;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="tb_competicao")
public class Competicao {
	@Id
	@Column(name = "id_competicao")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idCompeticao;
	@Column(nullable = false)
	private String nome;
	@Column(nullable = false, name = "qntd_maxima_membros_por_equipe")
	private Integer qntdMaximaMembrosPorEquipe;
	@Column(nullable = false, name = "qntd_minima_membros_por_equipe")
	private Integer qntdMinimaMembrosPorEquipe;
	@Column(nullable = false, name = "tempo_maximo_video")
	private Float tempoMaximoVideo;
	@Column(nullable = false, name = "arquivo_regulamento_competicao")
	private File arquivoRegulamentoCompeticao;
	@Column(nullable = false, name = "dominio_competicao")
	private String dominioCompeticao;
	
}
