package Statistics.handler;

import Statistics.components.Month;
import animalType.AnimalType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringJoiner;
import java.util.TreeMap;
import Statistics.components.Image;
import meteoAPI.MeteoAPI;
import meteoAPI.MeteoPerDay;

/**
 * Sert a enregistrer toutes les donnees necessaires a la generation des
 * statistiques
 * @author Groupe PRO B-9
 */
public class StatisticsHandler {
    
    private static final int HOURSINADAY = 24;
    private static final String MIN = "min";
    private static final String MAX = "max";
    private ArrayList<Image> images = new ArrayList<>();

    private List<MeteoPerDay> meteo;
    private Map<AnimalType, Integer> animalTypeCounter = new HashMap<>();
    private Map<String, Integer> cameraObservations = new HashMap<>();
    private Map<String, Integer> dateObservations = new HashMap<>();
    private Map<String, Integer> sequenceObservations = new HashMap<>();
    private Map<Month, Integer> monthlyObservations = new HashMap<>();
    private Map<Month, List<Integer>> monthlyObservationsByAnimalType = new TreeMap<Month, List<Integer>>(
            (Month o1, Month o2) -> o1.compareTo(o2));

    private Map<Month, Map<Integer, Integer>> dailyObservations = new HashMap<>();
    private Map<Month, Map<Integer, List<Integer>>> dailyObservationsByAnimalType = new HashMap<>();

    private Map<Month, Map<Integer, Map<Integer, Integer>>> hourlyObservations = new HashMap<>();
    private Map<Month, Map<Integer, Map<Integer, List<Integer>>>> hourlyObservationsByAnimalType = new HashMap<>();

    private int nbImages;
    private int nbSequences;
    private int nbTaggedImages;
    private int nbTaggedSequences;
    private int totalNbOfAnimals;
    
    List<String> cameraMaxKeys;
    List<String> cameraMinKeys;
    List<String> dateMaxKeys;
    List<String> dateMinKeys;
    List<String> sequenceMaxKeys;
    List<String> sequenceMinKeys;
    
    /**
     * Constructeur
     */
    public StatisticsHandler() {
        initiasize();
        images.clear();
        MeteoAPI mAPI = new MeteoAPI();
        meteo = mAPI.getList();
        //System.out.println(meteo.toString());
    }

    /**
     * Initialisation des attributs 
     */
    private void initiasize() {   
  
        for (AnimalType a : AnimalType.values()) {
            // initialisation de la liste pour animalTypeCounter�a 0 pour toute categorie categorie d'animaux
            animalTypeCounter.put(a, 0);
        }

        for (Month a : Month.values()) {
            // delcaration et initialisation de la liste pour monthlyObservationsByAnimalType a�0 pour toute categorie d'animaux
            List<Integer> list_byType_ForDaily = createListOfAnimals();
            
            // declaration du mappage entre les observations des animaux observ�s et les jours d'un mois
            Map<Integer, Integer> mapDay_Observations = new HashMap();
            
            // declaration du mappage entre les observations des animaux PAR TYPE observ�s et les jours d'un mois
            Map<Integer, List<Integer>> mapDay_Observations_byType = new HashMap();
            
            //declaration du mappage entre le jour d'un mois et les observations d'un animal par heure
            Map<Integer, Map<Integer,Integer>> mapMonth_HourDay = new HashMap();
            
            //declaration du mappage entre le jour d'un mois et les observations d'un animal par heure
            Map<Integer, Map<Integer, List<Integer>>> mapMonth_HourDay_byType = new HashMap();
            
            
            for (int jour = 1; jour <= a.getNbDays(); jour++) {
           
                // initialisation � 0 du mappage journalier 
                mapDay_Observations.put(jour, 0);
                
                
                // delcaration et initialisation de la liste pour dailyObservationsByAnimalType a�0 pour toute categorie d'animaux
                List<Integer> listAnimals_byType_forDaily = createListOfAnimals(); 
                mapDay_Observations_byType.put(jour, listAnimals_byType_forDaily);
             
               
                // initialisation de le mappage pour connecter les animaux observ�s avec les heures d'un jour
                Map <Integer, Integer> mapHour_Observations = new HashMap<>();
                
                // initialisation de le mappage pour connecter les animaux observ�s PAR TYPE avec les heures d'un jour
                Map<Integer, List<Integer>> mapHour_Observations_byType = new HashMap();

                for (int hour = 0; hour < HOURSINADAY; hour++){
                    // initialisaiton � 0 du mappage horaire
                    mapHour_Observations.put(hour,0);
                    
                    //initialisation du mappage PAR TYPE des animaux observ�s par heure
                    List<Integer> listAnimals_byType_forHourly = createListOfAnimals(); 
                    mapHour_Observations_byType.put(hour, listAnimals_byType_forHourly);
                }  
                mapMonth_HourDay.put(jour,mapHour_Observations);
                
                mapMonth_HourDay_byType.put(jour, mapHour_Observations_byType);
                
            } 
            
            hourlyObservations.put(a, mapMonth_HourDay);
            
            hourlyObservationsByAnimalType.put(a, mapMonth_HourDay_byType);
            
            dailyObservations.put(a,mapDay_Observations);
            
            dailyObservationsByAnimalType.put(a,mapDay_Observations_byType);
                        
            //initialisation du mappage monthlyObservations a 0 comme valeur
            monthlyObservations.put(a, 0);
            //initialisation du mappage monthlyObservationsByAnimalType a liste comme valeur
            monthlyObservationsByAnimalType.put(a, list_byType_ForDaily);
            
        }

        totalNbOfAnimals = 0;
        nbImages = 0;
        nbSequences = 0;
        nbTaggedImages = 0;
        nbTaggedSequences = 0;
    }

