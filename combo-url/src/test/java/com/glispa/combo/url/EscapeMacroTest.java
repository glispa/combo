package com.glispa.combo.url;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EscapeMacroTest {

    @Test
    public void escapeParam() throws Exception {
        EscapeMacro<Object> macro = new EscapeMacro<>(new String[]{"form"});
        assertEquals("a.+%3F%2F%3D",macro.apply("a. ?/=", null));
    }

    @Test
    public void escapeFragment() throws Exception {
        EscapeMacro<Object> macro = new EscapeMacro<>(new String[]{"fragment"});
        assertEquals("@a.%20?/=",macro.apply("@a. ?/=", null));
    }

    @Test
    public void escapePath() throws Exception {
        EscapeMacro<Object> macro = new EscapeMacro<>(new String[]{"path"});
        assertEquals("%20%2Fpath@.(test)",macro.apply(" /path@.(test)", null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void noArguments() throws Exception {
        new EscapeMacro<>(new String[0]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void tooManyArguments() throws Exception {
        new EscapeMacro<>(new String[3]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidArgument() throws Exception {
        new EscapeMacro<>(new String[]{"unknown"});
    }
}