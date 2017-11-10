package com.glispa.combo;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TemplateWalkerTest {

    private static TemplateWalker<Object> templateWalker;

    @BeforeClass
    public static void setUp() throws Exception {
        MacroRegistry<Object> macroRegistry = new MacroRegistry<>();
        macroRegistry.register("hello", HelloMacro::new);
        templateWalker = new TemplateWalker<>(macroRegistry);
    }

    private static List<TemplateElement<Object>> visit(String text) {
        MacroLexer lexer = new MacroLexer(new ANTLRInputStream(text));
        MacroParser parser = new MacroParser(new CommonTokenStream(lexer));
        MacroParser.TemplateContext ctx = parser.template();
        return templateWalker.visit(ctx);
    }

    @Test
    public void singleItemChain() throws Exception {
        List<TemplateElement<Object>> elements = visit("${hello test}");
        assertEquals(1, elements.size());
        assertTrue(elements.get(0) instanceof MacroChainTemplateElement);
        assertEquals("test", elements.get(0).render(null));
    }

    @Test
    public void singleNoOp() throws Exception {
        List<TemplateElement<Object>> elements = visit("${xxx}");
        assertEquals(1, elements.size());
        assertTrue(elements.get(0) instanceof MacroChainTemplateElement);
        assertTrue(elements.get(0).render(null).isEmpty());
    }

    @Test
    public void singleEscEnd() throws Exception {
        List<TemplateElement<Object>> elements = visit("${hello te \\} st}");
        assertEquals(1, elements.size());
        assertTrue(elements.get(0) instanceof MacroChainTemplateElement);
        assertEquals("te,},st", elements.get(0).render(null));
    }

    @Test
    public void singleEscPipe() throws Exception {
        List<TemplateElement<Object>> elements = visit("${hello te \\| st}");
        assertEquals(1, elements.size());
        assertTrue(elements.get(0) instanceof MacroChainTemplateElement);
        assertEquals("te,|,st", elements.get(0).render(null));
    }

    @Test
    public void singleEscSpace() throws Exception {
        List<TemplateElement<Object>> elements = visit("${hello te \\  st}");
        assertEquals(1, elements.size());
        assertTrue(elements.get(0) instanceof MacroChainTemplateElement);
        assertEquals("te, ,st", elements.get(0).render(null));
    }

    @Test
    public void singleEscEsc() throws Exception {
        List<TemplateElement<Object>> elements = visit("${hello te \\\\ st}");
        assertEquals(1, elements.size());
        assertTrue(elements.get(0) instanceof MacroChainTemplateElement);
        assertEquals("te,\\,st", elements.get(0).render(null));
    }

    @Test
    public void allEsc() throws Exception {
        List<TemplateElement<Object>> elements = visit("${hello \\} \\| \\  \\\\}");
        assertEquals(1, elements.size());
        assertTrue(elements.get(0) instanceof MacroChainTemplateElement);
        assertEquals("},|, ,\\", elements.get(0).render(null));
    }

    @Test
    public void pipeline() throws Exception {
        List<TemplateElement<Object>> elements = visit("${hello A | hello B b | hello C}");
        assertEquals(1, elements.size());
        assertTrue(elements.get(0) instanceof MacroChainTemplateElement);
        assertEquals("A|B,b|C", elements.get(0).render(null));
    }

    @Test
    public void mixedContent() throws Exception {
        List<TemplateElement<Object>> elements = visit("Hello ${hello world}!");
        assertEquals(3, elements.size());
        assertTrue(elements.get(0) instanceof TextTemplateElement);
        assertTrue(elements.get(1) instanceof MacroChainTemplateElement);
        assertTrue(elements.get(2) instanceof TextTemplateElement);
        assertEquals("Hello ", elements.get(0).render(null));
        assertEquals("world", elements.get(1).render(null));
        assertEquals("!", elements.get(2).render(null));
    }
}