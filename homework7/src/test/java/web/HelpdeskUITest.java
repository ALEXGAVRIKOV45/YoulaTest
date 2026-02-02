package web;

import models.Ticket;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.StringContains;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class HelpdeskUITest {

    private WebDriver driver;

    @Before
    public void setup() throws IOException {
        // Читаем конфигурационный файл в System.properties
        System.getProperties().load(ClassLoader.getSystemResourceAsStream("config.properties"));
        // Создание экземпляра драйвера
        driver = new ChromeDriver();
        // Устанавливаем размер окна браузера, как максимально возможный
        driver.manage().window().maximize();
        // Установим время ожидания для поиска элементов
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        // Установить созданный драйвер для поиска в веб-страницах
        AbstractPage.setDriver(driver);
    }

    @After
    public void close(){
        driver.quit();
    }

    @Test
    public void createTicketTest() throws IOException {
        MainPage mainPage = new MainPage();

        Ticket ticket = buildNewTicket();

        driver.get(System.getProperty("site.url"));

        mainPage.mainMenu().newTicket();

        CreateTicketPage createTicketPage = new CreateTicketPage();
        createTicketPage.createTicket(ticket);

        createTicketPage.mainMenu().logIn();
        System.getProperties().load(ClassLoader.getSystemResourceAsStream("user.properties"));
        LoginPage loginPage = new LoginPage();
        loginPage.login(System.getProperty("user"), System.getProperty("password"));

        mainPage.mainMenu().searchTicket(ticket);
        SearchTicketPage searchTicketPage = new SearchTicketPage();
        searchTicketPage.openTicket(ticket);

        TicketPage ticketPage = new TicketPage();
        System.out.println("Проверяем имя тикета");
        MatcherAssert.assertThat("Имя тикета не соответствует", ticketPage.getTicketTitle(),
                new StringContains(ticket.getTitle()));
        System.out.println("Проверяем имя очереди");
        MatcherAssert.assertThat("Имя очереди не соответствует", ticketPage.getTicketQueue(),
                new StringContains(ticket.getQueue()));
        System.out.println("Проверяем приоритетность");
        Assert.assertEquals("Приоритетность проблемы не соответствует", ticket.getPriority(),
                ticketPage.getPriority());
        System.out.println("Проверяем адрес почты");
        Assert.assertEquals("Адрес почты не соответствует", ticket.getMail(),
                ticketPage.getEmail());
        System.out.println("Проверяем описание проблемы");
        Assert.assertEquals("Описание проблемы не соответствует", ticket.getDescription(),
                ticketPage.getDescription());


        //Закрываем текущее окно браузера
        driver.close();
    }


    protected Ticket buildNewTicket() {
        return new Ticket();
    }

}
