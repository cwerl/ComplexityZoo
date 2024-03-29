package de.cwerl.complexityzoo.controller.data;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import de.cwerl.complexityzoo.model.TinyMCESuggestion;
import de.cwerl.complexityzoo.model.data.ComplexityClass;
import de.cwerl.complexityzoo.model.data.ComplexityDataType;
import de.cwerl.complexityzoo.model.data.normal.NormalComplexityClass;
import de.cwerl.complexityzoo.model.data.para.ParaComplexityClass;
import de.cwerl.complexityzoo.repository.data.ComplexityClassRepository;
import de.cwerl.complexityzoo.repository.data.ProblemRepository;
import de.cwerl.complexityzoo.repository.relations.CTCRelationRepository;
import de.cwerl.complexityzoo.repository.relations.CTPRelationRepository;
import de.cwerl.complexityzoo.service.SuggestionParser;

@Controller
@RequestMapping(path = "/classes")
public class ComplexityClassController {
    @Autowired
    private ComplexityClassRepository classRepository;

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private CTCRelationRepository ctcRelationRepository;

    @Autowired
    private CTPRelationRepository ctpRelationRepository;

    @GetMapping(path = "")
    public String list(Model model) {
        model.addAttribute("title", "Browse complexity classes");
        model.addAttribute("classes", classRepository.findAllOrdered());
        return "classes/list";
    }

    @GetMapping(path = "/{id}")
    public String view(Model model, @PathVariable long id) {
        ComplexityClass c = classRepository.getById(id);
        model.addAttribute("title", c.getName());
        model.addAttribute("class", c);
        model.addAttribute("ctcCandidates", ctcRelationRepository.findAllRelationCandidatesOrdered(id));
        model.addAttribute("ctpCandidates", ctpRelationRepository.findAllProblemCandidatesOrdered(id, c.getType()));
        model.addAttribute("ctcRelations", ctcRelationRepository.findRelationsByComplexityClass(id));
        model.addAttribute("ctpRelations", ctpRelationRepository.findRelationsByComplexityClass(id));
        return "classes/view";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(path = "/new")
    public String create(Model model) {
        model.addAttribute("title", "Create new complexity class");
        List<TinyMCESuggestion> suggestions = new ArrayList<>();
        suggestions.addAll(SuggestionParser.parseComplexityClasses(classRepository.findAll()));
        suggestions.addAll(SuggestionParser.parseProblems(problemRepository.findAll()));
        model.addAttribute("suggestions", suggestions);
        return "classes/new";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(path = "/new/save")
    public String newSave(@Valid @RequestParam String name, @RequestParam String description,
            @RequestParam ComplexityDataType type) {
        ComplexityClass c;
        if (type == ComplexityDataType.PARAMETERIZED) {
            c = new ParaComplexityClass();
        } else {
            c = new NormalComplexityClass();
        }
        c.setName(name);
        c.setDescription(description);
        classRepository.save(c);
        return "redirect:/classes/" + c.getId() + "?success";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(path = "/{id}/edit")
    public String edit(Model model, @PathVariable long id) {
        ComplexityClass c = classRepository.getById(id);
        List<TinyMCESuggestion> suggestions = new ArrayList<>();
        suggestions.addAll(SuggestionParser.parseComplexityClasses(classRepository.findAll()));
        suggestions.addAll(SuggestionParser.parseProblems(problemRepository.findAll()));
        model.addAttribute("suggestions", suggestions);
        model.addAttribute("class", c);
        model.addAttribute("title", "Edit complexity class " + c.getName());
        return "classes/edit";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/{id}/edit/save")
    @Transactional
    public String editSave(@PathVariable long id, @RequestParam String description) {
        classRepository.updateDescription(id,
                Jsoup.clean(description, ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString(),
                        Safelist.relaxed().preserveRelativeLinks(true)));
        return "redirect:/classes/" + id;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/{id}/edit/delete", method = RequestMethod.DELETE)
    public String delete(@PathVariable long id) {
        classRepository.deleteById(id);
        return "redirect:/classes";
    }

    @RequestMapping(value = "/search")
    public String search(@RequestParam String q, Model model) {
        if (q == null || q.isEmpty()) {
            return "redirect:/classes";
        }
        model.addAttribute("title", "Complexity classes containing \"" + q + "\"");
        model.addAttribute("classes", classRepository.searchClass(q));
        model.addAttribute("query", q);
        return "classes/list";
    }
}
