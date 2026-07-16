package Pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class Contacts {
    private final SelenideElement contactsTitle = $(".ContactSection__title"),
            contactsNumber = $(".ContactSection__link--tel"),
            contactsEmail = $(".ContactSection__link--email"),
            contactsMap = $(".ContactSection__map_container");

    public Contacts checkContactsPageOpen() {
        contactsTitle.shouldHave(text("Контакты"));
        return this;
    }

    @Step("Проверить номер, почту и карту")
    public void checkNumberEmailMap(String number, String email) {
        contactsNumber.shouldHave(text(number));
        contactsEmail.shouldHave(text(email));
        contactsMap.shouldBe(visible);
    }
}
