package com.skr.mrrdframe.di.component;

import android.content.Context;

import com.skr.mrrdframe.di.module.ApplicationModule;
import com.skr.mrrdframe.di.scope.ContextLife;
import com.skr.mrrdframe.di.scope.PerApp;

import dagger.Component;

/**
 * @author hyw
 * @since 2016/11/24
 */
@PerApp
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    @ContextLife("Application")
    Context getApplication();

}

