package api;

import model.Status;
import model.Ticket;
import org.junit.Ignore;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

/** Для отладки, не участвует в автопрвоерке */
public class UpdateTicketTest extends BaseTest {

    @Test()
    public void updateTicketTest() {
        Ticket ticket = buildNewTicket(Status.CLOSED, 3);
        Ticket newTicket = createTicket(ticket);
        Assert.assertEquals(newTicket, ticket);
        newTicket.setStatus(Status.OPEN.getCode());
        updateTicketNegative(newTicket);
    }

    private void updateTicketNegative(Ticket ticket) {
        given()
                .body(ticket)
                .pathParam("id", ticket.getId())
                .when()
                .get("/api/tickets/{id}")
                .then()
                .statusCode(422);
    }
}
