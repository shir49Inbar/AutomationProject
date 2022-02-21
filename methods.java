package Selenium_1;

import java.time.Duration;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class methods {
	WebDriver driver;

	//constructor
	methods(WebDriver driver) {
		this.driver=driver;
	}
	//adding items to the cart
	// Randomly choosing 3 of one item and 2 of a second item
	void addingToCart () {
		Random rnd= new Random();
		int index;
		for(int i=0;i<2;i++) {
			//Main page
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			List<WebElement> categories= driver.findElements(By.cssSelector("div[id='SidebarContent']>a")); //finding the different categories of animals
			index= rnd.nextInt(categories.size());// choosing a random number based of the length of the categories list
			categories.get(index).click(); //choosing the number of the category according to the number selected

			//products page
			List<WebElement> productsList= driver.findElements(By.tagName("tr")); //list of all the products
			index=rnd.nextInt(productsList.size()-1)+1; //choosing a random number from 1 to the size-1
			WebElement chosenRow= productsList.get(index); //choosing the row according to the number chosen
			List<WebElement> productNumber= chosenRow.findElements(By.cssSelector("td")); //list of all the cells in the row chosen
			WebElement chosenProductNumber= productNumber.get(0); //getting the cell which holds the link
			chosenProductNumber.findElement(By.tagName("a")).click(); //clicking the product id

			//items page
			List<WebElement> addToCartList= driver.findElements(By.linkText("Add to Cart")); //creating a list of all the "add to cart" buttons
			index=rnd.nextInt(addToCartList.size()); //choosing a random number- the limit is the size of the list
			addToCartList.get(index).click(); //clicking the random "add to cart" button chosen

			//shopping cart
			List<WebElement> inputList= driver.findElements(By.cssSelector("input[type='text'][size='3']")); //listing the number of items in the shopping cart
			inputList.get(inputList.size()-1).clear(); //clearing the field (the default is 1)
			if(i==0) //If it's the first round- quantity is 3
				inputList.get(inputList.size()-1).sendKeys("3"); //writing 3 as quantity
			else //if it's the second round- quantity is 2
				inputList.get(inputList.size()-1).sendKeys("2"); //writing 2 as quantity
			driver.findElement(By.name("updateCartQuantities")).click(); //clicking the update button to make sure the cart is saved
			if(i==0)
				driver.findElement(By.linkText("Return to Main Menu")).click(); //returning to the main page
		}
	}

	void addingCart(String amount) {
		Random rnd= new Random();
		int index;
		//Main page
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		List<WebElement> categories= driver.findElements(By.cssSelector("div[id='SidebarContent']>a")); //finding the different categories of animals
		index= rnd.nextInt(categories.size());// choosing a random number based of the length of the categories list
		categories.get(index).click(); //choosing the number of the category according to the number selected

		//products page
		List<WebElement> productsList= driver.findElements(By.tagName("tr")); //list of all the products
		index=rnd.nextInt(productsList.size()-1)+1; //choosing a random number from 1 to the size-1
		WebElement chosenRow= productsList.get(index); //choosing the row according to the number chosen
		List<WebElement> productNumber= chosenRow.findElements(By.cssSelector("td")); //list of all the cells in the row chosen
		WebElement chosenProductNumber= productNumber.get(0); //getting the cell which holds the link
		chosenProductNumber.findElement(By.tagName("a")).click(); //clicking the product id

		//items page
		List<WebElement> addToCartList= driver.findElements(By.linkText("Add to Cart")); //creating a list of all the "add to cart" buttons
		index=rnd.nextInt(addToCartList.size()); //choosing a random number- the limit is the size of the list
		addToCartList.get(index).click(); //clicking the random "add to cart" button chosen

		//shopping cart
		List<WebElement> inputList= driver.findElements(By.cssSelector("input[type='text'][size='3']")); //listing the number of items in the shopping cart
		inputList.get(inputList.size()-1).clear(); //clearing the field (the default is 1)
		inputList.get(inputList.size()-1).sendKeys(amount); //writing 3 as quantity
		driver.findElement(By.name("updateCartQuantities")).click(); //clicking the update button to make sure the cart is saved
			
	}
	//saving the sub-total on the shopping cart
	double subTotalPrice() {
		List<WebElement> trList=driver.findElements(By.tagName("tr")); //first, saving a list of all the tr's in the page
		WebElement tdLast= trList.get(trList.size()-1);//then, saving the last tr and 
		List<WebElement> tdListofLast=tdLast.findElements(By.tagName("td")); //saving the list of the td's in the last row
		WebElement tdTotal=tdListofLast.get(0); //saving the first td of that row.
		String subTotal=tdTotal.getText();//getting the text of that td 
		subTotal= subTotal.substring(12);//cutting it short
		double lastSubTotal=Double.parseDouble(subTotal); //converting it to double
		return lastSubTotal; //returning the subtotal of the order in the shopping cart
	}
	//continuing to the final summary
	//clicking all the necessary button in order to arrive to the summary page 
	//that shows us the total of the order.
	void proceed() {
		driver.findElement(By.linkText("Proceed to Checkout")).click(); //clicking the checkout button
		driver.findElement(By.name("newOrder")).click(); //clicking the next button
		driver.findElement(By.linkText("Confirm")).click(); //clicking the confirm button
	}
	//saving the total price of the order in the summary
	double totalPrice() {
		List<WebElement> allTrList=driver.findElements(By.tagName("tr")); //first, saving a list of all the tr's in the page
		WebElement lastTr= allTrList.get(allTrList.size()-1); //then, saving the last tr 
		List<WebElement> listOfTh=lastTr.findElements(By.tagName("th")); // saving the list of th in the last tr row
		WebElement selectTh=listOfTh.get(0); //and the first th of that row.
		String total=selectTh.getText(); //getting the text of that th
		total= total.substring(8); //cutting it short 
		double lastTotal=Double.parseDouble(total); //converting it to a double
		return lastTotal; //returning the amount of the total
	}
}
