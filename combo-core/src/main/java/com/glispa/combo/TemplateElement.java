package com.glispa.combo;

/**
 * Internal abstraction to represent a text fragment or a macro
 */
interface TemplateElement<S> {
    String render(S state);
}
