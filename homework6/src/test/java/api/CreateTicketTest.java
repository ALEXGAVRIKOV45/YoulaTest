package api;

import model.Status;
import model.Ticket;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

/** Для отладки, не участвует в автопрвоерке */
public class CreateTicketTest extends BaseTest {

    @Test(enabled = false)
    public void createTicketTest() {
        Ticket ticket = buildNewTicket(Status.OPEN, 1);
        Ticket newTicket = createTicket(ticket);
        Assert.assertEquals(newTicket, ticket);
        Ticket actual = getTicket(newTicket.getId());
        Assert.assertEquals(actual, ticket);
    }

    protected Ticket getTicket(int id) {
        return given()
                .pathParam("id", id)
                .when()
                .get("/api/tickets/{id}")
                .then()
                .statusCode(200)
                .extract().body()
                .as(Ticket.class);
    }
}
