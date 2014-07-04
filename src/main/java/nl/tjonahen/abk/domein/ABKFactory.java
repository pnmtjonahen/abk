/*
 * Copyright (C) 2013 Philippe Tjon-A-Hen philippe@tjonahen.nl
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
package nl.tjonahen.abk.domein;

import nl.tjonahen.abk.domein.entity.Transactie;
import java.util.Date;

/**
 * Abstract Factory pattern, aka standalone factory.
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 *
 */
public final class ABKFactory {

    /**
     *
     * @param datum -
     * @param rekening -
     * @param code -
     * @param bijAf -
     * @param bedrag -
     * @param mutatieSoort -
     * @param mededeling -
     * @return -
     */
    public Transactie createTransactie(final Date datum, final String rekening,
            final String code, final String bijAf, final Double bedrag, final String mutatieSoort,
            final String mededeling) 
    {

        Transactie trans = new Transactie();

        trans.setDatum(datum);
        trans.setRekening(rekening);
        trans.setCode(code);
        trans.setBijAf(bijAf);
        trans.setBedrag(bedrag);
        trans.setMutatiesoort(mutatieSoort);
        trans.setMededeling(mededeling);
        return trans;
    }
}
