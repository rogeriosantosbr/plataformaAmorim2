/**
 *   Este codigo é software livre você e pode resdistribuir e/ou modificar ele seguindo os termos da
 *   Creative Commons Attribution 4.0 International Pare visualizar uma copia desta 
 *   licensa em ingles visite http://creativecommons.org/licenses/by/4.0/.
 *   
 *   This code is free software; you can redistribute it and/or modify it
 *   under the terms of Creative Commons Attribution 4.0 International License. 
 *   To view a copy of this license, visit http://creativecommons.org/licenses/by/4.0/.
 */
package br.com.muranodesign.resources;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;

import br.com.muranodesign.business.AlunoService;
import br.com.muranodesign.business.AlunoVariavelService;
import br.com.muranodesign.business.GrupoService;
import br.com.muranodesign.business.ObjetivoService;
import br.com.muranodesign.business.PlanejamentoRoteiroService;
import br.com.muranodesign.business.PlanoEstudoService;
import br.com.muranodesign.business.TutoriaService;
import br.com.muranodesign.model.AlunoVariavel;
import br.com.muranodesign.model.Grupo;
import br.com.muranodesign.model.Objetivo;
import br.com.muranodesign.model.PlanejamentoRoteiro;
import br.com.muranodesign.model.PlanoEstudo;
import br.com.muranodesign.model.Tutoria;


/**
 * Classe tem como objetivo disponibilizar os serviços relacionandos planejamento Roteiro
 *
 * @author Rogerio Lima dos Santos
 * @version 1.00
 * @since Release 1 da aplicação
 */


@Path("PlanejamentoRoteiro")
public class PlanejamentoRoteiroResource {

	/** The logger. */
	private Logger logger = Logger.getLogger(PlanejamentoRoteiroResource.class.getName());

	/**
	 * Gets the planejamento roteiro.
	 *
	 * @return the planejamento roteiro
	 */
	@GET
	@Produces("application/json")
	public List<PlanejamentoRoteiro> getPlanejamentoRoteiro() {
		logger.info("Listar PlanejamentoRoteiro ...");
		List<PlanejamentoRoteiro> resultado;
		resultado = new PlanejamentoRoteiroService().listarTodos();
		logger.info("QTD PlanejamentoRoteiro : " + resultado.size());
		return resultado;
	}

	/**
	 * Gets the evento.
	 *
	 * @param id the id
	 * @return the evento
	 */
	@Path("{id}")
	@GET
	@Produces("application/json")
	public PlanejamentoRoteiro getEvento(@PathParam("id") int id) {
		logger.info("Lista PlanejamentoRoteiro  por id " + id);
		List<PlanejamentoRoteiro> resultado;
		resultado = new PlanejamentoRoteiroService().listarkey(id);
		PlanejamentoRoteiro evento = resultado.get(0);

		return evento;

	}
	
	@Path("PlanoEstudo/{id}")
	@GET
	@Produces("application/json")
	public List<PlanejamentoRoteiro> getPlanejamento(@PathParam("id") int id){
		logger.info("Lista PlanoEstudo  por id " + id);
		List<PlanejamentoRoteiro> resultado = new ArrayList<PlanejamentoRoteiro>();
		List<PlanejamentoRoteiro> retorno = new PlanejamentoRoteiroService().listarPlanoEstudo(id);
		
	/*	int quantidade = retorno.size();
		for(int i = 0; i < quantidade; i++){
			if(!retorno.get(i).getStatus().equals("1")){
				resultado.add(retorno.get(i));
			}
		}*/
		
		return retorno;
	}
	
	
	
