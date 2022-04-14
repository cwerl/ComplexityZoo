package de.cwerl.complexityzoo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import de.cwerl.complexityzoo.model.ComplexityClass;
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

    @GetMapping(path="/{id}")
    public String getClass(Model model, @PathVariable Integer id) {
        ComplexityClass c = complexityClassRepository.getById(id);
        model.addAttribute("title", c.getName());
        model.addAttribute("class", c);
        return "classes/view";
    }

    @PostMapping(path="/add")
    public String addNewClass(@Valid @RequestParam String name, @RequestParam String description, Model model) {
        ComplexityClass c = new ComplexityClass();
        c.setName(name);
        c.setDescription(description);
        complexityClassRepository.save(c);
        model.addAttribute("newclass", c);
        return "redirect:/classes";
    }

    @RequestMapping(value="/{id}/delete", method = RequestMethod.DELETE)
    public String deleteClass(@PathVariable Integer id, Model model) {
        complexityClassRepository.deleteById(id);
        return "redirect:/classes";
    }
}
