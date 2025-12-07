package com.x0rtex.recipetrackerapp.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.x0rtex.recipetrackerapp.data.models.IngredientEntity

class Converters {
    private val gson = Gson()

    // Convert List<String> to String
    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return gson.toJson(value)
    }

    // Convert String to List<String>
    @TypeConverter
    fun toStringList(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, listType)
    }

    // Convert List<IngredientEntity> to String
    @TypeConverter
    fun fromIngredientList(value: List<IngredientEntity>): String {
        return gson.toJson(value)
    }

    // Convert String to List<IngredientEntity>
    @TypeConverter
    fun toIngredientList(value: String): List<IngredientEntity> {
        val listType = object : TypeToken<List<IngredientEntity>>() {}.type
        return gson.fromJson(value, listType)
    }
}