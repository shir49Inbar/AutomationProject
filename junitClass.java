package Selenium_1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.Duration;
import java.util.List;
import java.util.Random;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class junitClass {
	WebDriver driver;
	methods myMeth;
	
	
	@Before
	public void initDriver() {
		// setting up the web driver and logging in before any test!
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Shir Inbar\\OneDrive\\שולחן העבודה\\QA\\Java\\Selenium\\chromedriver.exe");
		driver= new ChromeDriver();
		myMeth= new methods(driver);
		driver.manage().window().maximize();
		driver.get("https://petstore.octoperf.com/actions/Catalog.action");
		driver.findElement(By.linkText("Sign In")).click();
		driver.findElement(By.name("username")).sendKeys("shir4444");
		driver.findElement(By.name("password")).clear(); //because the password field is not empty
		driver.findElement(By.name("password")).sendKeys("shir1234");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.findElement(By.name("signon")).click();
	}
	
	@Test
	public void verifySignOutButton() {
		//Test that checks if there's a sign out button after logging in
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		System.out.println("verifying the sign out button");
		WebElement signOut=driver.findElement(By.cssSelector("a[href='/actions/Account.action?signoff=']"));
		
		try {
			signOut= driver.findElement(By.linkText("Sign Out"));
		}
		catch (Exception e) {
			System.out.println("no sign out button");
		}
	}
	
	@Test
	public void checkingIfTotalSame() {
		//Test that checks if the sub-total on the shopping cart and the total on the
		//summary are the same
		myMeth.addingCart("3");
		driver.findElement(By.linkText("Return to Main Menu")).click(); //returning to the main page
		myMeth.addingCart("2");
		System.out.println("verifying the subtotal and total on the orders");
		double subtotal= myMeth.subTotalPrice();
		myMeth.proceed();
		double totalt=myMeth.totalPrice();
		assertTrue(subtotal==totalt);
	}
	
	@Test
	public void verifySignInButton() {
		//Test that checks if the sign in button appears after the 
		//user is signing out of the site
		System.out.println("verifying the sign in button");
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		WebElement signOut2= driver.findElement(By.linkText("Sign Out"));
		signOut2.click();
		try {
			driver.findElement(By.linkText("Sign I"));
		}
		catch (Exception e) {
			System.out.println("no sign in button");
		}

	}
	
	@After
	public void tearDown() {
		//quitting the window
		driver.quit();
	}

}
