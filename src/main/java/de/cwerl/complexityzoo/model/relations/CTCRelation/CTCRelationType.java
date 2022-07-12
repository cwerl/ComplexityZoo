package de.cwerl.complexityzoo.model.relations.CTCRelation;

public enum CTCRelationType {
    EQUAL_TO, SUBSET_OF, PROPER_SUBSET_OF, SUPERSET_OF, PROPER_SUPERSET_OF, NOT_EQUAL_TO, NOT_SUBSET_OF, NOT_SUPERSET_OF;

    public String toLaTeX(boolean reverse) {
        if(this == CTCRelationType.EQUAL_TO) return "$=$";
        if(this == CTCRelationType.NOT_EQUAL_TO) return "$\\neq$";
        if(this == CTCRelationType.SUBSET_OF) {
            if(reverse) {
                return "$\\supseteq$";
            } else {
                return "$\\subseteq$";
            }
        }
        if(this == CTCRelationType.PROPER_SUBSET_OF) {
            if(reverse) {
                return "$\\supset$";
            } else {
                return "$\\subset$";
            }
        }
        if(this == CTCRelationType.SUPERSET_OF) {
            if(reverse) {
                return "$\\subseteq$";
            } else {
                return "$\\supseteq$";
            }
        }
        if(this == CTCRelationType.PROPER_SUPERSET_OF) {
            if(reverse) {
                return "$\\subset$";
            } else {
                return "$\\supset$";
            }
        }
        if(this == CTCRelationType.NOT_SUBSET_OF) {
            if(reverse) {
                return "$\\nsupseteq$";
            } else {
                return "$\\nsubseteq$";
            }
        }
        if(this == CTCRelationType.NOT_SUPERSET_OF) {
            if(reverse) {
                return "$\\nsubseteq$";
            } else {
                return "$\\nsupseteq$";
            }
        }
        return "";
    }
}
