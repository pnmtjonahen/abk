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
package nl.tjonahen.abk.backend.boundry.upload;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import nl.tjonahen.abk.backend.model.FinancialTransaction;

/**
 *
 * @author Philippe Tjon - A - Hen, philippe@tjonahen.nl
 */
public class CsvJSScripting {

    private static final String ENGINE_NAME = "nashorn";
    private final Invocable invocable;
    
    public CsvJSScripting(final String script) {
        final ScriptEngineManager engineManager = new ScriptEngineManager();
        final ScriptEngine scriptEngine = engineManager.getEngineByName(ENGINE_NAME);
        try {
            scriptEngine.eval(script);
        } catch (ScriptException ex) {
            throw new IllegalStateException("Cannot eval script", ex);
        }
        invocable  = (Invocable) scriptEngine;
    }

    public FinancialTransaction parse(String s) throws CsvJSScriptingException {
        try {
            return (FinancialTransaction) invocable.invokeFunction("parse", s);
        } catch(ScriptException | NoSuchMethodException ex) {
            throw new CsvJSScriptingException(ex);
        }
    }

    public static class CsvJSScriptingException extends Exception {

        public CsvJSScriptingException(java.lang.Exception ex) {
            super(ex);
        }
    }
    
    
}
