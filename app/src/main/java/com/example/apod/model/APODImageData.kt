package com.example.apod.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "ImageRecord")
data class APODImageData(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    var id: Int = 0,

    @ColumnInfo(name = "copyright")
    @SerializedName("copyright")
    val copyright: String?,


    @ColumnInfo(name = "date")
    @SerializedName("date")
    val date: String?,

    @ColumnInfo(name = "explanation")
    @SerializedName("explanation")
    val explanation: String?,


    @ColumnInfo(name = "hdurl")
    @SerializedName("hdurl")
    val hdurl: String?,


    @ColumnInfo(name = "media_type")
    @SerializedName("media_type")
    val mediaType: String?,


    @ColumnInfo(name = "service_version")
    @SerializedName("service_version")
    val serviceVersion: String?,

    @ColumnInfo(name = "title")
    @SerializedName("title")
    val title: String?,

    @ColumnInfo(name = "url")
    @SerializedName("url")
    val url: String?
)