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

import de.cwerl.complexityzoo.SuggestionParser;
import de.cwerl.complexityzoo.model.ComplexityClass;
import de.cwerl.complexityzoo.repository.ComplexityClassRepository;

@Controller
@RequestMapping(path="/classes")
public class ComplexityClassController {
    @Autowired
    private ComplexityClassRepository complexityClassRepository;

    @GetMapping(path="")
    public String getAllClasses(Model model) {
        model.addAttribute("title", "Browse complexity classes");
        model.addAttribute("classes", complexityClassRepository.findAllOrdered());
        model.addAttribute("classSuggestions", SuggestionParser.parse(complexityClassRepository.findAll(), "Complexity Class"));
        return "classes/list";
    }

    @GetMapping(path="/{id}")
    public String getClass(Model model, @PathVariable long id) {
        ComplexityClass c = complexityClassRepository.getById(id);
        model.addAttribute("title", c.getName());
        model.addAttribute("class", c);
        return "classes/view";
    }

    @GetMapping(path="/new")
    public String createNewClass(Model model) {
        model.addAttribute("title", "Create new complexity class");
        return "classes/new";
    }


    @PostMapping(path="/new/save")
    public String saveNewClass(@Valid @RequestParam String name, @RequestParam String description, Model model) {
        ComplexityClass c = new ComplexityClass();
        if(complexityClassRepository.existsByNameIgnoreCase(name)) {
            return "redirect:/classes/" + complexityClassRepository.findByNameIgnoreCase(name).getClassId() + "?redir";
        }
        c.setName(name);
        c.setDescription(description);
        complexityClassRepository.save(c);
        return "redirect:/classes/" + c.getClassId() + "?success";
    }

    @GetMapping(path="/{id}/edit")
    public String editClass(Model model, @PathVariable long id) {
        ComplexityClass c = complexityClassRepository.getById(id);
        model.addAttribute("class", c);
        model.addAttribute("classSuggestions", SuggestionParser.parse(complexityClassRepository.findAll(), "Complexity Class"));
        model.addAttribute("title", "Edit complexity class " + c.getName());
        return "classes/edit";
    }

    @PostMapping(value="/{id}/edit/save")
    public String saveClass(Model model, ComplexityClass c, @PathVariable long id) {
        c.setClassId(id);
        c.setName(complexityClassRepository.getById(id).getName());
        complexityClassRepository.save(c);
        return "redirect:/classes/" + id;
    }

    @RequestMapping(value="/{id}/edit/delete", method = RequestMethod.DELETE)
    public String deleteClass(@PathVariable long id, Model model) {
        complexityClassRepository.deleteById(id);
        return "redirect:/classes";
    }

    @RequestMapping(value="/search")
    public String searchClass(@RequestParam String q, Model model) {
        if(q == null || q.isEmpty()) {
            return "redirect:/classes";
        }
        model.addAttribute("title", "Complexity classes containing \"" + q + "\"");
        model.addAttribute("classes", complexityClassRepository.searchClass(q));
        model.addAttribute("query", q);
        return "classes/list";
    }
}
