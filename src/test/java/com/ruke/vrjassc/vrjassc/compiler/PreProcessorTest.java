package com.ruke.vrjassc.vrjassc.compiler;

import com.ruke.vrjassc.vrjassc.util.ProjectPath;
import com.ruke.vrjassc.vrjassc.util.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import java.net.URL;

public class PreProcessorTest extends TestHelper {

    @Test
    public void nestedImport() {
        this.run("import \"./src/test/java/com/ruke/vrjassc/vrjassc/compiler/imports/c.j\"");
    }

    @Test
    public void shouldIncludeCodeOnce() {
        String path = ProjectPath.getTest() + "/compiler/import-test.j";

        String code =
        "function bar\n" +
            "call foo()\n" +
        "end\n" +
        "import \"" + path + "\"\n" +
        "import \"" + path + "\"";

        String expected =
        "globals\n" +
        "endglobals\n" +
        "function baz takes nothing returns nothing\n" +
        "endfunction\n" +
        "function foo takes nothing returns nothing\n" +
        "endfunction\n" +
        "function bar takes nothing returns nothing\n" +
            "call foo()\n" +
        "endfunction";

        Assert.assertEquals(expected, this.run(code));
    }

}
