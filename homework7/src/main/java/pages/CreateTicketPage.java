package pages;

import models.Ticket;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class CreateTicketPage extends SendboxBasePage {

    @FindBy(xpath = "//select[@name='queue']")
    private WebElement newTicketQueue;

    @FindBy(xpath = "//input[@name='title']")
    private WebElement inputProblem;

    @FindBy(xpath = "//textarea[@name='body']")
    private WebElement inputProblemDescription;

    @FindBy(xpath = "//select[@name='priority']")
    private WebElement inputPriority;

    @FindBy(xpath = "//input[@name='due_date']")
    private WebElement inputDate;

    @FindBy(xpath = "//input[@id='id_submitter_email']")
    private WebElement inputMail;

    @FindBy(xpath = "//button[normalize-space()='Submit Ticket']")
    private WebElement createTicket;

    public CreateTicketPage() {
        PageFactory.initElements(driver, this);
    }


    /**
     * Создание тикета
     * @param ticket
     */
    public void createTicket(Ticket ticket) {
        System.out.println("Создаём тикет");
        setInputQueue(ticket.getQueue());
        setInputProblem(ticket.getTitle());
        setInputProblemDescription(ticket.getDescription());
        setInputPriority(ticket.getPriority());
        setInputDate(ticket.getStringDate());
        setInputMail(ticket.getMail());
        createTicket();
    }

    public void setInputProblem(String text) {
        System.out.printf("Вводим имя проблемы '%s'\n", text);
        inputProblem.sendKeys(text);
    }

    public void setInputQueue(String text) {
        System.out.printf("Выбираем имя очереди '%s'\n", text);
        setOption(newTicketQueue, text);
    }

    public void setInputProblemDescription(String text) {
        System.out.printf("Вводим описание проблемы '%s'\n", text);
        inputProblemDescription.sendKeys(text);
    }

    public void setInputPriority(String text) {
        System.out.printf("Выбираем приоритетность '%s'\n", text);
        setOption(inputPriority, text);
    }

    public void setInputDate(String text) {
        System.out.printf("Вводим дату возникновения проблемы '%s'\n", text);
        inputDate.sendKeys(text);
    }

    public void setInputMail(String text) {
        System.out.printf("Вводим адрес почты '%s'\n", text);
        inputMail.sendKeys(text);
    }

    public void createTicket() {
        System.out.println("Нажимаем кнопку создания тикета");
        createTicket.click();
    }

    private void setOption(WebElement selector, String option){
        new Select(selector).selectByVisibleText(option);
    }

}
