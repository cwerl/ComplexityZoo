package de.cwerl.complexityzoo.controller.relations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
            @RequestParam CTCRelationType relationType, @RequestHeader(value = HttpHeaders.REFERER, required = false) final String redirect, final RedirectAttributes redirectAttributes) {
        ModelAndView mav;
        if (!(ctcRepository.existsByClassPair(firstClassId, secondClassId) || firstClassId == secondClassId)) {
            ComplexityClass firstClass = classRepository.getById(firstClassId);
            ComplexityClass secondClass = classRepository.getById(secondClassId);
            CTCRelation relation = new CTCRelation();
            relation.setFirstClass(firstClass);
            relation.setSecondClass(secondClass);
            relation.setRelationType(relationType);
            ctcRepository.save(relation);
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

    // private List<List<CTCRelation>> getAllPaths(ComplexityClass s, ComplexityClass d) {
    //     Set<ComplexityClass> visited = new HashSet<ComplexityClass>();
    //     List<CTCRelation> pathList = new ArrayList<CTCRelation>();
    //     List<List<CTCRelation>> paths = new ArrayList<List<CTCRelation>>();
    //     getAllPathsUtil(s,d,visited, pathList, paths);
    //     return paths;
    // }

    // private void getAllPathsUtil(ComplexityClass u, ComplexityClass d, Set<ComplexityClass> visited, List<CTCRelation> pathList, List<List<CTCRelation>> paths) {
    //     if(u.equals(d) && pathList.size() > 2) {
    //         paths.add(new ArrayList<CTCRelation> (pathList));
    //         return;
    //     }

    //     visited.add(u);
    //     ComplexityClass neighbor;
    //     CTCRelation temp;
    //     for(CTCRelation ctc : ctcRepository.findRelationsByComplexityClass(u.getId())) {
    //         if(ctc.getFirstClass().equals(u)) {
    //             neighbor = ctc.getSecondClass();
    //             temp = ctc;
    //         } else {
    //             neighbor = ctc.getFirstClass();
    //             temp = new CTCRelation(ctc.getId(), ctc.getSecondClass(), ctc.getFirstClass(), ctc.getRelationType().reverse());
    //         }
    //         if(!visited.contains(neighbor)) {
    //             pathList.add(temp);
    //             getAllPathsUtil(neighbor, d, visited, pathList, paths);
    //             pathList.remove(temp);
    //         }
    //     }
    //     visited.remove(u);
    // }
}
