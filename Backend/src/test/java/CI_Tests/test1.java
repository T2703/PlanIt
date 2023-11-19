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
    public void testCreateChat(){

        Response response = RestAssured.given().
                header("Content-Type", "JSON").
                header("charset", "utf-8").
                body("{\"name\": \"chat1\"}").
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

    @Test
    public void testCreateTeamChat(){ //TODO create team within test?
        Response response = RestAssured.given().
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
        Response response = RestAssured.given().
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

        Response response = RestAssured.given().
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
        Response response = RestAssured.given().
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
    public void TestDeleteChat(){

        Response response = RestAssured.given().
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

    @Test
    public void testRemoveUserFromChat(){

        Response response = RestAssured.given().
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

}
