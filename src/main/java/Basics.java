import files.*;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import static org.hamcrest.Matchers.equalTo;

public class Basics {

    public static void main (String args[]){
    RestAssured.baseURI="https://rahulshettyacademy.com";
    String postResponse = RestAssured.given().log().all().queryParam("key","qaclick123")
            .header("Content-Type","application/json")
            .body(payload.addPlace())
            .when().post("maps/api/place/add/json")
            .then().assertThat().statusCode(200).body("scope", equalTo("APP"))
            .header("server","Apache/2.4.41 (Ubuntu)").extract().asString();
            JsonPath js = new JsonPath(postResponse);
            String place_id = js.getString("place_id");


            String newAdress = "Bloco 3, Terceiro andar";
            RestAssured.given().log().all().queryParam("key","qaclick123")
                    .header("Content-Type","application/json")
                    .body(payload.updatePlace(place_id,newAdress))
                    .when().put("maps/api/place/update/json")
                    .then().assertThat().log().all().statusCode(200).body("msg",equalTo("Address successfully updated"));

            String getResponse = RestAssured.given().log().all().queryParam("key","qaclick123")
                    .queryParam("place_id",place_id)
                    .when().get("maps/api/place/get/json")
                    .then().assertThat().log().all().statusCode(200).extract().asString();
                js = new JsonPath(getResponse);
                String actualAdress = js.getString("address");
                Assert.assertEquals(actualAdress,newAdress);
    }
}
