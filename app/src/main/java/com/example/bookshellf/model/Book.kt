package com.example.bookshellf.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class Book(
    val id: String,
    val description: String,
    val volumeInfo: VolumeInfo,
    val saleInfo: SaleInfo
){

    fun getPrice(): String{
        if (saleInfo.listPrice == null) {
            return ""
        }
        return "${saleInfo.listPrice.amount} ${saleInfo.listPrice.currency}"
    }
}

@Serializable
data class VolumeInfo(
    val title: String,
    val subtitle: String,
    @SerialName("description")
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

@Serializable
data class SaleInfo(
    val country: String,
    val isEbook: Boolean,
    val listPrice: ListPrice?
){
    //this works too...
    val getPrice2: String
        get() = "${listPrice?.amount ?: "N/A"} ${listPrice?.currency ?: "N/A"}"
}

@Serializable
data class ListPrice(
    val amount: Float?,
    val currency: String? = ""
)


