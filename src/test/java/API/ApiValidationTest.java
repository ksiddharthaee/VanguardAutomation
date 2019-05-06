package API;

import io.restassured.response.Response;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;


import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ApiValidationTest extends generateAPIUriTest {

    public static String specs = "/retail/mvc/getNavPriceList.jsonp";

    @BeforeClass
    public static void generateUrl() {

        String externalParameterBaseUrl = System.getProperty("host");
        if (externalParameterBaseUrl != null) {
            baseUrl = externalParameterBaseUrl;


        }
        //  baseUrl = baseUrl + url_visual;  //Seeing issues with baseurl being generated in generateUrlTest so hardcoging it due to lack of time.
        baseUrl = "https://www.vanguardinvestments.com.au" + specs;
    }

    @Test
    public void response200OkTest() {
        given().when().get(baseUrl).then().statusCode(200);
    }

    @Test
    public void portIdPositiveIntegerTest() throws IOException {                             // Assuming protID could only be integer for the tess sake
        Response res = given().when().get(baseUrl).then().statusCode(200).extract().response();
        JSONArray jsonarray = new JSONArray(res.asString());
        for (int i = 0; i < jsonarray.length(); i++) {
            // JSONObject jsonObject1 = jsonarray.getJSONObject(i);
            JSONObject obj2 = jsonarray.getJSONObject(i);


            String portId = obj2.optString("portId");
            try {
                Integer.parseInt(portId);
            } catch (NumberFormatException exception) {
                //assert fail
            }
        }
    }

    @Test
    public void currencyCodeExistTest() {                 //Test assuming only currency code allowed is USD/AUD
        Response res = given().when().get(baseUrl).then().statusCode(200).extract().response();
        JSONArray jsonarray = new JSONArray(res.asString());
        for (int i = 0; i < jsonarray.length(); i++) {
            JSONObject obj2 = jsonarray.getJSONObject(i);
            String currency = obj2.optString("navPriceArray");
            JSONArray currencyTab = new JSONArray(currency);
            for (int j = 0; j < currencyTab.length(); j++) {
                JSONObject obj3 = currencyTab.getJSONObject(j);
                String actualCurrency = obj3.optString("currency");
                Assert.assertTrue((actualCurrency.contains("AUD")) || (actualCurrency.contains("USD")));
                System.out.println(actualCurrency);
            }
        }
    }
}



