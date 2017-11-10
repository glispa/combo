package com.glispa.combo;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.requireNonNull;

/**
 * A {@code MacroRegistry} holds a map of identifiers linked to corresponding {@link MacroFactory}.
 * When an identifier is read in a {@link Template}, the corresponding {@link Macro} implementations is created with the factory.
 *
 * @param <S> the state type
 */
public class MacroRegistry<S> {

    private final MacroFactory<S> defaultMacroFactory;
    private final Map<String, MacroFactory<S>> factories;

    /**
     * Creates a new instance of {@code MacroRegistry}.
     * Default {@link MacroFactory} must be provided in order to link a {@link Macro} implementation
     * when the macro's identifier is unknown
     *
     * @param defaultMacroFactory the default macro factory, cannot be null
     */
    public MacroRegistry(MacroFactory<S> defaultMacroFactory) {
        requireNonNull(defaultMacroFactory, "You must provide a default macro");
        this.defaultMacroFactory = defaultMacroFactory;
        this.factories = new HashMap<>();
    }

    /**
     * Creates a new instance of {@code MacroRegistry} with {@link NoOpMacro} as default
     */
    public MacroRegistry() {
        this(args -> new NoOpMacro<>());
    }

    /**
     * Add a {@link MacroFactory} for this registry
     *
     * @param identifier the identifier used by the parser
     * @param macroFactory the {@link MacroFactory} to add
     * @return the {@link MacroRegistry} for fluent usage
     */
    public MacroRegistry<S> register(String identifier, MacroFactory<S> macroFactory) {
        factories.put(identifier, macroFactory);
        return this;
    }

    /**
     * Gets a macro for a given identifier
     *
     * @param identifier the identifier
     * @return the {@link Macro} that matches this identifier or the default {@link Macro}
     */
    public MacroFactory<S> get(String identifier) {
        return factories.getOrDefault(identifier, defaultMacroFactory);
    }
}
