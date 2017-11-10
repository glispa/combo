package com.glispa.combo;

class StateMacro implements Macro<Object> {

    @Override
    public String apply(String in, Object state) {
        StringBuilder result = new StringBuilder();
        result.append(in);

        if (state != null) {
            result.append(state);
        }

        return result.toString();
    }
}
