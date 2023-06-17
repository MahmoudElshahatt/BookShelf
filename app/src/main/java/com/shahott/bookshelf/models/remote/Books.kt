package com.shahott.bookshelf.models.remote


import com.squareup.moshi.Json
import androidx.annotation.Keep
import com.shahott.bookshelf.models.local.LocalBook
import com.shahott.bookshelf.models.domain.DomainBooks

@Keep
data class Books(
    @field:Json(name = "items")
    val items: List<Item?>? = listOf(),
    @field:Json(name = "totalItems")
    val totalItems: Int? = 0 // 576
) {
    @Keep
    data class Item(
        @field:Json(name = "id")
        val id: String? = "", // 2MV4CwAAQBAJ
        @field:Json(name = "volumeInfo")
        val bookInfo: VolumeInfo? = VolumeInfo()
    ) {

        @Keep
        data class VolumeInfo(
            @field:Json(name = "authors")
            val authors: List<String?>? = listOf(),
            @field:Json(name = "averageRating")
            val averageRating: Double? = 0.0, // 4.5
            @field:Json(name = "categories")
            val categories: List<String?>? = listOf(),
            @field:Json(name = "contentVersion")
            val contentVersion: String? = "", // preview-1.0.0
            @field:Json(name = "description")
            val description: String? = "", // Have you ever felt frustrated working with someone else’s code? Difficult-to-maintain source code is a big problem in software development today, leading to costly delays and defects. Be part of the solution. With this practical book, you’ll learn 10 easy-to-follow guidelines for delivering Java software that’s easy to maintain and adapt. These guidelines have been derived from analyzing hundreds of real-world systems. Written by consultants from the Software Improvement Group (SIG), this book provides clear and concise explanations, with advice for turning the guidelines into practice. Examples for this edition are written in Java, while our companion C# book provides workable examples in that language. Write short units of code: limit the length of methods and constructors Write simple units of code: limit the number of branch points per method Write code once, rather than risk copying buggy code Keep unit interfaces small by extracting parameters into objects Separate concerns to avoid building large classes Couple architecture components loosely Balance the number and size of top-level components in your code Keep your codebase as small as possible Automate tests for your codebase Write clean code, avoiding "code smells" that indicate deeper problems
            @field:Json(name = "imageLinks")
            val imageLinks: ImageLinks? = ImageLinks(),
            @field:Json(name = "infoLink")
            val infoLink: String? = "", // http://books.google.com.eg/books?id=2MV4CwAAQBAJ&dq=software&hl=&source=gbs_api
            @field:Json(name = "language")
            val language: String? = "", // en
            @field:Json(name = "maturityRating")
            val maturityRating: String? = "", // NOT_MATURE
            @field:Json(name = "pageCount")
            val pageCount: Int? = 0, // 168
            @field:Json(name = "previewLink")
            val previewLink: String? = "", // http://books.google.com.eg/books?id=2MV4CwAAQBAJ&printsec=frontcover&dq=software&hl=&cd=1&source=gbs_api
            @field:Json(name = "printType")
            val printType: String? = "", // BOOK
            @field:Json(name = "publishedDate")
            val publishedDate: String? = "", // 2016-01-28
            @field:Json(name = "publisher")
            val publisher: String? = "", // "O'Reilly Media, Inc."
            @field:Json(name = "ratingsCount")
            val ratingsCount: Int? = 0, // 2
            @field:Json(name = "subtitle")
            val subtitle: String? = "", // Ten Guidelines for Future-Proof Code
            @field:Json(name = "title")
            val title: String? = "" // Building Maintainable Software, Java Edition
        ) {
            @Keep
            data class ImageLinks(
                @field:Json(name = "smallThumbnail")
                val smallThumbnail: String? = "", // http://books.google.com/books/content?id=2MV4CwAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api
                @field:Json(name = "thumbnail")
                val thumbnail: String? = "" // http://books.google.com/books/content?id=2MV4CwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api
            )
        }
    }
}


/**
 * Convert Network results to domain and database objects
 */
fun Books.Item.VolumeInfo.asDomainModel() = DomainBooks(
    bookName = title ?: "",
    desc = description ?: "",
    bookLanguage = language ?: "en",
    pageCount = pageCount ?: 0,
    author = authors?.get(0) ?: "",
    imageUrl = imageLinks?.thumbnail ?: ""
)


fun Books.Item.VolumeInfo.asLocalDBModel() = LocalBook(
    bookName = title ?: "",
    desc = description ?: "",
    bookLanguage = language ?: "en",
    pageCount = pageCount ?: 0,
    author = authors?.get(0) ?: "",
    imageUrl = imageLinks?.thumbnail ?: ""
)
