package com.glispa.combo.url;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ParameterMacroTest {

    @Test
    public void knownParameter() throws Exception {
        ParameterMacro macro = new ParameterMacro(new String[]{"hello"});
        assertEquals("world", macro.apply("", ImmutableMap.of("hello", "world")));
    }

    @Test
    public void unknownParameter() throws Exception {
        ParameterMacro macro = new ParameterMacro(new String[]{"hello"});
        assertEquals("", macro.apply("", ImmutableMap.of("ello", "world")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void noArguments() throws Exception {
        new ParameterMacro(new String[0]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void tooManyArguments() throws Exception {
        new ParameterMacro(new String[3]);
    }
}