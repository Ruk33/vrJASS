package com.ruke.vrjassc.vrjassc.compiler;

import com.ruke.vrjassc.vrjassc.util.TestHelper;
import org.junit.Assert;
import org.junit.Test;

public class MethodOperatorTest extends TestHelper {

    @Test
    public void genericAndOperator() {
        String code =
            "struct foo<e>\n" +
                "e element\n" +
                "public operator []= takes integer i, e element\n" +
                    "set this.element = element\n" +
                "end\n" +
            "end\n" +
            "function baz\n" +
                "local foo<string> s = cast 1 to foo\n" +
                "set s[1] = \"something!\"\n" +
            "end";

        String expected =
            "globals\n" +
                "integer struct_foo_element=1\n" +
                "hashtable vrjass_structs=InitHashtable()\n" +
                "integer vtype=-1\n" +
            "endglobals\n" +
            "function struct_foo_bracket_set_op_string takes integer this,integer i,string element returns nothing\n" +
                "call SaveStr(vrjass_structs,this,struct_foo_element,element)\n" +
            "endfunction\n" +
            "function baz takes nothing returns nothing\n" +
                "local integer s=1\n" +
                "call struct_foo_bracket_set_op_string(s,1,\"something!\")\n" +
            "endfunction";

        Assert.assertEquals(expected, this.run(code));
    }

    @Test
    public void bracketSet() {
        String code =
            "struct foo\n" +
                "public operator []= takes integer index, string value\n" +
                "end\n" +
            "end\n" +
            "function bar\n" +
                "local foo f\n" +
                "set f[1]=\"dat\"\n" +
                "set f[2]=\"ddat\"\n" +
            "end";

        String expected =
            "globals\n" +
                "hashtable vrjass_structs=InitHashtable()\n" +
                "integer vtype=-1\n" +
            "endglobals\n" +
            "function struct_foo_bracket_set_op takes integer this,integer index,string value returns nothing\n" +
            "endfunction\n" +
            "function bar takes nothing returns nothing\n" +
                "local integer f=0\n" +
                "call struct_foo_bracket_set_op(f,1,\"dat\")\n" +
                "call struct_foo_bracket_set_op(f,2,\"ddat\")\n" +
            "endfunction";

        Assert.assertEquals(expected, this.run(code));
    }

    @Test
    public void bracketGet() {
        String code =
            "struct foo\n" +
                "public operator [] takes integer index returns integer\n" +
                    "return index\n" +
                "end\n" +
            "end\n" +
            "function bar\n" +
                "local foo f\n" +
                "call I2S(f[1])\n" +
            "end";

        String expected =
            "globals\n" +
                "hashtable vrjass_structs=InitHashtable()\n" +
                "integer vtype=-1\n" +
            "endglobals\n" +
            "function struct_foo_bracket_op takes integer this,integer index returns integer\n" +
                "return index\n" +
            "endfunction\n" +
            "function bar takes nothing returns nothing\n" +
                "local integer f=0\n" +
                "call I2S(struct_foo_bracket_op(f,1))\n" +
            "endfunction";

        Assert.assertEquals(expected, this.run(code));
    }

}
