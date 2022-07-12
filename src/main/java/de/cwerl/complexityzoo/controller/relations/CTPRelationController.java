package de.cwerl.complexityzoo.controller.relations;

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

import de.cwerl.complexityzoo.model.data.AbstractProblem;
import de.cwerl.complexityzoo.model.data.ComplexityClass;
import de.cwerl.complexityzoo.model.relations.CTPRelation.CTPRelation;
import de.cwerl.complexityzoo.model.relations.CTPRelation.CTPRelationType;
import de.cwerl.complexityzoo.repository.data.ComplexityClassRepository;
import de.cwerl.complexityzoo.repository.data.ProblemRepository;
import de.cwerl.complexityzoo.repository.relations.CTPRelationRepository;

@Controller
@RequestMapping(value = "/relations/ctp")
public class CTPRelationController {

    @Autowired
    private CTPRelationRepository ctpRepository;

    @Autowired
    private ComplexityClassRepository classRepository;

    @Autowired
    private ProblemRepository problemRepository;
    
    @PreAuthorize("isAuthenticated()")
    @PostMapping(value="/new/save")
    public String newCTPRelationSave(@RequestParam long classId, @RequestParam long problemId, @RequestParam CTPRelationType relationType, @RequestParam String reference, @RequestParam String redirect) {
        ComplexityClass complexityClass = classRepository.getById(classId);
        AbstractProblem problem = problemRepository.getById(problemId);
        CTPRelation relation = new CTPRelation();
        relation.setComplexityClass(complexityClass);
        relation.setProblem(problem);
        relation.setRelationType(relationType);
        relation.setReference(reference);
        ctpRepository.save(relation);
        return "redirect:" + redirect;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value="/{relationId}/delete", method = RequestMethod.DELETE)
    public String deleteCTPRelation(@PathVariable long relationId, @RequestParam String redirect) {
        ctpRepository.deleteById(relationId);
        return "redirect:" + redirect;
    }

    @GetMapping(value = "/{id}")
    public String view(Model model, @PathVariable long id) {
        CTPRelation relation = ctpRepository.getById(id);
        model.addAttribute("relation", relation);
        model.addAttribute("title", "Relation details");
        model.addAttribute("relationTypeString", relation.getRelationType().toLaTeX(false));
        return "relations/view";
    }

    @GetMapping(value = "/{id}/edit")
    public String edit(Model model, @PathVariable long id) {
        model.addAttribute("relation", ctpRepository.getById(id));
        model.addAttribute("title", "Edit relation");
        return "relations/edit";
    }

    @Transactional
    @PostMapping(value = "/{id}/edit/save")
    public String editSave(Model model, @PathVariable long id, @RequestParam String reference) {
        ctpRepository.updateReference(id, reference);
        return "redirect:/relations/ctp/" + id;
    }
}
