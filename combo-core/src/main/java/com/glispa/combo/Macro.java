package com.glispa.combo;

/**
 * This interface represents a macro. A {@link Macro} object has the responsibility of handling a replacement.
 * Macros have the same lifetime as the {@link Template} containing them. Consequently {@link Macro} implementations
 * should be immutable in order to use the {@link Template} object in a thread safe and reusable way.
 * Dynamic parameters should be passed part of the state object on each call to {@link Template#render(Object)}
 *
 * @param <S> the state type
 */
public interface Macro<S> {

    /**
     * Applies the replacement.
     * The contract for a replacement is to produce a new output string according
     * the input string (coming from a previous macro in a pipeline) or/and the current state.
     *
     * @param in the standard input, could be empty but never null
     * @param state the current state
     * @return the standard output could be empty but never null
     */
    String apply(String in, S state);
}
