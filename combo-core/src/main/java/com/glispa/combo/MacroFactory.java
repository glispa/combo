package com.glispa.combo;

/**
 * The MacroFactory has the responsibility to create a {@link Macro} object for given argument
 *
 * @param <S> the state type
 */
public interface MacroFactory<S> {

    /**
     * Builds the macro with provided arguments.
     * The factory is called while parsing a {@link Template}'s text each time
     * the macro keyword is encountered.
     *
     * @param args the list of arguments passed to the macro, never null
     */
    Macro<S> build(String[] args);
}
