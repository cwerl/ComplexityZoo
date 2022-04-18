package de.cwerl.complexityzoo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import de.cwerl.complexityzoo.model.Problem;
import de.cwerl.complexityzoo.repository.ComplexityClassRepository;
import de.cwerl.complexityzoo.repository.ProblemRepository;

@Controller
@RequestMapping(path="/problems")
public class ProblemController {
    @Autowired
    ProblemRepository problemRepository;

    @Autowired
    ComplexityClassRepository complexityClassRepository;

    @GetMapping(path="")
    public String newProblem(Model model) {
        model.addAttribute("problems", problemRepository.findAll());
        model.addAttribute("classes", complexityClassRepository.findAll());
        return "problems/list";
    }

    @PostMapping(path="/add")
    public String addNewProblem(Model model, @RequestParam String name, @RequestParam String description, @RequestParam Integer complexityClass) {
        Problem p = new Problem();
        p.setName(name);
        p.setDescription(description);
        p.setComplexityClass(complexityClassRepository.getById(complexityClass));
        problemRepository.save(p);
        model.addAttribute("newproblem", p);
        return "redirect:/problems";
    }
}
