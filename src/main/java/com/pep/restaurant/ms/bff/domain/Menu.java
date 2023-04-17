package com.pep.restaurant.ms.bff.domain;

public class Menu {
    private String language;

    private String uid;

    /**
     * Get Menu language.
     * @return menu language.
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Set menu language.
     * @param language language.
     */
    public void setLanguage(final String language) {
        this.language = language;
    }

    /**
     * Builder Menu for language.
     * @param language language to build.
     * @return menu with language.
     */
    public Menu language(final String language){
        this.language = language;
        return this;
    }

    /**
     * Get Menu uid.
     * @return menu uid.
     */
    public String getUid() {
        return uid;
    }

    /**
     * Set menu uid.
     * @param uid uid.
     */
    public void setUid(final String uid) {
        this.uid = uid;
    }

    /**
     * Builder Menu for uid.
     * @param uid uid to build.
     * @return menu with uid.
     */
    public Menu uid(final String uid){
        this.uid = uid;
        return this;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "language='" + language + '\'' +
                ", uid='" + uid + '\'' +
                '}';
    }
}
