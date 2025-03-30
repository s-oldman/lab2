package com.cmsy265;

/**
 * TV class file: Just 1 String, plus some boilerplate.
 * 
 * @author Sam Young
 * @version 1.1
 * @since 2025-02-21
 */
class TV implements Constants {

    /**
     * One private variable; String for TV's ID number (alphanumeric).
     */
    private String id;

    /**
     * Default constructor, sets id to NULL.
     */
    public TV() {
        this.id = null;
    }

    /**
     * Full constructor, sets id to given String.
     * @param String
     * @return TV
     */
    public TV(String newid) {
        this.id = newid;
    }

    /**
     * Getter; returns ID.
     * @return String
     */
    public String get() {
        return this.id;
    }

    /**
     * Setter; overwrites ID.
     * @param id
     */
    public void set(String id) {
        this.id = id;
    }

    /**
     * toString; returns TV id with display prefix.
     * @return String
     */
    @Override
    public String toString() {
        return "TV id: " + this.id;
    }

    /**
     * @description Build the next TV ID from the previous one. (For adding new TVs.)
     * @param id Current ID number.
     * @return String containing the new ID number (the current one, +1).
     */
    public static String nextId(String id) {

        int idNum = Integer.parseInt(id.substring(7));
        idNum++;
        return id.substring(0,7) + idNum;

    }

}
