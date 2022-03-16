package com.meishe.base.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * author : lhz
 * date   : 2020/9/2
 * desc   :标识
 * 媒体标识类
 * Media identification class
 */
public class MediaTag implements Parcelable {
    private int type;
    /**
     * 在集合中的索引
     * The index in the collection
     */
    private int index = -1;

    public MediaTag(){}

    protected MediaTag(Parcel in) {
        type = in.readInt();
        index = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(type);
        dest.writeInt(index);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MediaTag> CREATOR = new Creator<MediaTag>() {
        @Override
        public MediaTag createFromParcel(Parcel in) {
            return new MediaTag(in);
        }

        @Override
        public MediaTag[] newArray(int size) {
            return new MediaTag[size];
        }
    };

    /**
     * Gets type.
     * 获取类型
     *
     * @return the type
     */
    public int getType() {
        return type;
    }


    public MediaTag setType(int type) {
        this.type = type;
        return this;
    }


    public int getIndex() {
        return index;
    }


    public MediaTag setIndex(int index) {
        this.index = index;
        return this;
    }
}
