package com.example.shiyan3;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Weather implements Parcelable {
    private String province;
    private String city;
    private String weather;
    private String temperature;
    private String winddirection;
    private String windpower;
    private String humidity;
    private String reporttime;
    public Weather(String province,String city,String weather,String temperature,String winddirection,String windpower,String humidity,String reporttime){
        this.province = province;
        this.city = city;
        this.weather = weather;
        this.temperature = temperature;
        this.winddirection = winddirection;
        this.windpower = windpower;
        this.humidity = humidity;
        this.reporttime = reporttime;
    }
    protected Weather(Parcel in){
        province=in.readString();
        city=in.readString();
        weather=in.readString();
        temperature=in.readString();
        winddirection=in.readString();
        windpower=in.readString();
        humidity=in.readString();
        reporttime=in.readString();
    }
    public static final Creator<Weather> CREATOR = new Creator<Weather>() {
        @Override
        public Weather createFromParcel(Parcel in) {
            return new Weather(in);
        }

        @Override
        public Weather[] newArray(int i) {
            return new Weather[i];
        }
    };

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getWinddirection() {
        return winddirection;
    }

    public void setWinddirection(String winddirection) {
        this.winddirection = winddirection;
    }

    public String getWindpower() {
        return windpower;
    }

    public void setWindpower(String windpower) {
        this.windpower = windpower;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getReporttime() {
        return reporttime;
    }

    public void setReporttime(String reporttime) {
        this.reporttime = reporttime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", weather='" + weather + '\'' +
                ", temperature='" + temperature + '\'' +
                ", winddirection='" + winddirection + '\'' +
                ", windpower='" + windpower + '\'' +
                ", humidity='" + humidity + '\'' +
                ", reporttime='" + reporttime + '\'' +
                '}';
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(province);
        parcel.writeString(city);
        parcel.writeString(weather);
        parcel.writeString(temperature);
        parcel.writeString(winddirection);
        parcel.writeString(windpower);
        parcel.writeString(humidity);
        parcel.writeString(reporttime);
    }
}
