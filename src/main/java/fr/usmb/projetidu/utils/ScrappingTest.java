package fr.usmb.projetidu.utils;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ScrappingTest {
	
	public static void main(String[] args) throws IOException {
		
		login();
}
	
	@SuppressWarnings("deprecation")
	public static void login() {
				
		String log = "nicolath";
		String pass = "ao61na76&*Tao61na76";
		
		ChromeOptions options = new ChromeOptions();
	    // setting headless mode to true.. so there isn't any ui
		options.setAcceptInsecureCerts(true);
        options.setHeadless(true);

	    // Create a new instance of the Chrome driver
	    WebDriver driver = new ChromeDriver(options);
		
		driver.manage().window().maximize();
		driver.get("https://cas-uds.grenet.fr/login?service=https%3A%2F%2Fintranet.univ-smb.fr%2F");
		
		WebElement username = driver.findElement(By.id("username"));
		WebElement password = driver.findElement(By.id("password"));
		WebElement login = driver.findElement(By.className("btn-submit"));
		
		username.sendKeys(log);
		password.sendKeys(pass);
		login.click();
		
		driver.get("https://monprofil.univ-smb.fr/profil/fr/" + log);
		
		// Trouver les éléments qui contiennent les informations
	    List<WebElement> indentificationElement = driver.findElements(By.className("table"));
	   // WebElement mailElement = (WebElement) driver.findElements(By.className("table"));
	    
	    // Extraire les informations

	    String t = "";
	    for(WebElement we : indentificationElement) {
	    	 t += we.getText() + " ";
	    }
	    
	    String[] values = getStudentIdentification(t);
	    
	    //String mail = mailElement.getText();
	    
	    // Afficher les informations
	    
	    System.out.println(values[0]);
	    System.out.println(values[1]);
	    System.out.println(values[2]);
	    System.out.println(values[3]);
	    System.out.println(values[4]);
	    System.out.println(values[5]);
		
	}
	
	
	private static String[] getStudentIdentification(String texte) {
		
		String[] splited = texte.split("\\s+");
		
		return new String[]{splited[3], splited[6], splited[13], splited[15], splited[30], splited[51]};
		
		
	}
	
}
