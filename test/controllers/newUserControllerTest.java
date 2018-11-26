package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import play.api.test.CSRFTokenHelper;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static play.test.Helpers.route;

/**
 * @author: Lucas Buccilli
 * Date: 11/7/18
 * Time: 3:15 PM
 * Project Name: meetup-app
 * File Name: newUserControllerTest
 */
public class newUserControllerTest extends WithApplication {

    @Test
    public void newUserTest() {
        JsonNode jsonNode = null;
        try {
            jsonNode = (new ObjectMapper()).readTree("{ \"firstName\": \"fake\", \"lastName\": \"fake\", \"email\": \"fake\", \"username\": \"jsmith\", \"password\": \"password\" }");
            Http.RequestBuilder request = new Http.RequestBuilder()
                    .method("POST")
                    .bodyJson(jsonNode)
                    .uri(controllers.routes.newUserController.createNewUser().url());

            request = CSRFTokenHelper.addCSRFToken(request);
            Result result = route(request);
           String flash = result.flash().get("Message");

            assertTrue(flash.contains("Username already exists"));
        } catch (IOException e) {
            assertEquals(1,0);
        }


    }


}
