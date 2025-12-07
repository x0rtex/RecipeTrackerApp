package com.x0rtex.recipetrackerapp.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.x0rtex.recipetrackerapp.data.database.RecipeDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CleanupWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    companion object {
        const val TAG = "CleanupWorker"
        const val WORK_NAME = "cleanup_work"

        // Every 24 hours default
        const val REPEAT_INTERVAL_HOURS = 24L
    }

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        return@withContext try {
            Log.d(TAG, "Starting database cleanup...")

            // Get database
            val database = RecipeDatabase.get(applicationContext)
            val recipeDao = database.recipeDao()

            // Perform cleanup
            performDatabaseMaintenance(recipeDao)

            // Log success
            Log.d(TAG, "Database cleanup completed")
            Result.success()

        } catch (e: Exception) {
            Log.e(TAG, "Database cleanup failed: ${e.message}", e)

            // Retry on failure
            if (runAttemptCount < 3) {
                Result.retry()
            } else {
                Result.failure()
            }
        }
    }

    private suspend fun performDatabaseMaintenance(recipeDao: com.x0rtex.recipetrackerapp.data.dao.RecipeDao) {
        // Get current recipe count
        val recipeCount = recipeDao.getRecipeCount()
        Log.d(TAG, "Current recipe count: $recipeCount")

        // Log recipes without images
        val recipesWithoutImages = recipeDao.getRecipesWithoutImages()
        Log.d(TAG, "Recipes without images: ${recipesWithoutImages.size}")
    }
}