package controllers;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import play.api.test.CSRFTokenHelper;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import play.test.WithApplication;

import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

public class LoginControllerTest extends WithApplication {


    @Test
    public void testViewLogin() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/login");

        Result result = route(app, request);
        assertEquals(OK, result.status());
        assertEquals("text/html", result.contentType().get());
        assertEquals("utf-8", result.charset().get());
        assertTrue(contentAsString(result).contains("Login"));
        assertTrue(contentAsString(result).contains("Username"));
        assertTrue(contentAsString(result).contains("Password"));
    }


//    Application fakeApp = fakeApplication();
//
//    Application fakeAppWithMemoryDb = fakeApplication(inMemoryDatabase("test"));
//
//    @Before
//    public void setup() {
//        Module testModule = new AbstractModule() {
//            @Override
//            public void configure() {
//                // Install custom test binding here
//            }
//        };
//
//        GuiceApplicationBuilder builder = new GuiceApplicationLoader()
//                .builder(new ApplicationLoader.Context(Environment.simple()))
//                .overrides(testModule);
//        Guice.createInjector(builder.applicationModule()).injectMembers(this);
//
//        Helpers.start(fakeApp);
//    }

    @Test
    public void testBadRoute() {
        Http.RequestBuilder request = Helpers.fakeRequest()
                .method(GET)
                .uri("/xx/Kiwi");

        Result result = route(app, request);
        assertEquals(NOT_FOUND, result.status());
    }



    @Test
    public void authenticateUser() throws Exception {

        JsonNode jsonNode = (new ObjectMapper()).readTree("{ \"username\": \"jsmith\", \"password\": \"password\"  }");
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method("POST")
                .bodyJson(jsonNode)
                .uri(controllers.routes.LoginController.login().url());

        request = CSRFTokenHelper.addCSRFToken(request);
        Result result = route(request);


        Map<String, String> sessionMap = result.session();


        assertEquals( "jsmith",sessionMap.get("username"));

        request = new Http.RequestBuilder()
                .method("GET")
                .uri(controllers.routes.LoginController.logout().url());

        request = CSRFTokenHelper.addCSRFToken(request);
        result = route(request);

        assertEquals(303, result.status());

        jsonNode = (new ObjectMapper()).readTree("{ \"username\": \"noone\", \"password\": \"password\"  }");
        request = new Http.RequestBuilder()
                .method("POST")
                .bodyJson(jsonNode)
                .uri(controllers.routes.LoginController.login().url());

        request = CSRFTokenHelper.addCSRFToken(request);
        result = route(request);

        sessionMap = result.session();


        assertEquals(sessionMap.get("username"), "null");
    }




}
