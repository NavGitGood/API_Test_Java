import static io.restassured.RestAssured.*;

import dataLoader.ConfigurationLoader;
import dataLoader.YAMLLoader;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;
import util.Helper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;

public class GetRequestTest {

    // using properties files
    String getOrdersURL = ConfigurationLoader.getPropertyValue("url").concat("/orders");
    // using YAML file
//    String urlFromYAML = YAMLLoader.base.getUrl();

    @Test(description = "Assert all the values")
    public void orderValueTest() {
        Map<String, Object> order1 = Helper.getMapFromInstance(YAMLLoader.base.getOrders().get(0));
        Map<String, Object> order2 = Helper.getMapFromInstance(YAMLLoader.base.getOrders().get(1));
        Map<String, Object> order3 = Helper.getMapFromInstance(YAMLLoader.base.getOrders().get(2));

        Response response = given().relaxedHTTPSValidation().when().get(getOrdersURL);
        assertEquals(200, response.getStatusCode());
        String json = response.asString();
        JsonPath jp = new JsonPath(json);
        List<HashMap> obj = jp.get("$");
        assertEquals(obj.size(), 3);
        assertEquals(obj.get(0).values().toString(), order1.values().toString());
        assertEquals(obj.get(1).values().toString(), order2.values().toString());
        assertEquals(obj.get(2).values().toString(), order3.values().toString());
        List<String> l = jp.get("Crust");
        assertEquals(l.get(2), "NORMAL");
    }

    @Test(description = "Assert all the keys")
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

    @Test(description = "Assert that Crust for third order is NORMAL ")
    public void orderCrustTest() {
        Response response = given().relaxedHTTPSValidation().when().get(getOrdersURL);
        String json = response.asString();
        JsonPath jp = new JsonPath(json);
        List<String> l = jp.get("Crust");
        assertEquals(l.get(2), "NORMAL");
    }

}
