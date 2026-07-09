import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Components {
    private String addToFavoritesOrCompareButton = "button.BaseButton__btn--prepend.BaseButton__btn",
            cardResaleTitle = ".RealEstateCardSkeleton__estate",
            cardNewBuildsTitle = ".ComplexCard__title";
    private String savedResaleTitle;
    private String savedNewBuildTitle1;
    private String savedNewBuildTitle2;
    private final SelenideElement searchButton = $(withText("Найти")),
            allFilters = $(withText("Все фильтры")),
            rentOrBuyInput = $("[title='Купить'] span.SelectInput__text_input"),
            buildingTypeInput = $("[title='Квартиры в новостройке'] span.SelectInput__text_input"),
            typeOfCostInput = $(".Modal__body .TabSelectInput__item.TabSelectInput__item--active"),
            minCostInput = $(".FullFormWidget__field--selected_price_type .RangeInput__text_input--left"),
            maxCostInput = $(".FullFormWidget__field--selected_price_type .RangeInput__text_input--right"),
            maxCostUnit = $(".FullFormWidget__field--selected_price_type .RangeInput__label--end .RangeInput__unit"),
            typeOfWayToSubway = $(withText("Пешком")),
            minAreaInput = $(".FullFormWidget__field--increased_indent .RangeInput__text_input--left"),
            maxAreaInput = $(".FullFormWidget__field--increased_indent .RangeInput__text_input--right"),
            submitButton = $(".Modal__body button[type='submit']"),
            quantityOfRooms = $(".FiltersSection__field--room"),
            rangeOfCostInFiltersSelector = $("[name='rangePrice']").parent(),
            tagsOfSelectedFilters = $(".SelectedFilters__tags"),
            contactsButton = $(withText("Контакты")),
            contactsTitle = $(".ContactSection__title"),
            contactsNumber = $(".ContactSection__link--tel"),
            contactsEmail = $(".ContactSection__link--email"),
            contactsMap = $(".ContactSection__map_container"),
            resaleApartmentsRecomendationFilter = $(".TagSelectInput.TagSelectInput--md.TagSelectInput--default.RealEstateSection__tags").$(withText("Вторичка")),
            resaleRecomendationFirstCard = $(".PropertyCard.RealEstateSection__secondary_card"),
            resaleRecomendationFirstCardTitle = resaleRecomendationFirstCard.$(cardResaleTitle),
            resaleRecomendationFirstCardAddToFavoritesButton = resaleRecomendationFirstCard.$(addToFavoritesOrCompareButton),
            favoritesButton = $("[href='/favorites']"),
            comparisonButton = $("[href='/comparison']"),
            favoritesPageTitle = $(".FavoritesPage__header"),
            comparisonPageTitle = $(".ComparisonPage__header");


    private final ElementsCollection newBuildsCards = $$(".ComplexCard");
    private final ElementsCollection cardsInformationInSearchResult = $$(".RealEstateCardSkeleton__information");


    @Step("Открыть страницу /")
    public Components openPage() {
        open("");
        return this;
    }

    @Step("Нажать найти")
    public Components clickSearch() {
        searchButton.click();
        return this;
    }

    @Step("Нажать Все фильтры")
    public Components clickAllFilters() {
        allFilters.click();
        return this;
    }

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

    @Step("Выбрать кол-во комнат")
    public Components chooseQuantityOfRooms(String value) {
        $(withText(value)).click();
        return this;
    }

    @Step("Проверить, что выбрана общая стоимость")
    public Components checkTotalCostChosen() {
        typeOfCostInput.shouldHave(text("Общая"));
        return this;
    }

    @Step("Указать минимальную стоимость")
    public Components setMinCost(int value) {
        minCostInput.setValue(String.valueOf(value));
        return this;
    }

    @Step("Указать максимальную стоимость")
    public Components setMaxCost(int value) {
        maxCostInput.setValue("0");
        maxCostUnit.shouldHave(text("млн"));
        maxCostInput.setValue(String.valueOf(value));
        return this;
    }

    @Step("Проверить, что выбрано расстояние до метро пешком")
    public Components checkWalkingDistanceToSubway() {
        typeOfWayToSubway.shouldHave(cssClass("TabSelectInput__item--active"));
        return this;
    }

    @Step("Указать расстояние до метро")
    public Components setDistanceToSubway(String value) {
        $(".Modal__body").$(withText(value)).scrollIntoView("{block: \"center\"}").click();
        return this;
    }

    @Step("Указать диапазон площади")
    public Components setArea(int min, int max) {
        minAreaInput.setValue(String.valueOf(min));
        maxAreaInput.setValue("0");
        maxAreaInput.shouldHave(value(String.valueOf(min)));
        maxAreaInput.setValue(String.valueOf(max));
        return this;
    }

    @Step("Указать опции этажей")
    public Components setOptionsOfFloors(String value) {
        $(".Modal__body").$(withText(value)).scrollIntoView("{block: \"center\"}").click();
        return this;
    }

    @Step("Завершить поиск")
    public Components submitSearch() {
        String submitButtonText = submitButton.getText();
        submitButton.shouldNotHave(exactText(submitButtonText));
        submitButton.shouldBe(enabled).click();
        return this;
    }

    @Step("Проверить кол-во комнат")
    public Components checkQuantityOfRooms(String value) {
        quantityOfRooms.shouldHave(text(value));
        return this;
    }

    @Step("Проверить диапазон цены в фильтрах")
    public Components checkCostInFilters(String value) {
        rangeOfCostInFiltersSelector.shouldHave(text(value));
        return this;
    }

    @Step("Проверить содержание выбранных фильтров")
    public Components checkTextSelectedFilters(String value) {
        tagsOfSelectedFilters.shouldHave(text(value));
        return this;
    }

    @Step("Проверить путь до метро в карточках недвижимости")
    public Components checkDistanceToSubwayInCards(int value) {
        for (SelenideElement element : cardsInformationInSearchResult) {
            String text = element.$(".Subway").$(withText("мин")).getText();
            int minutes = Integer.parseInt(text.split(" ")[0]);
            assertTrue(minutes <= value);
        }
        return this;
    }

    @Step("Проверить стоимость в карточках недвижимости")
    public Components checkCostInCards(int min, int max) {
        for (SelenideElement element : cardsInformationInSearchResult) {
            String textWithCost = element.$(".ComplexCard__accordion_title").getText();
            textWithCost = textWithCost.replace(",", ".");
            double cost = Double.parseDouble(textWithCost.split(" ")[1]);
            assertTrue(min <= cost && cost <= max);
        }
        return this;
    }

    @Step("Проверить кол-во комнат, площадь и цену в карточках недвижимости")
    public Components checkRoomsAreaCostInCards(int minRooms, int maxRooms, int minArea, int maxArea, int minCost, int maxCost) {
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

    @Step("Выбрать быстрый фильтр метро")
    public Components chooseQuickFilterMetro(String value) {
        $("[href='/buildings/search/params/metro_" + value + "']").click();
        return this;
    }

    @Step("Проверить название метро в карточках")
    public Components checkSubwayNameInCards(String value) {
        for (SelenideElement element : cardsInformationInSearchResult) {
            element.$(".Subway__names_title").shouldHave(text(value));
        }
        return this;
    }

    @Step("Выбрать вторичку")
    public Components chooseResaleApartments() {
        resaleApartmentsRecomendationFilter.click();
        return this;
    }

    @Step("Получить заголовок с карточки вторички")
    public Components saveResaleTitle() {
        savedResaleTitle = resaleRecomendationFirstCardTitle.should(appear).getText();
        return this;
    }

    @Step("Получить заголовок c карточки новостройки")
    public Components saveNewBuildsCardTitleByIndex(int index1, int index2) {
        savedNewBuildTitle1 = newBuildsCards.get(index1).$(cardNewBuildsTitle).should(appear).getText();
        savedNewBuildTitle2 = newBuildsCards.get(index2).$(cardNewBuildsTitle).should(appear).getText();
        return this;
    }

    @Step("Добавить карточку вторички в избранное")
    public Components addResaleToFavorites() {
        resaleRecomendationFirstCardAddToFavoritesButton.scrollIntoView("{block: \"center\"}").hover().should(appear).click();
        return this;
    }

    @Step("Добавить карточку новостройки в сравнение")
    public Components addNewBuildsToComparison(int index) {
        newBuildsCards.get(index).$(addToFavoritesOrCompareButton).scrollIntoView("{block: \"center\"}").hover().should(appear).click();
        return this;
    }

    @Step("Открыть избранное")
    public Components openFavorites() {
        favoritesButton.click();
        favoritesPageTitle.shouldHave(text("Избранное"));
        return this;
    }

    @Step("Открыть сравнение")
    public Components openComparison() {
        comparisonButton.click();
        comparisonPageTitle.should(appear).shouldHave(text("Сравнение"));
        return this;
    }

    @Step("Проверить заголовок вторички в избранном")
    public Components checkFavoritesResaleFirstCardTitle() {
        $(cardResaleTitle).shouldHave(text(savedResaleTitle));
        return this;
    }

    @Step("Проверить заголовок новостроек в сравнении")
    public Components checkNewBuildCardTitleInComparison() {
        newBuildsCards.get(0).shouldHave(text(savedNewBuildTitle1));
        newBuildsCards.get(1).shouldHave(text(savedNewBuildTitle2));
        return this;
    }

    @Step("Нажать контакты")
    public Components clickContacts() {
        contactsButton.click();
        return this;
    }

    @Step("Проверить номер, почту и карту")
    public Components checkNumberEmailMap(String number, String email) {
        contactsTitle.shouldHave(text("Контакты"));
        contactsNumber.shouldHave(text(number));
        contactsEmail.shouldHave(text(email));
        contactsMap.shouldBe(visible);
        return this;
    }
}