    /**
     * Enregistre combien d'animaux il y a pour chaque type dans une liste
     * @return la liste generee
     */
    private List<Integer> createListOfAnimals() {

        List<Integer> animalList = new ArrayList<>();
        for (AnimalType b : AnimalType.values()) {
            animalList.add(0);
        }
        return animalList;
    }
    
    
    /**
     * Cherche la valeur minimale ou maximale de la map
     * @param map la map a sonder
     * @param type min ou max
     * @return la valeur minimale ou maximale
     */
    private Integer findLimitValueInMap(Map map, String type) {
        Integer result = 0;
        
        if (type.equals(MIN)) {
            result = (Integer) Collections.min(map.values());
        } else if (type.equals(MAX)) {
            result = (Integer) Collections.max(map.values());
        }
        return result;
    }

    /**
     * Cherche la cle donnant la valeur minimale ou maximale de la map
     * @param map la map a sonder
     * @param value la valeur min ou max de la map
     * @return la liste des cles trouvees
     */
    private List<String> findLimitKeysInMap(Map map, int value) {
        List<String> keys = new ArrayList<>();
        
        for (Object iter : map.entrySet()) {
            Entry<String, Integer> entry = (Entry<String, Integer>) iter;
            if (entry.getValue() == value) {
                keys.add(entry.getKey());
            }
        }
        return keys;
    }

    private String returnDataString(List<String> list) {
        StringJoiner joiner = new StringJoiner(" - ");
        
        for (String iter : list) {
            joiner.add(iter);
        }
        return joiner.toString();
    }

    /**
     * Insere une valeur et potentiellement sa cle dans une map
     * @param map la map ou faire l'insertion
     * @param key la cle
     * @param value la valeur
     */
    private void countInMap(Map map, Object key, int value) {
        if (map.containsKey(key)) {
            map.put(key, (Integer) (map.get(key)) + value);
        } else {
            map.put(key, value);
        }
    }
    
    /**
     * Compte les observations pour une camera
     * @param cameraName le nom de la camera
     * @param numberOfTagsForAnImage la valeur a mettre dans la cle de la camera
     */
    public void countCameraObservation(String cameraName, int numberOfTagsForAnImage) {
        this.countInMap(cameraObservations, cameraName, numberOfTagsForAnImage);
    }

    /**
     * Compte les observations pour un jour
     * @param dayName le nom du jour
     * @param numberOfTagsForAnImage la valeur a mettre dans la cle du jour
     */
    public void countDaysObservation(String dayName, int numberOfTagsForAnImage) {
        this.countInMap(dateObservations, dayName, numberOfTagsForAnImage);
    }

