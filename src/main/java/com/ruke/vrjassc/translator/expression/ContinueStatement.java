package com.ruke.vrjassc.translator.expression;

public class ContinueStatement extends Statement {

    @Override
    public String translate() {
        LoopStatement loop = ((StatementBody) this.getParent()).getLoop();

        if (loop == null || loop.getContinueSymbol() == null) {
            return "";
        }

        Expression continueTrue = new AssignmentStatement(
            new VariableExpression(loop.getContinueSymbol(), null),
            new RawExpression("true")
        );

        return continueTrue.translate() + "\nexitwhen true\n";
    }

}
