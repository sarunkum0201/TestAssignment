package pages;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.SessionStorage;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.LinkedList;
import java.util.List;

public class ProductsPage {

    public WebDriver driver;

    public WebDriverWait wait;

    WebStorage webStorage;
    LocalStorage local;
    SessionStorage sessionStorage;
    Cookie cookie;

    @FindBy(className = "app_logo")
    WebElement appLogo;

    @FindBy(className = "product_label")
    WebElement productLabel;

    @FindBy(id = "add-to-cart-sauce-labs-backpack")
    WebElement addToCartSauceLabsBackpack;

    @FindBy(id = "add-to-cart-sauce-labs-bike-light")
    WebElement addToCartSauceLabsBikeLight;

    @FindBy(className = "inventory_item_name")
    List<WebElement> inventoryItemNames;

    @FindBy(className = "inventory_item_price")
    List<WebElement> inventoryItemPrices;

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15), Duration.ofMillis(100));
        PageFactory.initElements(driver, this);
        webStorage = (WebStorage) new Augmenter().augment(driver);
        local = webStorage.getLocalStorage();
        sessionStorage = webStorage.getSessionStorage();
        cookie = driver.manage().getCookieNamed("cookieName");
    }

    public boolean isAppLogo() {
        return appLogo.isDisplayed();
    }

    public boolean isProductLabelPresent() {
        return productLabel.isDisplayed();
    }

    public String getCookieSessionUsername() {
        return driver.manage().getCookieNamed("session-username").getValue();
    }

    public void setLocalStorage(String key, String value) {
        local.setItem(key, value);
    }

    public String getLocalStorage(String key) {
        return local.getItem(key);
    }

    public void clickAddToCartSauceLabsBackpack() {
        addToCartSauceLabsBackpack.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
    }

    public void clickAddToCartSauceLabsBikeLight() {
        addToCartSauceLabsBikeLight.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
    }

    public List<WebElement> getInventoryItemNames() {
        return inventoryItemNames;
    }

    public List<String> getInventoryItemPrices() {
        List<String> itemPrices = new LinkedList<>();
        for (int i = 0; i < inventoryItemNames.size(); i++) {
            itemPrices.add(inventoryItemPrices.get(i).getText().substring(1));
        }
        return itemPrices;
    }

    public double getTotalPrice(List<String> itemPrices) {
        double totalPrice = 0;
        for (int i = 0; i < itemPrices.size(); i++) {
            totalPrice = totalPrice + Double.parseDouble(itemPrices.get(i));
        }
        return totalPrice;
    }
}
