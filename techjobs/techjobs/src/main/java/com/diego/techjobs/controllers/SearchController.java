package com.diego.techjobs.controllers;

import com.diego.techjobs.repository.CandidateRepository;
import com.diego.techjobs.repository.JobsRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class SearchController {

    private final CandidateRepository candidateRepository;
    private final JobsRepository jobsRepository;

    /*INJEÇÃO DOS REPOSITORIES*/
    public SearchController(CandidateRepository candidateRepository, JobsRepository jobsRepository) {
        this.candidateRepository = candidateRepository;
        this.jobsRepository = jobsRepository;
    }

    //GET
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView abrirIndex() {
        ModelAndView mv = new ModelAndView("index");
        return mv;
    }

    //POST
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ModelAndView searchIndex(@RequestParam("buscar") String buscar, @RequestParam("nome") String nome){


        ModelAndView mv = new ModelAndView("index");
        String mensagem = "Resultados da busca por " + buscar;

        if (nome.equals("nameCadidate")){
            mv.addObject("candidates", candidateRepository.findByNamesCandidatos(buscar));

        }else if (nome.equals("name")){
            mv.addObject("vagas", jobsRepository.findByNamesJobsOportunity(buscar));

        }else {
            mv.addObject("candidates", candidateRepository.findByNamesCandidatos(buscar));
            mv.addObject("vagas", jobsRepository.findByNamesJobsOportunity(buscar));

        }
        return mv;



    }


}






