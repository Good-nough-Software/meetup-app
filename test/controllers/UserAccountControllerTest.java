package controllers;

import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;

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
    public void testUserAccountSettings() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/");

        Result result = route(app, request);
        assertEquals(OK, result.status());
        assertEquals("text/html", result.contentType().get());
        assertEquals("utf-8", result.charset().get());
        // assertEquals(contentAsString(result).contains("Account Settings"));
        // assertEquals(contentAsString(result).contains("Update Username"));
        // assertEquals(contentAsString(result).contains("Your Name"));
        // assertEquals(contentAsString(result).contains("Update Email"));
        // assertEquals(contentAsString(result).contains("example@email.com"));
        // assertEquals(contentAsString(result).contains("Update Phone Number"));
        // assertEquals(contentAsString(result).contains("(000) - 000 - 0000"));
        // assertEquals(contentAsString(result).contains("Reset Password"));
        // assertEquals(contentAsString(result).contains("Places I've Been"));
        // assertEquals(contentAsString(result).contains("Example High School"));
        // assertEquals(contentAsString(result).contains("Home Address"));
        // assertEquals(contentAsString(result).contains("My Home"));

    }

}
