package part2;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Salsabil Sofyan
 */
public class SignInTest {

    public SignInTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        System.out.println("before class");
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws Exception {
        System.out.println("before");
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of update method, of class SignIn. to verify the behaviour of the
     * method
     */
    @Test
    public void testUpdateHomeFlagTrue() {

        System.out.println("update");
        FilmTheatreApp fta = new FilmTheatreApp();
        DBManager dbManager = new DBManager();

        SignIn signIn = new SignIn(fta, dbManager);
        signIn.getUserNameTextField().setText("testUser");
        signIn.getPasswordTextField().setText("testPassword");

        Data data = new Data();
        data.setHomeFlag(true);

        signIn.update(null, data);

        assertTrue(signIn.isDisplayable());
        // assertTrue(signIn.isDisposed());
        assertTrue(fta.isVisible());
    }

    /**
     * Test of signInFlag to verify the behaviour of the method
     */
    @Test
    public void testUpdateSignInFlagTrue() {
        System.out.println("Sign In Flag update");
        FilmTheatreApp fta = new FilmTheatreApp();
        DBManager dbManager = new DBManager();

        SignIn signIn = new SignIn(fta, dbManager);
        signIn.getUserNameTextField().setText("testUser");
        signIn.getPasswordTextField().setText("testPassword");

        Data data = new Data();
        data.setSignInFlag(true);
        signIn.update(null, data);

        assertEquals(" ", signIn.getUserNameTextField().getText());
        assertEquals(" ", signIn.getPasswordTextField().getText());
        assertEquals("Wrong username or password", signIn.message.getText());

    }

}
