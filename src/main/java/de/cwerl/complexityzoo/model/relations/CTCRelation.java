package de.cwerl.complexityzoo.model.relations;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import de.cwerl.complexityzoo.model.data.ComplexityClass;
import lombok.Getter;
import lombok.Setter;

@Entity
public class CTCRelation extends Relation {

    @ManyToOne
    @JoinColumn(name = "first_class_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Getter @Setter
    private ComplexityClass firstClass;

    @ManyToOne
    @JoinColumn(name = "second_class_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Getter @Setter
    private ComplexityClass secondClass;

    @Getter @Setter
    @Enumerated(EnumType.STRING)
    private CTCRelationType type;
}
