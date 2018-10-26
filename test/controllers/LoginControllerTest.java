package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import controllers.routes;
import models.loginForm;
import org.junit.Test;
import play.Application;
import play.api.test.CSRFTokenHelper;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.filters.csrf.CSRF;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Call;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import play.test.Helpers;
import play.test.WithApplication;
import play.twirl.api.Content;
import play.twirl.api.Html;
import views.html.viewLogin;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

public class LoginControllerTest extends WithApplication {

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }

    @Test
    public void testViewLogin() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/");

        Result result = route(app, request);
        assertEquals(OK, result.status());
        assertEquals("text/html", result.contentType().get());
        assertEquals("utf-8", result.charset().get());
        assertTrue(contentAsString(result).contains("Login"));
        assertTrue(contentAsString(result).contains("Username"));
        assertTrue(contentAsString(result).contains("Password"));
    }

    @Test
    public void testLogin() {

        //test post request with user: test password: password
        //Sucessful login
        try {
            JsonNode jsonNode = (new ObjectMapper()).readTree("{ \"username\": \"test\", \"password\": \"password\"  }");
            Http.RequestBuilder request = new Http.RequestBuilder().method("POST")
                    .bodyJson(jsonNode)
                    .uri(controllers.routes.LoginController.login().url());
            request = CSRFTokenHelper.addCSRFToken(request);
            Result result = route(request);


            assertEquals(OK, result.status());
            assertTrue(contentAsString(result).contains("Username: test"));

        } catch (Exception e) {
            assertTrue(false);
        }

        //test post request with user: test password: p
        //password is not correct
        try {
            JsonNode jsonNode = (new ObjectMapper()).readTree("{ \"username\": \"test\", \"password\": \"p\"  }");
            Http.RequestBuilder request = new Http.RequestBuilder().method("POST")
                    .bodyJson(jsonNode)
                    .uri(controllers.routes.LoginController.login().url());
            request = CSRFTokenHelper.addCSRFToken(request);
            Result result = route(request);


            assertEquals(OK, result.status());
            assertTrue(contentAsString(result).contains("Invalid Password"));

        } catch (Exception e) {
            assertTrue(false);
        }

        //test post request with user: notAUser password: p
        //password is not correct
        try {
            JsonNode jsonNode = (new ObjectMapper()).readTree("{ \"username\": \"notAUser\", \"password\": \"p\"  }");
            Http.RequestBuilder request = new Http.RequestBuilder().method("POST")
                    .bodyJson(jsonNode)
                    .uri(controllers.routes.LoginController.login().url());
            request = CSRFTokenHelper.addCSRFToken(request);
            Result result = route(request);


            assertEquals(OK, result.status());
            assertTrue(contentAsString(result).contains("Username not found"));

        } catch (Exception e) {
            assertTrue(false);
        }
    }

}
