package fr.usmb.projetidu.utils;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ScrappingTest {
	
	public static void main(String[] args) throws IOException {
		
		loginToUSMBIntranet();
		
	}
	
	@SuppressWarnings("deprecation")
	public static void loginToUSMBIntranet() {
				
		String login = "nicolath";
		String pass = "ao61na76&*Tao61na76";
		
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
	    
	    String name = values[0];
	    String surname = values[1];
	    String bday = values[2];
	    String mail = values[3];
	    String endOfLicence = values[4];
	    String INE = values[5];
	    
	    System.out.println("Nom : " + surname);
	    System.out.println("Prenom : " + name);
	    System.out.println("Anniversaire : " + bday);
	    System.out.println("Mail : " + mail);
	    System.out.println("Fin de licence : " + endOfLicence);
	    System.out.println("INE : " + INE);
	    
	    
	    try {
			loginToPolytechIntranet(surname, name);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
	    
		
	}
	
	public static void loginToPolytechIntranet(String surname, String name) throws InterruptedException {
		
		String login = "nicolath";
		String pass = "ao61na76&*Tao61na76";
		
		ChromeOptions options = new ChromeOptions();
		options.setAcceptInsecureCerts(true);
        options.setHeadless(true);
        
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
		
		
		/** Partie récupération des polypoints **/
		
		driver.get("https://www.polytech.univ-smb.fr/intranet/pages-speciales/espace-etudiants/espace-personnel.html");
		
		List<WebElement> pointsElement = driver.findElements(By.className("nb_points"));
		
		int polyPoints = 0;
		 
		for(WebElement we : pointsElement) {
			 polyPoints += Integer.parseInt(we.getText());
		}
		 
		 System.out.println("Polypoints : " + polyPoints);
		 
		 
		/** Partie récupération de la filière via le trombinoscope **/ 
		 
		 driver.get("https://www.polytech.univ-smb.fr/intranet/eleves-ingenieurs.html");
		 
		 WebElement search = driver.findElement(By.xpath("//*[@id=\"c3262\"]/div/div/form/ul/li[27]/input"));
		 WebElement searchButton = driver.findElement(By.className("icon-color"));
		 
		 search.sendKeys(surname);
		 searchButton.click();
		 
		 List<WebElement> students = driver.findElements(By.className("item"));
		 
		 String filiere = "";
		 int year = -1;
		 
		 for(WebElement student : students) {
			 
			 
			 String[] splited = student.getText().split("\\s+");
			 
			 if(splited[splited.length - 1].equalsIgnoreCase(name) && splited[splited.length - 2].equalsIgnoreCase(surname)) {
				 
				 String yearFI = splited[0];
				 filiere = splited[2];
				 
				 year = Integer.parseInt(yearFI.substring(yearFI.length() - 1));   
				 
				 System.out.println("Annee : " + year);
				 System.out.println("Filiere : " + filiere);
				 
			 }
			 
			 
			 
		 }
		 
		 loginToPlanning(filiere, year);
		
		 
		 /** Partie récupération des informations liées aux modules **/
		 
		/* driver.get("https://www.polytech.univ-smb.fr/intranet/scolarite/programmes-ingenieur.html");
		 
		 
		 WebElement idu = driver.findElement(By.xpath("//*[@id=\"c3506\"]/div/div/form/ul/li[2]/div/label[3]"));
		 idu.click();
		 
		 WebElement validate = driver.findElement(By.className("icon-color"));
		 validate.click(); */
		 
		
	}
	
	public static void loginToPlanning(String filiere, int year){
		
		String login = "nicolath";
		String pass = "ao61na76&*Tao61na76";
		
		ChromeOptions options = new ChromeOptions();
		options.setAcceptInsecureCerts(true);
        //options.setHeadless(true);

	    WebDriver driver = new ChromeDriver(options);
		
		driver.get("https://ade-usmb-ro.grenet.fr/direct/index.jsp");
		
		WebElement username = driver.findElement(By.id("username"));
		WebElement password = driver.findElement(By.id("password"));
		WebElement submit = driver.findElement(By.className("btn-submit"));
		
		username.sendKeys(login);
		password.sendKeys(pass);
		submit.click();
		
		By validateButton = By.className("x-btn-text");
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(validateButton)).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(validateButton));
		
		By searchArea = By.xpath("//*[@id=\"x-auto-136-input\"]");
		wait.until(ExpectedConditions.elementToBeClickable(searchArea)).sendKeys(filiere + "-" + year);
		
		WebElement searchButton = driver.findElement(By.xpath("//*[@id=\"x-auto-138\"]/tbody/tr[2]/td[2]/em/button/img"));
		searchButton.click();
		
		By firstHoraire = By.className("xtn-button");
		wait.until(ExpectedConditions.presenceOfElementLocated(firstHoraire));
		
		/*** LUNDI ***/
		
		WebElement monday = driver.findElement(By.xpath("//*[@id=\"x-auto-161\"]/tbody/tr[2]/td[2]/em/button"));
		monday.click();
		
		List<WebElement> horaires = driver.findElements(By.className("eventText"));
		for(WebElement we : horaires) {
			System.out.println("------------------------------------------------");
			System.out.println(we.getText());
		}
		
		//driver.get("https://ade-usmb-ro.grenet.fr/direct/index.jsp?data=7020a3fce84ff3ba42e6d53dcf2a8626dbb4ee8dad47f25db24afb2b83a03d759fd8c31cee53c321e535653ae26cd5be,1&ticket=ST-684424-XUQCf9grD0dqbZOiamLU-cas-uds.grenet.fr");
		
	}
	
	private static String[] getStudentIdentification(String texte) {
		
		String[] splited = texte.split("\\s+");
		
		return new String[]{splited[3], splited[6], splited[13], splited[15], splited[30], splited[51]};
		
		
	}
	
}
