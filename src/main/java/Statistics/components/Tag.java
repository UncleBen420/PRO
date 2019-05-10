/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Statistics.components;

/**
 *
 * @author Marion
 */
public class Tag {

    private String animalType;
    private boolean isMale;
    private double size;
    private boolean isEnteringTunnel;

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

