package de.cwerl.complexityzoo.controller.relations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import de.cwerl.complexityzoo.model.data.ComplexityClass;
import de.cwerl.complexityzoo.model.data.AbstractProblem;
import de.cwerl.complexityzoo.model.relations.CTPRelation;
import de.cwerl.complexityzoo.repository.data.ComplexityClassRepository;
import de.cwerl.complexityzoo.repository.data.ProblemRepository;
import de.cwerl.complexityzoo.repository.relations.CTPRelationRepository;

@Controller
public class CTPRelationController {

    @Autowired
    CTPRelationRepository ctpRepository;

    @Autowired
    ComplexityClassRepository classRepository;

    @Autowired
    ProblemRepository problemRepository;
    
    @PostMapping(value="/ctp-relations/new/save")
    public String newCTPRelationSave(@RequestParam long classId, @RequestParam long problemId, @RequestParam String reference, @RequestParam String redirect) {
        ComplexityClass complexityClass = classRepository.getById(classId);
        AbstractProblem problem = problemRepository.getById(problemId);
        CTPRelation relation = new CTPRelation();
        relation.setComplexityClass(complexityClass);
        relation.setProblem(problem);
        relation.setReference(reference);
        ctpRepository.save(relation);
        return "redirect:" + redirect;
    }

    @RequestMapping(value="/ctp-relations/{relationId}/delete", method = RequestMethod.DELETE)
    public String deleteCTPRelation(@PathVariable long relationId, @RequestParam String redirect) {
        ctpRepository.deleteById(relationId);
        return "redirect:" + redirect;
    }
}
