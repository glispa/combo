package com.glispa.combo;

import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Internal class for walking through the parsed tree
 */
class TemplateWalker<S> extends MacroBaseListener {

    private final MacroRegistry<S> macroRegistry;
    private List<TemplateElement<S>> currentElements;
    private Collection<Macro<S>> currentChain;

    public TemplateWalker(MacroRegistry<S> macroRegistry) {
        this.macroRegistry = macroRegistry;
    }

    public List<TemplateElement<S>> visit(MacroParser.TemplateContext ctx) {
        currentElements = new ArrayList<>(ctx.getChildCount());
        ParseTreeWalker.DEFAULT.walk(this, ctx);
        return currentElements;
    }

    @Override
    public void enterMacroChain(MacroParser.MacroChainContext ctx) {
        currentChain = new LinkedList<>();
    }

    @Override
    public void enterMacro(MacroParser.MacroContext ctx) {
        MacroFactory<S> macroFactory = macroRegistry.get(ctx.macroId().getText());
        List<MacroParser.ArgContext> argContexts = ctx.arg();
        String[] args = new String[argContexts.size()];
        for (int i = 0; i < argContexts.size(); ++i) {
            args[i] = argContexts.get(i).getText();
        }
        currentChain.add(macroFactory.build(args));
    }

    @Override
    public void exitMacroChain(MacroParser.MacroChainContext ctx) {
        currentElements.add(new MacroChainTemplateElement<>(currentChain));
    }

    @Override
    public void exitText(MacroParser.TextContext ctx) {
        currentElements.add(new TextTemplateElement<>(ctx.getText()));
    }
}
