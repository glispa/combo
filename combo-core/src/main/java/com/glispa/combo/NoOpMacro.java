package com.glispa.combo;

/**
 * A no op macro, perfect as a default
 */
public class NoOpMacro<S> implements Macro<S> {

    /**
     * Does nothing
     *
     * @param in the standard input, could be null
     * @param state the current state
     * @return the input unchanged
     */
    @Override
    public String apply(String in, S state) {
        return in;
    }
}
