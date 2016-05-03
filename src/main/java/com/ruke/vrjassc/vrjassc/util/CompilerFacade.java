package com.ruke.vrjassc.vrjassc.util;

import com.ruke.vrjassc.vrjassc.antlr4.vrjassLexer;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser;
import com.ruke.vrjassc.vrjassc.exception.SyntaxErrorException;
import com.ruke.vrjassc.vrjassc.phase.DefinitionPhase;
import com.ruke.vrjassc.vrjassc.phase.ReferencePhase;
import com.ruke.vrjassc.vrjassc.phase.TranslationPhase;
import com.ruke.vrjassc.vrjassc.symbol.VrJassScope;
import org.antlr.v4.runtime.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CompilerFacade {
	
	static VrJassScope nativeScope = null;
	
	public boolean translateCode = true;
	
	protected vrjassParser getParser(ANTLRInputStream is) {
		vrjassLexer lexer = new vrjassLexer(is);
		TokenStream token = new CommonTokenStream(lexer);
		
		return new vrjassParser(token);
	}
	
	protected String getCommonJ() throws IOException {
		return String.join("\n", Files.readAllLines(Paths.get("./resources/common.j")));
	}
	
	protected String getBlizzardJ() throws IOException {
		return String.join("\n", Files.readAllLines(Paths.get("./resources/blizzard.j")));
	}
	
	protected VrJassScope getNatives() throws IOException {		
		if (nativeScope == null) {
			String natives = this.getCommonJ() + "\n" + this.getBlizzardJ();
			vrjassParser parser = this.getParser(new ANTLRInputStream(natives));
			
			TokenSymbolBag symbols = new TokenSymbolBag();
			nativeScope = new VrJassScope();
			
			DefinitionPhase defPhase = new DefinitionPhase(symbols, nativeScope);
			defPhase.setValidator(null);
			
			defPhase.visit(parser.init());
		}
		
		return nativeScope;
	}
	
	public String compile(ANTLRInputStream is) throws IOException {
		vrjassParser parser = this.getParser(is);
		
		TokenSymbolBag symbols = new TokenSymbolBag();
		VrJassScope scope = new VrJassScope();

		try {
			scope.define(this.getNatives());
		} catch (Exception e) {

		}
		
		DefinitionPhase defPhase = new DefinitionPhase(symbols, scope);
		ReferencePhase refPhase = new ReferencePhase(symbols, scope);
		TranslationPhase tranPhase = new TranslationPhase(symbols, scope);
		
		parser.removeErrorListeners();
		parser.addErrorListener(new BaseErrorListener() {
			@Override
			public void syntaxError(Recognizer<?, ?> recognizer,
									Object offendingSymbol,
									int line,
									int charPositionInLine,
									String msg,
									RecognitionException e)
			{
				throw new SyntaxErrorException(
					line,
					charPositionInLine,
					msg
						.replaceAll("ID", "identifier")
						.replaceAll("<EOF>", "end of file")
						.replace("NL", "new line")
				);
			}
		});
		
		defPhase.visit(parser.init());
		parser.reset();

		refPhase.visit(parser.init());
		parser.reset();
		
		if (!this.translateCode) {
			return "";
		}
		
		return tranPhase.visit(parser.init()).translate();
	}
	
}
