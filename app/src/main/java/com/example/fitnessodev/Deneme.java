package com.example.fitnessodev;

public class Deneme
{
    String tarih,kalori,vakit;

    public Deneme(String tarih, String kalori, String vakit)
    {
        this.tarih = tarih;
        this.kalori = kalori;
        this.vakit = vakit;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih)
    {
        this.tarih = tarih;
    }

    public String getKalori()
    {
        return kalori;
    }

    public void setKalori(String kalori)
    {
        this.kalori = kalori;
    }

    public String getVakit() {
        return vakit;
    }

    public void setVakit(String vakit) {
        this.vakit = vakit;
    }
}
