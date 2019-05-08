package meteoAPI;

public enum TYPEMETEO {
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
    
    static public TYPEMETEO getTypeByString(String s) {
    	for(TYPEMETEO weather : TYPEMETEO.values()) {
    		if(weather.toString().equals(s)) {
    			return weather;
    		}
    	}
    	
    	return null;
    }
}
