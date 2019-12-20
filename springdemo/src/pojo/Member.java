package pojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component("m")
public class Member {
    private int id;
    private String name = "member 1";
//    @Autowired
    @Resource(name = "u")
    private User user;

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

    public User getUser() {
        return user;
    }

//    @Autowired
    public void setUser(User user) {
        this.user = user;
    }
}
