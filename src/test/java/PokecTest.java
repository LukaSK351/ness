import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class PokecTest {
    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;

    private final String BASE_URL = "https://pokec.azet.sk";

    @Before
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
    }

    @Test
    public void testConnectToPokecUrl(){
        driver.get(BASE_URL);
        String URL = driver.getCurrentUrl();
        Assert.assertEquals(URL, "https://pokec.azet.sk/" );
    }

    @Test
    public void testPokecLoginBadPass(){
        driver.get(BASE_URL);
        driver.findElement(By.xpath("//button[@aria-label = 'Súhlas']")).click();

        var loginName = driver.findElement(By.xpath("//div[@class = 'sc-jTzLTM cnQeMi']/input"));
        var loginPass = driver.findElement(By.xpath("//div[@class = 'sc-cSHVUG cBUnPO']/input"));

        loginName.sendKeys("ferino");
        loginPass.sendKeys("badPass");

        driver.findElement(By.xpath("//button[@class ='Button-sc-1fngo4c-0 sc-iwsKbI gQISxy']")).click();

        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

        driver.findElement(By.xpath("//p[@class ='sc-ckVGcZ ibKsds']"));
    }

    @Test
    public void testPokecLoginGoodPass(){
        loginToPokec();
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        driver.findElement(By.id("nickBull"));
    }

    @Test
    public void testPokecLogout(){
        loginToPokec();

        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//a[@gg='logout']")).click();
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

        Assert.assertEquals("https://pokec.azet.sk/", driver.getCurrentUrl());
    }

    public void loginToPokec(){
        //put correct name here
        var name = "login";
        // put correct pass here
        var pass = "pass";

        driver.get(BASE_URL);
        driver.findElement(By.xpath("//button[@aria-label = 'Súhlas']")).click();

        var loginName = driver.findElement(By.xpath("//div[@class = 'sc-jTzLTM cnQeMi']/input"));
        var loginPass = driver.findElement(By.xpath("//div[@class = 'sc-cSHVUG cBUnPO']/input"));

        loginName.sendKeys(name);
        loginPass.sendKeys(pass);

        driver.findElement(By.xpath("//button[@class ='Button-sc-1fngo4c-0 sc-iwsKbI gQISxy']")).click();
    }

    @After
    public void cleanUp(){
        driver.quit();
    }
}
