package by.savik.notification.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FruitResponse {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("color")
    private String color;
    @JsonProperty("weight")
    private String weight;

    public FruitResponse() {
    }

    public FruitResponse(Long id, String name, String color, String weight) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.weight = weight;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
