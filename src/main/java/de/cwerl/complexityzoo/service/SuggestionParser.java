package de.cwerl.complexityzoo.service;

import java.util.ArrayList;
import java.util.List;

import de.cwerl.complexityzoo.model.TinyMCESuggestion;
import de.cwerl.complexityzoo.model.data.ComplexityData;

public class SuggestionParser {
    
    public static List<TinyMCESuggestion> parse(List<? extends ComplexityData> data) {
        List<TinyMCESuggestion> suggestions = new ArrayList<>();
        for (ComplexityData entity : data) {
            suggestions.add(entity.toTinyMCESuggestion());
        }
        return suggestions;
    }
}
