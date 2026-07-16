package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static pages.Components.pageTitle;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class Buildings {
    Components components = new Components();

    private final SelenideElement allFilters = $(withText("Все фильтры")),
            typeOfCostInput = $(".Modal__body .TabSelectInput__item.TabSelectInput__item--active"),
            minCostInput = $(".FullFormWidget__field--selected_price_type .RangeInput__text_input--left"),
            maxCostInput = $(".FullFormWidget__field--selected_price_type .RangeInput__text_input--right"),
            maxCostUnit = $(".FullFormWidget__field--selected_price_type .RangeInput__label--end .RangeInput__unit"),
            typeOfWayToSubway = $(withText("Пешком")),
            minAreaInput = $(".FullFormWidget__field--increased_indent .RangeInput__text_input--left"),
            maxAreaInput = $(".FullFormWidget__field--increased_indent .RangeInput__text_input--right"),
            submitButton = $(".Modal__body button[type='submit']");

    public Buildings checkBuildingsPageOpen() {
        $(pageTitle).shouldHave(text("Новостройки в Санкт-Петербурге и Ленинградской области"));
        return this;
    }

    public Buildings checkPurchaseChosen() {
        components.checkPurchaseChosen();
        return this;
    }

    @Step("Нажать Все фильтры")
    public Buildings clickAllFilters() {
        allFilters.should(appear).click();
        return this;
    }

    public Buildings checkNewBuildingsChosen() {
        components.checkNewBuildingsChosen();
        return this;
    }

    @Step("Выбрать кол-во комнат")
    public Buildings chooseQuantityOfRooms(String value) {
        $(withText(value)).click();
        return this;
    }

    @Step("Проверить, что выбрана общая стоимость")
    public Buildings checkTotalCostChosen() {
        typeOfCostInput.shouldHave(text("Общая"));
        return this;
    }

    @Step("Указать минимальную стоимость")
    public Buildings setMinCost(int value) {
        minCostInput.setValue(String.valueOf(value));
        return this;
    }

    @Step("Указать максимальную стоимость")
    public Buildings setMaxCost(int value) {
        maxCostInput.setValue("0");
        maxCostUnit.shouldHave(text("млн"));
        maxCostInput.setValue(String.valueOf(value));
        return this;
    }

    @Step("Проверить, что выбрано расстояние до метро пешком")
    public Buildings checkWalkingDistanceToSubway() {
        typeOfWayToSubway.shouldHave(cssClass("TabSelectInput__item--active"));
        return this;
    }

    @Step("Указать расстояние до метро")
    public Buildings setDistanceToSubway(String value) {
        $(".Modal__body").$(withText(value)).scrollIntoView("{block: \"center\"}").click();
        return this;
    }

    @Step("Указать диапазон площади")
    public Buildings setArea(int min, int max) {
        minAreaInput.setValue(String.valueOf(min));
        // Обнуляем MAX поле, чтобы UI переключил множитель с "тыс. кв.м" на "кв.м"
        maxAreaInput.setValue("0");
        // Ждем, пока UI перерисуется и синхронизирует поле с минимальным значением
        maxAreaInput.shouldHave(value(String.valueOf(min)));
        maxAreaInput.setValue(String.valueOf(max));
        return this;
    }

    @Step("Указать опции этажей")
    public Buildings setOptionsOfFloors(String value) {
        $(".Modal__body").$(withText(value)).scrollIntoView("{block: \"center\"}").click();
        return this;
    }

    @Step("Завершить поиск")
    public SearchResults submitSearch() {
        String submitButtonText = submitButton.shouldBe(visible).getText();
        submitButton.shouldNotHave(exactText(submitButtonText));
        submitButton.shouldBe(visible, enabled).click();
        SearchResults searchResults = new SearchResults();
        searchResults.checkSearchResultPageOpen();
        return searchResults;
    }
}