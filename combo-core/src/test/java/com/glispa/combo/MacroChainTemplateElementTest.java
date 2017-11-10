package com.glispa.combo;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MacroChainTemplateElementTest {

    @Test
    public void rendering() throws Exception {
        List<Macro<Object>> macros = new LinkedList<>();
        macros.add(new HelloMacro("a"));
        macros.add(new HelloMacro("B", "b"));
        macros.add(new HelloMacro("c"));
        MacroChainTemplateElement<Object> templateElement = new MacroChainTemplateElement<>(macros);
        assertEquals("a|B,b|c", templateElement.render(null));
    }

    @Test(expected = NullPointerException.class)
    public void invalidMacro() throws Exception {
        List<Macro<Object>> macros = new LinkedList<>();
        macros.add((in, state) -> null);
        MacroChainTemplateElement<Object> templateElement = new MacroChainTemplateElement<>(macros);
        templateElement.render(null);
    }
}