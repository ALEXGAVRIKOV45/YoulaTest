package elements;

import models.Ticket;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class MainMenu {

    private WebDriver driver;

    private By newTicket = By.xpath("//span[contains(text(),'New Ticket')]");

    private By logIn = By.id("userDropdown");

    private By search = By.xpath("//input[@class='form-control']");

    private By searchGo = By.xpath("//button[@class='btn btn-primary']");


    public MainMenu(WebDriver driver) {
        this.driver = driver;
    }

    public void newTicket() {
        System.out.println("Нажимаем кнопку создания нового тикета");
        new WebDriverWait(driver, 5)
                .until(visibilityOfElementLocated(newTicket)).click();
    }

    public void logIn() {
        System.out.println("Нажимаем кнопку авторизации");
        new WebDriverWait(driver, 5)
                .until(visibilityOfElementLocated(logIn)).click();
    }

    public void searchTicket(Ticket ticket) {
        System.out.printf("Ищем тикет с именем '%s'\n", ticket.getTitle());
        inputSearch(ticket.getTitle()).
                search();
    }

    /*
        Если мы предполагаем,что после вызова метода который ни чего не возвращает,
        нам может потребоваться вызов другого метода этого же класса,
        то мы можем вернуть сам класс и после вызова этого метода, вызвать следующий метод через точку.
     */
    public MainMenu inputSearch(String text) {
        System.out.printf("Вводим в поисковую строку значение %s\n", text);
        new WebDriverWait(driver, 5)
                .until(visibilityOfElementLocated(search)).sendKeys(text);
        return this;
    }

    public void search() {
        System.out.println("Нажимаем кнопку поиска");
        new WebDriverWait(driver, 5)
                .until(visibilityOfElementLocated(searchGo)).click();
    }



}
