package de.cwerl.complexityzoo.controller.data;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import de.cwerl.complexityzoo.model.TinyMCESuggestion;
import de.cwerl.complexityzoo.model.data.ComplexityDataType;
import de.cwerl.complexityzoo.model.data.Problem;
import de.cwerl.complexityzoo.model.data.normal.NormalProblem;
import de.cwerl.complexityzoo.model.data.para.ParaProblem;
import de.cwerl.complexityzoo.model.data.para.ParaSubProblem;
import de.cwerl.complexityzoo.repository.data.ComplexityClassRepository;
import de.cwerl.complexityzoo.repository.data.ParaSubProblemRepository;
import de.cwerl.complexityzoo.repository.data.ProblemRepository;
import de.cwerl.complexityzoo.repository.relations.CTPRelationRepository;
import de.cwerl.complexityzoo.repository.relations.PTPRelationRepository;
import de.cwerl.complexityzoo.service.SuggestionParser;

@Controller
@RequestMapping(path = "/problems")
public class ProblemController {
    
    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private ParaSubProblemRepository subProblemRepository;

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

    @GetMapping(path = "/{id}")
    public String view(Model model, @PathVariable long id) {
        Problem p = problemRepository.getById(id);
        if(p.getType().equals(ComplexityDataType.PARAMETERIZED.name())) {
            model.addAttribute("subproblems", problemRepository.getSubProblems(id));
            model.addAttribute("parent", problemRepository.getById(id));
        }
        model.addAttribute("title", p.getName());
        model.addAttribute("problem", p);
        model.addAttribute("ptpRelations", ptpRepository.findRelationsByProblem(id));
        model.addAttribute("ptpTypes", ptpRepository.findAllTypes());
        model.addAttribute("ptpCandidates", ptpRepository.findAllRelationCandidatesOrdered(p));
        model.addAttribute("ctpRelations", ctpRepository.findRelationsByProblem(id));
        model.addAttribute("ctpCandidates", ctpRepository.findAllComplexityClassCandidatesOrdered(p));
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
    @Transactional
    public String editSave(@PathVariable long id, @RequestParam String description) {
        problemRepository.updateDescription(id, description);
        return "redirect:/problems/" + id;
    }

    @RequestMapping(value="/{id}/edit/delete", method = RequestMethod.DELETE)
    public String delete(@PathVariable long id) {
        problemRepository.deleteById(id);
        return "redirect:/problems";
    }

    @PostMapping(value = "/{id}/param/new/save")
    public String newParamSave(@PathVariable long id, @RequestParam String description, @RequestParam String parameter, Model model) {
        ParaProblem parentProblem = problemRepository.getParaById(id);
        ParaSubProblem newProblem = new ParaSubProblem();
        newProblem.setName(parentProblem.getName() + " (" + parameter + ")");
        newProblem.setDescription(description);
        newProblem.setParameter(parameter);
        newProblem.setParentProblem(parentProblem);
        subProblemRepository.save(newProblem);
        return "redirect:/problems/" + newProblem.getId();
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
