package de.cwerl.complexityzoo;

import java.util.ArrayList;
import java.util.List;

import de.cwerl.complexityzoo.model.ComplexityClass;
import de.cwerl.complexityzoo.model.TinyMCESuggestion;

public class SuggestionParser {
    
    public static List<TinyMCESuggestion> parse(List<ComplexityClass> classes, String type) {
        List<TinyMCESuggestion> classStrings = new ArrayList<>();
        for (ComplexityClass complexityClass : classes) {
            TinyMCESuggestion suggestion = new TinyMCESuggestion();
            suggestion.setText(complexityClass.getName());
            suggestion.setType(type);
            suggestion.setValue("<a href=\"../classes/" + complexityClass.getId() + "\">"+ complexityClass.getName() +"</a>");
            classStrings.add(suggestion);
        }
        return classStrings;
    }
}
