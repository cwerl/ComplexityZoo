package de.cwerl.complexityzoo.model.relations.CTCRelation;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class CTCInterpretation {
    @Getter @Setter
    private CTCRelationType interpretedType;

    @Getter @Setter
    private List<CTCRelation> path;

    public CTCInterpretation(CTCRelationType interpretedType, List<CTCRelation> path) {
        this.interpretedType = interpretedType;
        this.path = path;
    }
}
