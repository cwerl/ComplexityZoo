package de.cwerl.complexityzoo;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.spring5.SpringTemplateEngine;

public class DescriptionDialect extends AbstractProcessorDialect {

    public DescriptionDialect() {
        super(
            "Description dialect",
            "cz",
            1000
        );
    }
    
    public Set<IProcessor> getProcessors(final String dialectPrefix) {
        final Set<IProcessor> processors = new HashSet<IProcessor>();
        processors.add(new DescriptionProcessor(dialectPrefix));
        return processors;
    }
}
