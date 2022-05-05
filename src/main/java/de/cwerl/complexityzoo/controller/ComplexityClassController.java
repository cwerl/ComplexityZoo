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
import de.cwerl.complexityzoo.model.CTCRelation;
import de.cwerl.complexityzoo.model.CTCRelationType;
import de.cwerl.complexityzoo.model.ComplexityClass;
import de.cwerl.complexityzoo.repository.CTCRelationRepository;
import de.cwerl.complexityzoo.repository.ComplexityClassRepository;

@Controller
@RequestMapping(path="/classes")
public class ComplexityClassController {
    @Autowired
    private ComplexityClassRepository complexityClassRepository;

    @Autowired
    private CTCRelationRepository ctcRelationRepository;

    @GetMapping(path="")
    public String getAllClasses(Model model) {
        model.addAttribute("title", "Browse complexity classes");
        model.addAttribute("classes", complexityClassRepository.findAllOrdered());
        return "classes/list";
    }

    @GetMapping(path="/{id}")
    public String getClass(Model model, @PathVariable long id) {
        ComplexityClass c = complexityClassRepository.getById(id);
        model.addAttribute("title", c.getName());
        model.addAttribute("class", c);
        model.addAttribute("relationCandidates", ctcRelationRepository.findAllRelationCandidatesOrdered(id));
        model.addAttribute("relations", ctcRelationRepository.getAllRelations(id));
        return "classes/view";
    }
    
    @PostMapping(value="/{firstClassId}/new-relation/save")
    public String newRelationSave(@PathVariable long firstClassId, @RequestParam long secondClassId, @RequestParam int type, @RequestParam String description) {
        if(ctcRelationRepository.existsByClassPair(firstClassId, secondClassId)) {
            return "redirect:/classes/{firstClassId}";
        }
        CTCRelation relation = new CTCRelation();
        ComplexityClass firstClass = complexityClassRepository.getById(firstClassId);
        ComplexityClass secondClass = complexityClassRepository.getById(secondClassId);
        switch(type) {
            case 1:
                relation.setFirstClass(firstClass);
                relation.setSecondClass(secondClass);
                relation.setType(CTCRelationType.EQUAL_TO);
                break;
            case 2:
                relation.setFirstClass(firstClass);
                relation.setSecondClass(secondClass);
                relation.setType(CTCRelationType.SUBSET_OF);
                break;
            case 3:
                relation.setFirstClass(firstClass);
                relation.setSecondClass(secondClass);
                relation.setType(CTCRelationType.PROPER_SUBSET_OF);
                break;
            case 4:
                relation.setFirstClass(firstClass);
                relation.setSecondClass(secondClass);
                relation.setType(CTCRelationType.SUPERSET_OF);
                break;
            case 5:
                relation.setFirstClass(firstClass);
                relation.setSecondClass(secondClass);
                relation.setType(CTCRelationType.PROPER_SUPERSET_OF);
                break;
        }
        relation.setDescription(description);
        ctcRelationRepository.save(relation);
        return "redirect:/classes/{firstClassId}";
    }

    @RequestMapping(value="{classId}/relation/{relationId}/delete", method = RequestMethod.DELETE)
    public String deleteRelation(@PathVariable long classId, @PathVariable long relationId) {
        ctcRelationRepository.deleteById(relationId);
        return "redirect:/classes/{classId}";
    }

    @GetMapping(path="/new")
    public String createNewClass(Model model) {
        model.addAttribute("title", "Create new complexity class");
        model.addAttribute("classSuggestions", SuggestionParser.parse(complexityClassRepository.findAll(), "Complexity Class"));
        return "classes/new";
    }


    @PostMapping(path="/new/save")
    public String saveNewClass(@Valid @RequestParam String name, @RequestParam String description) {
        ComplexityClass c = new ComplexityClass();
        if(complexityClassRepository.existsByNameIgnoreCase(name)) {
            return "redirect:/classes/" + complexityClassRepository.findByNameIgnoreCase(name).getId() + "?redir";
        }
        c.setName(name);
        c.setDescription(description);
        complexityClassRepository.save(c);
        return "redirect:/classes/" + c.getId() + "?success";
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
    public String saveClass(ComplexityClass c, @PathVariable long id) {
        c.setId(id);
        c.setName(complexityClassRepository.getById(id).getName());
        complexityClassRepository.save(c);
        return "redirect:/classes/" + id;
    }

    @RequestMapping(value="/{id}/edit/delete", method = RequestMethod.DELETE)
    public String deleteClass(@PathVariable long id) {
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
