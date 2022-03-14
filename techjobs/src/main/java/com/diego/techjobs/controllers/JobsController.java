package com.diego.techjobs.controllers;


import com.diego.techjobs.models.Candidate;
import com.diego.techjobs.models.Jobs;
import com.diego.techjobs.repository.CandidateRepository;
import com.diego.techjobs.repository.JobsRepository;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.validation.Valid;


@Controller
@RequestMapping("/techjobs")
public class JobsController {

    private final JobsRepository jobsRepository;
    private final CandidateRepository  candidateRepository;

    public JobsController(JobsRepository jobsRepository, CandidateRepository candidateRepository) {
        this.jobsRepository = jobsRepository;
        this.candidateRepository = candidateRepository;
    }

    @GetMapping("cadastrar-vaga")
    public String form(){
        return "/formJobs";
    }

    @PostMapping("/cadastrar-vaga")
    public String saveJobs(@Valid Jobs jobs, BindingResult result, RedirectAttributes attributes){

        if (result.hasErrors()){
            attributes.addFlashAttribute("mensagem", "verifique os campos");
            return "redirect:/cadsastrar-vaga";
        }
        jobsRepository.save(jobs);
        attributes.addFlashAttribute("mensagem", "vaga cadastrada com sucesso");
        return "redirect:/cadsastrar-vaga";

    }

    /*listar as vagas*/
    @GetMapping("/jobs")
    public ModelAndView getJobs(){
        ModelAndView mv = new ModelAndView("jobs/listaVaga");
        Iterable<Jobs> jobs = jobsRepository.findAll();
        mv.addObject("jobs", "jobs");
        return mv;

    }

    @GetMapping("/{code}")
    public ModelAndView getDetailJobs(@PathVariable ("code") Long code){
        Jobs jobs = jobsRepository.findByCode(code);
        ModelAndView mv = new ModelAndView("jobs/jobs detail");
        mv.addObject("jobs", jobs);
        Iterable<Candidate> candidates = candidateRepository.findByJobs(jobs);
        mv.addObject("candidates", candidates);
        return  mv;

    }

    @DeleteMapping("/delete-vaga")
    public  String deleteJobs(Long code){
        Jobs jobs  = jobsRepository.findByCode(code);
        jobsRepository.delete(jobs);
        return "redirect:/jobs";

    }

    @GetMapping
    public String detailJobs(@PathVariable ("code") Long code, @Valid Candidate candidate, BindingResult result,
                             RedirectAttributes attributes){

        if (result.hasErrors()){
            attributes.addFlashAttribute("mensagem", "verifique os campos");
            return "redirect:/{code}";

        }
        if (candidateRepository.findByRg(candidate.getRg())!= null){
            attributes.addFlashAttribute("erro", "RG duplicado");
            return "redirect:/{code}";

        }

        Jobs jobs = jobsRepository.findByCode(code);
        candidate.setJobs(jobs);
        candidateRepository.save(candidate);
        attributes.addFlashAttribute("mensagem", "candidato adiocionado");
        return "redirect:/{code}";


    }







}
