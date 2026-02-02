package pages;

import elements.MainMenu;

public class SendboxBasePage extends AbstractPage {

    /**
     * Доступ к элементам главного меню
     */
    public MainMenu mainMenu() {
        return new MainMenu(driver);
    }
}
