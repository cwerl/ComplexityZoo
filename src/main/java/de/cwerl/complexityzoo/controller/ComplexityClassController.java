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
        model.addAttribute("classes", complexityClassRepository.findAllByOrderByNameAsc());
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
        if(complexityClassRepository.existsByNameIgnoreCase(name)) {
            return "redirect:/classes/" + complexityClassRepository.findByNameIgnoreCase(name).getId() + "?redir";
        }
        c.setName(name);
        c.setDescription(description);
        complexityClassRepository.save(c);
        return "redirect:/classes/" + c.getId() + "?success";
    }

    @RequestMapping(path="/{id}/edit")
    public String editClass(Model model, @PathVariable Integer id) {
        ComplexityClass c = complexityClassRepository.getById(id);
        model.addAttribute("class", c);
        model.addAttribute("title", "Edit complexity class " + c.getName());
        return "classes/edit";
    }

    @PostMapping(value="/{id}/edit")
    public String saveClass(Model model, ComplexityClass c, String description, @PathVariable Integer id) {
        if (!c.getName().equalsIgnoreCase(complexityClassRepository.getById(id).getName()) && complexityClassRepository.existsByNameIgnoreCase(c.getName())) {
            return "redirect:/classes/" + id + "/edit?error";
        }
        c.setId(id);
        complexityClassRepository.save(c);
        return "redirect:/classes/" + id;
    }

    @RequestMapping(value="/{id}/delete", method = RequestMethod.DELETE)
    public String deleteClass(@PathVariable Integer id, Model model) {
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
