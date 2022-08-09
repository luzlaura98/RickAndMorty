package com.luz.rickmorty.data.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Luz on 8/8/2022.
 */
data class Location(
    val name : String?,
    private val url : String?
) : Parcelable{

    constructor(source : Parcel) : this(source.readString(), source.readString())

    override fun describeContents() = 0

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        p0?.apply {
            writeString(name)
            writeString(url)
        }
    }

    companion object{
        @JvmField final val CREATOR : Parcelable.Creator<Location> = object : Parcelable.Creator<Location>{
            override fun createFromParcel(p0: Parcel) = Location(p0)
            override fun newArray(p0: Int): Array<Location?> = arrayOfNulls(p0)
        }
    }
}
