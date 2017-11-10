package com.glispa.combo;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TemplateTest {
    private static MacroRegistry<Object> macroRegistry;

    @BeforeClass
    public static void setUp() throws Exception {
        macroRegistry = new MacroRegistry<>()
                .register("hello", HelloMacro::new)
                .register("state", args -> new StateMacro())
                .register("Com.Pl3_x", args -> new ComplexIdentifierMacro(args))
                .register("un-supported", args -> new UnsupportedIdentifierMacro(args[0]));
    }

    @Test
    public void empty() throws Exception {
        assertEquals("",
                     new Template<>(macroRegistry, "").render(null));
    }

    @Test
    public void testMacroIdentifiers() {
        assertEquals("Test complex stuff",
                     new Template<>(macroRegistry, "Test complex ${Com.Pl3_x stuff}").render(null));
    }

    @Test
    public void testUnsupported() {
        assertEquals("Test ${un-supported stuff}",
                     new Template<>(macroRegistry, "Test ${un-supported stuff}").render(null));
    }

    @Test
    public void simpleTemplate() throws Exception {
        assertEquals("This is a simple test",
                     new Template<>(macroRegistry, "This is a simple ${hello test}").render(null));

        assertEquals("A little,bit,more complex",
                     new Template<>(macroRegistry, "A ${hello little bit more} complex").render(null));

        assertEquals("More funny} one",
                     new Template<>(macroRegistry, "More ${hello funny\\}} one").render(null));
    }

    @Test
    public void simpleTemplateWithSpecialChar() {
        assertEquals("http://google.com?qu$ery=funny",
                     new Template<>(macroRegistry, "http://google.com?qu$ery=${hello funny}").render(null));
    }

    @Test
    public void pipeline() throws Exception {
        assertEquals("This is a A|B|C test",
                     new Template<>(macroRegistry, "This is a ${hello A | hello B | hello C} test").render(null));

        assertEquals("This is a A|C test",
                     new Template<>(macroRegistry, "This is a ${hello A | fake B | hello C} test").render(null));
    }

    @Test
    public void stressGrammar() throws Exception {
        assertEquals("Fear te st,o{}m,|g",
                     new Template<>(macroRegistry, "Fear ${hello te\\ st o{\\}m \\|g}").render(null));
        assertEquals("{} test \\ |what}",
                     new Template<>(macroRegistry, "{} ${hello test} \\ |what}").render(null));
        assertEquals("a b,${hello,test",
                     new Template<>(macroRegistry, "a ${hello b ${hello test}").render(null));
        assertEquals("a ${hello never ending",
                     new Template<>(macroRegistry, "a ${hello never ending").render(null));

        assertEquals("a ${hello never ending \\| should keep escape",
                     new Template<>(macroRegistry, "a ${hello never ending \\| should keep escape").render(null));
    }

    @Test
    public void withAState() throws Exception {
        assertEquals("Hello you !",
                     new Template<>(macroRegistry, "Hello ${state} !").render("you"));
        assertEquals("Hello youyou !",
                     new Template<>(macroRegistry, "Hello ${state|state} !").render("you"));
    }

    @Test
    public void macroInMacro() throws Exception {
        assertEquals("${hello,world},third",
                     new Template<>(macroRegistry, "${hello ${hello world\\} third}").render(null));
    }

    @Test
    public void argsWithEscape() throws Exception {
        assertEquals("Hello World",
                     new Template<>(macroRegistry, "${hello Hello\\ World}").render(null));
    }

    @Test
    public void preserveEscape() throws Exception {
        assertEquals("\\ Hello World} \\}",
                     new Template<>(macroRegistry, "\\ ${hello Hello\\ World\\}} \\}").render(null));
    }
}