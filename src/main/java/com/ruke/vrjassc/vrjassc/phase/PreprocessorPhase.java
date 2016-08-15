package com.ruke.vrjassc.vrjassc.phase;

import com.ruke.vrjassc.vrjassc.antlr4.vrjassBaseVisitor;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser;
import com.ruke.vrjassc.vrjassc.exception.ImportNotFoundException;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class PreprocessorPhase extends vrjassBaseVisitor<Void> {
    
    private File file;
    
    /**
     * Imported files
     */
    private Set<File> files = new HashSet<File>();
    
    public PreprocessorPhase(File file) {
        this.file = file;
    }
    
    public Set<File> getFiles() {
        return this.files;
    }

    @Override
    public Void visitImportStatement(vrjassParser.ImportStatementContext ctx) {
        String path = ctx.path.getText().replaceAll("\"", "");
        File _import = this.file.toPath().getParent().resolve(path).toFile();
        
        if (!_import.exists()) {
            // If resolving didn't work, try the raw path instead
            _import = Paths.get(path).toFile();
        }
        
        if (!_import.exists() || !_import.isFile()) {
            throw new ImportNotFoundException(ctx.getStart(), ctx.path.getText());
        }

        this.files.add(_import);

        return null;
    }
}
