package com.example.shiyan2;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Diary implements Parcelable {
    private int id;
    private String title;
    private String content;
    private String createTime;
    private String author;
    public Diary(){}

    public Diary(int id, String title, String content, String createTime, String author) {
        this.id=id;
        this.author=author;
        this.content=content;
        this.createTime=createTime;
        this.title=title;
    }
    protected Diary(Parcel in){
        id=in.readInt();
        title=in.readString();
        content=in.readString();
        createTime=in.readString();
        author=in.readString();
    }
    public static final Creator<Diary> CREATOR = new Creator<Diary>() {
        @Override
        public Diary createFromParcel(Parcel in) {
            return new Diary(in);
        }

        @Override
        public Diary[] newArray(int i) {
            return new Diary[i];
        }
    };
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(content);
        parcel.writeString(createTime);
        parcel.writeString(author);

    }
}
