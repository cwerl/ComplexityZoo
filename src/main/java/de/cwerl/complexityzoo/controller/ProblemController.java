package de.cwerl.complexityzoo.controller;

import java.util.ArrayList;
import java.util.List;

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
import de.cwerl.complexityzoo.model.ComplexityDataType;
import de.cwerl.complexityzoo.model.NormalProblem;
import de.cwerl.complexityzoo.model.ParaProblem;
import de.cwerl.complexityzoo.model.Problem;
import de.cwerl.complexityzoo.model.TinyMCESuggestion;
import de.cwerl.complexityzoo.repository.ComplexityClassRepository;
import de.cwerl.complexityzoo.repository.ProblemRepository;
import de.cwerl.complexityzoo.repository.relations.CTPRelationRepository;
import de.cwerl.complexityzoo.repository.relations.PTPRelationRepository;

@Controller
@RequestMapping(path = "/problems")
public class ProblemController {
    
    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private ComplexityClassRepository classRepository;

    @Autowired
    private PTPRelationRepository ptpRepository;

    @Autowired
    private CTPRelationRepository ctpRepository;

    @GetMapping(path="")
    public String list(Model model) {
        model.addAttribute("title", "Browse problems");
        model.addAttribute("problems", problemRepository.findAllOrdered());
        return "problems/list";
    }

    @GetMapping(path="/{id}")
    public String view(Model model, @PathVariable long id) {
        Problem p = problemRepository.getById(id);
        model.addAttribute("title", p.getName());
        model.addAttribute("problem", p);
        model.addAttribute("ptpRelations", ptpRepository.findRelationsByProblem(id));
        model.addAttribute("ptpTypes", ptpRepository.findAllTypes());
        model.addAttribute("ptpCandidates", ptpRepository.findAllRelationCandidatesOrdered(id));
        model.addAttribute("ctpRelations", ctpRepository.findRelationsByProblem(id));
        model.addAttribute("ctpCandidates", ctpRepository.findAllComplexityClassCandidatesOrdered(id));
        return "problems/view";
    }

    @GetMapping(path="/new")
    public String create(Model model) {
        model.addAttribute("title", "Create new problem");
        List<TinyMCESuggestion> suggestions = new ArrayList<>();
        suggestions.addAll(SuggestionParser.parse(classRepository.findAll()));
        suggestions.addAll(SuggestionParser.parse(problemRepository.findAll()));
        model.addAttribute("suggestions", suggestions);
        model.addAttribute("classes", classRepository.findAll());
        return "problems/new";
    }

    @PostMapping(path="/new/save")
    public String newSave(@Valid @RequestParam String name, @RequestParam String description, @RequestParam ComplexityDataType type) {
        if(problemRepository.existsByNameIgnoreCase(name)) {
            return "redirect:/problems/" + problemRepository.findByNameIgnoreCase(name).getId() + "?redir";
        }
        Problem p;
        if(type == ComplexityDataType.PARAMETERIZED) {
            p = new ParaProblem();
        } else {
            p = new NormalProblem();
        }
        p.setName(name);
        p.setDescription(description);
        problemRepository.save(p);
        return "redirect:/problems/" + p.getId() + "?success";
    }

    @GetMapping(path="/{id}/edit")
    public String edit(Model model, @PathVariable long id) {
        Problem p = problemRepository.getById(id);
        List<TinyMCESuggestion> suggestions = new ArrayList<>();
        suggestions.addAll(SuggestionParser.parse(classRepository.findAll()));
        suggestions.addAll(SuggestionParser.parse(problemRepository.findAll()));
        model.addAttribute("suggestions", suggestions);
        model.addAttribute("problem", p);
        model.addAttribute("title", "Edit problem " + p.getName());
        return "problems/edit";
    }

    @PostMapping(value="/{id}/edit/save")
    public String editSave(Problem p, @PathVariable long id) {
        p.setId(id);
        p.setName(problemRepository.getById(id).getName());
        problemRepository.save(p);
        return "redirect:/problems/" + id;
    }

    @RequestMapping(value="/{id}/edit/delete", method = RequestMethod.DELETE)
    public String delete(@PathVariable long id) {
        problemRepository.deleteById(id);
        return "redirect:/problems";
    }

    @RequestMapping(value="/search")
    public String search(@RequestParam String q, Model model) {
        if(q == null || q.isEmpty()) {
            return "redirect:/problems";
        }
        model.addAttribute("title", "Problems containing \"" + q + "\"");
        model.addAttribute("problems", problemRepository.searchProblem(q));
        model.addAttribute("query", q);
        return "problems/list";
    }
}
