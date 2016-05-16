package com.ruke.vrjassc.vrjassc.validator;

import com.ruke.vrjassc.vrjassc.exception.AlreadyDefinedException;
import com.ruke.vrjassc.vrjassc.exception.ImportException;
import com.ruke.vrjassc.vrjassc.exception.ImportNotFoundException;
import com.ruke.vrjassc.vrjassc.util.ProjectPath;
import com.ruke.vrjassc.vrjassc.util.TestHelper;
import org.junit.Ignore;
import org.junit.Test;

public class ImportTest extends TestHelper {

    @Test
    public void shouldThrowExceptionIfImportNotFound() {
        this.expectedEx.expect(ImportNotFoundException.class);
        this.expectedEx.expectMessage("1:0 Import \"nope.j\" was not found.");

        this.run(
        "import \"nope.j\""
        );
    }

    @Test
    @Ignore
    public void shouldDetectAlreadyDefined() {
        String path = ProjectPath.getTest() + "/compiler/import-test.j";

        this.expectedEx.expect(ImportException.class);
        this.expectedEx.expectMessage("2:9 Element <foo> is already defined on 1:9");

        this.run(
        "import \"" + path + "\"\n" +
        "function foo\n" +
        "end"
        );
    }

}
