package animalType;

/**
 * Enumeration des type d'animaux possible
 * @author Groupe PRO B-9
 */
public enum AnimalType {
    
   
    GRENOUILLE("Grenouille"),
    CRAPAUD("Crapaud"),
    TRITON("Triton"),
    AUTRE("Autre");

    private final String animalName;

    AnimalType(String animalName) {
        this.animalName = animalName;
    }
    
    public String getName(){
        return animalName;
    }


}
