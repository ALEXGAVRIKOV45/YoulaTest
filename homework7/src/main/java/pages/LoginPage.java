package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends SendboxBasePage {

    @FindBy(id = "username")
    private WebElement user;

    // Поиск элемента через аннотацию
    @FindBy(id = "password")
    private WebElement password;

    @FindBy(css = "[type='submit']")
    private WebElement loginBtn;

    public LoginPage() {
        PageFactory.initElements(driver, this);
    }

    /**
     * Авторизация пользователя
     * @param user логин пользователя
     * @param password пароль пользователя
     */
    public void login(String user, String password) {
        System.out.println("Проводим авторизацию пользователя");
        setUser(user);
        setPassword(password);
        clickLoginBtn();
    }

    private void setUser(String user){
        System.out.println("Вводим логин пользователя");
        this.user.sendKeys(user);
    }

    private void setPassword(String password){
        System.out.println("Вводим пароль пользователя");
        this.password.sendKeys(password);
    }

    private void clickLoginBtn(){
        System.out.println("Нажимаем кнопку авторизации");
        this.loginBtn.click();
    }
}
