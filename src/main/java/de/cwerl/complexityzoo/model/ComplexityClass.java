package de.cwerl.complexityzoo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.Getter;
import lombok.Setter;

@Entity
public class ComplexityClass {

    @Getter @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Getter @Setter
    @NotBlank(message = "Name is mandatory")
    private String name;

    @Getter
    @Column(length = 5000)
    private String description;

    /**
     * Setting the complexity class description after sanitizing the content.
     * @param description The complexity class description.
     */
    public void setDescription(String description) {
        this.description = Jsoup.clean(description, ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString(),
            Safelist.relaxed()
            .preserveRelativeLinks(true));
    }
}
