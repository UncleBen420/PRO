/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Statistics.components;

/**
 *
 * @author Edd993Surface
 */
public enum Month {

    JAN("January", "Jan", 31),
    FEB("February", "Feb", 28),
    MAR("March", "Mar", 31),
    APR("April", "Apr", 30),
    MAY("May", "May", 31),
    JUN("June", "Jun", 30),
    JUL("July", "Jul", 31),
    AUG("August", "Aug", 31),
    SEP("September", "Sep", 30),
    OCT("October", "Oct", 31),
    NOV("November", "Nov", 30),
    DEC("December", "Dec", 31);

    private final String name;
    private final String abbreviation;
    private final int nbDays;

    Month(String name, String abbreviation, int nbDays) {
        this.name = name;
        this.abbreviation = abbreviation;
       // this.numericAbbreivation =
        this.nbDays = nbDays;
    }

    public String getName() {
        return name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public int getNbDays() {
        return nbDays;
    }
    
    
}
