package com.glispa.combo;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NoOpMacroTest {

    @Test
    public void apply() throws Exception {
        assertEquals("Hello", new NoOpMacro<>().apply("Hello", null));
        assertEquals("", new NoOpMacro<>().apply("", null));
    }
}