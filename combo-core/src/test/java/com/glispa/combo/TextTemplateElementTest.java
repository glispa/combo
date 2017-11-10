package com.glispa.combo;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TextTemplateElementTest {

    @Test
    public void rendering() throws Exception {
        assertEquals("Hello World !", new TextTemplateElement<>("Hello World !").render(null));
        assertEquals("Hello World !", new TextTemplateElement<>("Hello World !").render(new Object()));
    }
}