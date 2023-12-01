package planIT;

// Import Local classes
import planIT.Entity.Users.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.springframework.boot.test.context.SpringBootTest;

//import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.boot.test.web.server.LocalServerPort;	// SBv3

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class TestingSystemTest {

    @LocalServerPort
    int port;

    private String success = "{\"message\":\"success\"}";

    @Before
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    public void userTest() {
        //User r = new User("test", "password", "test@gmail.com");

        //System.out.println(r);
        // Send request and receive response
        Response response = RestAssured.given().
//                contentType("application/json").
//                body(r).
                when().
                get("/username/test");


        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        // Check response body for correct response
//        String returnString = response.getBody().asString();
//        try {
//            JSONTokener token = new JSONTokener(returnString);
//            JSONObject obj = new JSONObject(token);
//            assertEquals(success, obj.toString());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }

}
