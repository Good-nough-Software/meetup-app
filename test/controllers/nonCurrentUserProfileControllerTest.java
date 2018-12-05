package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

public class nonCurrentUserProfileControllerTest extends WithApplication {

    @Before
    public void setUp() throws Exception {
    }

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }

    @Test
    public void testIndex() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/viewNonUserProfile");

        Result result = route(app, request);
        assertEquals(OK, result.status());
    }

    @Test
    public void testOne() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/viewNonUserProfile");

        Result result = route(app, request);

        assertTrue(contentAsString(result).contains("Name: John Smith"));

        assertTrue(contentAsString(result).contains("Email: jsmith@gmail.com"));

        assertTrue(contentAsString(result).contains("Phone: 12311234567"));

        assertTrue(contentAsString(result).contains("Location: United States, Houghton, 1701 Woodland rd, 49931"));

    }

    @Test
    public void testTwo() throws Exception {


        JsonNode jsonNode = (new ObjectMapper()).readTree("{ \"nonUserProfileUsername\": \"jdoe\" }");


        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .session("nonUserProfileUsername", "jdoe")
                .uri("/viewNonUserProfile");


        Result result = route(app, request);

        String temp = contentAsString(result);
        assertTrue(contentAsString(result).contains("Name: Jane Doe"));

        assertEquals(OK, result.status());
    }
}