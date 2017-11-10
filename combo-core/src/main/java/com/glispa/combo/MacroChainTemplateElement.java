package com.glispa.combo;

import static java.util.Objects.requireNonNull;

/**
 * Internal implementation of {@link TemplateElement} for macro chain
 */
class MacroChainTemplateElement<S> implements TemplateElement<S> {

    private final Iterable<Macro<S>> chain;

    MacroChainTemplateElement(Iterable<Macro<S>> chain) {
        this.chain = chain;
    }

    @Override
    public String render(S state) {
        String result = "";
        for (Macro<S> macro : chain) {
            result = requireNonNull(
                    macro.apply(result, state),
                    "null is not a valid macro result but it was return by " + macro.getClass().getName());
        }
        return result;
    }
}
