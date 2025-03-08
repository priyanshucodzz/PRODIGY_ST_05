import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class UItesting {
    WebDriver driver;

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://www.automationpractice.pl/index.php");
    }

    @Test(priority = 1)
    public void addToCart() {
        // Click on a product
        driver.findElement(By.cssSelector(".product-container a.product-name")).click();
        
        // Click "Add to cart"
        driver.findElement(By.id("add_to_cart")).click();
        
        // Wait for cart modal and proceed to checkout
        WebElement proceedBtn = driver.findElement(By.cssSelector(".button-container a[title='Proceed to checkout']"));
        proceedBtn.click();
        
        // Verify cart page
        Assert.assertTrue(driver.getCurrentUrl().contains("order"), "Failed to reach cart page");
    }

    @Test(priority = 2)
    public void proceedToCheckout() {
        // Proceed to checkout from cart
        driver.findElement(By.cssSelector(".cart_navigation a[title='Proceed to checkout']")).click();
        
        // Login
        driver.findElement(By.id("email")).sendKeys("testuser@example.com");
        driver.findElement(By.id("passwd")).sendKeys("Test@123");
        driver.findElement(By.id("SubmitLogin")).click();
        
        // Verify Address page
        Assert.assertTrue(driver.getCurrentUrl().contains("address"), "Address page not loaded");
        
        // Proceed
        driver.findElement(By.name("processAddress")).click();
        
        // Agree to terms and proceed
        driver.findElement(By.id("cgv")).click();
        driver.findElement(By.name("processCarrier")).click();
        
        // Verify Shipping page
        Assert.assertTrue(driver.getCurrentUrl().contains("carrier"), "Shipping page not loaded");
    }

    @Test(priority = 3)
    public void completePurchase() {
        // Select payment method
        driver.findElement(By.className("bankwire")).click();
        
        // Confirm order
        driver.findElement(By.cssSelector("#cart_navigation button[type='submit']")).click();
        
        // Verify order success message
        WebElement successMessage = driver.findElement(By.cssSelector(".cheque-indent strong"));
        Assert.assertEquals(successMessage.getText(), "Your order on My Store is complete.", "Order failed");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
