package com.glispa.combo;

/**
 * Internal implementation of {@link TemplateElement} for plain text
 */
class TextTemplateElement<S> implements TemplateElement<S> {

    private final String text;

    public TextTemplateElement(String text) {
        this.text = text;
    }

    @Override
    public String render(S state) {
        return text;
    }
}