	@Path("/ObjetivoAluno/{id}")
	@GET
	@Produces("application/json")
	public Hashtable<String, Long> ObjetivoAluno(@PathParam("id") int id){
		//List<PlanejamentoRoteiro> resultadoTotal;
		//List<PlanejamentoRoteiro> resultadoCompletos;
		//List<PlanejamentoRoteiro> resultadoCorrigidos;
		int quantidade;
		long total = 0, completos = 0, corrigidos = 0, resultadoTotal = 0, resultadoCompletos = 0, resultadoCorrigidos =0;
		Hashtable<String, Long> retorno = new Hashtable<String, Long>();
		
		
		//quantidade = resultadoTotal.size();
		//quantidade = resultadoCompletos.size();
		//quantidade = resultadoCorrigidos.size();
		
		resultadoTotal = new PlanejamentoRoteiroService().listarAlunoTotal(id);
		total = total + resultadoTotal;
		retorno.put("QuantidadeTotal", total);
		
		resultadoCompletos = new PlanejamentoRoteiroService().listarAlunoCompletos(id);
		completos = completos + resultadoCompletos;
		retorno.put("QuantidadeCompletos", completos);
		
		
		resultadoCorrigidos = new PlanejamentoRoteiroService().listarAlunoCorrigidos(id);
		corrigidos = corrigidos + resultadoCorrigidos;
		retorno.put("QuantidadeCorrigidos", corrigidos);
		
	return retorno;
	}
	
	
	@Path("/ObjetivoAlunoProfessor/{id}")
	@GET
	@Produces("application/json")
	public Hashtable<String, Integer> ObjetivoAlunoProfessor(@PathParam("id") int id){
		Hashtable<String, Integer> retorno = new Hashtable<String, Integer>();
		int total = 0, completos = 0, corrigidos = 0, resultadoTotal = 0, resultadoCompletos = 0, resultadoCorrigidos =0;
		List<AlunoVariavel> alunos = new ArrayList<AlunoVariavel>();

		List<Grupo> grupos = new ArrayList<Grupo>();
		List<Integer> anos = new ArrayList<Integer>();
		
		List<Tutoria> tutorias = new TutoriaService().listarkey(id);
		List<AlunoVariavel> alunosTOTAL = new ArrayList<AlunoVariavel>();
		
		List<Objetivo> objetivo = new ArrayList<Objetivo>();
		List<Objetivo> objetivotoltal = new ArrayList<Objetivo>();
		
		List<PlanejamentoRoteiro> planos = new ArrayList<PlanejamentoRoteiro>();
		List<PlanejamentoRoteiro> planosTotal = new ArrayList<PlanejamentoRoteiro>();
		/*
		List<PlanejamentoRoteiro> planejamentoRoteirosentregues = new ArrayList<PlanejamentoRoteiro>();
		List<PlanejamentoRoteiro> planejamentoRoteiroscompletos = new ArrayList<PlanejamentoRoteiro>();
		
		
		List<PlanejamentoRoteiro> planejamentoRoteirosentreguesTotal = new ArrayList<PlanejamentoRoteiro>();
		List<PlanejamentoRoteiro> planejamentoRoteiroscompletoTotal = new ArrayList<PlanejamentoRoteiro>();
		
		List<PlanejamentoRoteiro> planejamentoRoteiroFull = new ArrayList<PlanejamentoRoteiro>();
		List<PlanejamentoRoteiro> planejamentoRoteiroFullTotal = new ArrayList<PlanejamentoRoteiro>();
*/


		long obj = 0;
		int qtdAluno = 0;
		
		grupos = new GrupoService().listarTutor(tutorias.get(0).getIdtutoria());
		
		int qtdGrupo = grupos.size();

		for (int j = 0; j < qtdGrupo; j++) {
			alunos = new AlunoVariavelService().listaGrupo(grupos.get(j).getIdgrupo());
			alunosTOTAL.addAll(alunos);
		}


		if (!alunosTOTAL.isEmpty()) {
			qtdAluno = alunosTOTAL.size();
			for (int k = 0; k < qtdAluno; k++) {
				if (!anos.contains(alunosTOTAL.get(k).getAnoEstudo().getIdanoEstudo())) {
					anos.add(alunosTOTAL.get(k).getAnoEstudo().getIdanoEstudo());
				}
			}
			
			int qtdAno = anos.size();
			for (int l = 0; l < qtdAno; l++) {

				
				obj = new ObjetivoService().listarGrafico(anos.get(l));
				//count = count + obj;
				
				objetivo = new ObjetivoService().listarEntregues(anos.get(l));
				objetivotoltal.addAll(objetivo);
			}
		}
		
		for(int t = 0; t < qtdAluno; t++){
			planos = new PlanejamentoRoteiroService().listarIdAluno(alunosTOTAL.get(t).getAluno().getIdAluno());
			for(int k = 0; k < planos.size(); k++){
				
				if(planos.get(k).getStatus().equals("2")){
					completos++;
				}
				if(planos.get(k).getStatus().equals("3")){
					corrigidos++;
				}
				total++;
				//planosTotal.addAll(planos);
			}
		}
		/*
		for(int k = 0; k < count; k++){
			planejamentoRoteiroscompletos = new PlanejamentoRoteiroService().listarObjetivoCompleto(objetivotoltal.get(k).getIdobjetivo());
			if(!planejamentoRoteiroscompletos.isEmpty()){
				planejamentoRoteiroscompletoTotal.add(planejamentoRoteiroscompletos.get(0));
			}
		}
			
		for(int k = 0; k < count; k++){
			planejamentoRoteirosentregues = new PlanejamentoRoteiroService().listarObjetivoPendente(objetivotoltal.get(k).getIdobjetivo());
			if(!planejamentoRoteirosentregues.isEmpty()){
				planejamentoRoteirosentreguesTotal.add(planejamentoRoteirosentregues.get(0));
			}
		}
	
		
		for(int k = 0; k < count; k++){
			planejamentoRoteiroFull = new PlanejamentoRoteiroService().listarObjetivoTotal(objetivotoltal.get(k).getIdobjetivo());
			if(!planejamentoRoteiroFull.isEmpty()){
				planejamentoRoteiroFullTotal.add(planejamentoRoteiroFull.get(0));
			}
		}
		*/

		System.out.println(anos);
		retorno.put("QuantidadeTotal", total/*planejamentoRoteiroFullTotal.size()*/);
		retorno.put("QuantidadeCompletos",completos /*planejamentoRoteirosentreguesTotal.size()*/);
		retorno.put("QuantidadeCorrigidos", corrigidos/*planejamentoRoteiroscompletoTotal.size()*/);
		
	return retorno;
	}
	
