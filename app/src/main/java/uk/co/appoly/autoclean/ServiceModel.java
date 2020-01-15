package uk.co.appoly.autoclean;

public class ServiceModel {

    public int id;
    public String title;
    public int price;
    public String description;
    public int imageResource;
    public String time;


    public ServiceModel(int id, String title, int price, String description, int imageURL, String time) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.description = description;
        this.imageResource = imageURL;
        this.time = time;
    }

}
