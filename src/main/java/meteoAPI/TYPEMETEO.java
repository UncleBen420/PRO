package meteoAPI;

/**
 * Enumeration representant les differentes conditions meteorologique possible
 *
 * @author Groupe PRO B-9
 */
public enum TYPEMETEO {
    DEGAGE("Clear"),
    PEUNUAGUEX("Partly Cloudly"),
    TRESNUAGEUX("Mostly Cloudly"),
    OVERCAST("Overcast"),
    Rain("Rain"),
    LightRain("Light Rain"),
    Snow("Snow"),
    LightSleet("Light Sleet"),
    Foggy("Foggy");

    private String meteo;

    /**
     * Constructeur de l'enum
     *
     * @param meteo
     */
    TYPEMETEO(String meteo) {
        this.meteo = meteo;
    }

    /**
     * Retourne les conditions en string
     *
     * @return meteo
     */
    public String toString() {
        return meteo;
    }

    /**
     * Retourne les bytes de la condition fournie en parametre
     *
     * @param condition
     * @return weather
     */
    static public TYPEMETEO getTypeByString(String condition) {
        for (TYPEMETEO weather : TYPEMETEO.values()) {
            if (weather.toString().equals(condition)) {
                return weather;
            }
        }
        return null;
    }
}
