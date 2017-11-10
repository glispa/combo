package com.glispa.combo;

class HelloMacro implements Macro<Object> {

    private final String argChain;

    public HelloMacro(String... args) {
        this.argChain = String.join(",", args);
    }

    @Override
    public String apply(String in, Object state) {
        String result = argChain;
        if (!in.isEmpty()) {
            result = in + "|" + result;
        }
        return result;
    }
}
