package de.cwerl.complexityzoo.controller.data;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import de.cwerl.complexityzoo.model.TinyMCESuggestion;
import de.cwerl.complexityzoo.model.data.ComplexityDataType;
import de.cwerl.complexityzoo.model.data.normal.Problem;
import de.cwerl.complexityzoo.model.data.para.Parameterization;
import de.cwerl.complexityzoo.repository.data.ComplexityClassRepository;
import de.cwerl.complexityzoo.repository.data.ParameterizationRepository;
import de.cwerl.complexityzoo.repository.data.ProblemRepository;
import de.cwerl.complexityzoo.repository.relations.CTPRelationRepository;
import de.cwerl.complexityzoo.repository.relations.PTPRelationRepository;
import de.cwerl.complexityzoo.service.SuggestionParser;

@Controller
@RequestMapping(path = "/problems/{id}/params")
public class ParameterizationController {

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private ParameterizationRepository parameterizationRepository;

    @Autowired
    private ComplexityClassRepository classRepository;

    @Autowired
    private PTPRelationRepository ptpRepository;

    @Autowired
    private CTPRelationRepository ctpRepository;

    @GetMapping(path="/{paramId}")
    public String viewParam(Model model, @PathVariable("id") long id, @PathVariable("paramId") long paramId) {
        Parameterization param = parameterizationRepository.getById(paramId);
        model.addAttribute("problem", param);
        model.addAttribute("title", param.getName());
        model.addAttribute("isParam", true);
        model.addAttribute("ptpRelations", ptpRepository.findRelationsByProblem(paramId));
        model.addAttribute("ptpTypes", ptpRepository.findAllTypes());
        model.addAttribute("ptpCandidates", ptpRepository.findAllRelationCandidatesOrdered(paramId));
        model.addAttribute("ctpRelations", ctpRepository.findRelationsByProblem(paramId));
        model.addAttribute("ctpCandidates", ctpRepository.findAllComplexityClassCandidatesOrdered(paramId, ComplexityDataType.Values.PARAMETERIZED));
        return "problems/params/view";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/new/save")
    public String newParamSave(@PathVariable long id, @RequestParam String description, @RequestParam String name, Model model) {
        Problem p = problemRepository.getById(id);
        Parameterization para = new Parameterization();
        para.setDescription(description);
        para.setName(name);
        para.setParent(p);
        parameterizationRepository.save(para);
        return "redirect:/problems/" + id + "/params/" + para.getId() + "?success";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(path="/{paramId}/edit")
    public String editParam(Model model, @PathVariable long id, @PathVariable long paramId) {
        Parameterization para = parameterizationRepository.getById(paramId);
        List<TinyMCESuggestion> suggestions = new ArrayList<>();
        suggestions.addAll(SuggestionParser.parseComplexityClasses(classRepository.findAll()));
        suggestions.addAll(SuggestionParser.parseProblems(problemRepository.findAll()));
        model.addAttribute("suggestions", suggestions);
        model.addAttribute("problem", para);
        model.addAttribute("title", "Edit parameterization " + para.getName());
        return "problems/params/edit";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value="/{paramId}/edit/save")
    @Transactional
    public String editParamSave(@PathVariable long id, @PathVariable long paramId, @RequestParam String description) {
        parameterizationRepository.updateDescription(paramId, description);
        return "redirect:/problems/" + id + "/params/" + paramId;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value="/{paramId}/edit/delete", method = RequestMethod.DELETE)
    public String delete(@PathVariable long id, @PathVariable long paramId) {
        parameterizationRepository.deleteById(paramId);
        return "redirect:/problems/" + id;
    }
}
