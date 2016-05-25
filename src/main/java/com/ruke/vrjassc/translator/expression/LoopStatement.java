package com.ruke.vrjassc.translator.expression;

import com.ruke.vrjassc.vrjassc.symbol.BuiltInTypeSymbol;
import com.ruke.vrjassc.vrjassc.symbol.Modifier;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;

/**
 * Thanks to Aniki for providing the continue translation!
 * http://www.hiveworkshop.com/forums/warcraft-editing-tools-277/vrjass-264114/index9.html#post2788578
 */
public class LoopStatement extends StatementBody {

	protected static int LOOP_CONTINUE_COUNTER = 0;
	protected Symbol _continue;

	protected void registerContinue() {
		if (this._continue != null) {
			return;
		}

		this._continue = new Symbol("vr_c_" + LOOP_CONTINUE_COUNTER, null, null);
		this._continue.setType(new BuiltInTypeSymbol("boolean", null, null));
		this._continue.setModifier(Modifier.LOCAL, true);

		this.add(new VariableStatement(this._continue, null));

		LOOP_CONTINUE_COUNTER++;
	}

	@Override
	public void add(Statement e) {
		super.add(e);

		if (this._continue == null && this.containsContinue(e)) {
			this.registerContinue();
		}
	}

	protected boolean containsContinue(Statement e) {
		if (e == null) {
			return false;
		}

		if (e instanceof LoopStatement) {
			return false;
		}

		if (e instanceof ContinueStatement) {
			return true;
		}

		if (e instanceof StatementBody) {
			for (Statement s : ((StatementBody) e).getStatements()) {
				if (this.containsContinue(s)) {
					return true;
				}
			}
		}

		return false;
	}

	public Symbol getContinueSymbol() {
		return this._continue;
	}

	@Override
	public String translate() {
		String continueHeader = "";
		String body = super.translate();
		String continueFooter = "";

		if (this._continue != null) {
			continueHeader = new AssignmentStatement(
				new VariableExpression(this._continue, null),
				new RawExpression("false")
			).translate() + "\n";

			body = "loop\n" + body + "endloop\n";

			continueFooter = new ExitWhenStatement(
				new BooleanExpression(
					new VariableExpression(this._continue, null),
					BooleanExpression.Operator.EQUAL_EQUAL,
					new RawExpression("false")
				)
			).translate() + "\n";
		}

		return
			"loop\n" +
				continueHeader +
				body +
				continueFooter +
			"endloop";
	}
	
}
