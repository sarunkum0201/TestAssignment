package Utils;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

public class CustomWebElement extends RemoteWebElement {
    private final WebElement element;

    public CustomWebElement(WebElement element) {
        this.element = element;
//        setParent(element.getWrappedDriver());
        setId(((RemoteWebElement) element).getId());
    }

    @Override
    public String getText() {
        String text = element.getText();
        // Modify or process the text as needed
        return text.toUpperCase(); // Example: Convert text to uppercase
    }
}