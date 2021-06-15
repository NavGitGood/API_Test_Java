import dataLoader.ConfigurationLoader;
import dataLoader.YAMLLoader;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.*;
import util.Helper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

public class APIRequestTest {

    private static String bearerToken = "";

    Response response;
    String getOrdersURL = ConfigurationLoader.getPropertyValue("url").concat("/orders");


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

    @Test(description = "Assert post request to be successful", dependsOnMethods = { "assertBearerToken" })
    public void postOrder() {
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

    @Test(description = "Assert three orders to be present", dependsOnMethods = { "postOrder" })
    public void orderValueTest() {
        Map<String, Object> order1 = Helper.getMapFromInstance(YAMLLoader.base.getOrders().get(0));
        Map<String, Object> order2 = Helper.getMapFromInstance(YAMLLoader.base.getOrders().get(1));
        Map<String, Object> order3 = Helper.getMapFromInstance(YAMLLoader.base.getOrders().get(2));

        Response response = given().relaxedHTTPSValidation().when().get(getOrdersURL);
        assertEquals(200, response.getStatusCode());
        String json = response.asString();
        JsonPath jp = new JsonPath(json);
        List<Map<String, Object>> obj = jp.get("$");
        assertEquals(obj.size(), 3);
        assertEquals(Helper.removeKey("Timestamp", obj.get(0)).values().toString(), Helper.removeKey("timestamp", order1).values().toString());
        assertEquals(Helper.removeKey("Timestamp", obj.get(1)).values().toString(), Helper.removeKey("timestamp", order2).values().toString());
        assertEquals(Helper.removeKey("Timestamp", obj.get(2)).values().toString(), Helper.removeKey("timestamp", order3).values().toString());

        List<String> l = jp.get("Crust");
        assertEquals(l.get(2), "NORMAL");
    }

    @Test(description = "Assert all the keys", dependsOnMethods = { "postOrder" })
    public void orderKeyTest() {
        Map<String, Object> order1 = Helper.getMapFromInstance(YAMLLoader.base.getOrders().get(0));
        Map<String, Object> order2 = Helper.getMapFromInstance(YAMLLoader.base.getOrders().get(1));
        Map<String, Object> order3 = Helper.getMapFromInstance(YAMLLoader.base.getOrders().get(2));

        Response response = given().relaxedHTTPSValidation().when().get(getOrdersURL);
        String json = response.asString();
        JsonPath jp = new JsonPath(json);
        List<HashMap> obj = jp.get("$");
        assertThat(obj.get(0).keySet().toString(), equalToIgnoringCase(order1.keySet().toString()));
        assertThat(obj.get(1).keySet().toString(), equalToIgnoringCase(order2.keySet().toString()));
        assertThat(obj.get(2).keySet().toString(), equalToIgnoringCase(order3.keySet().toString()));
    }

    @Test(description = "Assert that Crust for third order is NORMAL", dependsOnMethods = { "postOrder" })
    public void orderCrustTest() {
        Response response = given().relaxedHTTPSValidation().when().get(getOrdersURL);
        String json = response.asString();
        JsonPath jp = new JsonPath(json);
        List<String> l = jp.get("Crust");
        assertEquals(l.get(2), "NORMAL");
    }

    @AfterClass(description = "Delete All Orders")
    public void deleteAll() {
        deleteAllOrders();
    }
}
