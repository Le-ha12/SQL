package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.data.DataHelper;


import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private final SelenideElement login = $("[data-test-id=login] input");
    private final SelenideElement password = $("[data-test-id=password] input");
    private final SelenideElement loginButton = $("[data-test-id=action-login]");
    private final SelenideElement errorNotification =  $("[data-test-id=error-notification]");

    public void verifyError(){ errorNotification.shouldHave(text("Ошибка! " + "Неверно указан логин или пароль"));
        errorNotification.shouldBe(visible);
    }

     public VerificationPage validLogin(DataHelper.AuthInfo info){
        login.setValue(info.getLogin());
        password.setValue(info.getPassword());
        loginButton.click();
        return new VerificationPage();
    }

}
