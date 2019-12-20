import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pojo.Member;
import pojo.User;
import service.UserService;

public class TestSpring {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                new String[]{
                        "applicationContext.xml"
                }
        );
        User u = (User) context.getBean("u");
        System.out.println(u.getName());
        Member m = (Member)context.getBean("m");
        System.out.println(m.getUser().getName() + m.getName());

        UserService userService = (UserService)context.getBean("s");
        userService.doService();
    }
}
