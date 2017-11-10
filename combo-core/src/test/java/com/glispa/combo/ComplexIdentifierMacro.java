package com.glispa.combo;

public class ComplexIdentifierMacro implements Macro<Object> {
    private final String out;

    public ComplexIdentifierMacro(String[] args) {
        this.out = args[0];
    }

    @Override
    public String apply(String in, Object state) {
        return out;
    }
}