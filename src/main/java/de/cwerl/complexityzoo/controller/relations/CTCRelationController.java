package de.cwerl.complexityzoo.controller.relations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import de.cwerl.complexityzoo.model.data.ComplexityClass;
import de.cwerl.complexityzoo.model.relations.CTCRelation.CTCInterpretation;
import de.cwerl.complexityzoo.model.relations.CTCRelation.CTCRelation;
import de.cwerl.complexityzoo.model.relations.CTCRelation.CTCRelationType;
import de.cwerl.complexityzoo.repository.data.ComplexityClassRepository;
import de.cwerl.complexityzoo.repository.relations.CTCRelationRepository;

@Controller
@RequestMapping(value = "/relations/ctc")
public class CTCRelationController {

    @Autowired
    private ComplexityClassRepository classRepository;

    @Autowired
    private CTCRelationRepository ctcRepository;

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/new/save")
    public ModelAndView newSave(@RequestParam long firstClassId, @RequestParam long secondClassId,
            @RequestParam CTCRelationType relationType,
            @RequestHeader(value = HttpHeaders.REFERER, required = false) final String redirect,
            final RedirectAttributes redirectAttributes) {
        if (!(ctcRepository.existsByClassPair(firstClassId, secondClassId) || firstClassId == secondClassId)) {
            ComplexityClass firstClass = classRepository.getById(firstClassId);
            ComplexityClass secondClass = classRepository.getById(secondClassId);
            CTCRelation relation = new CTCRelation();
            relation.setFirstClass(firstClass);
            relation.setSecondClass(secondClass);
            relation.setRelationType(relationType);
            CTCInterpretation interpretation = getAllPaths(relation);
            ctcRepository.save(relation);
            if(interpretation != null) {
                ModelAndView mav = new ModelAndView("redirect:/relations/ctc/" + relation.getId());
                redirectAttributes.addFlashAttribute("altpath", interpretation);
                return mav;
            }
            return new ModelAndView("redirect:/relations/ctc/" + relation.getId() + "?success");
        }
        return new ModelAndView("redirect:" + redirect);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/{relationId}/delete", method = RequestMethod.DELETE)
    public String delete(@PathVariable long relationId, @RequestParam String redirect) {
        ctcRepository.deleteById(relationId);
        return "redirect:" + redirect;
    }

    @GetMapping(value = "/{id}")
    public String view(Model model, @PathVariable long id) {
        CTCRelation relation = ctcRepository.getById(id);
        model.addAttribute("relation", relation);
        model.addAttribute("title", "Relation details");
        model.addAttribute("relationTypeString", relation.getRelationType().toLaTeX());
        return "relations/view";
    }

    @GetMapping(value = "/{id}/edit")
    public String edit(Model model, @PathVariable long id) {
        model.addAttribute("relation", ctcRepository.getById(id));
        model.addAttribute("title", "Edit relation");
        return "relations/edit";
    }

    @Transactional
    @PostMapping(value = "/{id}/edit/save")
    public String editSave(Model model, @PathVariable long id, @RequestParam String reference) {
        ctcRepository.updateReference(id,
                Jsoup.clean(reference, ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString(),
                        Safelist.relaxed().preserveRelativeLinks(true)));
        return "redirect:/relations/ctc/" + id;
    }

    private CTCInterpretation getAllPaths(CTCRelation r) {
        Set<ComplexityClass> visited = new HashSet<ComplexityClass>();
        List<CTCRelation> pathList = new ArrayList<CTCRelation>();
        return getAllPathsUtil(r.getFirstClass(), r.getSecondClass(), visited, pathList, r.getRelationType());
    }

    private CTCInterpretation getAllPathsUtil(ComplexityClass u, ComplexityClass d,
            Set<ComplexityClass> visited, List<CTCRelation> pathList, final CTCRelationType newType) {
        if (u.equals(d) && pathList.size() > 1) {
            CTCRelationType derived = pathInterpreter(pathList);
            if(derived != null) {
                if(!areCompatible(derived, newType)) {
                    return new CTCInterpretation(derived, pathList);
                }
            }
        }

        visited.add(u);
        ComplexityClass neighbor;
        CTCRelation temp;
        for (CTCRelation ctc : ctcRepository.findRelationsByComplexityClass(u.getId())) {
            if (ctc.getFirstClass().equals(u)) {
                neighbor = ctc.getSecondClass();
                temp = ctc;
            } else {
                neighbor = ctc.getFirstClass();
                temp = new CTCRelation(ctc.getId(), ctc.getSecondClass(),
                        ctc.getFirstClass(), ctc.getRelationType().reverse());
            }
            if (!visited.contains(neighbor)) {
                pathList.add(temp);
                CTCInterpretation interpretation = getAllPathsUtil(neighbor, d, visited, pathList, newType);
                if(interpretation != null) {
                    return interpretation;
                }
                pathList.remove(temp);
            }
        }
        visited.remove(u);
        return null;
    }

    private CTCRelationType pathInterpreter(List<CTCRelation> path) {
        CTCRelationType derived = path.get(0).getRelationType();
        List<CTCRelation> temp = new ArrayList<CTCRelation>(path);
        temp.remove(derived);
        for (CTCRelation r : temp) {
            switch (derived) {
                case EQUAL_TO:
                    derived = r.getRelationType();
                    break;
                case SUBSET_OF:
                    switch (r.getRelationType()) {
                        case SUBSET_OF:
                            derived = CTCRelationType.SUBSET_OF;
                            break;
                        case EQUAL_TO:
                            derived = CTCRelationType.SUBSET_OF;
                            break;
                        default:
                            return null;
                    }
                    break;
                case PROPER_SUBSET_OF:
                    switch (r.getRelationType()) {
                        case PROPER_SUBSET_OF:
                            derived = CTCRelationType.PROPER_SUBSET_OF;
                            break;
                        case EQUAL_TO:
                            derived = CTCRelationType.PROPER_SUBSET_OF;
                            break;
                        default:
                            return null;
                    }
                    break;
                case SUPERSET_OF:
                    switch (r.getRelationType()) {
                        case SUPERSET_OF:
                            derived = CTCRelationType.SUPERSET_OF;
                            break;
                        case EQUAL_TO:
                            derived = CTCRelationType.SUPERSET_OF;
                            break;
                        default:
                            return null;
                    }
                    break;
                case PROPER_SUPERSET_OF:
                    switch (r.getRelationType()) {
                        case PROPER_SUPERSET_OF:
                            derived = CTCRelationType.PROPER_SUPERSET_OF;
                            break;
                        case EQUAL_TO:
                            derived = CTCRelationType.PROPER_SUPERSET_OF;
                            break;
                        default:
                            return null;
                    }
                    break;
                default:
                    return null;
            }
        }
        return derived;
    }

    /**
     * Two relations are considered compatible if they are the same or if the newly created relation is more strict than the derived relation.
     * @param derived The relation that has been derived from a path.
     * @param created The direct relation that has just been created.
     * @return
     */
    private boolean areCompatible(CTCRelationType derived, CTCRelationType created) {
        if(derived == created) {
            return true;
        } else if(derived == CTCRelationType.SUBSET_OF && created == CTCRelationType.EQUAL_TO || derived == CTCRelationType.SUPERSET_OF && created == CTCRelationType.EQUAL_TO) {
            return true;
        } else if(derived == CTCRelationType.SUBSET_OF && created == CTCRelationType.PROPER_SUBSET_OF || derived == CTCRelationType.SUPERSET_OF && created == CTCRelationType.PROPER_SUPERSET_OF) {
            return true;
        } else if(derived == CTCRelationType.SUBSET_OF && created == CTCRelationType.NOT_EQUAL_TO || derived == CTCRelationType.SUPERSET_OF && created == CTCRelationType.NOT_EQUAL_TO) {
            return true;
        }
        return false;
    }
}