package Pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;

import static Pages.Components.pageTitle;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchResults {

    private final SelenideElement rangeOfCostInFiltersSelector = $("[name='rangePrice']").parent(),
            tagsOfSelectedFilters = $(".SelectedFilters__tags"),
            quantityOfRooms = $(".FiltersSection__field--room");

    private final ElementsCollection cardsInformationInSearchResult = $$(".RealEstateCardSkeleton__information");

    Components components = new Components();

    public SearchResults checkSearchResultPageOpen() {
        $(pageTitle).shouldHave(text("Квартиры в новостройке"));
        return this;
    }

    public SearchResults checkPurchaseChosen() {
        components.checkPurchaseChosen();
        return this;
    }

    public SearchResults checkNewBuildingsChosen() {
        components.checkNewBuildingsChosen();
        return this;
    }

    @Step("Проверить кол-во комнат")
    public SearchResults checkQuantityOfRooms(String value) {
        quantityOfRooms.shouldHave(text(value));
        return this;
    }

    @Step("Проверить диапазон цены в фильтрах")
    public SearchResults checkCostInFilters(String value) {
        rangeOfCostInFiltersSelector.shouldHave(text(value));
        return this;
    }

    @Step("Проверить содержание выбранных фильтров")
    public SearchResults checkTextSelectedFilters(String value) {
        tagsOfSelectedFilters.shouldHave(text(value));
        return this;
    }

    @Step("Проверить путь до метро в карточках недвижимости")
    public SearchResults checkDistanceToSubwayInCards(int value) {
        for (SelenideElement element : cardsInformationInSearchResult) {
            String text = element.$(".Subway").$(withText("мин")).getText();
            int minutes = Integer.parseInt(text.split(" ")[0]);
            assertTrue(minutes <= value);
        }
        return this;
    }

    @Step("Проверить стоимость в карточках недвижимости")
    public SearchResults checkCostInCards(int min, int max) {
        for (SelenideElement element : cardsInformationInSearchResult) {
            String textWithCost = element.$(".ComplexCard__accordion_title").getText();
            textWithCost = textWithCost.replace(",", ".");
            double cost = Double.parseDouble(textWithCost.split(" ")[1]);
            assertTrue(min <= cost && cost <= max);
        }
        return this;
    }

    @Step("Проверить кол-во комнат, площадь и цену в карточках недвижимости")
    public SearchResults checkRoomsAreaCostInCards(int minRooms, int maxRooms, int minArea, int maxArea, int minCost, int maxCost) {
        for (SelenideElement element : cardsInformationInSearchResult) {
            element.$(".RealEstateCardSkeleton__accordion").scrollIntoView("{block: \"center\"}").hover().should(appear);
            var lines = element.$$(".ComplexCard__list_item:not([style='display: none;']) .ComplexCard__caption");
            for (SelenideElement line : lines) {
                line.shouldBe(visible).scrollIntoView("{block: \"center\"}");
                String text = line.getText();
                int rooms = Integer.parseInt(text.split(" ")[0].replace("-комн", ""));
                Assertions.assertTrue(minRooms <= rooms && rooms <= maxRooms);
                int area = Integer.parseInt(text.split(" ")[2]);
                Assertions.assertTrue(minArea <= area && area <= maxArea);
            }
            var prices = element.$$(".ComplexCard__list_item:not([style='display: none;']) .ComplexCard__link .ComplexCard__value");
            for (SelenideElement price : prices) {
                price.shouldBe(visible);
                String text = price.getText();
                double cost = Double.parseDouble(text.split(" ")[1].replace(",", "."));
                Assertions.assertTrue(minCost <= cost && cost <= maxCost);
            }
        }
        return this;
    }

    @Step("Проверить название метро в карточках")
    public SearchResults checkSubwayNameInCards(String value) {
        for (SelenideElement element : cardsInformationInSearchResult) {
            element.$(".Subway__names_title").shouldHave(text(value));
        }
        return this;
    }
}
