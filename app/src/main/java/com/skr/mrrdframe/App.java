package com.skr.mrrdframe;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;

import com.blankj.utilcode.utils.AppUtils;
import com.skr.mrrdframe.di.component.ApplicationComponent;
import com.skr.mrrdframe.di.component.DaggerApplicationComponent;
import com.skr.mrrdframe.di.module.ApplicationModule;
import com.skr.mrrdframe.repository.db.DBManager;
import com.socks.library.KLog;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author hyw
 * @since 2016/11/18
 */
public class App extends Application {
    private static final String LOG_TAG = "App";
    public static Context getAppContext() {
        return sAppContext;
    }

    private static Context sAppContext;

    private static ApplicationComponent mApplicationComponent;
    private RefWatcher refWatcher;

    public static RefWatcher getRefWatcher(Context context) {
        App application = (App) context.getApplicationContext();
        return application.refWatcher;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        initLeakCanary();

        sAppContext = this;
        initActivityLifecycleLogs();
        initStrictMode();
        KLog.init(BuildConfig.LOG_DEBUG, AppUtils.getAppPackageName(this));
        DBManager.init();
        initApplicationComponent();
        initBugly();
    }

    private void initBugly() {
        if (!BuildConfig.DEBUG) {
            // 如果App使用了多进程且各个进程都会初始化Bugly（例如在Application类onCreate()中初始化Bugly），那么每个进程下的Bugly都会进行数据上报，造成不必要的资源浪费。因此，为了节省流量、内存等资源，建议初始化的时候对上报进程进行控制，只在主进程下上报数据：判断是否是主进程（通过进程名是否为包名来判断），并在初始化Bugly时增加一个上报进程的策略配置。
            Context context = getApplicationContext();
            // 获取当前包名
            String packageName = context.getPackageName();
            // 获取当前进程名
            String processName = getProcessName(android.os.Process.myPid());
            // 设置是否为上报进程
            CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
            strategy.setUploadProcess(processName == null || processName.equals(packageName));
            // 初始化Bugly  // TODO:
            CrashReport.initCrashReport(getApplicationContext(), "注册时申请的APPID", BuildConfig.DEBUG);
        }
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    private void initLeakCanary() {
        if (BuildConfig.DEBUG) {
            refWatcher = LeakCanary.install(this);
        } else {
            refWatcher = installLeakCanary();
        }
    }

    /**
     * release版本使用此方法
     */
    protected RefWatcher installLeakCanary() {
        return RefWatcher.DISABLED;
    }

    private void initActivityLifecycleLogs() {
        this.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                KLog.v(LOG_TAG, activity.getComponentName() + "  onActivityCreated");
            }

            @Override
            public void onActivityStarted(Activity activity) {
                KLog.v(LOG_TAG, activity.getComponentName() + "  onActivityStarted");
            }

            @Override
            public void onActivityResumed(Activity activity) {
                KLog.v(LOG_TAG, activity.getComponentName() + "  onActivityResumed");
            }

            @Override
            public void onActivityPaused(Activity activity) {
                KLog.v(LOG_TAG, activity.getComponentName() + "  onActivityPaused");
            }

            @Override
            public void onActivityStopped(Activity activity) {
                KLog.v(LOG_TAG, activity.getComponentName() + "  onActivityStopped");
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                KLog.v(LOG_TAG, activity.getComponentName() + "  onActivitySaveInstanceState");
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                KLog.v(LOG_TAG, activity.getComponentName() + "  onActivityDestroyed");
            }
        });
    }

    private void initStrictMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                    new StrictMode.ThreadPolicy.Builder()
                            .detectAll()
//                            .penaltyDialog() // 弹出违规提示对话框
                            .penaltyLog() // 在logcat中打印违规异常信息
                            .build());
            StrictMode.setVmPolicy(
                    new StrictMode.VmPolicy.Builder()
                            .detectAll()
                            .penaltyLog()
                            .build());
        }
    }

    private void initApplicationComponent() {
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }


    public static ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }
}
