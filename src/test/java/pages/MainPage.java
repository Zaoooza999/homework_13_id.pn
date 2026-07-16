package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static pages.Components.cardResaleTitle;
import static pages.Components.newBuildsCards;
import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class MainPage {
    private final String cardNewBuildsTitle = ".ComplexCard__title",
            addToFavoritesOrCompareButton = "button.BaseButton__btn--prepend.BaseButton__btn";

    private final SelenideElement searchButton = $(withText("Найти")),
            resaleApartmentsRecomendationFilter = $(".TagSelectInput.TagSelectInput--md.TagSelectInput--default.RealEstateSection__tags").$(withText("Вторичка")),
            resaleRecomendationFirstCard = $(".PropertyCard.RealEstateSection__secondary_card"),
            favoritesButton = $("[href='/favorites']"),
            resaleRecomendationFirstCardTitle = resaleRecomendationFirstCard.$(cardResaleTitle),
            resaleRecomendationFirstCardAddToFavoritesButton = resaleRecomendationFirstCard.$(addToFavoritesOrCompareButton),
            contactsButton = $(withText("Контакты")),
            comparisonButton = $("[href='/comparison']");

    @Step("Открыть страницу /")
    public MainPage openPage() {
        open("");
        return this;
    }

    @Step("Нажать найти")
    public Buildings clickSearch() {
        searchButton.should(appear).click();
        Buildings buildings = new Buildings();
        buildings.checkBuildingsPageOpen();
        return buildings;
    }

    @Step("Выбрать быстрый фильтр метро")
    public SearchResults chooseQuickFilterMetro(String value) {
        $("[href='/buildings/search/params/metro_" + value + "']").click();
        SearchResults searchResults = new SearchResults();
        searchResults.checkSearchResultPageOpen();
        return searchResults;
    }

    @Step("Выбрать вторичку")
    public MainPage chooseResaleApartments() {
        resaleApartmentsRecomendationFilter.click();
        return this;
    }

    @Step("Получить заголовок с карточки вторички")
    public String getResaleTitle() {
        return resaleRecomendationFirstCardTitle.should(appear).getText();
    }

    @Step("Добавить карточку вторички в избранное")
    public MainPage addResaleToFavorites() {
        resaleRecomendationFirstCardAddToFavoritesButton.scrollIntoView("{block: \"center\"}").hover().should(appear).click();
        return this;
    }

    @Step("Получить заголовок c карточки новостройки")
    public String getNewBuildsCardTitleByIndex(int index) {
        return $$(newBuildsCards).get(index).$(cardNewBuildsTitle).should(appear).getText();
    }

    @Step("Добавить карточку новостройки в сравнение")
    public MainPage addNewBuildsToComparison(int index) {
        $$(newBuildsCards).get(index).$(addToFavoritesOrCompareButton).scrollIntoView("{block: \"center\"}").hover().should(appear).click();
        return this;
    }

    @Step("Открыть избранное")
    public Favorites openFavorites() {
        favoritesButton.click();
        Favorites favorites = new Favorites();
        favorites.checkFavoritesPageOpen();
        return favorites;
    }

    @Step("Нажать контакты")
    public Contacts clickContacts() {
        contactsButton.click();
        Contacts contacts = new Contacts();
        contacts.checkContactsPageOpen();
        return contacts;
    }

    @Step("Открыть сравнение")
    public Comparison openComparison() {
        comparisonButton.click();
        Comparison comparison = new Comparison();
        comparison.checkComparisonPageOpen();
        return comparison;
    }
}
