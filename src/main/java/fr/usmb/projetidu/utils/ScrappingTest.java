package fr.usmb.projetidu.utils;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ScrappingTest {
	
	public static void main(String[] args) throws IOException {
		
		try {
			loginToPolytechIntranet();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	@SuppressWarnings("deprecation")
	public static void loginToUSMBIntranet() {
				
		String login = "";
		String pass = "";
		
		ChromeOptions options = new ChromeOptions();
		options.setAcceptInsecureCerts(true);
        options.setHeadless(true);

	    WebDriver driver = new ChromeDriver(options);
		
		driver.get("https://cas-uds.grenet.fr/login?service=https%3A%2F%2Fintranet.univ-smb.fr%2F");
		
		WebElement username = driver.findElement(By.id("username"));
		WebElement password = driver.findElement(By.id("password"));
		WebElement submit = driver.findElement(By.className("btn-submit"));
		
		username.sendKeys(login);
		password.sendKeys(pass);
		submit.click();
		
		driver.get("https://monprofil.univ-smb.fr/profil/fr/" + login);
		
		// Trouver les éléments qui contiennent les informations
	    List<WebElement> indentificationElement = driver.findElements(By.className("table"));
	    
	    String fullText = "";
	    for(WebElement we : indentificationElement) {
	    	fullText += we.getText() + " ";
	    }
	    
	    String[] values = getStudentIdentification(fullText);
	    
	    System.out.println(values[0]);
	    System.out.println(values[1]);
	    System.out.println(values[2]);
	    System.out.println(values[3]);
	    System.out.println(values[4]);
	    System.out.println(values[5]);
		
	}
	
	public static void loginToPolytechIntranet() throws InterruptedException {
		
		String login = "";
		String pass = "";
		
		ChromeOptions options = new ChromeOptions();
		options.setAcceptInsecureCerts(true);
        //options.setHeadless(true);

	    WebDriver driver = new ChromeDriver(options);
		
		driver.get("https://www.polytech.univ-smb.fr/login");
		
		By cookies_accept = By.xpath("//*[@id=\"tarteaucitronPersonalize\"]");
	    
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    wait.until(ExpectedConditions.elementToBeClickable(cookies_accept)).click();
	    wait.until(ExpectedConditions.invisibilityOfElementLocated(cookies_accept));
		
		WebElement username = driver.findElement(By.id("user"));
		WebElement password = driver.findElement(By.id("pass"));
		WebElement submit = driver.findElement(By.className("submit"));
		
		username.sendKeys(login);
		password.sendKeys(pass);
		submit.click();
		
		driver.get("https://www.polytech.univ-smb.fr/intranet/pages-speciales/espace-etudiants/espace-personnel.html");
		
		List<WebElement> pointsElement = driver.findElements(By.className("nb_points"));
		
		 int polyPoints = 0;
		 for(WebElement we : pointsElement) {
			 polyPoints += Integer.parseInt(we.getText());
		 }
		 
		 System.out.println(polyPoints);
		 
		 driver.get("https://www.polytech.univ-smb.fr/intranet/scolarite/programmes-ingenieur.html");
		 
		 
		 WebElement idu = driver.findElement(By.xpath("//*[@id=\"c3506\"]/div/div/form/ul/li[2]/div/label[3]"));
		 idu.click();
		 
		 WebElement validate = driver.findElement(By.className("icon-color"));
		 validate.click();
		
	}
	
	private static String[] getStudentIdentification(String texte) {
		
		String[] splited = texte.split("\\s+");
		
		return new String[]{splited[3], splited[6], splited[13], splited[15], splited[30], splited[51]};
		
		
	}
	
}
