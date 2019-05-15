package meteoAPI;

/**
 *
 * @author gaetan
 */
public enum TYPEMETEO {

    /**
     *
     */
    DEGAGE("Clear"),

    /**
     *
     */
    PEUNUAGUEX("Partly Cloudly"),

    /**
     *
     */
    TRESNUAGEUX("Mostly Cloudly"),

    /**
     *
     */
    OVERCAST("Overcast"),
	
	Rain("Rain"),
	
	LightRain("Light Rain"),
	
	Snow("Snow"),
	
	LightSleet("Light Sleet"),
	
	Foggy("Foggy");
	
	
	
	

    private String meteo;

    TYPEMETEO(String meteo) {
        this.meteo = meteo;
    }
    public String toString(){
        return meteo;
    }
    
    /**
     *
     * @param s
     * @return
     */
    static public TYPEMETEO getTypeByString(String s) {
    	for(TYPEMETEO weather : TYPEMETEO.values()) {
    		if(weather.toString().equals(s)) {
    			return weather;
    		}
    	}
    	
    	return null;
    }
}
