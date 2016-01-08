package br.com.samirrolemberg.carb.utils;

import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import br.com.samirrolemberg.carb.R;
import io.fabric.sdk.android.Fabric;

/**
 * Created by samir on 05/02/2015.
 */
public class C extends Application{
    private static Context context;

    public static GoogleAnalytics analytics;
    public static Tracker tracker;

    public static Context getContext(){
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        context=this;

        setupGA();
    }

    public void setupGA(){
        analytics = GoogleAnalytics.getInstance(getContext());
        analytics.setLocalDispatchPeriod(1800);

        tracker = analytics.newTracker(getContext().getResources().getString(R.string.ga_app_key)); // Replace with actual tracker/property Id
        tracker.enableExceptionReporting(true);
        tracker.enableAdvertisingIdCollection(true);
        tracker.enableAutoActivityTracking(true);
    }

    public static Tracker getTracker(){
        return tracker;
    }

}
