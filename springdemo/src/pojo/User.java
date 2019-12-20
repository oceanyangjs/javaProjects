package pojo;

import org.springframework.stereotype.Component;

@Component("u")
public class User {
    private int id;
    private String name = "user 1";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
