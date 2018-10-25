package controllers;

import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

public class RoutesTest extends WithApplication {

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
        assertTrue(contentAsString(result).contains("Login"));
    }

    @Test
    public void testViewHome() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/home");

        Result result = route(app, request);
        assertTrue(contentAsString(result).contains("meetup-search-box"));
    }

    @Test
    public void testViewNewUser() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/viewNewUser");

        Result result = route(app, request);
        assertTrue(contentAsString(result).contains("Create new user"));
    }

    @Test
    public void testViewForgotPassword() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/viewResetPassword");

        Result result = route(app, request);
        assertTrue(contentAsString(result).contains("Reset Password"));
    }
}
