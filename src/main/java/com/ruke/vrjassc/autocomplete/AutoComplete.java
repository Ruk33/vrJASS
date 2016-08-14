package com.ruke.vrjassc.autocomplete;

import com.ruke.vrjassc.vrjassc.symbol.*;
import com.ruke.vrjassc.vrjassc.util.CompilerFacade;
import org.antlr.v4.runtime.ANTLRInputStream;

import java.util.ArrayList;

public class AutoComplete {

    private CompilerFacade compiler;
    private String code;
    private int line;
    private int col;
    private int limit = -1;

    public AutoComplete(CompilerFacade compiler, String code, int line, int col) {
        this.compiler = compiler;
        this.code = code;
        this.line = line;
        this.col = col-1;
    }

    public AutoComplete(String code, int line, int col) {
        this(new CompilerFacade(), code, line, col);
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    private void completeSuggestions(ArrayList<Symbol> suggestions, String word, Class type, ScopeSymbol scope, Symbol symbol, Type compatibleWith) {
        Symbol parent = null;
        boolean onlyStatic = false;

        if (this.limit > 0 && this.limit <= suggestions.size()) {
            return;
        }

        if (symbol != null) {
            if (symbol instanceof ScopeSymbol) {
                parent = symbol;
                onlyStatic = true;
            } else if (symbol.getType() instanceof ScopeSymbol) {
                parent = (Symbol) symbol.getType();
            }
        }

        if (parent instanceof ScopeSymbol) {
            for (Symbol child : ((ScopeSymbol) parent).getChilds().values()) {
                if (this.limit > 0 && this.limit <= suggestions.size()) {
                    return;
                }

                if (onlyStatic != child.hasModifier(Modifier.STATIC)) {
                    continue;
                }

                if (type != null && child.getClass() != type) {
                    continue;
                }

                if (compatibleWith != null && !compatibleWith.isTypeCompatible(child)) {
                    continue;
                }

                if (!scope.hasAccess(child)) {
                    continue;
                }

                if (word != null) {
                    if (!child.getName().toLowerCase().contains(word)) {
                        continue;
                    }
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
        ArrayList<Symbol> suggestions = new ArrayList<Symbol>();
        String[] codeLines = this.code.split("\n");
        String word = "";

        this.compiler.catchSymbolIn(this.line, this.col);
        this.compiler.translateCode = false;

        try {
            this.compiler.compile(
                new VrJassScope(),
                new ANTLRInputStream(this.code),
                false,
                false,
                true
            );

            if (codeLines.length >= this.line-1 && codeLines[this.line-1].length() >= this.col) {
                int validCol = this.col;

                while (validCol > 0 && String.valueOf(codeLines[this.line - 1].charAt(validCol - 1)).matches("\\w")) {
                    validCol--;
                }

                word = codeLines[this.line - 1].substring(validCol).toLowerCase().trim();

                if (!word.matches("\\w+")) {
                    word = null;
                }
            }

            this.completeSuggestions(
                suggestions,
                word,
                this.compiler.getCatchedSymbolType(),
                this.compiler.getCatchedScope(),
                this.compiler.getCatchedSymbol(),
                this.compiler.getCatchedTypeCompatible()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        return suggestions;
    }

}
