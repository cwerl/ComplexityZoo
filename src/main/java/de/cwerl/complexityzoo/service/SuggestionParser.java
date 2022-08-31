package de.cwerl.complexityzoo.service;

import java.util.ArrayList;
import java.util.List;

import de.cwerl.complexityzoo.model.TinyMCESuggestion;
import de.cwerl.complexityzoo.model.data.ComplexityClass;
import de.cwerl.complexityzoo.model.data.normal.Problem;

public class SuggestionParser {
    
    public static List<TinyMCESuggestion> parseComplexityClasses(List<ComplexityClass> classes) {
        List<TinyMCESuggestion> suggestions = new ArrayList<>();
        for(ComplexityClass c : classes) {
            TinyMCESuggestion suggestion = new TinyMCESuggestion();
            suggestion.setText(c.getName());
            suggestion.setType("Complexity class");
            suggestion.setValue("<a href=\"" + c.getPath() + "\">" + c.getName() + "</a>");
            suggestions.add(suggestion);
        }
        return suggestions;
    }

    public static List<TinyMCESuggestion> parseProblems(List<Problem> problems) {
        List<TinyMCESuggestion> suggestions = new ArrayList<>();
        for(Problem p : problems) {
            TinyMCESuggestion suggestion = new TinyMCESuggestion();
            suggestion.setText(p.getName());
            suggestion.setType("Complexity class");
            suggestion.setValue("<a href=\"" + p.getPath() + "\">" + p.getName() + "</a>");
            suggestions.add(suggestion);
        }
        return suggestions;
    }
}
