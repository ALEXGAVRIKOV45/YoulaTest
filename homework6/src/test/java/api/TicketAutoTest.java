package api;

import framework.AssertReflection;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import model.Status;
import model.Ticket;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TicketAutoTest {

    private static final String BASE_TEST_CLASS = "api.BaseTest";
//        private static final String BASE_TEST_CLASS = "api.BaseAutoTest";
    private static final String UPDATE_TICKET_TEST_CLASS = "api.UpdateTicketTest";
//        private static final String UPDATE_TICKET_TEST_CLASS = "api.UpdateTicketAutoTest";
    private static final String CREATE_TICKET_TEST_CLASS = "api.CreateTicketTest";
//        private static final String CREATE_TICKET_TEST_CLASS = "api.CreateTicketAutoTest";

    @BeforeClass
    public void prepare() throws IOException {
        System.getProperties().load(ClassLoader.getSystemResourceAsStream("config.properties"));
        String baseUri = System.getProperty("base.uri");
        Assert.assertFalse(baseUri == null || baseUri.isEmpty(), "Отсутствует значение base.uri в configuration.properties");
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setBaseUri(System.getProperty("base.uri"))
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
        RestAssured.filters(new ResponseLoggingFilter());
    }

    @Test
    public void createTicketTest() {
        try {
            Method buildNewTicketMethod = AssertReflection.shouldHaveAndGetMethod(Class.forName(BASE_TEST_CLASS), "buildNewTicket", Status.class, int.class);
            Object createTicketTest = Class.forName(CREATE_TICKET_TEST_CLASS).getDeclaredConstructor().newInstance();
            Ticket ticket = (Ticket) buildNewTicketMethod.invoke(createTicketTest, Status.OPEN, 1);

            Method createTicketMethod = AssertReflection.shouldHaveAndGetMethod(Class.forName(BASE_TEST_CLASS), "createTicket", Ticket.class);
            Ticket newTicket = (Ticket) createTicketMethod.invoke(createTicketTest, ticket);
            assertEquals(newTicket, ticket, "Созданный объект не совпадает с исходным");

            Method getIdMethod = AssertReflection.shouldHaveAndGetMethod(Ticket.class, "getId");
            int id = (int) getIdMethod.invoke(newTicket);
            Method getTicketMethod = AssertReflection.shouldHaveAndGetMethod(Class.forName(CREATE_TICKET_TEST_CLASS), "getTicket", int.class);
            getTicketMethod.setAccessible(true);
            Ticket actual = (Ticket) getTicketMethod.invoke(createTicketTest, id);
            assertEquals(actual, ticket, "Полученный по id объект не совпадает с созданным");
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException | ClassNotFoundException e) {
            throw new AssertionError("Ошибка: " + e.getMessage(), e);
        }
    }

    @Test
    public void updateTicketTest() {
        try {
            Method buildNewTicketMethod = AssertReflection.shouldHaveAndGetMethod(Class.forName(BASE_TEST_CLASS), "buildNewTicket", Status.class, int.class);
            Object updateTicketTest = Class.forName(UPDATE_TICKET_TEST_CLASS).getDeclaredConstructor().newInstance();
            Ticket ticket = (Ticket) buildNewTicketMethod.invoke(updateTicketTest, Status.CLOSED, 3);

            Method createTicketMethod = AssertReflection.shouldHaveAndGetMethod(Class.forName(BASE_TEST_CLASS), "createTicket", Ticket.class);
            Ticket newTicket = (Ticket) createTicketMethod.invoke(updateTicketTest, ticket);
            assertEquals(newTicket, ticket, "Созданный объект не совпадает с исходным");

            Method setStatus = AssertReflection.shouldHaveAndGetMethod(Ticket.class, "setStatus", int.class);
            setStatus.invoke(newTicket, Status.OPEN.getCode());

            Method updateTicketNegative = AssertReflection.shouldHaveAndGetMethod(Class.forName(UPDATE_TICKET_TEST_CLASS), "updateTicketNegative", Ticket.class);
            updateTicketNegative.setAccessible(true);
            try {
                updateTicketNegative.invoke(updateTicketTest, newTicket);
            } catch (InvocationTargetException e) {
                if (e.getCause() instanceof AssertionError)
                    return;
            }
            Assert.fail("Тест должен находить дефект");
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException | ClassNotFoundException e) {
            throw new AssertionError("Ошибка: " + e.getMessage(), e);
        }
    }

    private void assertEquals(Ticket ticket1, Ticket ticket2, String text) throws InvocationTargetException, IllegalAccessException {
        try {
            Method getTitle = AssertReflection.shouldHaveAndGetMethod(Ticket.class, "getTitle");
            Assert.assertEquals(getTitle.invoke(ticket1), getTitle.invoke(ticket2));
            Method getQueue = AssertReflection.shouldHaveAndGetMethod(Ticket.class, "getQueue");
            Assert.assertEquals(getQueue.invoke(ticket1), getQueue.invoke(ticket2));
            Method getPriority = AssertReflection.shouldHaveAndGetMethod(Ticket.class, "getPriority");
            Assert.assertEquals(getPriority.invoke(ticket1), getPriority.invoke(ticket2));
        } catch (AssertionError e) {
            throw new AssertionError(text + ": " + e.getMessage(), e);
        }
    }
}
