package com.ruke.vrjassc.vrjassc.util;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;

import com.ruke.vrjassc.vrjassc.antlr4.vrjassLexer;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser;
import com.ruke.vrjassc.vrjassc.phase.ReferencePhase;
import com.ruke.vrjassc.vrjassc.phase.DefinitionPhase;
import com.ruke.vrjassc.vrjassc.phase.TranslationPhase;
import com.ruke.vrjassc.vrjassc.symbol.Scope;
import com.ruke.vrjassc.vrjassc.symbol.VrJassScope;

public class CompilerFacade {

	public String compile(ANTLRInputStream is) {
		vrjassLexer lexer = new vrjassLexer(is);
		TokenStream token = new CommonTokenStream(lexer);
		vrjassParser parser = new vrjassParser(token);
		
		TokenSymbolBag symbols = new TokenSymbolBag();
		Scope scope = new VrJassScope();
		
		DefinitionPhase defPhase = new DefinitionPhase(symbols, scope);
		ReferencePhase refPhase = new ReferencePhase(symbols, scope);
		TranslationPhase tranPhase = new TranslationPhase(symbols, scope);
		
		defPhase.visit(parser.init());
		parser.reset();

		refPhase.visit(parser.init());
		parser.reset();
		
		tranPhase.visit(parser.init());
		
		return tranPhase.getOutput();
	}
	
}
