package de.cwerl.complexityzoo.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

import lombok.Getter;
import lombok.Setter;

@Entity
public class CTCRelation {

    @Getter @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "first_class_id", referencedColumnName = "id")
    @Getter @Setter
    private ComplexityClass firstClass;

    @ManyToOne
    @JoinColumn(name = "second_class_id", referencedColumnName = "id")
    @Getter @Setter
    private ComplexityClass secondClass;

    @Getter
    private String description;

    @Getter @Setter
    @Enumerated(EnumType.STRING)
    private CTCRelationType type;

    public void setDescription(String description) {
        String cleanString = Jsoup.clean(description, Safelist.none());
        this.description = "<a href=\"" + cleanString + "\" target=\"_blank\">" + cleanString + "</a>";
    }
}
