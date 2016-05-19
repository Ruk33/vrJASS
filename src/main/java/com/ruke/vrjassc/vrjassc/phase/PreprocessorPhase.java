package com.ruke.vrjassc.vrjassc.phase;

import com.ruke.vrjassc.vrjassc.antlr4.vrjassBaseVisitor;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser;
import com.ruke.vrjassc.vrjassc.exception.CompileException;
import com.ruke.vrjassc.vrjassc.exception.ImportException;
import com.ruke.vrjassc.vrjassc.exception.ImportNotFoundException;
import com.ruke.vrjassc.vrjassc.symbol.VrJassScope;
import com.ruke.vrjassc.vrjassc.util.CompilerFacade;
import org.antlr.v4.runtime.ANTLRInputStream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PreprocessorPhase extends vrjassBaseVisitor<Void> {

    private CompilerFacade compiler;
    private VrJassScope symbols;

    public PreprocessorPhase(CompilerFacade compiler, VrJassScope symbols) {
        this.compiler = compiler;
        this.symbols = symbols;
    }

    @Override
    public Void visitImportStatement(vrjassParser.ImportStatementContext ctx) {
        try {
            Path path = Paths.get(ctx.path.getText().replaceAll("\"", ""));
            String code = String.join("\n", Files.readAllLines(path)) + "\n";

            this.compiler.compile(this.symbols, new ANTLRInputStream(code), true, false, false);
        } catch (CompileException e) {
            throw new ImportException(ctx.getStart(), e);
        } catch (IOException e) {
            throw new ImportNotFoundException(ctx.getStart(), ctx.path.getText());
        }

        return null;
    }
}
