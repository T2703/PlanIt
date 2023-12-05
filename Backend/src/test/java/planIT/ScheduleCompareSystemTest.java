package planIT;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import planIT.Entity.Events.Event;
import planIT.Entity.Teams.Team;
import planIT.Entity.Users.User;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.web.server.LocalServerPort;	// SBv3

import java.util.Date;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class ScheduleCompareSystemTest {

    @LocalServerPort
    int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    public void scTestA(){
        //SETUP
        User testUser = new User("testUser", "password", "test@gmail.com");
        Response response1 = RestAssured.given().
                contentType("application/json").
                body(testUser).
                when().
                post("/users");
        int statusCode1 = response1.getStatusCode();
        assertEquals(200, statusCode1);

        //Post new event
        Event event1 = new Event("event1", "desc1", "loc 1", "Type 1", new Date(), new Date());
        Response response2 = RestAssured.given().
                contentType("application/json").
                body(event1).
                when().
                post("/users/testUser/events");
        int statusCode2 = response2.getStatusCode();
        assertEquals(200, statusCode2);

        //Add user to team
        Team team1 = new Team("team1", "desc1");
        Response response3 = RestAssured.given().
                contentType("application/json").
                body(team1).
                when().
                post("/users/testUser/teams");
        int statusCode3 = response3.getStatusCode();
        assertEquals(200, statusCode3);

        Response response4 = RestAssured.given().
                when().
                get("/username/testUser");
        System.out.println("HERE>>>" +response4.getBody().asString()); //DEBUG
    }

    @Test
    public void scTestB() {
        //run schedule compare
        ///compareSchedule/{teamId}/dates"

        Date d1 = new Date();
        d1.setMinutes(d1.getMinutes()-30);
        Date d2 = new Date();
        d2.setMinutes(d2.getMinutes()+30);

        Event event2 = new Event("compareRange", "desc1", "loc 1", "Type 1", d1, d2);
        Response response3 = RestAssured.given().
                contentType("application/json").
                body(event2).
                when().
                get("/compareSchedule/1/dates");
        int statusCode3 = response3.getStatusCode();
        assertEquals(200, statusCode3);
        System.out.println(response3.getBody());

    }

    @Test
    public void scTestC(){
        Date d1 = new Date();
        d1.setMinutes(d1.getMinutes()-30);
        Date d2 = new Date();
        d2.setMinutes(d2.getMinutes()+30);

        Event event2 = new Event("compareRange", "desc1", "loc 1", "Type 1", d1, d2);

        //compareStandard
        Response response2 = RestAssured.given().
                contentType("application/json").
                body(event2).
                when().
                get("/compareStandard/1/dates");
        int statusCode2 = response2.getStatusCode();
        assertEquals(200, statusCode2);
        System.out.println(response2.getBody());

        //compareBy30
        Response response3 = RestAssured.given().
                contentType("application/json").
                body(event2).
                when().
                get("/compareBy30/1/dates");
        int statusCode3 = response3.getStatusCode();
        assertEquals(200, statusCode3);
        System.out.println(response3.getBody());
    }

    @Test
    public void scTestD() {
        //CLEANUP
        //DELETE TEAM
        Response response1= RestAssured.given().
                when().
                delete("/users/testUser/teams/1");
        int statusCode1 = response1.getStatusCode();
        assertEquals(200, statusCode1);

        //DELETE EVENT
        Response response2 = RestAssured.given().
                when().
                delete("/users/testUser/events/2");
        int statusCode2 = response2.getStatusCode();
        assertEquals(200, statusCode2);

        // Get the "test" User ID
        Response response3 = RestAssured.given().
                when().
                get("/username/testUser");
        int statusCode3 = response3.getStatusCode();
        assertEquals(200, statusCode3);



        JsonPath path1 = response3.jsonPath();
        // Delete the User From the ID
        Response response4 = RestAssured.given().
                when().
                delete("/users/" + path1.getString("id"));
        int statusCode4 = response4.getStatusCode();
        assertEquals(200, statusCode4);

    }


}
