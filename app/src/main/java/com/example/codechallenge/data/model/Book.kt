package com.example.codechallenge.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose


data class Book(

    @Expose(serialize = true, deserialize = true)
    var id : String? = "",
    @Expose(serialize = true, deserialize = true)
    var title : String? = "",
    @Expose(serialize = true, deserialize = true)
    var author : String? = "",
    @Expose(serialize = true, deserialize = true)
    var genre : String? = "",
    @Expose(serialize = true, deserialize = true)
    var createdAt : String? = "",
    @Expose(serialize = true, deserialize = true)
    var yearPublished : Int? = 0,
    @Expose(serialize = true, deserialize = true)
    var checkedOut : Boolean? = false,
    @Expose(serialize = false, deserialize = false)
    @Transient var imgColor : String? = "",
    @Expose(serialize = false, deserialize = false)
    @Transient  var imgLetter : String? = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(p0: Parcel, p1: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<Book> {
        override fun createFromParcel(parcel: Parcel): Book {
            return Book(parcel)
        }

        override fun newArray(size: Int): Array<Book?> {
            return arrayOfNulls(size)
        }
    }
}