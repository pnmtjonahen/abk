/*
 * Copyright (C) 2015 Philippe Tjon - A - Hen, philippe@tjonahen.nl
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package nl.tjonahen.abk.backend.javascript;

import java.io.InputStreamReader;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import nl.tjonahen.abk.backend.model.FinancialTransaction;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 *
 * @author Philippe Tjon - A - Hen, philippe@tjonahen.nl
 */
public class JavaScriptTest {

    @Test
    public void testParseToJson() throws ScriptException, NoSuchMethodException {
        final ScriptEngineManager engineManager
                = new ScriptEngineManager();

        final ScriptEngine engine
                = engineManager.getEngineByName("nashorn");
        engine.eval(new InputStreamReader(this.getClass().getResourceAsStream("/parse.js")));
        final Invocable invocable = (Invocable) engine;

        final ScriptObjectMirror obj = (ScriptObjectMirror) invocable.invokeFunction("parseToJson", "\"20141208\",\"Omschrijving\",\"NL01FIRA0001234567\",\"NL01FIRA0009999999\",\"BA\",\"Af\",\"99,99\",\"Betaalautomaat\",\"mededeling\"");

        assertEquals("20141208", obj.getMember("date"));
        assertEquals("Omschrijving", obj.getMember("contraAccountName"));
        assertEquals("NL01FIRA0001234567", obj.getMember("accountNumber"));
        assertEquals("NL01FIRA0009999999", obj.getMember("contraAccountNumber"));
        assertEquals("BA", obj.getMember("code"));
        assertEquals("debit", obj.getMember("debitCreditIndicator"));
        assertEquals("99,99", obj.getMember("amount"));
        assertEquals("Betaalautomaat", obj.getMember("mutatiesoort"));
        assertEquals("mededeling", obj.getMember("description"));

    }

    @Test
    public void testParseToJavaClass() throws ScriptException, NoSuchMethodException {
        final ScriptEngineManager engineManager
                = new ScriptEngineManager();

        final ScriptEngine engine
                = engineManager.getEngineByName("nashorn");
        engine.eval(new InputStreamReader(this.getClass().getResourceAsStream("/parse.js")));
        final Invocable invocable = (Invocable) engine;
        final FinancialTransaction ft = (FinancialTransaction) invocable.invokeFunction("parseToJavaClass", "\"20141208\",\"Omschrijving\",\"NL01FIRA0001234567\",\"\",\"BA\",\"Af\",\"99,99\",\"Betaalautomaat\",\"mededeling\"");

        assertEquals("20141208", ft.getDate());
        assertEquals("Omschrijving", ft.getContraAccountName());
        assertEquals("NL01FIRA0001234567", ft.getAccountNumber());
        assertEquals("", ft.getContraAccountNumber());
        assertEquals("BA", ft.getCode());
        assertEquals("debit", ft.getDebitCreditIndicator());
        assertEquals("99,99", ft.getAmount());
        assertEquals("Betaalautomaat", ft.getMutatiesoort());
        assertEquals("mededeling", ft.getDescription());

    }

    @Test(expected = NoSuchMethodException.class)
    public void testParseToNoMethod() throws ScriptException, NoSuchMethodException {
        final ScriptEngineManager engineManager
                = new ScriptEngineManager();

        final ScriptEngine engine
                = engineManager.getEngineByName("nashorn");
        engine.eval(new InputStreamReader(this.getClass().getResourceAsStream("/parse.js")));
        final Invocable invocable = (Invocable) engine;
        final FinancialTransaction ft = (FinancialTransaction) invocable.invokeFunction("parseToNoMethod", "\"20141208\",\"Omschrijving\",\"NL01FIRA0001234567\",\"NL01FIRA0009999999\",\"BA\",\"Af\",\"99,99\",\"Betaalautomaat\",\"mededeling\"");

    }

    @Test
    public void testParseToJavaClassWrong() throws ScriptException, NoSuchMethodException {
        final ScriptEngineManager engineManager
                = new ScriptEngineManager();

        final ScriptEngine engine
                = engineManager.getEngineByName("nashorn");
        engine.eval(new InputStreamReader(this.getClass().getResourceAsStream("/parse.js")));
        final Invocable invocable = (Invocable) engine;
        final FinancialTransaction ft = (FinancialTransaction) invocable.invokeFunction("parseToJavaClassWrong");

        assertEquals("20150107", ft.getDate());
        assertNull(ft.getContraAccountName());
        assertNull(ft.getAccountNumber());
        assertNull(ft.getContraAccountNumber());
        assertNull(ft.getCode());
        assertNull(ft.getDebitCreditIndicator());
        assertNull(ft.getAmount());
        assertNull(ft.getMutatiesoort());
        assertNull(ft.getDescription());

    }

