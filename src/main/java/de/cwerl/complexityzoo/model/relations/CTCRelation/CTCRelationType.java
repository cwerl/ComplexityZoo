package de.cwerl.complexityzoo.model.relations.CTCRelation;

public enum CTCRelationType {
    EQUAL_TO, SUBSET_OF, PROPER_SUBSET_OF, SUPERSET_OF, PROPER_SUPERSET_OF, NOT_EQUAL_TO, NOT_SUBSET_OF, NOT_SUPERSET_OF;

    public String toLaTeX() {
        if(this == CTCRelationType.EQUAL_TO) return "$=$";
        if(this == CTCRelationType.NOT_EQUAL_TO) return "$\\neq$";
        if(this == CTCRelationType.SUBSET_OF) return "$\\subseteq$";
        if(this == CTCRelationType.PROPER_SUBSET_OF) return "$\\subset$";
        if(this == CTCRelationType.SUPERSET_OF) return "$\\supseteq$";
        if(this == CTCRelationType.PROPER_SUPERSET_OF) return "$\\supset$";
        if(this == CTCRelationType.NOT_SUBSET_OF) return "$\\nsubseteq$";
        if(this == CTCRelationType.NOT_SUPERSET_OF) return "$\\nsupseteq$";
        return "";
    }

    public CTCRelationType reverse() {
        if(this == CTCRelationType.SUBSET_OF) return SUPERSET_OF;
        if(this == CTCRelationType.PROPER_SUBSET_OF) return PROPER_SUPERSET_OF;
        if(this == CTCRelationType.SUPERSET_OF) return SUBSET_OF;
        if(this == CTCRelationType.PROPER_SUPERSET_OF) return PROPER_SUBSET_OF;
        if(this == CTCRelationType.NOT_SUBSET_OF) return NOT_SUPERSET_OF;
        if(this == CTCRelationType.NOT_SUPERSET_OF) return NOT_SUBSET_OF;
        return this;
    }
}
