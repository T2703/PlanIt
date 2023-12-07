package planIT;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import planIT.Entity.Chats.Chat;
import planIT.Entity.Teams.Team;
import planIT.Entity.Users.User;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class TeamSystemTest {

    @LocalServerPort
    int port;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @Before
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    public void teamTestA() {

        // Create a User to Use with Teams
        User r = new User("test", "password", "test@gmail.com");
        RestAssured.given().
                contentType("application/json").
                body(r).
                when().
                post("/users");

        // Get Teams Before Post Method
        Response response1 = RestAssured.given().
                when().
                get("users/test/teams");

        int statusCode1 = response1.getStatusCode();
        assertEquals(200, statusCode1);

        String body1 = response1.getBody().asString();
        try {
            JSONArray array = new JSONArray(body1);
            assertEquals(0, array.length());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Post Team To Database
        Team t = new Team("test", "description");
        Response response2 = RestAssured.given().
                contentType("application/json").
                body(t).
                when().
                post("/users/test/teams");

        int statusCode2 = response2.getStatusCode();
        assertEquals(200, statusCode2);

//        String body2 = response2.getBody().asString();
//        assertEquals(success, body2);

        // Get Teams After Post Method
        Response response3 = RestAssured.given().
                when().
                get("users/test/teams");

        int statusCode3 = response3.getStatusCode();
        assertEquals(200, statusCode3);

        String body3 = response3.getBody().asString();
        try {
            JSONArray array = new JSONArray(body3);
            assertEquals(1, array.length());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void teamTestB() {

        // Get the Teams Before Put Method
        Response response = RestAssured.given().
                when().
                get("/teams/2");

        JsonPath path = response.jsonPath();

        assertEquals("test", path.getString("name"));

        // Updates the Teams (Put)
        Team t = new Team("test-updated", "description");
        Response response1 = RestAssured.given().
                contentType("application/json").
                body(t).
                when().
                put("/teams/2");

        int statusCode1 = response1.getStatusCode();
        assertEquals(200, statusCode1);

        String body1 = response1.getBody().asString();
        assertEquals(success, body1);

        // Get the Teams After Put Method
        Response response2 = RestAssured.given().
                when().
                get("/teams/2");

        int statusCode2 = response2.getStatusCode();
        assertEquals(200, statusCode2);

        JsonPath path2 = response2.jsonPath();

        assertEquals("test-updated", path2.getString("name"));
    }


    ////////////// PIGGY  BACK
    @Test
    public void teamTestC(){
        //Create team chat
        Response response1 = RestAssured.given().
                contentType("application/json").
                when().
                post("/createTeamChat/2");
        int statusCode1 = response1.getStatusCode();
        assertEquals(200, statusCode1);

        Response response2 = RestAssured.given().
                when().
                get("/teams/2");
        int statusCode2 = response2.getStatusCode();
        assertEquals(200, statusCode2);
    }

    @Test
    public void teamTestD() {

        // Create a User to Add to Teams
        User r = new User("test-add", "password", "test@gmail.com");
        RestAssured.given().
                contentType("application/json").
                body(r).
                when().
                post("/users");

        // Check to See One User in Team
        Response response1 = RestAssured.given().
                when().
                get("/teams/2");

        JsonPath path1 = response1.jsonPath();

        assertEquals(1, path1.getList("users").size());

        // Add New User to Team
        Response response = RestAssured.given().
                when().
                put("teams/2/add-user/test-add");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String body = response.getBody().asString();
        assertEquals(success, body);

        // Check to See New User Is Added
        Response response2 = RestAssured.given().
                when().
                get("/teams/2");

        JsonPath path2 = response2.jsonPath();

        assertEquals(2, path2.getList("users").size());

        // Remove the New User
        Response response3 = RestAssured.given().
                when().
                put("teams/2/remove-user/test-add");

        int statusCode3 = response3.getStatusCode();
        assertEquals(200, statusCode3);

        String body3 = response3.getBody().asString();
        assertEquals(success, body3);

        // Remove the New User
        Response response4 = RestAssured.given().
                when().
                get("/teams/2");

        JsonPath path4 = response4.jsonPath();

        assertEquals(1, path4.getList("users").size());
    }

    @Test
    public void teamTestE() {

        // Should Return Null for the ToDos Does Not Exist
        Team t = new Team("test-updated", "description");
        Response response = RestAssured.given().
                contentType("application/json").
                body(t).
                when().
                put("/teams/3");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String body = response.getBody().asString();
        assertEquals(failure, body);
    }

    @Test
    public void teamTestF() {

        // Deletes Team From Database  ...also deletes the attached chat
        Response response = RestAssured.given().
                when().
                delete("users/test/teams/2");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String body = response.getBody().asString();
        assertEquals(success, body);

        // Get Teams After Delete Method
        Response response1 = RestAssured.given().
                when().
                get("users/test/teams");

        int statusCode1 = response1.getStatusCode();
        assertEquals(200, statusCode1);

        String body1 = response1.getBody().asString();
        try {
            JSONArray array = new JSONArray(body1);
            assertEquals(0, array.length());
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        // Delete team chat
//        Response response4 = RestAssured.given().
//                when().
//                delete("chats/2");
//        int statusCode4 = response4.getStatusCode();
//        assertEquals(200, statusCode4);


        // Get the "test" User ID
        Response response2 = RestAssured.given().
                when().
                get("/username/test");

        JsonPath path = response2.jsonPath();
        System.out.println("HERE>>>" +response2.getBody().asString()); //DEBUG

        // Delete the User From the ID
        RestAssured.given().
                when().
                delete("/users/" + path.getString("id"));

        // Get the "test" User ID
        Response response3 = RestAssured.given().
                when().
                get("/username/test-add");

        JsonPath path3 = response3.jsonPath();

        // Delete the User From the ID
        RestAssured.given().
                when().
                delete("/users/" + path3.getString("id"));

    }
}
