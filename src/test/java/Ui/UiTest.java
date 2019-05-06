package Ui;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UiTest extends generateUrlTest {

    // public static String baseUrl;
    //  public static WebDriver driver;
    public static String url_visual = "/au/portal/homepage.jsp";
    public static String prodTypeId = "vanguardfunds-assetclass-filtering-checkboxes";
    public static String checkBox1 = "F0AUS05IL9";
    public static String addFund1 = "addButton0";
    public static String checkBoxName1 = "F00000XDKX";
    public static String checkBoxName2 = "F0AUS05IK2";
    public static String checkBoxName3 = "F00000O76Q";
    public static String checkBoxName4 = "F000002TI7";


    @BeforeClass
    public static void generateUrl() {

        String externalParameterBaseUrl = System.getProperty("host");
        if (externalParameterBaseUrl != null) {
            baseUrl = externalParameterBaseUrl;


        }
        //  baseUrl = baseUrl + url_visual;  //Seeing issues with baseurl being generated in generateUrlTest from pom.xml so hardcoging it due to lack of time.
        baseUrl = "https://www.vanguardinvestments.com.au" + url_visual;
    }

    @Before
    public void loadDriver() {

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        try {
            driver.get(baseUrl);
        } catch (NoClassDefFoundError ex) {
            System.out.println("error: " + ex.getStackTrace());
        }


    }


    @Test
    public void loadHomePage() {

        driver.manage().window().maximize();
        String pageTitle = driver.getTitle();
        Assert.assertEquals("Low-cost managed funds and ETFs", pageTitle);
        WebElement element = findElementId("Individual & SMSF investors"); //validating if the hyper link text is present
        String text = element.getText();
        Assert.assertEquals("Individual & SMSF investors", text);
        element.click();
        WebElement element1 = findElementId("Fund compare");
        element1.click();
        for (String winHandle : driver.getWindowHandles()) { //Gets the new window handle
            System.out.println(winHandle);
            driver.switchTo().window(winHandle);        // switch focus of WebDriver to the next found window handle (that's your newly opened window)
        }

        String y = driver.getTitle();
        shouldShowMessageInTag("Compare products", "h2");
        clickButtonWithId(addFund1); //clicking add fund
        // WebDriverWait wait = new WebDriverWait(driver, 45);
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(addFund1)));
        driver.findElement(By.id(prodTypeId)).click();
        WebElement tcRadioButton = driver.findElement(By.id(prodTypeId));
        Assert.assertTrue(tcRadioButton.isEnabled()); // validating ALL is clicked
        WebElement tcCheckBox = driver.findElement(By.name("F0AUS05IL9"));
        tcCheckBox.click();
        //clickButtonWithId("addFund");
        // clickButtonWithId(addFund2);
        WebElement tcRadioButton1 = driver.findElement(By.id(prodTypeId));
        Assert.assertTrue(tcRadioButton1.isEnabled()); // validating ALL is clicked
        WebElement tcCheckBox1 = driver.findElement(By.name(checkBoxName1));
        tcCheckBox1.click();
        WebElement tcCheckBox2 = driver.findElement(By.name(checkBoxName2));
        tcCheckBox2.click();
        WebElement tcCheckBox3 = driver.findElement(By.name(checkBoxName3));
        tcCheckBox3.click();
        WebElement tcCheckBox4 = driver.findElement(By.name(checkBoxName4));
        tcCheckBox4.click();
        clickButtonWithId("compareFundBtn");

    }


    public WebElement findElementId(String text) {

        WebDriverWait wait = new WebDriverWait(driver, 45);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(text)));
        return element;
    }

    protected static void shouldShowMessageInTag(String expectedResponse, String tag) {
        WebDriverWait wait = new WebDriverWait(driver, 45);
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName(tag)));
        System.out.println(message.getText());
        assertTrue("Expected to contain: '" + expectedResponse + "' but was: '" + message.getText() + "'",
                message.getText().contains(expectedResponse));
    }

    protected static void clickButtonWithId(String buttonText) {
        WebElement button = waitForButtonWithText(buttonText);
        Actions actions = new Actions(driver);
        actions.moveToElement(button).click().perform();
        System.out.println("          Button '" + button.getText().replace("\n", " ").replace("\r", " ") + "' is clicked");
    }

    protected static WebElement waitForButtonWithText(String buttonText) {
        WebDriverWait wait = new WebDriverWait(driver, 45);
        String buttonXpath = "//button[contains(@id,'" + buttonText + "')]";
        System.out.println(buttonXpath);
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(buttonXpath)));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(buttonXpath)));
        WebElement element = null;
        element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(buttonXpath)));
        return element;
    }


    @After
    public void closeDriver() {
        driver.quit();
    }

}

