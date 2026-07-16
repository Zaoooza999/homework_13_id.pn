package Pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class Components {

    public static final String newBuildsCards = ".ComplexCard",
            cardResaleTitle = ".RealEstateCardSkeleton__estate";

    private final SelenideElement rentOrBuyInput = $("[title='Купить'] span.SelectInput__text_input"),
            buildingTypeInput = $("[title='Квартиры в новостройке'] span.SelectInput__text_input");

    @Step("Проверить, что выбрана покупка жилья")
    public Components checkPurchaseChosen() {
        rentOrBuyInput.shouldHave(text("Купить"));
        return this;
    }

    @Step("Проверить, что выбраны новостройки")
    public Components checkNewBuildingsChosen() {
        buildingTypeInput.shouldHave(text("Квартиры в новостройке"));
        return this;
    }
}
