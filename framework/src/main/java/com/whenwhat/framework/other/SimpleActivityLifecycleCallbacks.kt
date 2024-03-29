package com.whenwhat.framework.other

import android.app.Activity
import android.app.Application
import android.os.Bundle

interface SimpleActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {
    override fun onActivityPaused(activity: Activity) = Unit
    override fun onActivityStarted(activity: Activity) = Unit
    override fun onActivityDestroyed(activity: Activity) = Unit
    override fun onActivitySaveInstanceState(activity: Activity, outBundle: Bundle) = Unit
    override fun onActivityStopped(activity: Activity) = Unit
    override fun onActivityCreated(activity: Activity, savedInstance: Bundle?) = Unit
    override fun onActivityResumed(activity: Activity) = Unit
}