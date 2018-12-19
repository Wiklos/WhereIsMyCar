package pawel.wiklo.whereismycar;

import com.orm.SugarRecord;

public class DataBaseValues extends SugarRecord {
    double lat;
    double len;
    String data;
    String nazwa;

    public  DataBaseValues()
    {

    }

    public  DataBaseValues(double lat,double len,String data,String nazwa)
    {
        this.data = data;
        this.lat = lat;
        this.len = len;
        this.nazwa = nazwa;
    }
    public  DataBaseValues(double lat,double len,String data)
    {
        this.data = data;
        this.lat = lat;
        this.len = len;
        this.nazwa = "";
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLen() {
        return len;
    }

    public void setLen(double len) {
        this.len = len;
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