    @Test
    public void testParseToScriptJavaError() throws ScriptException, NoSuchMethodException {
        final ScriptEngineManager engineManager
                = new ScriptEngineManager();

        final ScriptEngine engine
                = engineManager.getEngineByName("nashorn");
        try {
            engine.eval(new InputStreamReader(this.getClass().getResourceAsStream("/parse.js")));
            final Invocable invocable = (Invocable) engine;
            invocable.invokeFunction("parseToScriptJavaError");
            fail("expecting runtime exception");
        } catch (RuntimeException e) {
            assertEquals("java.lang.ClassNotFoundException: java.type", e.getMessage());
        }

    }

    @Test(expected = ScriptException.class)
    public void testParseToScriptError() throws ScriptException, NoSuchMethodException {
        final ScriptEngineManager engineManager
                = new ScriptEngineManager();

        final ScriptEngine engine
                = engineManager.getEngineByName("nashorn");
        engine.eval(new InputStreamReader(this.getClass().getResourceAsStream("/parse.js")));
        final Invocable invocable = (Invocable) engine;
        invocable.invokeFunction("parseToScriptError");

    }
        @Test
    public void testParseToJavaClass2() throws ScriptException, NoSuchMethodException {
        final ScriptEngineManager engineManager
                = new ScriptEngineManager();

        final ScriptEngine engine
                = engineManager.getEngineByName("nashorn");
        engine.eval(new InputStreamReader(this.getClass().getResourceAsStream("/parse.js")));
        final Invocable invocable = (Invocable) engine;
        final FinancialTransaction ft = (FinancialTransaction) invocable.invokeFunction("parseToJavaClass", "\"20140701\",\"28-06-14 10:05 BETAALAUTOMAAT   \",\"NL46 INGB 0005 5212 08\",\"\",\"BA\",\"Af\",\"25,01\",\"Betaalautomaat\",\"C1000 TIEL / TIEL               019 313357 74MLT2               ING BANK NV PASTRANSACTIES      \"");

        assertEquals("20140701", ft.getDate());
        assertEquals("28-06-14 10:05 BETAALAUTOMAAT   ", ft.getContraAccountName());
        assertEquals("NL46 INGB 0005 5212 08", ft.getAccountNumber());
        assertEquals("", ft.getContraAccountNumber());
        assertEquals("BA", ft.getCode());
        assertEquals("debit", ft.getDebitCreditIndicator());
        assertEquals("25,01", ft.getAmount());
        assertEquals("Betaalautomaat", ft.getMutatiesoort());
        assertEquals("C1000 TIEL / TIEL               019 313357 74MLT2               ING BANK NV PASTRANSACTIES      ", ft.getDescription());

    }

    @Test
    public void testCSVtoArray() throws ScriptException, NoSuchMethodException {
        final ScriptEngineManager engineManager
                = new ScriptEngineManager();

        final ScriptEngine engine
                = engineManager.getEngineByName("nashorn");
        engine.eval(new InputStreamReader(this.getClass().getResourceAsStream("/parse.js")));
        final Invocable invocable = (Invocable) engine;
        ScriptObjectMirror obj = (ScriptObjectMirror) invocable.invokeFunction("CSVtoArray", "\"20111222\",\"1350002 NS-TIEL 201>\\TIEL> \\N\",\"5521208\",\"425008215\",\"BA\",\"Af\",\"13,00\",\"Betaalautomaat\",\"PASVOLGNR 017     22-12-2011 06:03TRANSACTIENR 1100332       \"");
        assertTrue(obj.isArray());
        obj = (ScriptObjectMirror) invocable.invokeFunction("CSVtoArray", "\"20111103\",\"3006841 Xenos Tiel 0040>\\TIEL> \\\",\"5521208\",\"438323009\",\"BA\",\"Af\",\"5,95\",\"Betaalautomaat\",\"PASVOLGNR 017     03-11-2011 18:25TRANSACTIENR 0502404       \"");

        assertNull(obj); // why???
        
    }
    
    
    
}
