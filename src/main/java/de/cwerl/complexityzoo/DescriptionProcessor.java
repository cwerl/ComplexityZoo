package de.cwerl.complexityzoo;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;
import org.unbescape.html.HtmlEscape;

public class DescriptionProcessor extends AbstractAttributeTagProcessor {
    
    private static final String ATTR_NAME = "descr";
    private static final int PRECEDENCE = 10000;

    public DescriptionProcessor(final String dialectPrefix) {
        super(
            TemplateMode.HTML, // This processor will apply only to HTML mode
            dialectPrefix,     // Prefix to be applied to name for matching
            null,              // No tag name: match any tag name
            false,             // No prefix to be applied to tag name
            ATTR_NAME,         // Name of the attribute that will be matched
            true,              // Apply dialect prefix to attribute name
            PRECEDENCE,        // Precedence (inside dialect's precedence)
            true
        );             // Remove the matched attribute afterwards
    }

    protected void doProcess(
            final ITemplateContext context, final IProcessableElementTag tag,
            final AttributeName attributeName, final String attributeValue,
            final IElementTagStructureHandler structureHandler) {
                if(tag.hasAttribute("class", "mention")) {
                    structureHandler.setBody(
                "Hello, " + HtmlEscape.escapeHtml5(attributeValue) + "!", false);
                }
        

    }
}
