package com.infinity.jerry.securitysupport.common.otherstuff;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by edwardliu on 16/4/18.
 */
public class PdfFile implements Parcelable, Comparable<PdfFile> {
    public String name;
    public String shortName;
    public String url;
    public boolean isExist;
    public boolean isDownloading = false;

    public PdfFile() {
    }

    protected PdfFile(Parcel in) {
        name = in.readString();
        shortName = in.readString();
        url = in.readString();
        isExist = in.readByte() != 0;
        isDownloading = in.readByte() != 0;
    }

    public static final Creator<PdfFile> CREATOR = new Creator<PdfFile>() {
        @Override
        public PdfFile createFromParcel(Parcel in) {
            return new PdfFile(in);
        }

        @Override
        public PdfFile[] newArray(int size) {
            return new PdfFile[size];
        }
    };

    @Override
    public int compareTo(PdfFile another) {
        return url.compareTo(another.url);
    }

    @Override
    public boolean equals(Object o) {
        if (o != null && o instanceof PdfFile) {
            PdfFile f = (PdfFile) o;
            return f.url.equalsIgnoreCase(url);
        }
        return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(shortName);
        dest.writeString(url);
        dest.writeByte((byte) (isExist ? 1 : 0));
        dest.writeByte((byte) (isDownloading ? 1 : 0));
    }
}
