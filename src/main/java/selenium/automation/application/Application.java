package selenium.automation.application;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import selenium.automation.application.commons.Constants;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import static java.lang.Thread.sleep;

public class Application {
    static Logger logger = Logger.getLogger(Application.class.getName());


    public static void main(String[] args) throws InterruptedException, IOException {

        File chromeDriver = placeDriverFile();
        System.setProperty(Constants.CHROME_WEB_DRIVER_ENV_VARIABLE_NAME, chromeDriver.getAbsolutePath());

        WebDriver webDriver = new ChromeDriver();
        webDriver.get(Constants.URL);
        sleep(Constants.TIME_TO_LOGIN);
        logger.info("Starting the Test");
        WebElement pincode = webDriver.findElement(By.id(Constants.PINCODE_FIELD_ID));
        WebElement submit = webDriver.findElement(By.tagName(Constants.PINCODE_SUBMIT_TAG_NAME));
        List<String> pincodes = getPincodes();
        Random random = new Random();
        while (true) {
            String pin = pincodes.get(random.nextInt(3));
            logger.info("Using Pin: " + pin);
            logger.info("Sending data to pincode...");
            pincode.sendKeys(pin);
            logger.info("Clicking on Submit Button...");
            submit.click();
            int waitTime = getRandomWaitTime();
            logger.info("Waiting for " + waitTime + " milliseconds");
            sleep(waitTime);
            logger.info("Clearing the pincode...");
            pincode.clear();
        }
    }

    private static List<String> getPincodes() {
        List<String> pincodes = new ArrayList<>();
        pincodes.add("411001");
        pincodes.add("411028");
        pincodes.add("411004");
        pincodes.add("411013");
        return pincodes;
    }

    private static File placeDriverFile() throws IOException {
        ClassLoader classLoader = Application.class.getClassLoader();
        URL resource = classLoader.getResource("chromedriver.exe");
        File f = new File("Driver");
        if (!f.exists()) {
            f.mkdirs();
        }
        File chromeDriver = new File("Driver" + File.separator + "chromedriver.exe");
        if (!chromeDriver.exists()) {
            chromeDriver.createNewFile();
            FileUtils.copyURLToFile(resource, chromeDriver);
        }
        return chromeDriver;
    }
    private static int getRandomWaitTime() {
        int max = 5;
        int min = 2;
        double random = Math.random() * (max - min + 1) + min;
        int waitTime = (int) (random * 60000);
        return waitTime;
    }
}