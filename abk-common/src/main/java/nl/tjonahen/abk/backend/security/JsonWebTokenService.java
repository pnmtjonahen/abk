/*
 * Copyright (C) 2017 Philippe Tjon - A - Hen philippe@tjonahen.nl
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
package nl.tjonahen.abk.backend.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Philippe Tjon - A - Hen
 */
public class JsonWebTokenService {

    private final static Logger LOGGER = Logger.getLogger(JsonWebTokenService.class.getName());

    private static final String CLAIM1 = "name";
    private static final String ISSUER = "nl.tjonahen.abk";
    private static final String MY_VERY_SUPER_SECRET = "my very super secret";

    private final Algorithm algorithm;
    private final JWTVerifier verifier;

    public JsonWebTokenService() throws IllegalArgumentException, UnsupportedEncodingException {
        algorithm = Algorithm.HMAC256(MY_VERY_SUPER_SECRET);
        verifier = JWT.require(algorithm)
                .withIssuer(ISSUER)
                .build();
    }

    public String createToken(final String naam) {
        try {
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withClaim(CLAIM1, naam)
                    .withExpiresAt(new Date(System.currentTimeMillis() + 30000)) // 5min
                    .sign(algorithm);
        } catch (IllegalArgumentException | JWTCreationException exception) {
            LOGGER.log(Level.SEVERE, "Error create JWT", exception);
            return null; // unable to create token
        }
    }

    public boolean isValidToken(String token) {
        try {
            verifier.verify(token);
            return true;

        } catch (JWTVerificationException exception) {
            if (exception instanceof InvalidClaimException) {
                LOGGER.info(() -> " Invalid token " + exception.getMessage());
            } else {
                LOGGER.log(Level.SEVERE, "Error decoding JWT", exception);
            }
        }
        return false;
    }

    public String refreshToken(String token) {
        try {
            final DecodedJWT jwt = verifier.verify(token);
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withClaim(CLAIM1, jwt.getClaim(CLAIM1).asString())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 30000)) // 5min
                    .sign(algorithm);
        } catch (JWTVerificationException exception) {
            LOGGER.log(Level.SEVERE, "Error decoding JWT", exception);
        }
        return token;
    }

}