	/**
	 * Gets the evento.
	 *
	 * @param id the id
	 * @return the evento
	 */
	@Path("aluno/{id}")
	@GET
	@Produces("application/json")
	public  List<PlanejamentoRoteiro>  getIdAluno(@PathParam("id") int id) {
		logger.info("Lista PlanejamentoRoteiro  por id Aluno" + id);
		List<PlanejamentoRoteiro> resultado;
		resultado = new PlanejamentoRoteiroService().listarIdAluno(id);
		

		return resultado;

	}
	
	/**
	 * Listar planejamento de roteiro por aluno e objetivo
	 * @param aluno
	 * @param objetivo
	 * @return
	 */
	@Path("alunoPendente/{aluno}/{objetivo}")
	@GET
	@Produces("application/json")
	public List<PlanejamentoRoteiro> getPendente(@PathParam("aluno") int aluno, @PathParam("objetivo") int objetivo){
		logger.info("Lista PlanejamentoRoteiro  por id Aluno e status 1" + aluno);
		List<PlanejamentoRoteiro> resultado;
		resultado = new PlanejamentoRoteiroService().listarPendente(aluno, objetivo);
		
		return resultado;
		
		}
	
	@Path("status/{id}")
	@GET
	@Produces("application/json")
	public List<PlanejamentoRoteiro> getstatus(@PathParam("id") int id){
		logger.info("Lista PlanejamentoRoteiro  por aluno" + id);
		List<PlanejamentoRoteiro> resultado;
		List<PlanejamentoRoteiro> retorno = new ArrayList<PlanejamentoRoteiro>();
		int quantidade;
		
		String ano = new AlunoVariavelService().listaAluno(new AlunoService().listarkey(id).get(0).getIdAluno()).get(0).getAnoEstudo().getAno();
		resultado = new PlanejamentoRoteiroService().listarStatus(id);
		quantidade = resultado.size();
		
		for(int i = 0; i < quantidade; i++){
			if(!ano.equals(resultado.get(i).getObjetivo().getRoteiro().getAnoEstudo().getAno())){
				retorno.add(resultado.get(i));
			}
		}
		
		return retorno;
	}
	
	@Path("RoteirosAtivos/{id}")
	@GET
	@Produces("application/json")
	public List<PlanejamentoRoteiro> RoteirosAtivos(@PathParam("id") int id){
		List<PlanejamentoRoteiro> planos = new PlanejamentoRoteiroService().listarPlanoEstudoTotal(id);
		List<PlanejamentoRoteiro> result = new ArrayList<PlanejamentoRoteiro>();
		
		int qtd = planos.size();
		int c = 0;
		System.out.println(qtd);
		
		for(int i = 0; i < qtd; i++){
			
			if(planos.get(i).getObjetivo().getRoteiro().getAtivo() == 1){
				result.add(planos.get(i));
				c++;
			}
		}
		
		return result;
	}
	
	/**
	 * Removes the planejamento roteiro.
	 *
	 * @param action the action
	 * @param id the id
	 * @return the string
	 */
	@Path("{action}/{id}")
	@Produces("text/plain")
	public String removePlanejamentoRoteiro(@PathParam("action") String action,
			@PathParam("id") int id) {

		logger.info("PlanejamentoRoteiro  " + action);
		if ( action.equals("delete")) {
			List<PlanejamentoRoteiro> resultado;
			resultado = new PlanejamentoRoteiroService().listarkey(id);
			PlanejamentoRoteiro res = resultado.get(0);
			new PlanejamentoRoteiroService().deletarPlanejamentoRoteiro(res);
			return "true";
		} else {
			return "false";
		}

	}
	
