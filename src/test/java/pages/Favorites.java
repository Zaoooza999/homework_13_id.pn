package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static pages.Components.cardResaleTitle;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class Favorites {
    private final SelenideElement favoritesPageTitle = $(".FavoritesPage__header");

    public Favorites checkFavoritesPageOpen() {
        favoritesPageTitle.shouldHave(text("Избранное"));
        return this;
    }

    @Step("Проверить заголовок вторички в избранном")
    public void checkFavoritesResaleFirstCardTitle(String value) {
        $(cardResaleTitle).shouldHave(text(value));
    }
}
