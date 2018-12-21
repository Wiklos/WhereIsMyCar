package pawel.wiklo.whereismycar;

import com.orm.SugarRecord;

public class DataBaseValues extends SugarRecord {
    double lat;
    double lon;
    String data;
    String nazwa;

    public  DataBaseValues()
    {
        this.data = "defaultDate";
        this.lat = 0.0;
        this.lon = 0.0;
        this.nazwa = "defaultName";
    }

    public  DataBaseValues(double lat,double lon,String data,String nazwa)
    {
        this.data = data;
        this.lat = lat;
        this.lon = lon;
        this.nazwa = nazwa;
    }
    public  DataBaseValues(double lat,double lon,String data)
    {
        this.data = data;
        this.lat = lat;
        this.lon = lon;
        this.nazwa = "";
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }
}
