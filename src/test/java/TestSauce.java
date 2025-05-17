import Utils.CustomWebElement;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.LoginPage;
import pages.ProductsPage;

import java.util.List;

public class TestSauce {
    ChromeOptions options;
    WebDriver driver;
    LoginPage loginPage;
    ProductsPage productsPage;
    CustomWebElement customWebElement;

    final String LOCAL_STORAGE_BACKTRACE_LAST_ACTIVE_KEY = "backtrace-last-active";
    final String LOCAL_STORAGE_BACKTRACE_GUID_KEY = "backtrace-guid";
    final String LOCAL_STORAGE_MESSAGE1_KEY = "messgae1";
    final String LOCAL_STORAGE_MESSAGE1_VALUE = "Welcome to Swag Labs";
    final String LOCAL_STORAGE_CART_CONTENTS_KEY = "cart-contents";

    @BeforeMethod
    @Parameters({"url"})
    public void beforeTest(String url) {
        options = new ChromeOptions();
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        System.out.println("Launched browser");
        driver.get(url);
        driver.manage().window().maximize();
        loginPage = new LoginPage(driver);
        productsPage = new ProductsPage(driver);
    }

    @Test(dataProvider = "positive-data-provider")
    public void testSauceLogin1(String uname, String pass) throws InterruptedException {
        System.out.println("----------BASIC TASK----------");
        System.out.println("----------Positive Test Case----------");
        loginPage.login(uname, pass);
        Assert.assertTrue(productsPage.isAppLogo());
        System.out.println("Sauce valid login test gets success");
        String localStorageBacktraceLastActive = productsPage.getLocalStorage(LOCAL_STORAGE_BACKTRACE_LAST_ACTIVE_KEY);
        System.out.println("Local Storage Backtrace Last Active - " + localStorageBacktraceLastActive);
        String localStorageBacktraceGuid = productsPage.getLocalStorage(LOCAL_STORAGE_BACKTRACE_GUID_KEY);
        System.out.println("Local Storage Backtrace Guid - " + localStorageBacktraceGuid);
        productsPage.setLocalStorage(LOCAL_STORAGE_MESSAGE1_KEY, LOCAL_STORAGE_MESSAGE1_VALUE);
        String localStorageMessage1 = productsPage.getLocalStorage(LOCAL_STORAGE_MESSAGE1_KEY);
        System.out.println("Local Storage Message1 - " + localStorageMessage1);
        productsPage.clickAddToCartSauceLabsBackpack();
        String localStorageCartContents1 = productsPage.getLocalStorage(LOCAL_STORAGE_CART_CONTENTS_KEY);
        System.out.println("Local Storage Cart Contents - " + localStorageCartContents1);
        productsPage.clickAddToCartSauceLabsBikeLight();
        String localStorageCartContents2 = productsPage.getLocalStorage(LOCAL_STORAGE_CART_CONTENTS_KEY);
        System.out.println("Local Storage Cart Contents - " + localStorageCartContents2);
    }

    @Test(dataProvider = "negative-data-provider")
    public void testSauceLogin2(String uname, String pass, String errorMessage) {
        System.out.println("----------BASIC TASK----------");
        System.out.println("----------Negative Test Case----------");
        loginPage.login(uname, pass);
        Assert.assertTrue(loginPage.getErrorMessage().contains(errorMessage));
        System.out.println("Sauce invalid login test gets success");
    }

    @Test(dataProvider = "positive-data-provider")
    public void testAdvancedTask1(String uname, String pass) {
        System.out.println("----------ADVANCED TASK 1----------");
        loginPage.login(uname, pass);
        Assert.assertTrue(productsPage.isAppLogo());
        System.out.println("Sauce valid login test gets success");
        Assert.assertEquals(productsPage.getCookieSessionUsername(), uname);
        System.out.println("Asserted username with session username successfully");
    }

    @Test(dataProvider = "override-data-provider")
    public void testAdvancedTask2(String uname, String pass) {
        System.out.println("----------ADVANCED TASK 2----------");
        loginPage.login(uname, pass);
        Assert.assertTrue(productsPage.isAppLogo());
        System.out.println("Sauce valid login test gets success");
        for (WebElement element : productsPage.getInventoryItemNames()) {
            CustomWebElement customElement = new CustomWebElement(element);
            String modifiedText = customElement.getText();
            System.out.println("Override test - " + modifiedText);
        }
    }

    @AfterMethod
    public void afterTest() {
        driver.quit();
    }

    @DataProvider(name = "positive-data-provider")
    public Object[][] dpMethod1() {
        return new Object[][]{{"standard_user", "secret_sauce"}, {"problem_user", "secret_sauce"}, {"performance_glitch_user", "secret_sauce"}};
    }

    @DataProvider(name = "negative-data-provider")
    public Object[][] dpMethod2() {
        return new Object[][]{{"locked_out_user", "secret_sauce", "Sorry, this user has been locked out."},
                {"", "secret_sauce", "Username is required"}, {"standard_user", "", "Password is required"}, {"", "", "Username is required"},
                {"standard_user123", "secret_sauce", "Username and password do not match any user in this service"},
                {"standard_user", "secret_sauce123", "Username and password do not match any user in this service"}};
    }

    @DataProvider(name = "override-data-provider")
    public Object[][] dpMethod3() {
        return new Object[][]{{"standard_user", "secret_sauce"}};
    }
}
