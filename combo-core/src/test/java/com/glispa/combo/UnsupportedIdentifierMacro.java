package com.glispa.combo;

public class UnsupportedIdentifierMacro implements Macro<Object> {

    private final String out;

    public UnsupportedIdentifierMacro(String out) {
        this.out = out;
    }

    @Override
    public String apply(String in, Object state) {
        return out;
    }
}