    /**
     * Compte les observations pour une sequence
     * @param sequenceName le nom de la sequence
     * @param numberOfTagsForAnImage la valeur a mettre dans la cle de la sequence
     */
    public void countSequenceObservation(String sequenceName, int numberOfTagsForAnImage) {
        this.countInMap(sequenceObservations, sequenceName, numberOfTagsForAnImage);
    }
    
    /**
     * Compte les observations pour un mois
     * @param month le nom du mois
     * @param numberOfTagsForAnImage la valeur a mettre dans la cle du mois
     */
    public void countMonthlyObservation(Month month, int numberOfTagsForAnImage) {
        this.countInMap(monthlyObservations, month, numberOfTagsForAnImage);
    }

    /**
     * Compte les observations pour un jour
     * @param month le nom du mois
     * @param day le jour
     * @param numberOfTagsForAnImage la valeur a mettre dans la cle du jour
     */
    public void countDailyObservation(Month month, int day, int numberOfTagsForAnImage) {
        Map<Integer, Integer> mapForThisMonth = dailyObservations.get(month);
        this.countInMap(mapForThisMonth, day, numberOfTagsForAnImage);
    }
    
    /**
     * Compte les observations pour une heure
     * @param month le nom du mois
     * @param day le jour
     * @param hour l'heure
     * @param numberOfTagsForAnImage la valeur a mettre dans la cle de l'heure
     */
    public void countHourlyObservation(Month month, int day, int hour, int numberOfTagsForAnImage) {
         Map<Integer, Map<Integer, Integer>> mapForThisMonth= hourlyObservations.get(month);
         Map<Integer, Integer> mapForThisDay = mapForThisMonth.get(day);
        this.countInMap(mapForThisDay, hour, numberOfTagsForAnImage);
    }
    
    /**
     * Compte le nombre d'observations totales d'un animal donne (peuple une map)
     * @param animal l'animal a traiter
     */
    public void countTotalObservationsByAnimalType(String animal) {
        for (AnimalType a : AnimalType.values()) {
            if (animal.equals(a.getName())) {
                animalTypeCounter.put(a, animalTypeCounter.get(a) + 1);
            }
        }
    }

    /**
     * Compte le nombre d'observations d'un animal donne (peuple une map) pour
     * un mois donne
     * @param animal l'animal a traiter
     * @param month le mois a traiter
     */
    public void countMonthlyObservationsByAnimalType(String animal, Month month) {
        for (Month a : Month.values()) {
            if (month.equals(a)) {
                for (AnimalType b : AnimalType.values()) {                 
                    if (animal.equals(b.getName())) {                       
                        List<Integer> list = monthlyObservationsByAnimalType.get(a);
                        list.set(b.ordinal(), (list.get(b.ordinal()) + 1));
                    }
                }
            } 
        }
    }
    
    /**
     * Compte le nombre d'observations d'un animal donne (peuple une map) pour
     * un jour donne
     * @param animal l'animal a traiter
     * @param month le mois a traiter
     * @param day le jour a traiter
     */
    public void countDailyObservationsByAnimalType(String animal, Month month, int day) {
        for (Month a : Month.values()) {
            if (month.equals(a)) {
                for (AnimalType b : AnimalType.values()) {                 
                    if (animal.equals(b.getName())) {   
                        Map<Integer, List<Integer>> mapForThisMonth = dailyObservationsByAnimalType.get(a);
                        List<Integer> list = mapForThisMonth.get(day);
                        list.set(b.ordinal(), (list.get(b.ordinal()) + 1));
                    }
                }
            }
        }
    }

    /**
     * Compte le nombre d'observations d'un animal donne (peuple une map) pour
     * une heure donnee
     * @param animal l'animal a traiter
     * @param month le mois a traiter
     * @param day le jour a traiter
     * @param hour l'heure a traiter
     */
    public void countHourlyObservationsByAnimalType(String animal, Month month, int day, int hour) {
        for (Month a : Month.values()) {
            if (month.equals(a)) {
                for (AnimalType b : AnimalType.values()) {
                    if (animal.equals(b.getName())) {
                        Map<Integer, Map<Integer, List<Integer>>> mapForThisMonth = hourlyObservationsByAnimalType.get(a);
                        Map<Integer, List<Integer>> mapForThisDay = mapForThisMonth.get(day);
                        List<Integer> list = mapForThisDay.get(hour);
                        list.set(b.ordinal(), (list.get(b.ordinal()) + 1));
                    }
                }
            }
        }
    }

