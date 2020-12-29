package com.example.android.jokesgenerator;

public class Joke {


    //category of joke
    private String category;

    //the setup of the joke
    private String setup;

    //the delivery of joke
    private String delivery;


    //flag
    private boolean nsfw;

    //flag
    private boolean religious;

    //flag
    private boolean political;

    //flag
    private boolean racist;

    //flag
    private boolean sexist;

    //flag
    private boolean explicit;

    public Joke(String category, String setup, String delivery, boolean nsfw, boolean religious, boolean political, boolean racist, boolean sexist, boolean explicit) {
        this.category = category;
        this.setup = setup;
        this.delivery = delivery;
        this.nsfw = nsfw;
        this.religious = religious;
        this.political = political;
        this.racist = racist;
        this.sexist = sexist;
        this.explicit = explicit;
    }

    public String getCategory() {
        return category;
    }

    public String getSetup() {
        return setup;
    }

    public String getDelivery() {
        return delivery;
    }

    public boolean isNsfw() {
        return nsfw;
    }

    public boolean isReligious() {
        return religious;
    }

    public boolean isPolitical() {
        return political;
    }

    public boolean isRacist() {
        return racist;
    }

    public boolean isSexist() {
        return sexist;
    }

    public boolean isExplicit() {
        return explicit;
    }

    @Override
    public String toString() {
        return "Joke{" +
                "category='" + category + '\'' +
                ", setup='" + setup + '\'' +
                ", delivery='" + delivery + '\'' +
                ", nsfw=" + nsfw +
                ", religious=" + religious +
                ", political=" + political +
                ", racist=" + racist +
                ", sexist=" + sexist +
                ", explicit=" + explicit +
                '}';
    }
}
