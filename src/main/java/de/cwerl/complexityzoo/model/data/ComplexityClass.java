package de.cwerl.complexityzoo.model.data;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotBlank;

import de.cwerl.complexityzoo.model.TinyMCESuggestion;
import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class ComplexityClass extends ComplexityData {

    @Getter
    @Column(name = "type", insertable = false, updatable = false, nullable = false)
    private String type;

    @Getter @Setter
    @Column(unique = true)
    @NotBlank(message = "Name is mandatory")
    private String name;

    public ComplexityClass() {}

    public ComplexityClass(String name) {
        this.name = name;
    }

    public TinyMCESuggestion toTinyMCESuggestion() {
        TinyMCESuggestion suggestion = new TinyMCESuggestion();
        suggestion.setText(this.getName());
        suggestion.setType("Complexity class");
        suggestion.setValue("<a href=\"../classes/" + this.getId() + "\">"+ this.getName() +"</a>");
        return suggestion;
    }

    @Override
    public String getPath() {
        return "/classes/" + this.getId();
    }
}
