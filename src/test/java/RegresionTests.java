import com.codeborne.selenide.Selenide;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.*;
import widgets.BasePage;
import widgets.MainPage;

import java.net.MalformedURLException;
import java.util.Map;

import static com.codeborne.selenide.Selenide.$;
import static io.qameta.allure.Allure.step;

@Listeners({TestAllureListener.class})
public class RegresionTests extends BaseClass {
    BasePage base = new BasePage();
    MainPage main = new MainPage();
    String env;

    @Parameters({"test"})
    @BeforeTest()
    public void beforeTest(String test) {
        System.out.println(test);
    }

    @Parameters({"app", "width", "height"})
    @BeforeClass(description = "Environment Configs")
    @Step("Application: {0} Environment: {1}  EnvironmentSize: {2} ")
    public void beforeClass(String app, int width, int height) throws MalformedURLException {
        env = app;
        initialize_driver(width,height); }

    @BeforeMethod(description = "Precondition")
    void beforeMethod() {
        base.open(); }

    @AfterClass(description = "Close Browser")
    void afterClass() {
        Selenide.closeWindow();
    }

    @Test(dataProvider = "csvReader", dataProviderClass = CsvDataProviders.class)
    void checkPositionOfElements(Map<String, String> testData) {
        String element;
        AllureLifecycle lifecycle = Allure.getLifecycle();
        String webElement = testData.get("webElement");
        String mobElement = testData.get("mobElement");
        String expectedResult = testData.get(getEnvironment(env));
        if (env.equals("Tablet")||env.equals("Mobile")){
            element = mobElement;
        } else {element = webElement;}
        String error = "Incorrect point for " + element + " on NavBar";
        lifecycle.updateTestCase(testResult -> testResult.setName("Check position for " + element));
        step("Check position for " + element, () -> {
            Assert.assertEquals(getPoint(main.getElement(element)), expectedResult, error);
        });
    }

    @Test(dataProvider = "csvReader", dataProviderClass = CsvDataProviders.class)
    void layOutTop(Map<String, String> testData) {
        AllureLifecycle lifecycle = Allure.getLifecycle();
        String element1 = testData.get("element1");
        String element2 = testData.get("element2");
        String expectedResult = testData.get(getEnvironment(env));
        String error = "Incorrect distance between "+element1+" and "+element2+" on Contact Us Page";
        lifecycle.updateTestCase(testResult -> testResult.setName("Check distance between "+element1+" and "+element2));
        step("Check distance between " +element1+" and " +element2, () -> {
            Assert.assertEquals(offsetTop(main.getElement(element1), main.getElement(element2)), expectedResult, error);
        });
    }

    @Test(dataProvider = "csvReader", dataProviderClass = CsvDataProviders.class)
    void layOutRight(Map<String, String> testData) {
        AllureLifecycle lifecycle = Allure.getLifecycle();
        String element1 = testData.get("element1");
        String element2 = testData.get("element2");
        String expectedResult = testData.get(getEnvironment(env));
        String error = "Incorrect distance between "+element1+" and "+element2+" on Contact Us Page";
        lifecycle.updateTestCase(testResult -> testResult.setName("Check distance between "+element1+" and "+element2));
        step("Check distance between " +element1+" and " +element2, () -> {
            Assert.assertEquals(offsetRight(main.getElement(element1), main.getElement(element2)), expectedResult, error);
        });
    }

    @Test(dataProvider = "csvReader", dataProviderClass = CsvDataProviders.class)
    void openPage(Map<String, String> testData) {
        String element;
        AllureLifecycle lifecycle = Allure.getLifecycle();
        String webElement = testData.get("webElement");
        String mobElement = testData.get("mobElement");
        String switchTab = testData.get("switchTab");
        String page = testData.get("page");
        String title = testData.get("title");
        String error = "Incorrect title on "+page+" Page";
        lifecycle.updateTestCase(testResult -> testResult.setName("Open "+page+ " Page"));
        if (env.equals("Tablet")||env.equals("Mobile")){
            element = mobElement;
        } else {element = webElement;}
        step("click on element " +element, () ->  $(main.getElement(element)).click());
        if (switchTab.equals("yes")){
            switchNewTab();}
        step("Title " +getTitle(), () -> Assert.assertEquals(getTitle(), title, error));
        if (switchTab.equals("yes")){
            closeWindows();} }
}
