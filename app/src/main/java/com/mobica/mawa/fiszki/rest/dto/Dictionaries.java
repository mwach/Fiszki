package com.mobica.mawa.fiszki.rest.dto;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mawa on 08/12/14.
 */
public class Dictionaries implements Parcelable {

    public static final Parcelable.Creator CREATOR =
            new Parcelable.Creator() {
                public Dictionaries createFromParcel(Parcel in) {
                    return new Dictionaries(in);
                }

                public Dictionaries[] newArray(int size) {
                    return new Dictionaries[size];
                }
            };
    @SerializedName(value = "dictionary")
    public List<Dictionary> dictionaries;

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private Dictionaries(Parcel in) {
        dictionaries = new ArrayList<Dictionary>();
        Dictionary dictionary = new Dictionary();
        dictionary.setRefLanguageId(in.readLong());
        dictionary.setBaseLanguageId(in.readLong());
        dictionary.setUuid(in.readString());
        dictionary.setDescription(in.readString());
        dictionary.setName(in.readString());
        dictionary.setId(in.readLong());
        dictionaries.add(dictionary);
        dictionary = new Dictionary();
        dictionary.setDescription(in.readString());
        dictionary.setDescription(in.readString());
    }

    public void setDictionaries(List<Dictionary> dictionaries) {
        this.dictionaries = dictionaries;
    }

    @Override
    public int describeContents() {
        return 0;
    }
    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
//    public static final Parcelable.Creator<Dictionaries> CREATOR = new Parcelable.Creator<Dictionaries>() {
//        public Dictionaries createFromParcel(Parcel in) {
//            return new Dictionaries(in);
//        }
//
//        public Dictionaries[] newArray(int size) {
//            return new Dictionaries[size];
//        }
//    };

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        for (Dictionary dictionary : dictionaries) {
            parcel.writeLong(dictionary.getId());
            parcel.writeString(dictionary.getName());
            parcel.writeString(dictionary.getDescription());
            parcel.writeString(dictionary.getUuid());
            parcel.writeLong(dictionary.getBaseLanguageId());
            parcel.writeLong(dictionary.getRefLanguageId());
        }
    }

}
