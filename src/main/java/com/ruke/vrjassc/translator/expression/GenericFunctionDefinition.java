package com.ruke.vrjassc.translator.expression;

import com.ruke.vrjassc.vrjassc.symbol.FunctionSymbol;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;
import com.ruke.vrjassc.vrjassc.symbol.Type;
import com.ruke.vrjassc.vrjassc.util.Prefix;

public class GenericFunctionDefinition extends FunctionDefinition {

    private FunctionDefinition original;

    public GenericFunctionDefinition(FunctionDefinition original) {
        super(null);
        this.original = original;
    }

    @Override
    public String translate() {
        String result = "";
        FunctionSymbol function = (FunctionSymbol) this.original.getSymbol();
        String functionName = Prefix.build(function);

        for (Symbol generic : function.getGenerics()) {
            function.getGeneric().setType((Type) generic.getGeneric());
            result += this.original.translate().replace(functionName, functionName + "_" + Prefix.build(generic.getGeneric())) + "\n";
        }

        return result.trim();
    }
}
