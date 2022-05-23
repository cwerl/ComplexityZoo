package de.cwerl.complexityzoo.model;

import javax.persistence.Entity;

@Entity
public class Problem extends ComplexityData {

    @Override
    public TinyMCESuggestion toTinyMCESuggestion() {
        TinyMCESuggestion suggestion = new TinyMCESuggestion();
        suggestion.setText(this.getName());
        suggestion.setType("Problem");
        suggestion.setValue("<a href=\"../problems/" + this.getId() + "\">"+ this.getName() +"</a>");
        return suggestion;
    }
}
