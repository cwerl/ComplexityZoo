package de.cwerl.complexityzoo.model.data;

public enum ComplexityDataType {
    NORMAL(Values.NORMAL), PARAMETERIZED(Values.PARAMETERIZED), PARAMETERIZED_SUB(Values.PARAMETERIZED_SUB);

    private ComplexityDataType(String name) {
        if(!this.name().equals(name)) {
            throw new IllegalArgumentException("Incorrent use of ComplexityDataType");
        }
    }

    public static class Values {
        public static final String NORMAL = "NORMAL";
        public static final String PARAMETERIZED = "PARAMETERIZED";
        public static final String PARAMETERIZED_SUB = "PARAMETERIZED_SUB";
    }
}
