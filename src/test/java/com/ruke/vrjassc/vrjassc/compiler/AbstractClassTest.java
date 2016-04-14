package com.ruke.vrjassc.vrjassc.compiler;

import com.ruke.vrjassc.vrjassc.util.TestHelper;
import org.junit.Assert;
import org.junit.Test;

public class AbstractClassTest extends TestHelper {

    @Test
    public void onInit() {
        String code =
        "abstract struct foo\n" +
            "public static method onInit\n" +
                "call GetLocalPlayer()\n" +
            "end\n" +
        "end\n" +
        "struct bar extends foo\n" +
            "public static method onInit\n" +
                "call Player(0)\n" +
            "end\n" +
        "end\n" +
        "function main\n" +
        "endfunction";

        String expected =
        "globals\n" +
            "hashtable vrjass_structs=InitHashtable()\n" +
            "integer vtype=-1\n" +
        "endglobals\n" +
        "function struct_foo_onInit takes nothing returns nothing\n" +
            "call GetLocalPlayer()\n" +
        "endfunction\n" +
        "function struct_foo_bar_onInit takes nothing returns nothing\n" +
            "call Player(0)\n" +
        "endfunction\n" +
        "function main takes nothing returns nothing\n" +
            "call ExecuteFunc(\"struct_foo_onInit\")\n" +
            "call ExecuteFunc(\"struct_foo_bar_onInit\")\n" +
        "endfunction";

        Assert.assertEquals(expected, this.run(code));
    }

    @Test
    public void test() {
        String code =
            "abstract struct foo\n" +
                "public method lorem\n" +
                "public method bar\n" +
                    "call this.lorem()\n" +
                "endmethod\n" +
                "public static method onInit\n" +
                "endmethod\n" +
            "endstruct\n" +
            "struct ipsum extends foo\n" +
                "public method lorem\n" +
                    "call GetLocalPlayer()\n" +
                "endmethod\n" +
                "public method l\n" +
                    "call this.lorem()\n" +
                    "call this.bar()\n" +
                "endmethod\n" +
                "public static method onInit\n" +
                "endmethod\n" +
            "endstruct\n" +
            "function main\n" +
            "endfunction";

        String expected =
            "globals\n" +
                "hashtable vrjass_structs=InitHashtable()\n" +
                "integer vtype=-1\n" +
            "endglobals\n" +
            "function struct_foo_ipsum_lorem takes integer this returns nothing\n" +
                "call GetLocalPlayer()\n" +
            "endfunction\n" +
            "function struct_foo_lorem_vtype takes integer this,integer vtype returns nothing\n" +
                "if vtype==2 then\n" +
                    "call struct_foo_ipsum_lorem(this)\n" +
                "else\n" +
                    "call struct_foo_ipsum_lorem(this)\n" +
                "endif\n" +
            "endfunction\n" +
            "function struct_foo_bar takes integer this returns nothing\n" +
                "call struct_foo_lorem_vtype(this,LoadInteger(vrjass_structs,this,vtype))\n" +
            "endfunction\n" +
            "function struct_foo_onInit takes nothing returns nothing\n" +
            "endfunction\n" +
            "function struct_foo_ipsum_l takes integer this returns nothing\n" +
                "call struct_foo_lorem_vtype(this,LoadInteger(vrjass_structs,this,vtype))\n" +
                "call struct_foo_bar(this)\n" +
            "endfunction\n" +
            "function struct_foo_ipsum_onInit takes nothing returns nothing\n" +
            "endfunction\n" +
            "function main takes nothing returns nothing\n" +
                "call ExecuteFunc(\"struct_foo_onInit\")\n" +
                "call ExecuteFunc(\"struct_foo_ipsum_onInit\")\n" +
            "endfunction";

        Assert.assertEquals(expected, this.run(code));
    }

}
