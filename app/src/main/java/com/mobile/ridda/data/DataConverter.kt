package com.mobile.ridda.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DataConverter {


    var gson = Gson()

    @TypeConverter
    fun foodRecipeToString(newsList: Ride): String{
        return gson.toJson(newsList)
    }

    @TypeConverter
    fun stringToJson(data: String): Ride {
        val listType = object : TypeToken<Ride>(){}.type
        return gson.fromJson(data, listType)
    }


    @TypeConverter
    fun stringToJsonlist(data: String): List<Ride> {
        val listType = object : TypeToken<List<Ride>>(){}.type
        return gson.fromJson(data, listType)
    }




}