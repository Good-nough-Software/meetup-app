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
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.route;

public class resetPasswordControllerTest extends WithApplication {

    @Test
    public void renderViewResetPassword() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method("GET")
                .uri(routes.resetPasswordController.renderViewResetPassword().url());

        request = CSRFTokenHelper.addCSRFToken(request);
        Result result = route(request);
        assertEquals(OK, result.status());

    }

    @Test
    public void resetPassword() throws Exception {
        JsonNode jsonNode = (new ObjectMapper()).readTree("{ \"email\": \"jsmith@gmail.com\" }");
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method("GET")
                .bodyJson(jsonNode)
                .uri(routes.resetPasswordController.resetPassword().url());

        request = CSRFTokenHelper.addCSRFToken(request);
        Result result = Helpers.route(request);


        Map<String, String> sessionMap = result.session();

        String code = sessionMap.get("resetCode");

        assertEquals(303, result.status());

        jsonNode = (new ObjectMapper()).readTree("{ \"resetCode\": \"" + code + "\", \"tempNewPassword\": \"password\", \"tempNewPasswordConfirm\": \"password\" }");
        request = new Http.RequestBuilder()
                .method("GET")
                .bodyJson(jsonNode)
                .uri(routes.resetPasswordController.sendResetPassword().url());

        request = CSRFTokenHelper.addCSRFToken(request);
        result = route(request);


    }

}