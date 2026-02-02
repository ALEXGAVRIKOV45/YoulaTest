package pages;

import models.Ticket;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class SearchTicketPage extends SendboxBasePage {

    @FindBy(xpath = "//div[@class = 'tickettitle']/a")
    List<WebElement> ticketsHref;

    public SearchTicketPage() {
        PageFactory.initElements(driver, this);
    }

    /**
     * Ищем строку с и тикетом и нажимаем на нее
     * @param ticket
     */
    public void openTicket(Ticket ticket) {
        System.out.printf("Открываем тикет '%s'\n", ticket.getTitle());
        ticketsHref.stream()
                .filter(WebElement::isDisplayed)
                .filter(ticketHref -> ticketHref.getText().contains(ticket.getId()))
                .findFirst()
                .get()
                .click();
    }

}
