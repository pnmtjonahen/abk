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
package nl.tjonahen.abk.backend.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Philippe Tjon - A - Hen, philippe@tjonahen.nl
 */
@XmlRootElement(name = "CsvReader")
@XmlAccessorType(XmlAccessType.FIELD)
public class CsvJsReader {

    private String headers;
    private String dryrun;
    private String script;


    public CsvJsReader() {
        // for JAXB purposes
    }


    public CsvJsReader(Boolean headers, Boolean dryrun, String script) {
        this.headers = headers.toString();
        this.dryrun = dryrun.toString();
        this.script = script;
    }


    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    public String getDryrun() {
        return dryrun;
    }

    public void setDryrun(String dryrun) {
        this.dryrun = dryrun;
    }


    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }


}
