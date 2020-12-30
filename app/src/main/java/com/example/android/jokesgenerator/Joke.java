package com.example.android.jokesgenerator;

public class Joke {


    //category of joke
    private String category;

    //the setup of the joke
    private String setup;

    //the delivery of joke
    private String delivery;


    public Joke(String category, String setup, String delivery) {
        this.category = category.toUpperCase();
        this.setup = setup;
        this.delivery = delivery;
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

    @Override
    public String toString() {
        return "Joke{" +
                "category='" + category + '\'' +
                ", setup='" + setup + '\'' +
                ", delivery='" + delivery + '\'' +
                '}';
    }
}
