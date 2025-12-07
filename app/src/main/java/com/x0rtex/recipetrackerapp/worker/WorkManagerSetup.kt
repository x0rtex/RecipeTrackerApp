package com.x0rtex.recipetrackerapp.worker

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import java.util.concurrent.TimeUnit

object WorkManagerSetup {
    // Schedule Cleanup
    fun schedulePeriodicTasks(context: Context) {
        scheduleDatabaseCleanup(context)
    }

    // Database cleanup schedule
    private fun scheduleDatabaseCleanup(context: Context) {
        // Create periodic work request
        val cleanupWorkRequest = PeriodicWorkRequestBuilder<CleanupWorker>(
            repeatInterval = CleanupWorker.REPEAT_INTERVAL_HOURS,
            repeatIntervalTimeUnit = TimeUnit.HOURS
        )
            .setBackoffCriteria(
                BackoffPolicy.EXPONENTIAL,
                WorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS
            )
            .addTag(CleanupWorker.TAG)
            .build()

        // Queue the work
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            CleanupWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            cleanupWorkRequest
        )
    }

    // Cancel All Tasks
    fun cancelAllTasks(context: Context) {
        WorkManager.getInstance(context).cancelAllWork()
    }

    // Cancel Task By Tag
    fun cancelTask(context: Context, tag: String) {
        WorkManager.getInstance(context).cancelAllWorkByTag(tag)
    }

    // Trigger Cleanup Manually
    fun triggerDatabaseCleanupNow(context: Context) {
        val cleanupRequest = OneTimeWorkRequestBuilder<CleanupWorker>()
            .addTag("${CleanupWorker.TAG}_manual")
            .build()

        WorkManager.getInstance(context).enqueue(cleanupRequest)
    }
}