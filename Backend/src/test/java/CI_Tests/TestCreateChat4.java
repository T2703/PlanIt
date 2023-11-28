package CI_Tests;

// Import Local classes
import planIT.Entity.Chats.Chat;
import planIT.Entity.Users.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
import org.springframework.boot.test.web.server.LocalServerPort;	// SBv3

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class TestCreateChat4 {

    @LocalServerPort
    int port;

    @Before
    public void setUp(){
        RestAssured.port =port;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    public void testCreateChat(){

//        User r = new User("test", "password", "test@gmail.com");
//
//        System.out.println(r);
//        // Send request and receive response
//        Response response = RestAssured.given().
//                contentType("application/json").
//                body(r).
//                when().
//                post("/users");

        Chat chat = new Chat("chat1");
        //EncoderConfig encoderConfig = encoderConfig().encodeContentTypeAs("JSON", ContentType.TEXT);
        Response response = RestAssured.given().
                contentType("application/json").
                body(chat).
                when().
                post("/chats");

        System.out.println(response);
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

    @Test
    public void testChatResources(){
        //POST: 3 users, team with users 1&2

        //USER 1
        Response response = RestAssured.given().
                header("Content-Type", "JSON").
                header("charset", "utf-8").
                body("        \"username\": \"A\",\n" +
                        "        \"email\": \"A\",\n" +
                        "        \"password\": \"A\"").
                when().
                post("/users");
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        //USER 2
        Response response2 = given().
                header("Content-Type", "JSON").
                header("charset", "utf-8").
                body("        \"username\": \"B\",\n" +
                        "        \"email\": \"B\",\n" +
                        "        \"password\": \"B\"").
                when().
                post("/users");
        statusCode = response2.getStatusCode();
        assertEquals(200, statusCode);

        //USER 3
        Response response3 = given().
                header("Content-Type", "JSON").
                header("charset", "utf-8").
                body("        \"username\": \"C\",\n" +
                        "        \"email\": \"C\",\n" +
                        "        \"password\": \"C\"").
                when().
                post("/users");
        statusCode = response3.getStatusCode();
        assertEquals(200, statusCode);

        //TEAM 1
        Response response4 = given().
                header("Content-Type", "JSON").
                header("charset", "utf-8").
                body("        \"name\": \"team1\",\n" +
                        "        \"description\": \"TD\"").
                when().
                post("/teams");
        statusCode = response2.getStatusCode();
        assertEquals(200, statusCode);

        //ADD USER A TO TEAM 1
        Response response5 = given().
                header("Content-Type", "JSON").
                header("charset", "utf-8").
                //body().
                when().
                post("/teams/1/user/A");
        statusCode = response5.getStatusCode();
        assertEquals(200, statusCode);

        //ADD USER B TO TEAM 1
        Response response6 = given().
                header("Content-Type", "JSON").
                header("charset", "utf-8").
                //body().
                when().
                post("/teams/1/user/B");
        statusCode = response6.getStatusCode();
        assertEquals(200, statusCode);

    }


    @Test
    public void testCreateTeamChat(){ //TODO create team within test?
        Response response = given().
                header("Content-Type", "JSON").
                header("charset", "utf-8").
                body("{\"name\": \"teamChat1\"}").
                when().
                post("/chats/1/chat");

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

    @Test
    public void testCreateMessageInChat(){
        Response response = given().
                header("Content-Type", "JSON").
                header("charset", "utf-8").
                body("\"body\": \"MESSAGE 1\",\n" +
                        "\"sendTime\": null,\n" +
                        "\"receiveTime\": null").
                when().
                post("/chats/1/messages");

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

    @Test
    public void testUpdateChat(){

        Response response = given().
                header("Content-Type", "JSON").
                header("charset", "utf-8").
                body("{\"name\": \"chat1UPDATED\"}").
                when().
                put("/chats");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();
        try{
            JSONArray returnArr = new JSONArray(returnString);
            JSONObject returnObj = returnArr.getJSONObject(returnArr.length()-1);
            assertEquals("{\"name\": \"chat1UPDATED\"}", returnObj.get("data"));
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testAddUserToChat(){
        Response response = given().
                header("Content-Type", "JSON").
                header("charset", "utf-8").
                //body("{\"name\": \"chat1\"}").
                when().
                put("/chats/1/users/A"); //TODO update variables...make sure user exists...and the chat exists

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

    @Test
    public void testRemoveUserFromChat(){

        Response response = given().
                header("Content-Type", "JSON").
                header("charset", "utf-8").
                //body("{\"name\": \"chat1\"}").
                when().
                delete("/chats/1/users/A"); //TODO make sure chat and user exist

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

    @Test
    public void TestDeleteChat(){

        Response response = given().
                header("Content-Type", "JSON").
                header("charset", "utf-8").
                //body("{\"name\": \"chat1\"}").
                        when().
                delete("/chats/1");

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
