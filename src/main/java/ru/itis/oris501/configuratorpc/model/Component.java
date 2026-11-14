package ru.itis.oris501.configuratorpc.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Component {
    private int id;
    private String name;
    private int categoryId;
    private BigDecimal price;
    private String imagePath;
    private String socket;
    private String ramType;
    private Integer powerWatts;
    private LocalDateTime createdAt;

    public Component(int id, String name, int categoryId, BigDecimal price, String imagePath, String socket, String ramType, Integer powerWatts) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
        this.price = price;
        this.imagePath = imagePath;
        this.socket = socket;
        this.ramType = ramType;
        this.powerWatts = powerWatts;
    }

    public Component(String name, int categoryId, BigDecimal price, String imagePath, String socket, String ramType, Integer powerWatts) {
        this.name = name;
        this.categoryId = categoryId;
        this.price = price;
        this.imagePath = imagePath;
        this.socket = socket;
        this.ramType = ramType;
        this.powerWatts = powerWatts;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setSocket(String socket) {
        this.socket = socket;
    }

    public void setRamType(String ramType) {
        this.ramType = ramType;
    }

    public void setPowerWatts(Integer powerWatts) {
        this.powerWatts = powerWatts;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getSocket() {
        return socket;
    }

    public String getRamType() {
        return ramType;
    }

    public Integer getPowerWatts() {
        return powerWatts;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
