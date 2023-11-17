package CI_Tests;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@RunWith(SpringRunner.class)
public class test1 {

    @LocalServerPort
    int port;

    @Before
    public void setUp(){
        RestAssured.port =port;
        RestAssured.baseURI = "http://localhsot";
    }

    @Test
    public void testChat(){

        Response response = RestAssured.given().
                header("Content-Type", "JSON").
                header("charset", "utf-8").
                body("{  \"name\": \"chat1\"}").
                when().
                post("/chats");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();
        try{
            JSONArray returnArr = new JSONArray(returnString);
            JSONObject returnObj = returnArr.getJSONObject(returnArr.length()-1);
            assertEquals("{\"message\":\"success\"}", returnObj.get("data"));
        } catch (JSONException e){
            e.printStackTrace();
        }
    }


}
