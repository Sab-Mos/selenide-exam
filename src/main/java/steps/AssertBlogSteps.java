package steps;

import pages.AssertBlogPage;

import static com.codeborne.selenide.Selenide.open;

public class AssertBlogSteps {
    AssertBlogPage assertBlogPage = new AssertBlogPage();

    public void openPage(){
        open("en/blogs");
    }

}
