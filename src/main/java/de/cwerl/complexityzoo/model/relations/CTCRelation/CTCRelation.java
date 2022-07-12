package de.cwerl.complexityzoo.model.relations.CTCRelation;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import de.cwerl.complexityzoo.model.data.ComplexityClass;
import de.cwerl.complexityzoo.model.data.ComplexityData;
import de.cwerl.complexityzoo.model.relations.Relation;
import lombok.Getter;
import lombok.Setter;

@Entity
public class CTCRelation extends Relation {

    @ManyToOne
    @JoinColumn(name = "first_class_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Getter @Setter
    @NotNull
    private ComplexityClass firstClass;

    @ManyToOne
    @JoinColumn(name = "second_class_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Getter @Setter
    @NotNull
    private ComplexityClass secondClass;

    @Getter @Setter
    @Enumerated(EnumType.STRING)
    @NotNull
    private CTCRelationType relationType;

    @Override
    public String getPath() {
        return "/relations/ctc/" + this.getId();
    }

    @Override
    public ComplexityData getFirst() {
        return firstClass;
    }

    @Override
    public ComplexityData getSecond() {
        return secondClass;
    }

    
}
