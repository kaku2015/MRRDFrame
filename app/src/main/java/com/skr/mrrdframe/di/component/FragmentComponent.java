package com.skr.mrrdframe.di.component;

import android.app.Activity;
import android.content.Context;

import com.skr.mrrdframe.di.module.FragmentModule;
import com.skr.mrrdframe.di.scope.ContextLife;
import com.skr.mrrdframe.di.scope.PerFragment;

import dagger.Component;

/**
 * @author hyw
 * @since 2016/11/24
 */
@PerFragment
@Component(dependencies = ApplicationComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();

    Activity getActivity();

//    void inject(SomeFragment someFragment);
}
