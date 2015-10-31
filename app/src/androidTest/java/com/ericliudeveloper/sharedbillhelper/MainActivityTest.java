package com.ericliudeveloper.sharedbillhelper;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.ericliudeveloper.sharedbillhelper.ui.activity.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by ericliu on 31/10/2015.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class);

    @Test
    public void isFabShown(){
        onView(withId(R.id.fab)).check(matches(isDisplayed()));
    }

    @Test
    public void areTabsShown(){
        onView(withText(R.string.bill)).check(matches(isDisplayed()));
        onView(withText(R.string.member)).check(matches(isDisplayed()));
    }


    @Test
    public void onMemberTabClicked(){
        onView(withText(R.string.member)).perform(click());
    }

    @Test
    public void onHamburgerButtonClicked(){
        onView(withId(R.id.home)).perform(click());
    }
}
