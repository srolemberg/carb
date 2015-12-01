package br.com.samirrolemberg.carb;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.*;

/**
 * Created by srolemberg on 26/11/15.
 * Classe de Teste do Teste do Expresso
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class HelloWorldTest {

    @Rule
    public ActivityTestRule<MainActivity> mActRule = new ActivityTestRule(MainActivity.class);

    @Test
    public void listGoesOverTheFold(){
        onView(ViewMatchers.withText("CARB")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void loopClickFAB(){
        for(int i = 0; i < 10; i++){
            onView(ViewMatchers.withId(R.id.fab)).perform(ViewActions.click());
            onView(ViewMatchers.withText(R.string.main_act_dialog_titulo)).check(ViewAssertions.matches(ViewMatchers.isDisplayed())).perform(ViewActions.pressBack());
        }
    }
}
