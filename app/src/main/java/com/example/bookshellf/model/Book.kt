package com.example.bookshellf.model

import kotlinx.serialization.Serializable

@Serializable
class Book(
    val id: String,
    val description: String,
    val volumeInfo: VolumeInfo
)

@Serializable
data class VolumeInfo(
    val title: String,
    val subtitle: String,
    val description: String,
    val imageLinks: ImageLinks? = null,
    val authors: List<String>,
    val publisher: String,
    val publishedDate: String,

    ){
    val allAuthorsx: String
        get() = allAuthors()

    fun allAuthors(): String{
        var x = ""
        for (author in authors) {
            x += "$author, "
        }
        return x.trimEnd(',',' ')
    }
}

@Serializable
data class ImageLinks (
    val smallThumbnail: String,
    val thumbnail: String
){
    val httpsThumbnail: String
        get() = thumbnail.replace("http","https")
}

