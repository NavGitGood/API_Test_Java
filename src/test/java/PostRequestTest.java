import dataLoader.ConfigurationLoader;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.*;

import java.util.stream.IntStream;

import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

public class PostRequestTest {

    private static String bearerToken = "";

    Response response;

    public void deleteAllOrders() {
        String deleteOrderURL = ConfigurationLoader.getPropertyValue("url").concat("/orders");
        IntStream.range(1, 10).forEach( order ->
                given()
                        .header("Content-type", "application/json")
                        .when()
                        .delete(deleteOrderURL + "/" + order)
        );
    }

    @BeforeClass(description = "Make auth request only once")
    public void makeAuthRequest() {
        String authURL = ConfigurationLoader.getPropertyValue("url").concat("/auth");
        String pass = System.getenv("pizza_api_pass");
        String user = ConfigurationLoader.getPropertyValue("username");
        String requestBody = "{ \"password\": \""+pass+"\",\"username\": \""+user+"\"}";
        response = given()
                .header("Content-type", "application/json")
                .and()
                .body(requestBody)
                .when()
                .post(authURL);
        deleteAllOrders();
    }

    @Test(description = "Assert StatusCode to be 200")
    public void assertStatusCode() {
        assertEquals(200, response.getStatusCode());
    }

    @Test(description = "Assert BearerToken to be present", dependsOnMethods = { "assertStatusCode" })
    public void assertBearerToken() {
        response.then()
                .body("$", hasKey("access_token"));
        String json = response.asString();
        JsonPath jp = new JsonPath(json);
        bearerToken = jp.get("access_token");
    }

    @Test(description = "Assert BearerToken to be present", dependsOnMethods = { "assertBearerToken" })
    public void postOneOrder() {
        String postOrderURL = ConfigurationLoader.getPropertyValue("url").concat("/orders");
        String requestBody = "{ \"Crust\": \"NORMAL\", \"Flavor\": \"VEGGIE\",\"Size\": \"M\", \"Table_No\": 1 }";
        String requestBody1 = "{ \"Crust\": \"THIN\", \"Flavor\": \"CHEESE\",\"Size\": \"S\", \"Table_No\": 2 }";
        String requestBody2 = "{ \"Crust\": \"NORMAL\", \"Flavor\": \"CHICKEN-FAJITA\",\"Size\": \"L\", \"Table_No\": 3 }";

        response = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer "+bearerToken)
                .and()
                .body(requestBody)
                .when()
                .post(postOrderURL);
        assertEquals(response.getStatusCode(), 201);
        response = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer "+bearerToken)
                .and()
                .body(requestBody1)
                .when()
                .post(postOrderURL);
        assertEquals(response.getStatusCode(), 201);
        response = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer "+bearerToken)
                .and()
                .body(requestBody2)
                .when()
                .post(postOrderURL);
        assertEquals(response.getStatusCode(), 201);
    }

    @AfterClass(description = "Delete All Orders")
    public void deleteAll() {
        deleteAllOrders();
    }
}
