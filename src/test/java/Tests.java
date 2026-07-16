import Pages.Buildings;
import Pages.MainPage;
import Pages.SearchResults;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;

public class Tests extends TestBase {
    MainPage mainPage = new MainPage();

    @DisplayName("Поиск по параметрам")
    @Test
    void searchByParameters() {
        Buildings buildings = mainPage.openPage().clickSearch();
        buildings.clickAllFilters();
        step("Установить параметры поиска", () -> {
            buildings.checkPurchaseChosen()
                    .checkNewBuildingsChosen()
                    .chooseQuantityOfRooms("2к")
                    .chooseQuantityOfRooms("3к")
                    .checkTotalCostChosen()
                    .setMinCost(5)
                    .setMaxCost(13)
                    .checkWalkingDistanceToSubway()
                    .setDistanceToSubway("15 мин")
                    .setArea(50, 90)
                    .setOptionsOfFloors("Не последний")
                    .setOptionsOfFloors("Не первый");
        });
        SearchResults searchResults = buildings.submitSearch();
        step("Проверить результат поиска", () -> {
            searchResults.checkPurchaseChosen()
                    .checkNewBuildingsChosen()
                    .checkQuantityOfRooms("2к")
                    .checkQuantityOfRooms("3к")
                    .checkCostInFilters("5 - 13 млн")
                    .checkTextSelectedFilters("Пешком: 15 мин")
                    .checkTextSelectedFilters("Общая площадь: от 50 м² - до 90 м²")
                    .checkTextSelectedFilters("Этаж: Не первый")
                    .checkTextSelectedFilters("Этаж: Не последний")
                    .checkDistanceToSubwayInCards(15)
                    .checkCostInCards(5, 13)
                    .checkRoomsAreaCostInCards(2, 3, 50, 90, 5, 13);
        });
    }

    @DisplayName("Использование быстрого фильтра метро")
    @Test
    void quickFilterTest() {
        mainPage.openPage()
                .chooseQuickFilterMetro("begovaya")
                .checkTextSelectedFilters("Беговая")
                .checkSubwayNameInCards("Беговая");
    }

    @DisplayName("Добавление в избранное")
    @Test
    void addToFavorites() {
        mainPage.openPage()
                .chooseResaleApartments();
        String expectedTitle = mainPage.getResaleTitle();
        mainPage.addResaleToFavorites()
                .openFavorites()
                .checkFavoritesResaleFirstCardTitle(expectedTitle);
    }

    @DisplayName("Переход в контакты")
    @Test
    void redirectToContacts() {
        mainPage.openPage()
                .clickContacts()
                .checkNumberEmailMap("+7 (812) ", "info@pn.ru");
    }

    @DisplayName("Добавление в сравнение")
    @Test
    void addToComparison() {
        mainPage.openPage();
        String expectedTitle1 = mainPage.getNewBuildsCardTitleByIndex(0);
        String expectedTitle2 = mainPage.getNewBuildsCardTitleByIndex(1);
        mainPage.addNewBuildsToComparison(0)
                .addNewBuildsToComparison(1)
                .openComparison()
                .checkNewBuildCardTitleInComparison(0, expectedTitle1)
                .checkNewBuildCardTitleInComparison(1, expectedTitle2);
    }
}