package com.x0rtex.recipetrackerapp.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.x0rtex.recipetrackerapp.data.models.IngredientEntity

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromStringList(list: List<String>?): String? {
        return list?.joinToString("|||")
    }

    @TypeConverter
    fun toStringList(data: String?): List<String>? {
        return data?.split("|||")?.map { it.trim() }
    }

    @TypeConverter
    fun fromIngredientEntityList(ingredients: List<IngredientEntity>?): String? {
        return gson.toJson(ingredients)
    }

    @TypeConverter
    fun toIngredientEntityList(data: String?): List<IngredientEntity>? {
        if (data == null) {
            return emptyList()
        }
        val listType = object : TypeToken<List<IngredientEntity>?>() {}.type
        return gson.fromJson(data, listType)
    }
}
