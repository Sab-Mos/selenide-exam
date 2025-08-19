package pages;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class AssertBlogPage {

    SelenideElement date = $("app-textpage-header app-textpage-additional");
    ElementsCollection cards = $$(" app-marketing-list tbcx-pw-image-card").shouldHave(CollectionCondition.sizeGreaterThan(0));

    ElementsCollection freshCards = $$("app-marketing-list tbcx-pw-image-card");
}
