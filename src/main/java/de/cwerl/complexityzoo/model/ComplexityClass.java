package de.cwerl.complexityzoo.model;

import javax.persistence.Entity;

@Entity
public class ComplexityClass extends ComplexityData {

    @Override
    public TinyMCESuggestion toTinyMCESuggestion() {
        TinyMCESuggestion suggestion = new TinyMCESuggestion();
        suggestion.setText(this.getName());
        suggestion.setType("Complexity class");
        suggestion.setValue("<a href=\"../classes/" + this.getId() + "\">"+ this.getName() +"</a>");
        return suggestion;
    }
}
