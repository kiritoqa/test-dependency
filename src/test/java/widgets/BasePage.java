package widgets;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Step;

public class BasePage{

    @Step("Open Main Page")
    public void open(){
        Selenide.open("http://cao.insomniac.world");
    }

}
