package com.glispa.combo.url;

import com.glispa.combo.Macro;

import java.util.Map;

import static java.util.Optional.of;

/**
 * Macro producing a value from a {@link Map} state
 */
public class ParameterMacro implements Macro<Map<String, String>> {

    private final String key;

    /**
     * The {@link ParameterMacro} takes a single parameter, the key used to do the lookup in the {@link Map} state
     *
     * @param args the macro argumens
     */
    public ParameterMacro(String[] args) {
        this.key = of(args)
                .filter(s -> s.length == 1)
                .map(s -> s[0])
                .orElseThrow(() -> new IllegalArgumentException("Parameter macro expect one argument, the parameter name"));
    }

    @Override
    public String apply(String in, Map<String, String> state) {
        return state.getOrDefault(key, "");
    }
}
