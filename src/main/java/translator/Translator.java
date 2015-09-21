package translator;

import com.ruke.vrjassc.vrjassc.antlr4.vrjassBaseVisitor;

public abstract class Translator extends vrjassBaseVisitor<Void> {
	
	public abstract String getOutput();
	
}
