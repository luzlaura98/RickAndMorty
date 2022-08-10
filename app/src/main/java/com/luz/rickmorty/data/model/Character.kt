package com.luz.rickmorty.data.model

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * Created by Luz on 3/8/2022.
 */
@Keep
data class Character(
    val id: Int,
    val name: String?,
    val status: String?,
    val species: String?,
    val type: String?,
    val gender: String?,
    val image: String?,
    val origin: Location?,
    val location: Location?,
    @SerializedName("episode")
    val episodes: List<String>
) : Parcelable {

    constructor(p: Parcel) : this(
        p.readInt(),
        p.readString(),
        p.readString(),
        p.readString(),
        p.readString(),
        p.readString(),
        p.readString(),
        p.readParcelable(Location::class.java.classLoader) as? Location,
        p.readParcelable(Location::class.java.classLoader) as? Location,
        mutableListOf<String>().apply {
            p.readStringList(this)
        }
    )

    override fun describeContents() = 0

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        p0?.apply {
            writeInt(id)
            writeString(name)
            writeString(status)
            writeString(species)
            writeString(type)
            writeString(gender)
            writeString(image)
            writeParcelable(origin, p1)
            writeParcelable(location, p1)
            writeStringList(episodes)
        }
    }

    companion object {
        @JvmField
        final val CREATOR: Parcelable.Creator<Character> = object : Parcelable.Creator<Character> {
            override fun createFromParcel(p0: Parcel) = Character(p0)
            override fun newArray(p0: Int): Array<Character?> = arrayOfNulls(p0)
        }
    }
}
