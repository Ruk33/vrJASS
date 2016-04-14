package com.ruke.vrjassc.vrjassc.validator;

import com.ruke.vrjassc.vrjassc.exception.AbstractMethodException;
import com.ruke.vrjassc.vrjassc.util.TestHelper;
import org.junit.Test;

public class AbstractClassTest extends TestHelper {

    @Test
    public void mustImplementMethods() {
        this.expectedEx.expect(AbstractMethodException.class);
        this.expectedEx.expectMessage("6:7 Class lorem must implement all methods of abstract class foo");
        this.run(
            "abstract struct foo\n" +
                "public abstract method bar\n" +
                "public method ipsum\n" +
                "endmethod\n" +
            "endstruct\n" +
            "struct lorem extends foo\n" +
                "public method dolor\n" +
                    "call this.ipsum()\n" +
                "endmethod\n" +
            "endstruct"
        );
    }

    @Test
    public void mustNotImplementMethodsInAbstractStruct() {
        this.expectedEx.none();
        this.run(
            "abstract struct foo\n" +
                "public method baz\n" +
            "endstruct\n" +
            "abstract struct bar extends foo\n" +
            "endstruct"
        );
    }

}
