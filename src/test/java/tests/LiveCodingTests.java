package tests;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import steps.AssertBlogSteps;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class LiveCodingTests {

    AssertBlogSteps assertBlogSteps;
    @BeforeClass
    public void setUp() {
        Configuration.browser = "chrome";
        Configuration.headless = false;
        Configuration.timeout = 7000;
        Configuration.baseUrl="https://www.tbcbank.ge/";
        Configuration.holdBrowserOpen= true;
        WebDriverManager.chromedriver().setup();
        open("");
        assertBlogSteps = new AssertBlogSteps();
    }

    @Test
    public void languageSwitchToEnglish() {
        $x("//div[contains(@class, 'tbcx-language-select__selected ng-tns-c2926132420-3 ng-star-inserted')]").click(); // FIXME: placeholder


        $$(".main-nav a").findBy(text("Products")).shouldBe(visible,
                Duration.ofSeconds(10)); // TODO: adjust text

    }

    @Test
    public void filterResetRestoresDefaultState() {
        open("en/offers/all-offers?segment=All&filters=");
        $("[data-test='Installment-filter']").click(); // FIXME

        ElementsCollection filtered = $$(".result-card"); // FIXME
        assertTrue(filtered.size() > 0, "Expected at least one filtered result");

        $("[data-test='reset-filters']").click(); // FIXME

        ElementsCollection validatedResults = $$(".result-card");

        assertTrue(validatedResults.size() > filtered.size());

    }

    @Test
    public void assertBlogDatesAreDescending_simple() {


        ElementsCollection cards = $$(" app-marketing-list tbcx-pw-image-card").shouldHave(CollectionCondition.sizeGreaterThan(0));

        int take = Math.min(5, cards.size());
        List<LocalDate> dates = new ArrayList<>();

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH);
        System.out.println(take);

        for (int i = 0; i < take; i++) {

            ElementsCollection freshCards = $$("app-marketing-list tbcx-pw-image-card").shouldHave(CollectionCondition.sizeGreaterThan(0));
            freshCards.get(i).scrollTo().click();

            String dateText = $("app-textpage-header app-textpage-additional").getText().trim();

            LocalDate parsed = LocalDate.parse(dateText, fmt);
            dates.add(parsed);
            back();
        }

        List<LocalDate> sortedDesc = new ArrayList<>(dates);
        sortedDesc.sort(Comparator.reverseOrder());

        assertEquals(dates, sortedDesc, "Blog dates are not in descending order");
    }
}