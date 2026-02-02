package pages;

import org.openqa.selenium.support.PageFactory;

public class MainPage extends SendboxBasePage {

    public MainPage() {
        PageFactory.initElements(driver, this);
    }

}
