package de.cwerl.complexityzoo.model.data;

public enum ComplexityDataType {
    NORMAL(Values.NORMAL), PARAMETERIZED(Values.PARAMETERIZED);

    private ComplexityDataType(String name) {
        if(!this.name().equals(name)) {
            throw new IllegalArgumentException("Incorrent use of ComplexityDataType");
        }
    }

    public static class Values {
        public static final String NORMAL = "NORMAL";
        public static final String PARAMETERIZED = "PARAMETERIZED";

        public static ComplexityDataType convert(String type) {
            if(type.equals(Values.NORMAL)) {
                return ComplexityDataType.NORMAL;
            } else if(type.equals(Values.PARAMETERIZED)) {
                return ComplexityDataType.PARAMETERIZED;
            } else {
                throw new IllegalArgumentException("Not a valid type.");
            }
        }
    }
}
