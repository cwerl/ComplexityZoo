package de.cwerl.complexityzoo.model;

import lombok.Getter;
import lombok.Setter;

public class TinyMCESuggestion {
    @Getter @Setter
    private String text;

    @Getter @Setter
    private String type;

    @Getter @Setter
    private String value;

    @Getter @Setter
    private String icon;
}
