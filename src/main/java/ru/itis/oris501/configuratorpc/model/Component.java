package ru.itis.oris501.configuratorpc.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Component {
    private final int id;
    private final String name;
    private final int category_id;
    private final BigDecimal price;
    private final String image_path;
    private final String socket;
    private final String ram_type;
    private final Integer power_watts;
    private final LocalDateTime created_at;

    public Component(int id, String name, int category_id, BigDecimal price, String image_path, String socket, String ram_type, int power_watts, LocalDateTime created_at) {
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

    public int getId() {
        return id;
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

    public int getPower_watts() {
        return power_watts;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }
}
