package com.diego.techjobs.controllers;

import javax.validation.Valid;

import com.diego.techjobs.models.Candidate;
import com.diego.techjobs.models.JobsOportunity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.diego.techjobs.repository.CandidateRepository;
import com.diego.techjobs.repository.JobsRepository;

import java.time.LocalDateTime;

@Slf4j
@Controller
public class JobsController {

	@Autowired
	private JobsRepository jobsRepository;
	
	@Autowired
	private CandidateRepository candidateRepository;

	// CADASTRA VAGA
	@RequestMapping(value = "/cadastrarVaga", method = RequestMethod.GET)
	public String form() {
		return "vaga/formVaga";
	}

	@RequestMapping(value = "/cadastrarVaga", method = RequestMethod.POST)
	public String form(@Valid JobsOportunity jobsOportunity, BindingResult result, RedirectAttributes attributes) {
		jobsOportunity.setDateCreate(LocalDateTime.now());
		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos...");
			log.info("erro ao cadastrar vaga");
			return "redirect:/cadastrarVaga";
		}

		jobsRepository.save(jobsOportunity);
		log.info("cadastrando vaga");
		attributes.addFlashAttribute("mensagem", "Vaga cadastrada com sucesso!");
		return "redirect:/cadastrarVaga";
	}

	//listvagas
	@RequestMapping("/vagas")
	public ModelAndView listaVagas() {
		ModelAndView mv = new ModelAndView("vaga/listaVaga");
		Iterable<JobsOportunity> vagas = jobsRepository.findAll();
		mv.addObject("vagas", vagas);
		return mv;
	}

	//
	@RequestMapping(value = "/{code}", method = RequestMethod.GET)
	public ModelAndView detalhesVaga(@PathVariable("code") long code) {
		JobsOportunity jobsOportunity = jobsRepository.findByCode(code);
		ModelAndView mv = new ModelAndView("vaga/detalhesVaga");
		mv.addObject("jobsOportunity", jobsOportunity);
		log.info("detalhes da vaga");

		Iterable<Candidate> canditades = candidateRepository.findByjobsOportunity(jobsOportunity);
		mv.addObject("candidates", canditades);

		return mv;

	}

	// DELETA VAGA
	@RequestMapping("/deletarVaga")
	public String deletarVaga(long code) {
		JobsOportunity jobsOportunity = jobsRepository.findByCode(code);
		jobsRepository.delete(jobsOportunity);
		log.info("deletando vaga");
		return "redirect:/vagas";
	}

	// ADICIONAR CANDIDATO
	@RequestMapping(value = "/{code}", method = RequestMethod.POST)
	public String detalhesVagaPost(@PathVariable("code") long code, @Valid Candidate candidate,
			BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos");
			return "redirect:/{code}";
		}

		// rg duplicado
		if (candidateRepository.findByRg(candidate.getRg()) != null) {
			attributes.addFlashAttribute("mensagem_erro", "RG duplicado");
			log.info("rg duplicado");
			return "redirect:/{code}";
		}

		JobsOportunity jobsOportunity = jobsRepository.findByCode(code);
		candidate.setVaga(jobsOportunity);
		candidateRepository.save(candidate);
		attributes.addFlashAttribute("mensagem", "Candidato adionado com sucesso!");
		log.info("adicionando candidato");
		return "redirect:/{code}";
	}

	// DELETA CANDIDATO pelo RG
	@RequestMapping("/deletarCandidato")
	public String deletarCandidato(String rg) {
		Candidate candidate = candidateRepository.findByRg(rg);
		JobsOportunity jobsOportunity = candidate.getVaga();
		String code = "" + jobsOportunity.getCode();

		candidateRepository.delete(candidate);

		return "redirect:/" + code;

	}

	// Métodos que atualizam vaga
	// formulário edição de vaga
	@RequestMapping(value = "/editar-vaga", method = RequestMethod.GET)
	public ModelAndView editarVaga(long code) {
		JobsOportunity jobsOportunity = jobsRepository.findByCode(code);
		ModelAndView mv = new ModelAndView("vaga/update-vaga");
		mv.addObject("jobsOportunity", jobsOportunity);
		return mv;
	}

	// UPDATE vaga
	@RequestMapping(value = "/editar-vaga", method = RequestMethod.POST)
	public String updateVaga(@Valid JobsOportunity jobsOportunity, BindingResult result, RedirectAttributes attributes) {
		jobsRepository.save(jobsOportunity);
		attributes.addFlashAttribute("success", "Vaga alterada com sucesso!");

		long codeLong = jobsOportunity.getCode();
		String code = "" + codeLong;
		return "redirect:/" + code;
	}

}
