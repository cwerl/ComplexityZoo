package de.cwerl.complexityzoo.model.data;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import de.cwerl.complexityzoo.model.TinyMCESuggestion;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class ComplexityClass extends ComplexityData {

    @Override
    public TinyMCESuggestion toTinyMCESuggestion() {
        TinyMCESuggestion suggestion = new TinyMCESuggestion();
        suggestion.setText(this.getName());
        suggestion.setType("Complexity class");
        suggestion.setValue("<a href=\"../classes/" + this.getId() + "\">"+ this.getName() +"</a>");
        return suggestion;
    }
}
