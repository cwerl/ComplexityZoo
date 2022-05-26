package de.cwerl.complexityzoo.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public abstract class ComplexityData {
    @Getter @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Getter @Setter
    @Column(unique = true)
    @NotBlank(message = "Name is mandatory")
    private String name;

    @Getter
    @Column(length = 5000)
    private String description;

    @Getter
    @Column(name = "type", insertable = false, updatable = false, nullable = false)
    private String type;

    /**
     * Setting the complexity class description after sanitizing the content.
     * @param description The complexity class description.
     */
    public void setDescription(String description) {
        this.description = Jsoup.clean(description, ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString(),
            Safelist.relaxed()
            .preserveRelativeLinks(true));
    }

    public abstract TinyMCESuggestion toTinyMCESuggestion();
}
