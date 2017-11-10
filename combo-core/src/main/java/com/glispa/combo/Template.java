package com.glispa.combo;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.List;

/**
 * Represents a text that contains macros. Templates should be created once and then reuse
 * with different state, eventually in parallel. The thread safety and re-usability depend
 * on the {@link Macro}s implementation used within the template.
 *
 * @param <S> the state type
 */
public class Template<S> {

    private final List<TemplateElement<S>> elements;
    private final int sizeHint;

    /**
     * Creates a new instance of {@code Template}.
     * Parses the text and constructs all the macro chains
     *
     * @param macroRegistry the {@link MacroRegistry} that would be use to retrieve the macro types contained in
     * the text
     * @param text the template text
     */
    public Template(MacroRegistry<S> macroRegistry, String text) {
        sizeHint = text.length();

        MacroLexer lexer = new MacroLexer(new ANTLRInputStream(text));
        MacroParser parser = new MacroParser(new CommonTokenStream(lexer));
        MacroParser.TemplateContext template = parser.template();

        TemplateWalker<S> templateWalker = new TemplateWalker<>(macroRegistry);
        elements = templateWalker.visit(template);
    }

    /**
     * Renders this template for a given state
     *
     * @param state the state
     * @return the template with all the macro substitutions operated
     */
    public String render(S state) {
        StringBuilder sb = new StringBuilder(sizeHint);
        for (TemplateElement<S> element : elements) {
            String result = element.render(state);
            sb.append(result);
        }
        return sb.toString();
    }

}
