import android.os.Parcel
import android.os.Parcelable

data class MenuItem(
    val name: String,
    val imageResId: Int,
    val description: String,
    val ingredients: String,
    val isVegetarian: Boolean,
    val isGlutenFree: Boolean,
    val tiempoMinutos: Int,
    val raciones: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(imageResId)
        parcel.writeString(description)
        parcel.writeString(ingredients)
        parcel.writeByte(if (isVegetarian) 1 else 0)
        parcel.writeByte(if (isGlutenFree) 1 else 0)
        parcel.writeInt(tiempoMinutos)
        parcel.writeString(raciones)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MenuItem> {
        override fun createFromParcel(parcel: Parcel): MenuItem {
            return MenuItem(parcel)
        }

        override fun newArray(size: Int): Array<MenuItem?> {
            return arrayOfNulls(size)
        }
    }
}