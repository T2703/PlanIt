package planIT;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import planIT.Entity.Messages.Message;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.web.server.LocalServerPort;	// SBv3

import java.util.Date;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class MessageSystemTest {

    @LocalServerPort
    int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    public void testA(){
        //create message
        Message msg = new Message("testUser", new Date(), new Date());
        Response response1 = RestAssured.given().
                contentType("application/json").
                body(msg).
                when().
                post("/messages");
        int statusCode1 = response1.getStatusCode();
        assertEquals(200, statusCode1);
    }

    @Test
    public void testB(){
        //update message
        Message msg1 = new Message("testUser_updated", new Date(), new Date());
        Response response1 = RestAssured.given().
                contentType("application/json").
                body(msg1).
                when().
                put("/messages/1");
        int statusCode1 = response1.getStatusCode();
        assertEquals(200, statusCode1);
    }

    @Test
    public void testC(){
        Response response1 = RestAssured.given().
                when().
                get("/messages");
        int statusCode1 = response1.getStatusCode();
        assertEquals(200, statusCode1);

        Response response2 = RestAssured.given().
                when().
                get("/messages/1");
        int statusCode2 = response2.getStatusCode();
        assertEquals(200, statusCode2);
    }

    @Test
    public void testD(){
        //DELETE MESSAGE
        Response response1 = RestAssured.given().
                when().
                delete("/messages/1");
        int statusCode1 = response1.getStatusCode();
        assertEquals(200, statusCode1);
    }

    @Test
    public void testE(){
        Message msg1 = new Message("testUser_updated_again", new Date(), new Date());
        Response response1 = RestAssured.given().
                contentType("application/json").
                body(msg1).
                when().
                put("/messages/69");
        int statusCode1 = response1.getStatusCode();
        assertEquals(200, statusCode1);
    }
}
