package uk.co.appoly.autoclean;

public class ServiceModel {

    public String title;
    public int price;
    public String description;
    public int imageResource;
    public String time;


    public ServiceModel(String title, int price, String description, int imageURL, String time) {
        this.title = title;
        this.price = price;
        this.description = description;
        this.imageResource = imageURL;
        this.time = time;
    }

}
