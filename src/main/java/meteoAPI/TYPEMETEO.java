package meteoAPI;

public enum TYPEMETEO {
    RAIN("rain"),
    NORAIN("no rain"),
    DEGAGE("Clear"),
    PEUNUAGUEX("Partly Cloudly"),
    TRESNUAGEUX("Mostly Cloudly"),
    OVERCAST("Overcast");

    private String meteo;

    TYPEMETEO(String meteo) {
        this.meteo = meteo;
    }
    public String toString(){
        return meteo;
    }
}
