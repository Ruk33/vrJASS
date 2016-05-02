package com.ruke.vrjassc.vrjassc.compiler;

import com.ruke.vrjassc.vrjassc.util.ProjectPath;
import com.ruke.vrjassc.vrjassc.util.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import java.net.URL;

public class PreProcessorTest extends TestHelper {

    @Test
    public void _import() {
        String path = ProjectPath.getTest() + "/compiler/";

        String code =
        "function bar\n" +
            "call foo()\n" +
        "end\n" +
        "//! import " + path + "/import-test.j\n" +
        "//! import " + path + "/import-test.j";

        String expected =
        "globals\n" +
        "endglobals\n" +
        "function foo takes nothing returns nothing\n" +
        "endfunction\n" +
        "function bar takes nothing returns nothing\n" +
            "call foo()\n" +
        "endfunction";

        Assert.assertEquals(expected, this.run(code));
    }

}
