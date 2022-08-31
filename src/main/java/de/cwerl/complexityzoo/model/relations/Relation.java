package de.cwerl.complexityzoo.model.relations;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import de.cwerl.complexityzoo.model.data.ComplexityData;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public abstract class Relation {
    @Getter @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Getter
    @Column(length = 5000)
    private String reference;

    public abstract String getPath();

    public abstract ComplexityData getFirst();
    
    public abstract ComplexityData getSecond();

    /**
     * Setting the complexity class description after sanitizing the content.
     * @param description The complexity class description.
     */
    public void setReference(String description) {
        this.reference = Jsoup.clean(description, ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString(),
            Safelist.relaxed()
            .preserveRelativeLinks(true));
    }
}
