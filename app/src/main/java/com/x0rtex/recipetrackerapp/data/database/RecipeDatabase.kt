package com.x0rtex.recipetrackerapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.x0rtex.recipetrackerapp.data.Converters
import com.x0rtex.recipetrackerapp.data.dao.RecipeDao
import com.x0rtex.recipetrackerapp.data.models.IngredientEntity
import com.x0rtex.recipetrackerapp.data.models.RecipeEntity

@Database(
    entities = [RecipeEntity::class, IngredientEntity::class],
    version = 1,
)
@TypeConverters(Converters::class)
abstract class RecipeDatabase : RoomDatabase() {

    abstract fun recipeDao(): RecipeDao

    companion object {
        @Volatile private var INSTANCE: RecipeDatabase? = null

        fun get(context: Context): RecipeDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    RecipeDatabase::class.java,
                    "recipes.db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}