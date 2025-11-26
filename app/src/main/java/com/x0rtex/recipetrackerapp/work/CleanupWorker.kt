package com.x0rtex.recipetrackerapp.work

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class CleanupWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        return Result.success()
    }
}