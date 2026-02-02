package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TicketPage extends SendboxBasePage {

    @FindBy(xpath = "//div[@class='card-body']//table/thead")
    WebElement ticketTitle;

    @FindBy(xpath = "//th[text()='Priority']")
    WebElement priority;

    @FindBy(xpath = "//th[text()='Submitter E-Mail']")
    WebElement email;

    @FindBy(id = "ticket-description")
    WebElement description;

    @FindBy(xpath = "//th[text()='Due Date']")
    WebElement dueDate;

    public TicketPage() {
        PageFactory.initElements(driver, this);
    }

    public String getTicketTitle() {
        System.out.println("Получаем имя");
        return ticketTitle.getText();
    }

    public String getTicketQueue() {
        System.out.println("Получаем очередь");
        return ticketTitle.getText();
    }

    public String getPriority() {
        System.out.println("Получаем приоритетность");
        return getValue(priority);
    }

    public String getEmail() {
        System.out.println("Получаем адрес почты");
        return getValue(email);
    }

    public String getDescription() {
        System.out.println("Получаем описание проблемы");
        return description
                .findElement(By.xpath("./p"))
                .getText()
                .trim();
    }

    private String getValue(WebElement columnElem){
        return columnElem
                .findElement(By.xpath("./following-sibling::td[1]"))
                .getText()
                .trim();
    }

}
