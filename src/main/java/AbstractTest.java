import org.apache.commons.lang.StringUtils;
import org.jfrog.artifactory.client.Artifactory;
import org.jfrog.artifactory.client.ArtifactoryClientBuilder;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class AbstractTest {

    private static String userName = "admin";
    private static String password = "password";
    public static String artifactoryUrl = "http://192.168.33.33:8082/artifactory";
    public Artifactory artifactory;

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite(ITestContext context) {
        artifactory = ArtifactoryConnection.createArtifactory(userName, password, artifactoryUrl);
    }

    @BeforeClass(alwaysRun = true)
    public void beforeClass() throws MalformedURLException {

    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Method method, ITestContext context, Object[] testData) {

    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result, ITestContext context) {

    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {

    }

    @AfterTest(alwaysRun = true)
    public void afterTest() {

    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() {

    }

    public String generateRandomStr() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 6) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }


}
