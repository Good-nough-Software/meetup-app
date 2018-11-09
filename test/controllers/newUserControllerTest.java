package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import play.api.test.CSRFTokenHelper;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;

import java.io.IOException;

import static junit.framework.TestCase.assertTrue;
import static play.test.Helpers.contentAsString;
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
            jsonNode = (new ObjectMapper()).readTree("{ \"username\": \"jsmith\", \"password\": \"password\" , " +
                    " \"email\": \"fake\", \"firstName\": \"fake\", \"lastName\": \"fake\" }");
            Http.RequestBuilder request = new Http.RequestBuilder()
                    .method("POST")
                    .bodyJson(jsonNode)
                    .uri(controllers.routes.newUserController.createNewUser().url());

            request = CSRFTokenHelper.addCSRFToken(request);
            Result result = route(request);


            assertTrue(contentAsString(result).contains("username taken"));
        } catch (IOException e) {
            assertTrue(1 ==0);
        }

         jsonNode = null;
        try {
            jsonNode = (new ObjectMapper()).readTree("{ \"username\": \"noone\", \"password\": \"password\" , " +
                    " \"email\": \"fake\", \"firstName\": \"fake\", \"lastName\": \"fake\" }");
            Http.RequestBuilder request = new Http.RequestBuilder()
                    .method("POST")
                    .bodyJson(jsonNode)
                    .uri(controllers.routes.newUserController.createNewUser().url());

            request = CSRFTokenHelper.addCSRFToken(request);
            Result result = route(request);


            assertTrue(contentAsString(result).contains("create user"));
        } catch (IOException e) {
            assertTrue(1 ==0);
        }

    }


}
