package controllers;

import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;


import static play.test.Helpers.*;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.GET;
import static play.test.Helpers.route;

public class UserAccountControllerTest extends WithApplication {

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }

    @Test
    public void testViewUserAccountSettings() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/viewUserAccountSettings");

        Result result = route(app, request);
        assertEquals(OK, result.status());
        assertEquals("text/html", result.contentType().get());
        assertEquals("utf-8", result.charset().get());
        assertTrue(contentAsString(result).contains("Meet-up"));
        assertTrue(contentAsString(result).contains("Account Settings"));
        assertTrue(contentAsString(result).contains("Username"));
        assertTrue(contentAsString(result).contains("Name"));
        assertTrue(contentAsString(result).contains("Password"));
        assertTrue(contentAsString(result).contains("Email"));
        assertTrue(contentAsString(result).contains("Phone"));
        assertTrue(contentAsString(result).contains("Address"));
    }

}
