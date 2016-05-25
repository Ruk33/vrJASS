package  com.ruke.vrjassc.vrjassc.autocomplete;

import com.ruke.vrjassc.autocomplete.AutoComplete;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class AutoCompleteTest {

    @Test
    public void shouldSuggestOnlyStructMembers() {
        String code =
            "struct foo\n" +
                "public method bar\n" +
                "end\n" +
                "public static method lorem\n" +
                "end\n" +
                "method baz\n" +
                "end\n" +
            "end\n" +
            "function bar\n" +
                "local foo f\n" +
                "call f.\n" +
            "end";

        AutoComplete autocomplete = new AutoComplete(code, 11, 7);
        ArrayList<Symbol> suggestions = autocomplete.get();

        Assert.assertEquals(1, suggestions.size());
        Assert.assertEquals("bar", suggestions.get(0).getName());
    }

    @Test
    public void shouldSuggestCompatibleTypesOnReturn() {
        String code =
            "function foo returns integer\n" +
                "return getunitp\n" +
            "end";

        AutoComplete autocomplete = new AutoComplete(code, 2, 15);
        ArrayList<Symbol> suggestions = autocomplete.get();

        Assert.assertEquals(4, suggestions.size());
        Assert.assertEquals("GetUnitPointValueByType", suggestions.get(0).getName());
        Assert.assertEquals("GetUnitPointValue", suggestions.get(1).getName());
        Assert.assertEquals("GetUnitPropWindowBJ", suggestions.get(2).getName());
        Assert.assertEquals("GetUnitPropWindow", suggestions.get(3).getName());
    }

    @Test
    public void shouldSuggestOnlyTypesInVariableDeclaration() {
        String code =
            "function foo\n" +
                "local fl\n" +
            "end";

        AutoComplete autocomplete = new AutoComplete(code, 2, 8);
        ArrayList<Symbol> suggestions = autocomplete.get();

        Assert.assertEquals(2, suggestions.size());
        Assert.assertEquals("mapflag", suggestions.get(0).getName());
        Assert.assertEquals("texmapflags", suggestions.get(1).getName());
    }

    @Test
    public void shouldSuggestTypesInParameters() {
        String code =
            "function foo takes fl\n" +
            "end";

        AutoComplete autocomplete = new AutoComplete(code, 1, 21);
        ArrayList<Symbol> suggestions = autocomplete.get();

        Assert.assertEquals(2, suggestions.size());
        Assert.assertEquals("mapflag", suggestions.get(0).getName());
        Assert.assertEquals("texmapflags", suggestions.get(1).getName());
    }

    @Test
    public void shouldLimitSuggestions() {
        String code =
            "function foo\n" +
                "call g\n" +
            "end";

        AutoComplete autocomplete = new AutoComplete(code, 2, 6);
        autocomplete.setLimit(10);

        ArrayList<Symbol> suggestions = autocomplete.get();

        Assert.assertEquals(10, suggestions.size());
    }

    @Test
    public void shouldSuggestSimilarFunctionNamesInCall() {
        String code =
            "function foo\n" +
                "call getunita\n" +
            "end";

        AutoComplete autocomplete = new AutoComplete(code, 2, 13);
        ArrayList<Symbol> suggestions = autocomplete.get();

        Assert.assertEquals(3, suggestions.size());
        Assert.assertEquals("GetUnitAbilityLevel", suggestions.get(0).getName());
        Assert.assertEquals("GetUnitAcquireRange", suggestions.get(1).getName());
        Assert.assertEquals("GetUnitAbilityLevelSwapped", suggestions.get(2).getName());
    }

    @Test
    public void shouldSuggestOnlyChildsInFunctionCallChainWithWord() {
        String code =
            "library a\n" +
                "globals\n" +
                    "public boolean fa\n" +
                "endglobals\n" +
                "public function foo returns integer\n" +
                    "return 1\n" +
                "end\n" +
                "public function bar\n" +
                "end\n" +
            "end\n" +
            "function baz\n" +
                "call a.f\n" +
            "end";

        AutoComplete autocomplete = new AutoComplete(code, 12, 8);
        ArrayList<Symbol> suggestions = autocomplete.get();

        Assert.assertEquals(1, suggestions.size());
        Assert.assertEquals("foo", suggestions.get(0).getName());
    }

    @Test
    public void shouldSuggestOnlyChildsInFunctionCallChain() {
        String code =
            "library a\n" +
                "globals\n" +
                    "public boolean b\n" +
                "endglobals\n" +
                "public function foo returns integer\n" +
                    "return 1\n" +
                "end\n" +
                "function bar\n" +
                "end\n" +
            "end\n" +
            "function baz\n" +
                "call a.\n" +
            "end";

        AutoComplete autocomplete = new AutoComplete(code, 12, 7);
        ArrayList<Symbol> suggestions = autocomplete.get();

        Assert.assertEquals(1, suggestions.size());
        Assert.assertEquals("foo", suggestions.get(0).getName());
    }

}
