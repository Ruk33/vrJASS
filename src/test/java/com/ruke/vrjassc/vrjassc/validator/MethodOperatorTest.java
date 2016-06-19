package com.ruke.vrjassc.vrjassc.validator;

import com.ruke.vrjassc.vrjassc.exception.BracketOperatorException;
import com.ruke.vrjassc.vrjassc.util.TestHelper;
import org.junit.Test;

public class MethodOperatorTest extends TestHelper {

    @Test
    public void bracketSetFirstParamMustBeInt() {
        this.expectedEx.expect(BracketOperatorException.class);
        this.expectedEx.expectMessage("2:16 Operator []= must take two parameters, being the first of type integer");
        this.run(
            "struct foo\n" +
                "public operator []= takes string i, integer value\n" +
                "end\n" +
            "end"
        );
    }

    @Test
    public void bracketSetShouldTakeTwoParams() {
        this.expectedEx.expect(BracketOperatorException.class);
        this.expectedEx.expectMessage("2:16 Operator []= must take two parameters, being the first of type integer");
        this.run(
            "struct foo\n" +
                "public operator []=\n" +
                "end\n" +
            "end"
        );
    }

    @Test
    public void bracketGetShouldReturn() {
        this.expectedEx.expect(BracketOperatorException.class);
        this.expectedEx.expectMessage("7:16 Operator [] must take one parameter of type integer and return a value");
        this.run(
            "struct bar\n" +
                "public operator [] takes integer i returns integer\n" +
                    "return 42\n" +
                "end\n" +
            "end\n" +
            "struct foo\n" +
                "public operator [] takes integer i\n" +
                "end\n" +
            "end"
        );
    }

    @Test
    public void bracketGetShouldTakeIntParam() {
        this.expectedEx.expect(BracketOperatorException.class);
        this.expectedEx.expectMessage("2:16 Operator [] must take one parameter of type integer");
        this.run(
            "struct foo\n" +
                "public operator [] takes string i\n" +
                "end\n" +
            "end"
        );
    }

    @Test
    public void bracketGetShouldTakeOneParam() {
        this.expectedEx.expect(BracketOperatorException.class);
        this.expectedEx.expectMessage("2:16 Operator [] must take one parameter of type integer and return a value");
        this.run(
            "struct foo\n" +
                "public operator []\n" +
                "end\n" +
            "end"
        );
    }

}
