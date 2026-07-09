import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.qameta.allure.Allure.step;

public class Tests extends TestBase {
    Components components = new Components();

    @DisplayName("Поиск по параметрам")
    @Test
    void searchByParameters() {
        components.openPage()
                .clickSearch()
                .clickAllFilters();
        step("Установить параметры поиска", () -> {
            components.checkPurchaseChosen()
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
        components.submitSearch();
        step("Проверить результат поиска", () -> {
            components.checkPurchaseChosen()
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
        components.openPage()
                .chooseQuickFilterMetro("begovaya")
                .checkTextSelectedFilters("Беговая")
                .checkSubwayNameInCards("Беговая");
    }

    @DisplayName("Добавление в избранное")
    @Test
    void addToFavorites() {
        components.openPage()
                .chooseResaleApartments()
                .saveResaleTitle()
                .addResaleToFavorites()
                .openFavorites()
                .checkFavoritesResaleFirstCardTitle();
    }

    @DisplayName("Переход в контакты")
    @Test
    void redirectToContacts() {
        components.openPage()
                .clickContacts()
                .checkNumberEmailMap("+7 (812) ", "info@pn.ru");

    }

    @DisplayName("Добавление в сравнение")
    @Test
    void addToComparison() {
        components.openPage()
                .saveNewBuildsCardTitleByIndex(0, 1)
                .addNewBuildsToComparison(0)
                .addNewBuildsToComparison(1)
                .openComparison()
                .checkNewBuildCardTitleInComparison();
    }
}