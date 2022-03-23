package com.diego.techjobs.controllers;

import com.diego.techjobs.repository.CandidateRepository;
import com.diego.techjobs.repository.JobsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class SearchController {

    /*INJEÇÃO DOS REPOSITORIES*/
    @Autowired
    private  CandidateRepository candidateRepository;
    @Autowired
    private  JobsRepository jobsRepository;



    //GET
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView abrirIndex() {
        ModelAndView mv = new ModelAndView("index");
        log.info("index request");
        return mv;
    }

    //POST
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ModelAndView buscarIndex(@RequestParam("buscar") String buscar, @RequestParam("name") String name) {

        ModelAndView mv = new ModelAndView("index");
        String mensagem = "Resultados da busca por " + buscar;
        log.info("resultado da busca" + buscar);

        if (name.equals("titlejobs")) {
            mv.addObject("vagas", jobsRepository.findByNamesJobsOportunity(buscar));

        }else if (name.equals("namecandidates")){
            mv.addObject("candidates",candidateRepository.findByNamesCandidates(buscar));
        }else {
            mv.addObject("vagas", jobsRepository.findByNamesJobsOportunity(buscar));
            mv.addObject("candidates",candidateRepository.findByNamesCandidates(buscar));

        }

        mv.addObject("mensagem", mensagem);

        return mv;
    }



}






