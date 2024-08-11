package com.app.task.model

import android.os.Parcel
import android.os.Parcelable
import com.app.task.helper.FirebaseHelper

data class Task(
    var id: String = "",
    var title: String = "",
    var status: Int = 0
) : Parcelable {

    init {
        this.id = FirebaseHelper.getDataBase().push().key ?: ""
    }

    constructor() : this("","",0)
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?:"",
        parcel.readInt() ?: 0
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeInt(status)
    }

    companion object CREATOR : Parcelable.Creator<Task> {
        override fun createFromParcel(parcel: Parcel): Task {
            return Task(parcel)
        }

        override fun newArray(size: Int): Array<Task?> {
            return arrayOfNulls(size)
        }
    }
}
