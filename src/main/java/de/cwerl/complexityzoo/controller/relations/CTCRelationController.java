package de.cwerl.complexityzoo.controller.relations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import de.cwerl.complexityzoo.model.relations.CTCRelation;
import de.cwerl.complexityzoo.model.relations.CTCRelationType;
import de.cwerl.complexityzoo.repository.data.ComplexityClassRepository;
import de.cwerl.complexityzoo.repository.data.ProblemRepository;
import de.cwerl.complexityzoo.repository.relations.CTCRelationRepository;

@Controller
public class CTCRelationController {

    @Autowired
    ComplexityClassRepository classRepository;

    @Autowired
    ProblemRepository problemRepository;

    @Autowired
    CTCRelationRepository ctcRepository;

    @PostMapping(value="/ctc-relations/new/save")
    public String newCTCRelationSave(@RequestParam long firstClassId, @RequestParam long secondClassId, @RequestParam CTCRelationType type, @RequestParam String reference, @RequestParam String redirect) {
        if(!(ctcRepository.existsByClassPair(firstClassId, secondClassId) || firstClassId == secondClassId)) {
            CTCRelation relation = new CTCRelation();
            relation.setFirstClass(classRepository.getById(firstClassId));
            relation.setSecondClass(classRepository.getById(secondClassId));
            relation.setType(type);
            relation.setReference(reference);
            ctcRepository.save(relation);
        }
        return "redirect:" + redirect;
    }

    @RequestMapping(value="/ctc-relations/{relationId}/delete", method = RequestMethod.DELETE)
    public String deleteCTCRelation(@PathVariable long relationId, @RequestParam String redirect) {
        ctcRepository.deleteById(relationId);
        return "redirect:" + redirect;
    }
}
