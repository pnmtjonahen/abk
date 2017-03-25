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
package nl.tjonahen.abk.backend.boundry.user;

import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import nl.tjonahen.kumuluzee.test.KumuluzeeClassRunner;
import nl.tjonahen.kumuluzee.test.KumuluzeeTestContext;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author Philippe Tjon - A - Hen philippe@tjonahen.nl
 */
@RunWith(KumuluzeeClassRunner.class)
@KumuluzeeTestContext(unitname = "abk")
public class UserResourceTest {

    /**
     * Test of login method, of class UserResource.
     */
    @Test
    public void testFlow() {
        given()
                .body("{\"username\":\"testuser\", \"password\":\"password\"}")
                .contentType(ContentType.JSON)
                .post("http://localhost:8080/user/login")
                .then()
                .statusCode(401);

        given()
                .body("{\"username\":\"testuser\", \"password\":\"password\"}")
                .contentType(ContentType.JSON)
                .post("http://localhost:8080/user/create")
                .then()
                .statusCode(200);

        given()
                .body("{\"username\":\"testuser\", \"password\":\"password\"}")
                .contentType(ContentType.JSON)
                .post("http://localhost:8080/user/login")
                .then()
                .statusCode(200);

    }

    @Test
    public void testNotAthorized() {
        given()
                .contentType(ContentType.JSON)
                .get("http://localhost:8080/user/")
                .then()
                .statusCode(401);

        String token = given()
                .body("{\"username\":\"testuser\", \"password\":\"password\"}")
                .contentType(ContentType.JSON)
                .post("http://localhost:8080/user/login")
                .then()
                .statusCode(200)
                .extract().header("Authorization");

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .get("http://localhost:8080/user/")
                .then()
                .statusCode(200);

    }
}
