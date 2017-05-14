import com.sun.org.apache.xpath.internal.SourceTree;
import espace.entity.User;
import espace.managers.UserManager;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Stateless
public class UserTests {

    @EJB
    UserManager userManager;

    @Test
    public void createUserTest() {
        /**
        try {
            User user = new User();
            user.setUserName("unit_test_user");
            userManager.addUser(user);
        } catch (Exception e ) {
            e.printStackTrace();
            //System.out.println(e.);
            fail();
        }

        try {
            User user = new User();
            user.setUserName("unit_test_user");
            userManager.addUser(user);
        } catch (Exception e ) {
            fail("This is goood");
        }
         **/
    }


}
