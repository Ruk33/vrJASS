package com.ruke.vrjassc.autocomplete;

import com.ruke.vrjassc.vrjassc.symbol.*;
import com.ruke.vrjassc.vrjassc.util.CompilerFacade;
import org.antlr.v4.runtime.ANTLRInputStream;

import java.util.ArrayList;

public class AutoComplete {

    private String code;
    private int line;
    private int col;
    private int limit = -1;

    public AutoComplete(String code, int line, int col) {
        this.code = code;
        this.line = line;
        this.col = col-1;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    private void completeSuggestions(ArrayList<Symbol> suggestions, String word, Class type, ScopeSymbol scope, Symbol symbol, Type compatibleWith) {
        if (symbol instanceof ScopeSymbol) {
            for (Symbol child : ((ScopeSymbol) symbol).getChilds().values()) {
                if (this.limit > 0 && this.limit <= suggestions.size()) {
                    return;
                }

                if (type != null && child.getClass() != type) {
                    continue;
                }

                if (!scope.hasAccess(child)) {
                    continue;
                }

                if (word.matches("\\w+")) {
                    if (!child.getName().toLowerCase().contains(word.toLowerCase())) {
                        continue;
                    }
                }

                if (compatibleWith != null && !compatibleWith.isTypeCompatible(child)) {
                    continue;
                }

                suggestions.add(child);
            }
        }

        if (symbol != null && symbol != scope) {
            return;
        }

        if (symbol == null && scope == null)
            return;

        if (symbol == null) {
            this.completeSuggestions(suggestions, word, type, scope, scope, compatibleWith);
            return;
        }

        if (symbol instanceof UserTypeSymbol == false) {
            if (symbol.getParentScope() == null) {
                return;
            }

            this.completeSuggestions(suggestions, word, type, scope, (Symbol) symbol.getParentScope(), compatibleWith);
        }
    }

    public ArrayList<Symbol> get() {
        CompilerFacade compiler = new CompilerFacade();
        ArrayList<Symbol> suggestions = new ArrayList<Symbol>();
        String[] codeLines = this.code.split("\n");
        String word = "";

        compiler.catchSymbolIn(this.line, this.col);
        compiler.translateCode = false;

        try {
            compiler.compile(
                new VrJassScope(),
                new ANTLRInputStream(this.code),
                false,
                false,
                true
            );

            if (codeLines.length >= this.line-1 && codeLines[this.line-1].length() >= this.col) {
                word = codeLines[this.line-1].substring(this.col);
            }

            this.completeSuggestions(
                suggestions,
                word,
                compiler.getCatchedSymbolType(),
                compiler.getCatchedScope(),
                compiler.getCatchedSymbol(),
                compiler.getCatchedTypeCompatible()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        return suggestions;
    }

}
