package com.glispa.combo.url;

import com.glispa.combo.MacroRegistry;

import java.util.Map;

/**
 * {@link MacroRegistry} for URL processing.
 */
public class UrlMacroRegistry extends MacroRegistry<Map<String, String>> {

    public UrlMacroRegistry() {
        super();
        register("param", ParameterMacro::new);
        register("escape", EscapeMacro::new);
    }
}
