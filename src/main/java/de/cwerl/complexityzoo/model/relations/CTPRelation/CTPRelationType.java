package de.cwerl.complexityzoo.model.relations.CTPRelation;

public enum CTPRelationType {
    IN, NOT_IN;

    public String toLaTeX(boolean reverse) {
        if(this == CTPRelationType.IN) {
            if(reverse) {
                return "$\\ni$";
            } else {
                return "$\\in$";
            }
        }
        if(this == CTPRelationType.NOT_IN) {
            if(reverse) {
                return "$\\not\\ni$";
            } else {
                return "$\\notin$";
            }
        }
        return "";
    }
}
