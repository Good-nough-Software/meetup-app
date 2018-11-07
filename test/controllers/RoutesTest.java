package controllers;

import junit.framework.TestCase;
import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import play.test.WithApplication;

import static play.test.Helpers.GET;
import static play.test.Helpers.route;

public class RoutesTest extends WithApplication {

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }

    @Test
    public void testRoutes() {

        Http.RequestBuilder request = Helpers.fakeRequest()
                .method(GET)
                .uri("/");

        Result result = route(app, request);
        TestCase.assertEquals(200, result.status());

        request = Helpers.fakeRequest()
                .method(GET)
                .uri("/home");

        result = route(app, request);
        TestCase.assertEquals(200, result.status());

        request = Helpers.fakeRequest()
                .method(GET)
                .uri("/results");

        result = route(app, request);
        TestCase.assertEquals(200, result.status());

        request = Helpers.fakeRequest()
                .method(GET)
                .uri("/viewNewUser");

        result = route(app, request);
        TestCase.assertEquals(200, result.status());

        request = Helpers.fakeRequest()
                .method(GET)
                .uri("/viewResetPassword");

        result = route(app, request);
        TestCase.assertEquals(200, result.status());

        request = Helpers.fakeRequest()
                .method(GET)
                .uri("/");

        result = route(app, request);
        TestCase.assertEquals(200, result.status());

        request = Helpers.fakeRequest()
                .method(GET)
                .uri("/");

        result = route(app, request);
        TestCase.assertEquals(200, result.status());



        request = Helpers.fakeRequest()
                .method(GET)
                .uri("/viewResetPassword");

        result = route(app, request);
        TestCase.assertEquals(200, result.status());
    }
}