	/**
	 * Alterar o status
	 * @param strid
	 * @param status
	 * @return
	 * @throws ParseException 
	 */
	@Path("StatusPlanejamento")
	@POST
	@Produces("text/plain")
	public String postStatus(@FormParam("id") String strid,@FormParam("status") String status) throws ParseException{
		int id=Integer.parseInt(strid);
		PlanejamentoRoteiro planejamento = new PlanejamentoRoteiroService().listarkey(id).get(0);
		PlanejamentoRoteiro resultado;
		
		Date data = null;
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		
		String k = new SimpleDateFormat("dd/MM/yyyy").format(new Date()).toString();  
	
	
		
		if(status.equals("2")){
			Date date = (Date)formatter.parse(k);
			planejamento.setDataStatusEntregue(date);
		}else if (status.equals("3")){
			Date date = (Date)formatter.parse(k);
			planejamento.setDataStatusVisto(date);
		}
		
		
		
		planejamento.setIdplanejamentoRoteiro(id);
		planejamento.setStatus(status);
		
		resultado = new PlanejamentoRoteiroService().atualizarPlanejamentoRoteiro(planejamento);
		
		return Integer.toString(resultado.getIdplanejamentoRoteiro());
	}
	
	/**
	 * Evento action.
	 *
	 * @param strid the strid
	 * @param action the action
	 * @param status the status
	 * @param objetivo the objetivo
	 * @param planoEstudo the plano estudo
	 * @return the string
	 * @throws ParseException 
	 */
	@POST
	@Produces("text/plain")
	public String eventoAction(
			
			@FormParam("id") String strid,
			@FormParam("action") String action,
			
			@FormParam("status") String status,
			@FormParam("objetivo") String objetivo,
			@FormParam("planoEstudo") int planoEstudo,
			@FormParam("idAluno") String idAluno
			
			
			
			
			) throws ParseException {
		PlanejamentoRoteiro objPlanejamentoRoteiro;
		PlanejamentoRoteiro resultado;
		
		logger.info("eventoAction ...");
		
		Date data = null;
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		
		String k = new SimpleDateFormat("dd/MM/yyyy").format(new Date()).toString();  
		Date date = (Date)formatter.parse(k);
		
		
		objPlanejamentoRoteiro = new PlanejamentoRoteiro();
		PlanoEstudo objPlanoEstudo = new PlanoEstudo();
			
		// get tipo Objetivo
		List<Objetivo> rsObjetivo;
		rsObjetivo = new ObjetivoService().listarkey(Integer.parseInt(objetivo));
		Objetivo objObjetivo = rsObjetivo.get(0);
		//TODO: Validar valores.
		
		// get tipo Objetivo
		
		List<PlanoEstudo> rsPlanoEstudo;
		//rsPlanoEstudo= new PlanoEstudoService().listarkey(Integer.parseInt(planoEstudo));
		rsPlanoEstudo= new PlanoEstudoService().listarkey(planoEstudo);
		
		if(!rsPlanoEstudo.isEmpty()){
			 objPlanoEstudo = rsPlanoEstudo.get(0);
		}
		
		//TODO: Validar valores.
		
		
		
	
		if (action.equals("create")) {
			logger.info("Craindo no  Planejamento Roteiro");
			
			objPlanejamentoRoteiro.setObjetivo(objObjetivo);
			objPlanejamentoRoteiro.setPlanoEstudo(objPlanoEstudo);
			objPlanejamentoRoteiro.setStatus(status);
			objPlanejamentoRoteiro.setIdAluno(Integer.parseInt(idAluno));
			
			objPlanejamentoRoteiro.setDataStatusPlanejado(date);
			resultado = new PlanejamentoRoteiroService().criarPlanejamentoRoteiro(objPlanejamentoRoteiro);
			
		}else if (action.equals("update")) {
			int id=Integer.parseInt(strid);
			List<PlanejamentoRoteiro> rsPlanejamentoRoteiro;
			rsPlanejamentoRoteiro= new PlanejamentoRoteiroService().listarkey(id);
			objPlanejamentoRoteiro= rsPlanejamentoRoteiro.get(0);
			objPlanejamentoRoteiro.setObjetivo(objObjetivo);
			if(!rsPlanoEstudo.isEmpty()){
				objPlanejamentoRoteiro.setPlanoEstudo(objPlanoEstudo);
			}
			objPlanejamentoRoteiro.setIdAluno(Integer.parseInt(idAluno));
			objPlanejamentoRoteiro.setStatus(status);
			//objEventoEdit = objEvento;
			
			resultado = new PlanejamentoRoteiroService().atualizarPlanejamentoRoteiro(objPlanejamentoRoteiro);
			
		} else {
			
			logger.info("Erro na URI  " + action);
			return "0";
			
		}
		
		return Integer.toString(resultado.getIdplanejamentoRoteiro());

	}
	
	
	
	

}
