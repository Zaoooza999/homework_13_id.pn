package Pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static Pages.Components.newBuildsCards;
import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class Comparison {
    private final SelenideElement comparisonPageTitle = $(".ComparisonPage__header");

    @Step("Проверить заголовок новостроек в сравнении")
    public Comparison checkNewBuildCardTitleInComparison(int index, String value) {
        $$(newBuildsCards).get(index).shouldHave(text(value));
        return this;
    }

    public Comparison checkComparisonPageOpen() {
        comparisonPageTitle.should(appear).shouldHave(text("Сравнение"));
        return this;
    }
}
