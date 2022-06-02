package de.cwerl.complexityzoo.controller.relations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import de.cwerl.complexityzoo.model.data.ComplexityClass;
import de.cwerl.complexityzoo.model.data.Problem;
import de.cwerl.complexityzoo.model.relations.NormalCTPRelation;
import de.cwerl.complexityzoo.model.relations.ParaCTPRelation;
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
    
    @PostMapping(value="/normal-ctp-relations/new/save")
    public String newNormalCTPRelationSave(@RequestParam long classId, @RequestParam long problemId, @RequestParam String reference, @RequestParam String redirect) {
        ComplexityClass complexityClass = classRepository.getById(classId);
        Problem problem = problemRepository.getById(problemId);
        NormalCTPRelation relation = new NormalCTPRelation();
        relation.setComplexityClass(complexityClass);
        relation.setProblem(problem);
        relation.setReference(reference);
        ctpRepository.save(relation);
        return "redirect:" + redirect;
    }

    @PostMapping(value="/para-ctp-relations/new/save")
    public String newParaCTPRelationSave(@RequestParam long classId, @RequestParam long problemId, @RequestParam String reference, @RequestParam String param, @RequestParam String redirect) {
        ComplexityClass complexityClass = classRepository.getById(classId);
        Problem problem = problemRepository.getById(problemId);
        ParaCTPRelation relation = new ParaCTPRelation();
        relation.setComplexityClass(complexityClass);
        relation.setProblem(problem);
        relation.setReference(reference);
        relation.setParam(param);
        ctpRepository.save(relation);
        return "redirect:" + redirect;
    }

    @RequestMapping(value="/ctp-relations/{relationId}/delete", method = RequestMethod.DELETE)
    public String deleteCTPRelation(@PathVariable long relationId, @RequestParam String redirect) {
        ctpRepository.deleteById(relationId);
        return "redirect:" + redirect;
    }
}
