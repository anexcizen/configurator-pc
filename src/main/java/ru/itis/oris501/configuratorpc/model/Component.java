package ru.itis.oris501.configuratorpc.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Component {
    private int id;
    private String name;
    private int category_id;
    private BigDecimal price;
    private String image_path;
    private String socket;
    private String ram_type;
    private Integer power_watts;
    private LocalDateTime created_at;

    public Component(int id, String name, int category_id, BigDecimal price, String image_path, String socket, String ram_type, Integer power_watts, LocalDateTime created_at) {
        this.id = id;
        this.name = name;
        this.category_id = category_id;
        this.price = price;
        this.image_path = image_path;
        this.socket = socket;
        this.ram_type = ram_type;
        this.power_watts = power_watts;
        this.created_at = created_at;
    }

    public Component(String name, int category_id, BigDecimal price, String image_path, String socket, String ram_type, Integer power_watts, LocalDateTime created_at) {
        this.name = name;
        this.category_id = category_id;
        this.price = price;
        this.image_path = image_path;
        this.socket = socket;
        this.ram_type = ram_type;
        this.power_watts = power_watts;
        this.created_at = created_at;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public void setSocket(String socket) {
        this.socket = socket;
    }

    public void setRam_type(String ram_type) {
        this.ram_type = ram_type;
    }

    public void setPower_watts(Integer power_watts) {
        this.power_watts = power_watts;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
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

    public int getCategory_id() {
        return category_id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getImage_path() {
        return image_path;
    }

    public String getSocket() {
        return socket;
    }

    public String getRam_type() {
        return ram_type;
    }

    public Integer getPower_watts() {
        return power_watts;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }
}
