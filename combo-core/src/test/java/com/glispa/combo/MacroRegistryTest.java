package com.glispa.combo;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class MacroRegistryTest {

    @Test(expected = NullPointerException.class)
    public void invalidInitialization() throws Exception {
        new MacroRegistry<>(null);
    }

    @Test
    public void defaultMacro() throws Exception {
        assertTrue(new MacroRegistry<>().get("xxx").build(new String[0]) instanceof NoOpMacro);
    }

    @Test
    public void normalBehavior() throws Exception {
        MacroRegistry<Object> registry = new MacroRegistry<>()
                .register("A", args -> new TestMacroA())
                .register("B", args -> new TestMacroB());

        assertTrue(registry.get("A").build(new String[0]) instanceof TestMacroA);
        assertTrue(registry.get("B").build(new String[0]) instanceof TestMacroB);
        assertTrue(registry.get("xxx").build(new String[0]) instanceof NoOpMacro);
    }

    private static class TestMacroA implements Macro<Object> {
        @Override
        public String apply(String in, Object state) {
            return "";
        }
    }

    private static class TestMacroB implements Macro<Object> {
        @Override
        public String apply(String in, Object state) {
            return "";
        }
    }
}