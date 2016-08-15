package com.ruke.vrjassc.compiler;

import com.ruke.vrjassc.vrjassc.antlr4.vrjassLexer;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser;
import com.ruke.vrjassc.vrjassc.exception.CompileException;
import com.ruke.vrjassc.vrjassc.exception.FileException;
import com.ruke.vrjassc.vrjassc.phase.DefinitionPhase;
import com.ruke.vrjassc.vrjassc.phase.PreprocessorPhase;
import com.ruke.vrjassc.vrjassc.phase.ReferencePhase;
import com.ruke.vrjassc.vrjassc.phase.TranslationPhase;
import com.ruke.vrjassc.vrjassc.symbol.ScopeSymbol;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;
import com.ruke.vrjassc.vrjassc.symbol.VrJassScope;
import com.ruke.vrjassc.vrjassc.util.TokenSymbolBag;
import org.antlr.v4.runtime.*;

import java.io.*;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Compiler {
    
    private File file;
    
    /**
     * True if we're using a temporary file
     */
    private boolean isTemp;
    
    /**
     * Symbols (variables, functions, etc.) collected during compilation
     */
    private ScopeSymbol symbols = new VrJassScope();
    
    /**
     * Symbols to inject (useful to inject natives, ex: common, blizzard)
     */
    private ScopeSymbol symbolsToInject = new VrJassScope();
    
    public Compiler(File file) {
        this.file = file;
    }
    
    public Compiler(String code) {
        try {
            this.file = File.createTempFile(String.valueOf(code.hashCode()), null);
            this.isTemp = true;
            
            PrintWriter writer = new PrintWriter(this.file);
            
            writer.write(code);
            writer.close();
        } catch (Exception e) {
            
        }
    }
    
    public File getFile() {
        return this.file;
    }
    
    public Compiler injectSymbol(Symbol symbol) {
        this.symbolsToInject.define(symbol);
        return this;
    }
    
    public ScopeSymbol getSymbols() {
        return this.symbols;
    }
    
    public String compile(boolean translate) throws CompileException, IOException {
        Set<File> files = new HashSet<File>();
        
        this.symbols.define(this.symbolsToInject);
        this.handleImports(this.symbols, files);
        
        if (translate) {
            List<String> lines = new LinkedList<String>();
            
            for (File file : files) {
                lines.addAll(Files.readAllLines(file.toPath()));
            }
    
            vrjassParser parser = this.parser(
                new ANTLRInputStream(
                    String.join("\n", lines) + "\n"
                )
            );
    
            TokenSymbolBag tokens = new TokenSymbolBag();
            ScopeSymbol symbols = new VrJassScope();
            
            symbols.define(this.symbolsToInject);
    
            DefinitionPhase definition = new DefinitionPhase(tokens, symbols);
            ReferencePhase reference = new ReferencePhase(tokens, symbols);
            TranslationPhase translator = new TranslationPhase(tokens, symbols);
            
            definition.visit(parser.init());
            parser.reset();

            reference.visit(parser.init());
            parser.reset();

            return translator.visit(parser.init()).translate();
        }
        
        return null;
    }
    
    private vrjassParser parser(CharStream stream) throws FileNotFoundException {
        Lexer lexer = new vrjassLexer(stream);
        TokenStream token = new CommonTokenStream(lexer);
        
        return new vrjassParser(token);
    }
    
    /**
     * @param symbols   Collected symbols
     * @param imports   Already imported/compiled files
     */
    private void handleImports(ScopeSymbol symbols, Set<File> imports) throws CompileException, IOException {
        if (!imports.add(this.file)) {
            return;
        }
    
        vrjassParser parser = this.parser(
            new ANTLRInputStream(
                new BufferedReader(new FileReader(this.file))
            )
        );
    
        TokenSymbolBag tokens = new TokenSymbolBag();
    
        PreprocessorPhase preprocessor = new PreprocessorPhase(this.file);
        DefinitionPhase definition = new DefinitionPhase(tokens, symbols);
        ReferencePhase reference = new ReferencePhase(tokens, symbols);
    
        parser.removeErrorListeners();
    
        try {
            preprocessor.visit(parser.init());
            parser.reset();
    
            definition.visit(parser.init());
            parser.reset();
    
            for (File file : preprocessor.getFiles()) {
                new Compiler(file).handleImports(symbols, imports);
            }
    
            // We don't need this phase on natives
            if (!this.isNative()) {
                reference.visit(parser.init());
                parser.reset();
            }
        } catch (CompileException e) {
            if (!this.isTemp && e instanceof FileException == false) {
                e = new FileException(this.file, e);
            }
            
            if (e.getFile() == null) {
                e.setFile(this.file);
            }
            
            throw e;
        }
    }
    
    private boolean isNative() {
        String path = this.file.getPath().toLowerCase();
        return path.endsWith("common.j") || path.endsWith("blizzard.j");
    }
    
}
