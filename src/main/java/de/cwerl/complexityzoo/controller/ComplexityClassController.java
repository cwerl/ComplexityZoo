package de.cwerl.complexityzoo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import de.cwerl.complexityzoo.repository.ComplexityClassRepository;

@Controller
@RequestMapping(path="/classes")
public class ComplexityClassController {
    @Autowired
    private ComplexityClassRepository complexityClassRepository;

    @GetMapping(path="")
    public String getAllClasses(Model model) {
        model.addAttribute("title", "All complexity classes");
        model.addAttribute("classes", complexityClassRepository.findAll());
        return "classes/list";
    }
}