    /**
     * Genere les observations min et max pour les cameras, les sequences et les jours
     * Si aucune image possede un tag, un message alternatif est affiche
     */
    public void analyzeData() {
        
        if (nbTaggedImages != 0) {
        cameraMinKeys = findLimitKeysInMap(cameraObservations, findLimitValueInMap(cameraObservations, MIN));
        cameraMaxKeys = findLimitKeysInMap(cameraObservations, findLimitValueInMap(cameraObservations, MAX));
        dateMinKeys = findLimitKeysInMap(dateObservations, findLimitValueInMap(dateObservations, MIN));
        dateMaxKeys = findLimitKeysInMap(dateObservations, findLimitValueInMap(dateObservations, MAX));
        sequenceMinKeys = findLimitKeysInMap(sequenceObservations, findLimitValueInMap(sequenceObservations, MIN));
        sequenceMaxKeys = findLimitKeysInMap(sequenceObservations, findLimitValueInMap(sequenceObservations, MAX));
        } else {
             cameraMinKeys = new ArrayList<String>();
             cameraMinKeys.add("no tagged images");
             
             cameraMaxKeys = new ArrayList<String>();
             cameraMaxKeys.add("no tagged images");
             
             dateMinKeys = new ArrayList<String>();
             dateMinKeys.add("no tagged images");
             
             dateMaxKeys = new ArrayList<String>();
             dateMaxKeys.add("no tagged images");
             
             sequenceMinKeys = new ArrayList<String>();
             sequenceMinKeys.add("no tagged images");
                  
             sequenceMaxKeys = new ArrayList<String>();
             sequenceMaxKeys.add("no tagged images");
        }
    }

   public void addImage(Image image) {
        images.add(image);
    }

    public void addNbAnimals(int n) {
        this.totalNbOfAnimals += n;
    }

    public void addNbImages(int n) {
        this.nbImages += n;
    }

    public void addNbSequences(int n) {
        this.nbSequences += n;
    }

    public void addNbTaggedSequences(int n) {
        this.nbTaggedSequences += n;
    }
    
     public int getNbImages() {
        return nbImages;
    }

    public int getNbTaggedImages() {
        return images.size();
    }

    public int getNbSequences() {
        return nbSequences;
    }

    public int getNbTaggedSequences() {
        return nbTaggedSequences;
    }
    
    
    
    
    
    
    public Map<AnimalType, Integer> getAnimalTypeCounter() {
        return this.animalTypeCounter;
    }

    public void setTotalNbOfAnimals(int n) {
        this.totalNbOfAnimals = n;
    }

    public int getTaggedAnimals() {
        return this.totalNbOfAnimals;
    }
    
    public int getTaggedImages() {
        return images.size();
    }

    public String getMostUsedCamera() {
        return returnDataString(cameraMaxKeys);
    }

    public String getLeastUsedCamera() {
        return returnDataString(cameraMinKeys);
    }

    public String getMostFrequentDate() {
        return returnDataString(dateMaxKeys);
    }

    public String getLeastFrequentDate() {
        return returnDataString(dateMinKeys);
    }
    
    public String getMostTaggedSequence() {
        return returnDataString(sequenceMaxKeys);
    }

    public String getLeastTaggedSequence() {
        return returnDataString(sequenceMinKeys);
    }

    public int getTaggedSequenceNumber() {
        return sequenceObservations.size();
    }

    public int getAnimalNbByMonth(Month month) {
        return monthlyObservations.get(month);
    }

    public Map<Integer, Integer> getAnimalNbByHourMap(Month month, int day) {
        return hourlyObservations.get(month).get(day);
    }

    public Map<Integer, Integer> getAnimalNbByDayMap(Month month) {
        return dailyObservations.get(month);
    }

    public List getAnimalNbByMonthByType(Month month) {
        return monthlyObservationsByAnimalType.get(month);
    }

    public Map<Integer, List<Integer>> getAnimalTypeByDayMap(Month month) {
        return dailyObservationsByAnimalType.get(month);
    }

    public Map<Integer, List<Integer>> getAnimalTypeByHourMap(Month month, int day) {
        return hourlyObservationsByAnimalType.get(month).get(day);
    }

}
