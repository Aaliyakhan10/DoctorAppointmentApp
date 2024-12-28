package com.example.doctorappointment.Model

import android.os.Parcel
import android.os.Parcelable

data class Users(
    var uid:String?=null,
    val phoneNumber:String?=null,
    var Name:String?=null,
    val sex:String?=null,
    val Age:String?=null,
    val Add:String?=null

    ):Parcelable{

    constructor(parcel: Parcel):this(
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readString().toString()
    ){}
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uid)
        parcel.writeString(phoneNumber)
        parcel.writeString(Name)
        parcel.writeString(sex)
        parcel.writeString(Age)
        parcel.writeString(Add)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Users> {
        override fun createFromParcel(parcel: Parcel): Users {
            return Users(parcel)
        }

        override fun newArray(size: Int): Array<Users?> {
            return arrayOfNulls(size)
        }
    }
}
