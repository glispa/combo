package com.glispa.combo.url;

import com.glispa.combo.Template;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class UrlMacroRegistryTest {

    @Test
    public void classicUseCase() throws Exception {
        UrlMacroRegistry macroRegistry = new UrlMacroRegistry();
        Template<Map<String, String>> template =
                new Template<>(macroRegistry,
                               "http://www.glispa.com/test/${param a | escape path}?q=${param b | escape form}#${param c | escape fragment}");
        assertEquals("http://www.glispa.com/test/1%3F23?q=45%3D6#hel/lo",
                     template.render(ImmutableMap.of("a", "1?23", "b", "45=6", "c", "hel/lo")));
    }

    @Test
    public void doubleEscape() throws Exception {
        UrlMacroRegistry macroRegistry = new UrlMacroRegistry();
        Template<Map<String, String>> template =
                new Template<>(macroRegistry,
                               "http://www.glispa.com/redir=http%3A%2F%2Fwww.glispa.com%2Ftest%3Fq%3D${param a | escape form | escape form}");
        assertEquals("http://www.glispa.com/redir=http%3A%2F%2Fwww.glispa.com%2Ftest%3Fq%3D45%253D6",
                     template.render(ImmutableMap.of("a", "45=6")));
    }
}