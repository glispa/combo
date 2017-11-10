package com.glispa.combo.url;

import com.glispa.combo.Macro;
import com.google.common.escape.Escaper;
import com.google.common.net.UrlEscapers;

import java.util.HashMap;
import java.util.Map;

import static java.util.Optional.of;

/**
 * Macro to escape special URL character from the input
 *
 * @param <S> the state type
 */
public class EscapeMacro<S> implements Macro<S> {

    private static final Map<String, Escaper> ESCAPERS = new HashMap<>();

    static {
        ESCAPERS.put("path", UrlEscapers.urlPathSegmentEscaper());
        ESCAPERS.put("fragment", UrlEscapers.urlFragmentEscaper());
        ESCAPERS.put("form", UrlEscapers.urlFormParameterEscaper());
    }

    private final Escaper escaper;

    /**
     * The {@link EscapeMacro} takes a single parameter with the following values :
     * "path" to use {@link UrlEscapers#urlPathSegmentEscaper()},
     * "fragment" to use {@link UrlEscapers#urlFragmentEscaper()} and
     * "form" to use {@link UrlEscapers#urlFormParameterEscaper()}
     *
     * @param args the macro arguments
     */
    public EscapeMacro(String[] args) {
        this.escaper = of(args)
                .filter(s -> s.length == 1)
                .map(s -> ESCAPERS.get(s[0]))
                .orElseThrow(() -> new IllegalArgumentException(
                        "Escaper is not provided or not valid, "
                                + "provided arguments [" + String.join(",", args) + "], "
                                + "valid values are [" + String.join(",", ESCAPERS.keySet()) + "]"));
    }

    @Override
    public String apply(String in, S state) {
        return escaper.escape(in);
    }
}
