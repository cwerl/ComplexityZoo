package de.cwerl.complexityzoo.controller.relations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import de.cwerl.complexityzoo.model.relations.PTPRelation;
import de.cwerl.complexityzoo.repository.data.ComplexityClassRepository;
import de.cwerl.complexityzoo.repository.data.ProblemRepository;
import de.cwerl.complexityzoo.repository.relations.PTPRelationRepository;

@Controller
public class PTPRelationController {

    @Autowired
    ComplexityClassRepository classRepository;

    @Autowired
    ProblemRepository problemRepository;

    @Autowired
    PTPRelationRepository ptpRepository;
    
    @PostMapping(value="/ptp-relations/new/save")
    public String newPTPRelationSave(@RequestParam long firstProblemId, @RequestParam long secondProblemId, @RequestParam String relationType, @RequestParam String reference, @RequestParam String redirect) {
        PTPRelation relation = new PTPRelation();
        relation.setRelationType(relationType);
        relation.setReference(reference);
        ptpRepository.save(relation);
        return "redirect:" + redirect;
    }

    @RequestMapping(value="/ptp-relations/{relationId}/delete", method = RequestMethod.DELETE)
    public String deletePTPRelation(@PathVariable long relationId, @RequestParam String redirect) {
        ptpRepository.deleteById(relationId);
        return "redirect:" + redirect;
    }
}
