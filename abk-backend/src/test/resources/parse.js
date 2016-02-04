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

function split(line) {
    var objPattern = new RegExp("\"(.+?)\"", "gi");

    var splitted = [];
    var arrMatches = null;


    while (arrMatches = objPattern.exec(line)) {
        splitted.push(arrMatches[1]);
    }
    return splitted;
}
;

function parseToJson(line) {
    var splitted = split(line);
    return {"date": splitted[0],
        "contraAccountName": splitted[1],
        "accountNumber": splitted[2],
        "contraAccountNumber": splitted[3],
        "code": splitted[4],
        "debitCreditIndicator": (splitted[5] === "Af" ? "debit" : "credit"),
        "amount": splitted[6],
        "mutatiesoort": splitted[7],
        "description": splitted[8]
    };
}
;

function parseToJavaClass(line) {
    var objPattern = new RegExp("\"([a-zA-Z0-9_ ,\-:/]*)\"", "gi");

    var splitted = [];
    var arrMatches = null;


    while (arrMatches = objPattern.exec(line)) {
        print(arrMatches[0])
        print(arrMatches[1]);
        
        splitted.push(arrMatches[1]);
    }

    var FinancialTransaction = Java.type("nl.tjonahen.abk.backend.model.FinancialTransaction");

    var ft = new FinancialTransaction();

    ft.date = splitted[0];
    ft.contraAccountName = splitted[1];
    ft.accountNumber = splitted[2];
    ft.contraAccountNumber = splitted[3];
    ft.code = splitted[4];
    ft.debitCreditIndicator = (splitted[5] === "Af" ? "debit" : "credit");
    ft.amount = splitted[6];
    ft.mutatiesoort = splitted[7];
    ft.description = splitted[8];
    return ft;
}
;
function parseToJavaClassWrong() {

    var FinancialTransaction = Java.type('nl.tjonahen.abk.backend.model.FinancialTransaction');

    var ft = new FinancialTransaction();

    ft.date = '20150107';
    ft.contraaccountname = 'splitted[1]';
    ft.accountnumber = 'splitted[2]';
    return ft;
}
;

function parseToScriptJavaError() {
    var FinancialTransaction = java.type('nl.tjonahen.abk.backend.model.FinancialTransaction');

    return new FinancialTransaction();
}
;
function parseToScriptError() {
    var FinancialTransaction = Java.type('nl.tjonahen.abk.backend.model.FinancialTransaction');

    var ft = new FinancialTransaction();

    return ft.date.year === '2015';
}
;
// Return array of string values, or NULL if CSV string not well formed.
function CSVtoArray(text) {
    var re_valid = /^\s*(?:'[^'\\]*(?:\\[\S\s][^'\\]*)*'|"[^"\\]*(?:\\[\S\s][^"\\]*)*"|[^,'"\s\\]*(?:\s+[^,'"\s\\]+)*)\s*(?:,\s*(?:'[^'\\]*(?:\\[\S\s][^'\\]*)*'|"[^"\\]*(?:\\[\S\s][^"\\]*)*"|[^,'"\s\\]*(?:\s+[^,'"\s\\]+)*)\s*)*$/;
    var re_value = /(?!\s*$)\s*(?:'([^'\\]*(?:\\[\S\s][^'\\]*)*)'|"([^"\\]*(?:\\[\S\s][^"\\]*)*)"|([^,'"\s\\]*(?:\s+[^,'"\s\\]+)*))\s*(?:,|$)/g;
    // Return NULL if input string is not well formed CSV string.
    if (!re_valid.test(text)) return null;
    var a = [];                     // Initialize array to receive values.
    text.replace(re_value, // "Walk" the string using replace with callback.
        function(m0, m1, m2, m3) {
            // Remove backslash from \' in single quoted values.
            if      (m1 !== undefined) a.push(m1.replace(/\\'/g, "'"));
            // Remove backslash from \" in double quoted values.
            else if (m2 !== undefined) a.push(m2.replace(/\\"/g, '"'));
            else if (m3 !== undefined) a.push(m3);
            return ''; // Return empty string.
        });
    // Handle special case of empty last value.
    if (/,\s*$/.test(text)) a.push('');
    return a;
};
