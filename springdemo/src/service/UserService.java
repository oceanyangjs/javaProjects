package service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service("s")
public class UserService {
    public void doService(){
        System.out.println("do service");
    }
}
