package web;

import framework.AssertReflection;
import models.Ticket;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.StringContains;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.AbstractPage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

public class HelpdeskUIAutoTest {

    private WebDriver driver;

    private static final String HELPDESK_UI_TEST_CLASS = "web.HelpdeskUITest";
    private static final String CREATE_TICKET_PAGE = "pages.CreateTicketPage";
    private static final String MAIN_MENU = "elements.MainMenu";
    private static final String LOGIN_PAGE = "pages.LoginPage";
    private static final String SEARCH_TICKET_PAGE = "pages.SearchTicketPage";
    private static final String TICKET_PAGE = "pages.TicketPage";


    @BeforeClass
    public static void prepare() throws IOException {
        System.getProperties().load(ClassLoader.getSystemResourceAsStream("config.properties"));
        System.getProperties().load(ClassLoader.getSystemResourceAsStream("user.properties"));
        String siteUrl = System.getProperty("site.url");
        Assert.assertFalse("Отсутствует значение site.url в configuration.properties", siteUrl == null || siteUrl.isEmpty());
        String webDriver = System.getProperty("webdriver.chrome.driver");
        Assert.assertFalse("Отсутствует значение driver в configuration.properties", webDriver == null || webDriver.isEmpty());
        String user = System.getProperty("user");
        Assert.assertFalse("Отсутствует значение user в user.properties", user == null || user.isEmpty());
        String password = System.getProperty("password");
        Assert.assertFalse("Отсутствует значение password в user.properties", password == null || password.isEmpty());
    }

    @Before
    public void setup() throws IOException {
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
    public void createTicketTest() {
        try {

            Method buildNewTicketMethod = AssertReflection.shouldHaveAndGetMethod(Class.forName(HELPDESK_UI_TEST_CLASS), "buildNewTicket");
            Object createTicketTest = Class.forName(HELPDESK_UI_TEST_CLASS).getDeclaredConstructor().newInstance();
            Ticket ticket = (Ticket) buildNewTicketMethod.invoke(createTicketTest);

            driver.get(System.getProperty("site.url"));

            Method newTicketMethod = AssertReflection.shouldHaveAndGetMethod(Class.forName(MAIN_MENU), "newTicket");
            Object mainMenu = Class.forName(MAIN_MENU).getDeclaredConstructor(WebDriver.class).newInstance(driver);
            newTicketMethod.invoke(mainMenu);

            Method createTicketMethod = AssertReflection.shouldHaveAndGetMethod(Class.forName(CREATE_TICKET_PAGE), "createTicket", Ticket.class);
            Object createTicketPage = Class.forName(CREATE_TICKET_PAGE).getDeclaredConstructor().newInstance();
            createTicketMethod.invoke(createTicketPage, ticket);

            Method logInMethod = AssertReflection.shouldHaveAndGetMethod(Class.forName(MAIN_MENU), "logIn");
            logInMethod.invoke(mainMenu);

            Method loginMethod = AssertReflection.shouldHaveAndGetMethod(Class.forName(LOGIN_PAGE), "login", String.class, String.class);
            Object loginPage = Class.forName(LOGIN_PAGE).getDeclaredConstructor().newInstance();
            loginMethod.invoke(loginPage, System.getProperty("user"), System.getProperty("password"));

            Method searchTicketMethod = AssertReflection.shouldHaveAndGetMethod(Class.forName(MAIN_MENU), "searchTicket", Ticket.class);
            searchTicketMethod.invoke(mainMenu, ticket);

            Method openTicketMethod = AssertReflection.shouldHaveAndGetMethod(Class.forName(SEARCH_TICKET_PAGE), "openTicket", Ticket.class);
            Object searchTicketPage = Class.forName(SEARCH_TICKET_PAGE).getDeclaredConstructor().newInstance();
            openTicketMethod.invoke(searchTicketPage, ticket);

            Method getTicketTitleMethod = AssertReflection.shouldHaveAndGetMethod(Class.forName(TICKET_PAGE), "getTicketTitle");
            Object ticketPage = Class.forName(TICKET_PAGE).getDeclaredConstructor().newInstance();
            MatcherAssert.assertThat("Имя тикета не соответствует", (String) getTicketTitleMethod.invoke(ticketPage),
                    new StringContains(ticket.getTitle()));

            Method getEmailMethod = AssertReflection.shouldHaveAndGetMethod(Class.forName(TICKET_PAGE), "getEmail");
            Assert.assertEquals("Адрес почты не соответствует", ticket.getMail(),
                    getEmailMethod.invoke(ticketPage));

        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException | ClassNotFoundException e) {
            throw new AssertionError("Ошибка: " + e.getMessage(), e);
        }
    }
}
