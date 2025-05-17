package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    public WebDriver driver;

    public WebDriverWait wait;

    @FindBy(id = "user-name")
    WebElement username;

    @FindBy(id = "password")
    WebElement password;

    @FindBy(id = "login-button")
    WebElement loginButton;

    @FindBy(css = "[data-test='error']")
    WebElement errorMessage;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15), Duration.ofMillis(100));
        PageFactory.initElements(driver, this);
    }

    public void login(String uname, String pass) {
        username.sendKeys(uname);
        password.sendKeys(pass);
        loginButton.click();
    }

    public String getErrorMessage() {
        return errorMessage.getText();
    }
}
