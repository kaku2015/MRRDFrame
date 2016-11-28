package com.skr.mrrdframe.di.module;

import android.content.Context;

import com.skr.mrrdframe.App;
import com.skr.mrrdframe.di.scope.ContextLife;
import com.skr.mrrdframe.di.scope.PerApp;

import dagger.Module;
import dagger.Provides;

/**
 * @author hyw
 * @since 2016/11/24
 */
@Module
public class ApplicationModule {
    private App mApplication;

    public ApplicationModule(App application) {
        mApplication = application;
    }

    @Provides
    @PerApp
    @ContextLife("Application")
    public Context provideApplicationContext() {
        return mApplication.getApplicationContext();
    }

}
