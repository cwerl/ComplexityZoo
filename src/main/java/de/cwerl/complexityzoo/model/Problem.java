package de.cwerl.complexityzoo.model;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class Problem extends ComplexityData {

    @Override
    public TinyMCESuggestion toTinyMCESuggestion() {
        TinyMCESuggestion suggestion = new TinyMCESuggestion();
        suggestion.setText(this.getName());
        suggestion.setType("Problem");
        suggestion.setValue("<a href=\"../problems/" + this.getId() + "\">"+ this.getName() +"</a>");
        return suggestion;
    }
}
