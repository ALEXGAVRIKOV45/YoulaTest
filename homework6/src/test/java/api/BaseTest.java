package api;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import model.Status;
import model.Ticket;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static io.restassured.RestAssured.given;

public abstract class BaseTest {
    @BeforeClass
    public void prepare() throws IOException {
        System.getProperties().load(ClassLoader.getSystemResourceAsStream("config.properties"));
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setBaseUri(System.getProperty("base.uri"))
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
        RestAssured.filters(new ResponseLoggingFilter());
    }

    protected Ticket buildNewTicket(Status status, int priority) {
        Ticket ticket = new Ticket();
        ticket.setDue_date(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
        ticket.setTitle("Autoschool_" + UUID.randomUUID().toString());
        ticket.setCreated(ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT));
        ticket.setModified(ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT));
        ticket.setSubmitter_email("autoschool@example.com");
        ticket.setStatus(status.getCode());
        ticket.setOn_hold(false);
        ticket.setDescription("Autoschool_description");
        ticket.setResolution("Autoschool_resolution");
        ticket.setPriority(priority);
        ticket.setSecret_key("secret");
        ticket.setQueue(1);
        ticket.setAssigned_to(1);
        ticket.setKbitem(1);
        ticket.setMerged_to(1);
        return ticket;
    }

    protected Ticket createTicket(Ticket ticket) {
        return given()
                .body(ticket)
                .when()
                .post("/api/tickets")
                .then()
                .statusCode(201)
                .extract().body()
                .as(Ticket.class);
    }
}
