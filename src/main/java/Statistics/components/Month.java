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

    JAN("January", "Jan"),
    FEB("February", "Feb"),
    MAR("March", "Mar"),
    APR("April", "Apr"),
    MAY("May", "May"),
    JUN("June", "Jun"),
    JUL("July", "Jul"),
    AUG("August", "Aug"),
    SEP("September", "Sep"),
    OCT("October", "Oct"),
    NOV("November", "Nov"),
    DEC("December", "Dec");

    private final String name;
    private final String abbreviation;

    Month(String name, String abbreviation) {
        this.name = name;
        this.abbreviation = abbreviation;
    }

    public String getName() {
        return name;
    }
    
        public String getAbbreviation() {
        return abbreviation;
    }
}
