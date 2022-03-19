package com.diego.techjobs.controllers;

import javax.validation.Valid;

import com.diego.techjobs.models.Candidate;
import com.diego.techjobs.models.Jobs;
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

@Controller
public class VagaController {

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
	public String form(@Valid Jobs jobs, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos...");
			return "redirect:/cadastrarVaga";
		}

		jobsRepository.save(jobs);
		attributes.addFlashAttribute("mensagem", "Vaga cadastrada com sucesso!");
		return "redirect:/cadastrarVaga";
	}

	// LISTA VAGAS

	@RequestMapping("/vagas")
	public ModelAndView listaVagas() {
		ModelAndView mv = new ModelAndView("vaga/listaVaga");
		Iterable<Jobs> vagas = jobsRepository.findAll();
		mv.addObject("vagas", vagas);
		return mv;
	}

	//
	@RequestMapping(value = "/{code}", method = RequestMethod.GET)
	public ModelAndView detalhesVaga(@PathVariable("code") long code) {
		Jobs jobs = jobsRepository.findByCode(code);
		ModelAndView mv = new ModelAndView("vaga/detalhesVaga");
		mv.addObject("vaga", jobs);

		Iterable<Candidate> canditados = candidateRepository.findByJobs(jobs);
		mv.addObject("candidatos", canditados);

		return mv;

	}

	// DELETA VAGA
	@RequestMapping("/deletarVaga")
	public String deletarVaga(long code) {
		Jobs jobs = jobsRepository.findByCode(code);
		jobsRepository.delete(jobs);
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
			return "redirect:/{code}";
		}

		Jobs jobs = jobsRepository.findByCode(code);
		candidate.setVaga(jobs);
		candidateRepository.save(candidate);
		attributes.addFlashAttribute("mensagem", "Candidato adionado com sucesso!");
		return "redirect:/{code}";
	}

	// DELETA CANDIDATO pelo RG
	@RequestMapping("/deletarCandidato")
	public String deletarCandidato(String rg) {
		Candidate candidate = candidateRepository.findByRg(rg);
		Jobs jobs = candidate.getVaga();
		String code = "" + jobs.getCode();

		candidateRepository.delete(candidate);

		return "redirect:/" + code;

	}

	// Métodos que atualizam vaga
	// formulário edição de vaga
	@RequestMapping(value = "/editar-vaga", method = RequestMethod.GET)
	public ModelAndView editarVaga(long code) {
		Jobs jobs = jobsRepository.findByCode(code);
		ModelAndView mv = new ModelAndView("vaga/update-vaga");
		mv.addObject("vaga", jobs);
		return mv;
	}

	// UPDATE vaga
	@RequestMapping(value = "/editar-vaga", method = RequestMethod.POST)
	public String updateVaga(@Valid Jobs jobs, BindingResult result, RedirectAttributes attributes) {
		jobsRepository.save(jobs);
		attributes.addFlashAttribute("success", "Vaga alterada com sucesso!");

		long codeLong = jobs.getCode();
		String code = "" + codeLong;
		return "redirect:/" + code;
	}

}
