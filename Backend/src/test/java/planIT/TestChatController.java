package planIT;

// Import Local classes
import planIT.Entity.Chats.Chat;
import planIT.Entity.Messages.Message;
import planIT.Entity.Teams.Team;
import planIT.Entity.Users.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.springframework.boot.test.context.SpringBootTest;

//import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.boot.test.web.server.LocalServerPort;	// SBv3

import java.util.Date;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class TestChatController {

    @LocalServerPort
    int port;

    @Before
    public void setUp(){
        RestAssured.port =port;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    public void testCreateChat(){

        Chat chat = new Chat("chat1");
        Response response = RestAssured.given().
                contentType("application/json").
                body(chat).
                when().
                post("/chats");

        System.out.println(response);
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();
        assertEquals("{\"message\":\"success\"}", returnString);
    }


    @Test
    public void testCreateTeamChat(){

        //USER C
        User user1 = new User("A", "A", "A");
        Response response = RestAssured.given().
                contentType("application/json").
                body(user1).
                when().
                post("/users");
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        //TEAM 1
        Team team1 = new Team("team1", "team1");
        Response response2 =RestAssured.given().
                contentType("application/json").
                body(team1).
                when().
                post("/teams");
        statusCode = response2.getStatusCode();
        assertEquals(200, statusCode);

        //ADD USER A TO TEAM 1
        Response response3 =RestAssured.given().
                when().
                put("/teams/1/user/A");
        statusCode = response3.getStatusCode();
        assertEquals(200, statusCode);

        //CREATE TEAM 1 CHAT
        Chat chat1 = new Chat("team 1 chat");
        Response response4 =RestAssured.given().
                contentType("application/json").
                body(chat1).
                when().
                post("/chats/1/chat");
        statusCode = response4.getStatusCode();
        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();
        assertEquals("{\"message\":\"success\"}", returnString);
    }

    @Test
    public void testCreateMessageInChat(){

        Message msg1 = new Message("MESSAGE 1", new Date(), new Date());
        Response response =RestAssured.given().
                contentType("application/json").
                body(msg1).
                when().
                post("/chats/1/messages");
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();
        assertEquals("{\"message\":\"success\"}", returnString);
    }

    @Test
    public void testUpdateChat(){

        Chat chat2 = new Chat("Chat 1 Updated");
        Response response =RestAssured.given().
                contentType("application/json").
                body(chat2).
                when().
                put("/chats/1");
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);
    }

    @Test
    public void testAddUserToChat(){

        //USER B
        User user1 = new User("B", "B", "B");
        Response response = RestAssured.given().
                contentType("application/json").
                body(user1).
                when().
                post("/users");
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        //ADD USER B TO CHAT 1
        Response response3 =RestAssured.given().
                when().
                put("/chats/1/users/B");
        statusCode = response3.getStatusCode();
        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();
        assertEquals("{\"message\":\"success\"}", returnString);
    }

    @Test
    public void testRemoveUserFromChat(){

        //USER C
        User user1 = new User("C", "C", "C");
        Response response = RestAssured.given().
                contentType("application/json").
                body(user1).
                when().
                post("/users");
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        //ADD USER B TO CHAT 2
        Response response2 =RestAssured.given().
                when().
                put("/chats/1/users/C");
        statusCode = response2.getStatusCode();
        assertEquals(200, statusCode);

        //REMOVE USER B FROM CHAT 1
        Response response3 =RestAssured.given().
                when().
                delete("/chats/1/users/C");
        statusCode = response3.getStatusCode();
        assertEquals(200, statusCode);

        String returnString = response3.getBody().asString();
        assertEquals("{\"message\":\"success\"}", returnString);
    }

    @Test
    public void testDeleteChat(){

        Response response =RestAssured.given().
                when().
                delete("/chats/2");
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();
        assertEquals("{\"message\":\"success\"}", returnString);
    }

}
