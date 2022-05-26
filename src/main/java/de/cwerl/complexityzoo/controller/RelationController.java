package de.cwerl.complexityzoo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import de.cwerl.complexityzoo.model.ComplexityClass;
import de.cwerl.complexityzoo.model.Problem;
import de.cwerl.complexityzoo.model.relations.CTCRelation;
import de.cwerl.complexityzoo.model.relations.CTCRelationType;
import de.cwerl.complexityzoo.model.relations.CTPRelation;
import de.cwerl.complexityzoo.model.relations.NormalCTPRelation;
import de.cwerl.complexityzoo.model.relations.PTPRelation;
import de.cwerl.complexityzoo.repository.ComplexityClassRepository;
import de.cwerl.complexityzoo.repository.ProblemRepository;
import de.cwerl.complexityzoo.repository.relations.CTCRelationRepository;
import de.cwerl.complexityzoo.repository.relations.CTPRelationRepository;
import de.cwerl.complexityzoo.repository.relations.PTPRelationRepository;

@Controller
public class RelationController {

    @Autowired
    ComplexityClassRepository classRepository;

    @Autowired
    ProblemRepository problemRepository;

    @Autowired
    CTCRelationRepository ctcRepository;

    @Autowired
    CTPRelationRepository ctpRepository;

    @Autowired
    PTPRelationRepository ptpRepository;

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

    @PostMapping(value="/ctp-relations/new/save")
    public String newCTPRelationSave(@RequestParam long classId, @RequestParam long problemId, @RequestParam String reference, @RequestParam String redirect) {
        ComplexityClass complexityClass = classRepository.getById(classId);
        Problem problem = problemRepository.getById(problemId);
        CTPRelation relation = new NormalCTPRelation();
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

    @PostMapping(value="/ptp-relations/new/save")
    public String newCTPRelationSave(@RequestParam long firstProblemId, @RequestParam long secondProblemId, @RequestParam String type, @RequestParam String reference, @RequestParam String redirect) {
        PTPRelation relation = new PTPRelation();
        relation.setFirstProblem(problemRepository.getById(firstProblemId));
        relation.setSecondProblem(problemRepository.getById(secondProblemId));
        relation.setType(type);
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
