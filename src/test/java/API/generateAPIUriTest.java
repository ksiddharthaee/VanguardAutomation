package API;

import org.openqa.selenium.WebDriver;

public abstract class generateAPIUriTest {
    public static String baseUrl;
    public static WebDriver driver;

    static {
        String externalParameterBaseUrl = System.getProperty("host");
        if (externalParameterBaseUrl != null) {
            baseUrl = externalParameterBaseUrl;

        }
    }
}
