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

import de.cwerl.complexityzoo.model.relations.PTPRelation.PTPRelation;
import de.cwerl.complexityzoo.repository.data.ProblemRepository;
import de.cwerl.complexityzoo.repository.relations.PTPRelationRepository;

@Controller
@RequestMapping(value = "/relations/ptp")
public class PTPRelationController {

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private PTPRelationRepository ptpRepository;
    
    @PreAuthorize("isAuthenticated()")
    @PostMapping(value="/new/save")
    public String newPTPRelationSave(@RequestParam long firstProblemId, @RequestParam long secondProblemId, @RequestParam String relationType, @RequestParam String redirect) {
        PTPRelation relation = new PTPRelation();
        relation.setFirstProblem(problemRepository.getById(firstProblemId));
        relation.setSecondProblem(problemRepository.getById(secondProblemId));
        relation.setRelationType(relationType);
        ptpRepository.save(relation);
        return "redirect:/relations/ptp/" + relation.getId() + "?success";
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value="/{relationId}/delete", method = RequestMethod.DELETE)
    public String deletePTPRelation(@PathVariable long relationId, @RequestParam String redirect) {
        ptpRepository.deleteById(relationId);
        return "redirect:" + redirect;
    }

    @GetMapping(value = "/{id}")
    public String view(Model model, @PathVariable long id) {
        PTPRelation relation = ptpRepository.getById(id);
        model.addAttribute("relation", relation);
        model.addAttribute("title", "Relation details");
        model.addAttribute("relationTypeString", relation.getRelationType());
        return "relations/view";
    }

    @GetMapping(value = "/{id}/edit")
    public String edit(Model model, @PathVariable long id) {
        model.addAttribute("relation", ptpRepository.getById(id));
        model.addAttribute("title", "Edit relation");
        return "relations/edit";
    }

    @Transactional
    @PostMapping(value = "/{id}/edit/save")
    public String editSave(Model model, @PathVariable long id, @RequestParam String reference) {
        ptpRepository.updateReference(id, reference);
        return "redirect:/relations/ptp/" + id;
    }
}
