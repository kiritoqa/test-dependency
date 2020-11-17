package widgets;

import org.openqa.selenium.By;

public class MainPage {
    public By getElement(String element) {
        String webElement = "";

        switch(element) {
            case "webelement":
                return By.cssSelector(webElement);
            default:
                return By.xpath("Element not found");}
    }
}
