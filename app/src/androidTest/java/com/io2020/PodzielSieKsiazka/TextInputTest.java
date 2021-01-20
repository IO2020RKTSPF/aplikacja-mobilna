package com.io2020.PodzielSieKsiazka;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class TextInputTest {
    private String stringToBetyped;
    private String resultString;
    private String string20;

    @Rule
    public ActivityScenarioRule<OfferActivity> activityRule
            = new ActivityScenarioRule<>(OfferActivity.class);

    @Before
    public void initValidString() {
        StringBuilder stringBuilder = new StringBuilder(41);
        for (int i = 0; i < stringBuilder.capacity(); i++)
            stringBuilder.append("x");
        stringToBetyped = stringBuilder.toString();

        stringBuilder = new StringBuilder(20);
        for (int i = 0; i < stringBuilder.capacity(); i++)
            stringBuilder.append("x");
        string20 = stringBuilder.toString();


        stringBuilder = new StringBuilder(40);
        for (int i = 0; i < stringBuilder.capacity(); i++)
            stringBuilder.append("x");
        resultString = stringBuilder.toString();
    }

    @Test
    public void changeText_sameActivity() {
        onView(withId(R.id.titleCharacters))
                .check(matches(withText("0/40")));

        onView(withId(R.id.offerTitleEdit))
                .perform(typeText(stringToBetyped));

        onView(withId(R.id.offerTitleEdit))
                .check(matches(withText(resultString)));

        onView(withId(R.id.titleCharacters))
                .check(matches(withText("40/40")));

        onView(withId(R.id.offerTitleEdit))
                .perform(clearText());

        onView(withId(R.id.titleCharacters))
                .check(matches(withText("0/40")));

        onView(withId(R.id.offerTitleEdit))
                .perform(typeText(string20));

        onView(withId(R.id.titleCharacters))
                .check(matches(withText("20/40")));

        onView(withId(R.id.authorCharacters))
                .check(matches(withText("0/40")));

        onView(withId(R.id.offerAuthorEdit))
                .perform(typeText(stringToBetyped));

        onView(withId(R.id.offerAuthorEdit))
                .check(matches(withText(resultString)));

        onView(withId(R.id.authorCharacters))
                .check(matches(withText("40/40")));

        onView(withId(R.id.offerAuthorEdit))
                .perform(clearText());

        onView(withId(R.id.authorCharacters))
                .check(matches(withText("0/40")));

    }

}
