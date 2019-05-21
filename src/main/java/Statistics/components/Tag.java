package Statistics.components;

/**
 * Represente un tag sur une image
 * @author Groupe PRO B-9
 */
public class Tag {

    private String animalType;
    private boolean isMale;
    private double size;
    private boolean isEnteringTunnel;

    /**
     * Constructeur
     */
    public Tag() {
    }

    public void setTypeAnimal(String animalType) {
        this.animalType = animalType;
    }
    
    public String getAnimalType() {
       return animalType;
    }
    
    public void setIsMale(boolean isMale) {
        this.isMale = isMale;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public void setIsEnteringTunnel(boolean isEnteringTunnel) {
        this.isEnteringTunnel = isEnteringTunnel;
    }
    
    public String toString(){
     return "{" + animalType + ", " + isMale + ", " + size + ", "+ isEnteringTunnel + "}";
    }
    
}

