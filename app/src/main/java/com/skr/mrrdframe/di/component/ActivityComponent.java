package com.skr.mrrdframe.di.component;

import android.app.Activity;
import android.content.Context;

import com.skr.mrrdframe.di.module.ActivityModule;
import com.skr.mrrdframe.di.scope.ContextLife;
import com.skr.mrrdframe.di.scope.PerActivity;

import dagger.Component;

/**
 * @author hyw
 * @since 2016/11/24
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();

    Activity getActivity();

//    void inject(SomeActivity someActivity);
}
