package planIT;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import planIT.Entity.Chats.Chat;
import planIT.Entity.Messages.Message;
import planIT.Entity.Teams.Team;
import planIT.Entity.Users.User;
import planIT.Login.LoginRequest;
import planIT.Login.Password;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.web.server.LocalServerPort;	// SBv3

import java.util.Date;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class ChatSystemTest {

    @LocalServerPort
    int port;

    private String success = "{\"message\":\"success\"}";

    @Before
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    public void A_testCreateChat(){
        // Create a chat
        Chat chat1 = new Chat("chat1");
        Response response1 = RestAssured.given().
                contentType("application/json").
                body(chat1).
                when().
                post("/chats");
        int statusCode1 = response1.getStatusCode();
        assertEquals(200, statusCode1);
    }

    @Test
    public void B_testGetChats(){
        //get all chats
        Response response2 = RestAssured.given().
                when().
                get("/chats");
        int statusCode2 = response2.getStatusCode();
        assertEquals(200, statusCode2);

        //get chat #1
        Response response3 = RestAssured.given().
                when().
                get("/chats/1");
        int statusCode3 = response2.getStatusCode();
        assertEquals(200, statusCode3);
    }

    @Test
    public void C_testUpdateChat(){
        Chat chat2 = new Chat("chat1-updated");
        Response response4 = RestAssured.given().
                contentType("application/json").
                body(chat2).
                when().
                put("/chats/1");
        int statusCode4 = response4.getStatusCode();
        assertEquals(200, statusCode4);
    }

    @Test
    public void D_messageToChat(){
        //create msg1
        Message msg1 = new Message("msg1", new Date(), new Date());
        Response response5 = RestAssured.given().
                contentType("application/json").
                body(msg1).
                when().
                post("/chats/1/messages");
        int statusCode5 = response5.getStatusCode();
        assertEquals(200, statusCode5);

        Message msg2 = new Message("msg2", new Date(), new Date());
        Response response6 = RestAssured.given().
                contentType("application/json").
                body(msg2).
                when().
                post("/messages");
        int statusCode6 = response6.getStatusCode();
        assertEquals(200, statusCode6);

        Response response7 = RestAssured.given().
                when().
                put("/chats/1/messages/2");
        int statusCode7 = response7.getStatusCode();
        assertEquals(200, statusCode7);


        //DELETE CHAT
        Response response8 = RestAssured.given().
                when().
                delete("/chats/1");
        int statusCode8 = response8.getStatusCode();
        assertEquals(200, statusCode8);

//    }
//
//    @Test
//    public void createDeleteTeamChat(){
        //CREATE/DELETE TEAM CHAT
//        Team team1 = new Team();
//        Response response9 = RestAssured.given().
//                contentType("application/json").
//                body(team1).
//                when().
//                post("/teams");
//        int statusCode9 = response9.getStatusCode();
//        assertEquals(200, statusCode9);
//
//        Response response10 = RestAssured.given().
//                when().
//                post("/chats/1/chat");
//        int statusCode10 = response10.getStatusCode();
//        assertEquals(200, statusCode10);
    }
}
