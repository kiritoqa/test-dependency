import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

import static com.codeborne.selenide.Selenide.$;

public class BaseClass {
    public static ThreadLocal<WebDriver> tdriver = new ThreadLocal<WebDriver>();

    public WebDriver initialize_driver(int width, int height) throws MalformedURLException{
        final String url = "http://localhost:4444/wd/hub";
        WebDriver driver = new RemoteWebDriver(new URL(url), DesiredCapabilities.chrome());
        driver.manage().window().setSize(new Dimension(width,height));
        WebDriverRunner.setWebDriver(driver);
        tdriver.set(driver);
        return getDriver();
    }

    public static synchronized WebDriver getDriver() {
        return tdriver.get();
    }

    @Step("switch to new Windows")
    public void switchNewTab(){
        Selenide.switchTo().window(1);
        Selenide.sleep(5000);
    }

    @Step("close actual Windows")
    public void closeWindows(){
        WebDriverRunner.getWebDriver().close();
        Selenide.switchTo().window(0);
    }

    public String offsetTop(By firstElement, By secondElement){
        return String.valueOf($(secondElement).getRect().getY()-$(firstElement).getRect().getY()-$(firstElement).getRect().getHeight());
    }

    public String offsetRight(By firstElement, By secondElement){
        return String.valueOf($(secondElement).getRect().getX()-$(firstElement).getRect().getX()-$(firstElement).getRect().getWidth());
    }
    @Step("get Title")
    public String getTitle(){
        return Selenide.title();
    }

    public String getPoint(By locator) {
        return String.valueOf($(locator).getRect().getPoint());
    }

    @Step("get {1}")
    public String getCssValue(By locator, String cssValue) {
        return  $(locator).getCssValue(cssValue);
    }

    @Step("get Screen")
    public void getScreen(String name){
        try {
            Allure.addAttachment(name, FileUtils.openInputStream(new File(Objects.requireNonNull(Selenide.screenshot(name+"png")))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getEnvironment(String env){
        String soft = "";
        if (env.equals("PC")){
            soft = "expectedPc";}
        if (env.equals("Tablet")){
            soft = "expectedTablet"; }
        if (env.equals("Mobile")){
            soft = "expectedMobile";}
        return soft;
    }
}
