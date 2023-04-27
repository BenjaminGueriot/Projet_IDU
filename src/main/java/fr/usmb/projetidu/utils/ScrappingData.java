package fr.usmb.projetidu.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import fr.usmb.projetidu.Personne.Eleve;
import fr.usmb.projetidu.interfaceGraphique.pages.AccueilEleve;
import fr.usmb.projetidu.interfaceGraphique.pages.ErrorPopup;
import javafx.stage.Stage;

public class ScrappingData {
	
	private static String username;
	private static String passwordSaved;
	private static String filiereEleve;
	private static int yearEleve;
	
	public static boolean login2USMBIntranet(Stage stage, String login, String pass) {
				
		try {
			
			ChromeOptions options = new ChromeOptions();
		    options.addArguments("--headless");

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
		    String INE = values[5];
		    
		    driver.quit();
		    
		    try {
		    	login2PolytechIntranet(stage, login, pass, surname, name, bday, mail, INE);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
			
		} catch (Exception e) {
			new ErrorPopup("Identifiant ou mot de passe incorrect !");
			return false;
		}
		
	    return true;
		
	}
	
	public static void login2PolytechIntranet(Stage stage, String login, String pass, String surname, String name, String bday, String mail, String INE) throws InterruptedException {
		
		
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless");
		options.addArguments("window-size=1920,1080");

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
				 
				 
			 }
			 
			 
			 
		 }
		 
		 
		 DatabaseRequests.addFiliere2Bdd(filiere + "" + year);
		 
		 //getAllModulesInfos(driver, filiere, year);
		 
		 login2Moodle(stage, login, pass, name, surname, bday, mail, polyPoints, INE, year, filiere);
		 
		 ScrappingData.username = login;
		 ScrappingData.passwordSaved = pass;
		 ScrappingData.filiereEleve = filiere;
		 ScrappingData.yearEleve = year;
			
		 driver.quit();
		 
		
	}
	
	public static void login2Moodle(Stage stage, String login, String pass, String name, String surname, String bday, String mail, int polyPoints, String INE, int year, String filiere){
		
		ChromeOptions options = new ChromeOptions();
		options.addArguments("headless");
		options.addArguments("window-size=1920,1080");

	    WebDriver driver = new ChromeDriver(options);
		
	    driver.get("https://cas-uds.grenet.fr/login?service=https%3A%2F%2Fintranet.univ-smb.fr%2F");
		
		WebElement username = driver.findElement(By.id("username"));
		WebElement password = driver.findElement(By.id("password"));
		WebElement submit = driver.findElement(By.className("btn-submit"));
		
		username.sendKeys(login);
		password.sendKeys(pass);
		submit.click();
		
		driver.get("https://moodle.univ-smb.fr/my/");
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		
		By connect = By.xpath("//*[@id=\"page-wrapper\"]/nav/ul[2]/li[2]/div/span/a");
		wait.until(ExpectedConditions.elementToBeClickable(connect)).click();
		
		By link = By.xpath("//*[@id=\"region-main\"]/div/div[2]/div/div/div/div/div[1]/a");
		wait.until(ExpectedConditions.elementToBeClickable(link)).click();
		
		driver.get("https://moodle.univ-smb.fr/user/profile.php");
		
		WebElement firstJoinElement = driver.findElement(By.xpath("//*[@id=\"region-main\"]/div/div/div/section[6]/div/ul/li[1]/dl/dd"));
		
		int firstJoin = Integer.parseInt(firstJoinElement.getText().replace(",", "").split(" ")[3]);
		DatabaseRequests.addPromo2Bdd(firstJoin, filiere + "" + year, "POPO");
		DatabaseRequests.addStudent2Bdd(surname, name, bday, mail, polyPoints, INE, login, DatabaseRequests.getIdOfPromo(firstJoin, filiere + "" + year, "POPO"));
		
		driver.quit();
		
		Eleve eleve = Initialize.InitializeEleve(login);
		
		AccueilEleve.accueilSender(stage, eleve);
		
		
	}
	
	public static void getAllModulesInfos(){
		
		 ChromeOptions options = new ChromeOptions();

		 options.addArguments("--headless");

		 options.addArguments("window-size=1920,1080");

		 WebDriver driver = new ChromeDriver(options);
		
		 driver.get("https://www.polytech.univ-smb.fr/login");
		
		 By cookies_accept = By.xpath("//*[@id=\"tarteaucitronPersonalize\"]");
		
		 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		 wait.until(ExpectedConditions.elementToBeClickable(cookies_accept)).click();
		 wait.until(ExpectedConditions.invisibilityOfElementLocated(cookies_accept));
		
		 WebElement username = driver.findElement(By.id("user"));
		 WebElement password = driver.findElement(By.id("pass"));
		 WebElement submit = driver.findElement(By.className("submit"));

		 username.sendKeys(ScrappingData.username);
		 password.sendKeys(ScrappingData.passwordSaved);

		 submit.click();

		 driver.get("https://www.polytech.univ-smb.fr/intranet/scolarite/programmes-ingenieur.html");
		 
		 WebElement years = driver.findElement(By.xpath("//*[@id=\"2021-2025\"]"));
		 years.click();
		 
		 try {
			 WebElement nameFil = driver.findElement(By.xpath("//*[@id='" + ScrappingData.filiereEleve.toLowerCase() + "_5' or @id='" + ScrappingData.filiereEleve.toLowerCase() + "_4']"));
			 nameFil.click();
		} catch (Exception e) {
			System.out.println("Not able to pick a promotion");
		}
		 
		 WebElement semester = driver.findElement(By.id("semestre"));
		 semester.click();
		 
		 int firstSemester = (ScrappingData.yearEleve * 2) - 1;
		 int secondSemester = ScrappingData.yearEleve * 2;
		 
		 launchModuleSearch(driver, firstSemester, ScrappingData.filiereEleve + "" + ScrappingData.yearEleve);
		 
		 try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		 
		 launchModuleSearch(driver, secondSemester, ScrappingData.filiereEleve + "" + ScrappingData.yearEleve);
		
		 driver.quit();
		
	}
	
	private static void launchModuleSearch(WebDriver driver, int semester, String filiere) {
		
		 WebElement option = driver.findElement(By.xpath("//*[@id=\"semestre\"]/option[" + (semester + 1) + "]"));
		 option.click();
		 
		 WebElement validate = driver.findElement(By.className("icon-color"));
		 validate.click();
		 
		 List<WebElement> anchors = driver.findElements(By.cssSelector("div[class='value'] ul li a"));
		 
		 List<String> links = new ArrayList<>();
		 
		 for(WebElement we : anchors) {
			 
			 links.add(we.getAttribute("href"));
			 
		 }
		 
		 for(String link : links) {
			 
			 driver.get(link);
			 
			 getModuleData(driver, filiere);
			 
		 }
		
		
	}
	
	private static void getModuleData(WebDriver driver, String filiere) {
		
		/* ------------------------------ Module ----------------------------------------------*/
		
		WebElement module = driver.findElement(By.xpath("//*[@id=\"c853\"]/div/div[2]/div[1]/div[2]"));
		String fullTextModule = module.getText();
		String moduleCode = fullTextModule.split(":")[0].trim();
		
		char c = moduleCode.charAt(moduleCode.length() - 1);
		if(c == 'a') {
			moduleCode = moduleCode.substring(0, moduleCode.length() - 1);
		}
		
		String nomModule = fullTextModule.split(":")[1].trim(); 
		
		/* ------------------------------ UE ----------------------------------------------*/
		
		WebElement ue = driver.findElement(By.xpath("//*[@id=\"c853\"]/div/div[2]/div[2]/div[1]/div[2]/div[2]"));
		String fullTextUe = ue.getText();
		String ueCode = fullTextUe.split(":")[0].trim();
		String nomUe = fullTextUe.split(":")[1].trim();
		
		DatabaseRequests.addUe2Bdd(ueCode, nomUe);
		DatabaseRequests.addRelationUeFiliere2Bdd(filiere, ueCode);
		
		/* ------------------------------ Responsable ----------------------------------------------*/
		
		WebElement respo = driver.findElement(By.xpath("//*[@id=\"c853\"]/div/div[2]/div[2]/div[3]/div[1]/div[2]"));
		String fullTextRespo = respo.getText();
		
		List<String> respos = new ArrayList<>();
		
		
		if(fullTextRespo.contains(" et ")) {
			
			for(String s : fullTextRespo.split(" et ")) {
				
				respos.add(s.trim());
				
			}
			
		} else if(fullTextRespo.contains("/")) {
			
			for(String s : fullTextRespo.split("/")) {
				
				respos.add(s.trim());
				
			}
			
		} else if(fullTextRespo.contains(";")) {
			
			for(String s : fullTextRespo.split(";")) {
				
				respos.add(s.trim());
				
			}
			
		} else {
			
			for(String s : fullTextRespo.split(",")) {
				
				respos.add(s.trim());
				
			}
			
		}
		
		/* ------------------------------ Mail ----------------------------------------------*/
		
		WebElement mail = driver.findElement(By.xpath("//*[@id=\"c853\"]/div/div[2]/div[2]/div[3]/div[2]/div[2]"));
		String fullTextMail = mail.getText();
		List<String> mails = new ArrayList<>();
		
		
		if(fullTextMail.contains(" et ")) {
			
			for(String s : fullTextMail.split(" et ")) {
				
				mails.add(s.trim());
				
			}
			
		} else if(fullTextRespo.contains("/")) {
			
			for(String s : fullTextRespo.split("/")) {
				
				respos.add(s.trim());
				
			}
			
		} else if(fullTextMail.contains(";")) {
			
			for(String s : fullTextMail.split(";")) {
				
				mails.add(s.trim());
				
			}
			
		} else {
			
			for(String s : fullTextMail.split(",")) {
				
				mails.add(s.trim());
				
			}
			
		}
		
		
		/* ------------------------------ Total hour ----------------------------------------------*/
		
		List<WebElement> totalHourNotFormatted = driver.findElements(By.cssSelector("div[class='field nbHeures'] div[class='value']"));
		
		double totalHour = Double.parseDouble(totalHourNotFormatted.get(totalHourNotFormatted.size() - 2).getText());
		
		/* ------------------------------ Coeff ----------------------------------------------*/
		
		WebElement coeffNotFormatted = driver.findElement(By.xpath("//*[@id=\"c853\"]/div/div[2]/div[2]/div[6]/div[2]/div[2]"));
		
		double coeff;
		if(coeffNotFormatted.getText().isEmpty()) {
			coeff = 0.0;
		} else {
			coeff = Double.parseDouble(coeffNotFormatted.getText());
		}
		
		/* ------------------------------ Evaluation ----------------------------------------------*/
		
		WebElement eval = driver.findElement(By.xpath("//*[@id=\"c853\"]/div/div[2]/div[2]/div[7]/div[1]/div[2]"));
		String evaluation = eval.getText();
		
		/* ------------------------------ Descriptif ----------------------------------------------*/

		String descriptif = null;
		
		try {
			WebElement desc = driver.findElement(By.xpath("//*[@id=\"c853\"]/div/div[2]/div[2]/div[11]/div[1]/div[2]/div/p[1]"));
			descriptif = desc.getText();
		} catch (Exception e) {	
			descriptif = "No data";
		}
		
		DatabaseRequests.addModule2Bdd(moduleCode, nomModule.replace("'", " "), totalHour, coeff, evaluation, descriptif.replace("'", " "), ueCode);
		
		for(String enseignant : respos) {
			
			String prenom = enseignant.replace(".", " ").split(" ")[0];
			String nom = enseignant.replace(".", " ").split(" ")[1];
			
			int index = respos.indexOf(enseignant);
			
			String email = mails.get(index);
			
			DatabaseRequests.addProf2Bdd(nom.toUpperCase(), prenom, email);
			DatabaseRequests.addEnseigne2Bdd(nom, prenom, moduleCode);
		}
		
		
	}
	
	public static void login2Planning(){
		
		getAllModulesInfos();
		
		ChromeOptions options = new ChromeOptions();
		//options.addArguments("--headless");
		options.addArguments("window-size=1920,1080");
		
	    WebDriver driver = new ChromeDriver(options);
		
		driver.get("https://ade-usmb-ro.grenet.fr/direct/index.jsp");
		
		WebElement username = driver.findElement(By.id("username"));
		WebElement password = driver.findElement(By.id("password"));
		WebElement submit = driver.findElement(By.className("btn-submit"));
		
		username.sendKeys(ScrappingData.username);
		password.sendKeys(ScrappingData.passwordSaved);
		submit.click();
		
		By validateButton = By.className("x-btn-text");
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(validateButton)).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(validateButton));
		
		By searchArea = By.xpath("//*[@id=\"x-auto-136-input\"]");
		wait.until(ExpectedConditions.elementToBeClickable(searchArea)).sendKeys(ScrappingData.filiereEleve + "-" + ScrappingData.yearEleve);
		
		WebElement searchButton = driver.findElement(By.xpath("//*[@id=\"x-auto-138\"]/tbody/tr[2]/td[2]/em/button/img"));
		searchButton.click();
		
		By planning = By.className("eventText");
		wait.until(ExpectedConditions.presenceOfElementLocated(planning));
		
		/*** Récupération des jours de la semaine ***/
		
		By settings = By.xpath("//*[@id=\"x-auto-134\"]/tbody/tr[2]/td[2]/em/button");
		wait.until(ExpectedConditions.elementToBeClickable(settings)).click();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		By settings2 = By.xpath("//*[@id=\"x-auto-445_x-auto-494\"]");
		wait.until(ExpectedConditions.elementToBeClickable(settings2)).click();
		
		try {
			Thread.sleep(1000);
			Actions action = new Actions(driver);
			action.sendKeys(Keys.ESCAPE).build().perform();
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		
		
		By page = By.xpath("//*[@id=\"x-auto-55\"]");
		wait.until(ExpectedConditions.elementToBeClickable(page)).click();
		
		By sept = By.xpath("//*[@id=\"x-auto-118\"]/div[2]/table/tbody/tr[3]/td[2]/a");
		wait.until(ExpectedConditions.elementToBeClickable(sept)).click();
		
		By yearStart = By.xpath("//*[@id=\"x-auto-118\"]/div[2]/table/tbody/tr[5]/td[3]/a");
		wait.until(ExpectedConditions.elementToBeClickable(yearStart)).click();
		
		By ok = By.xpath("//*[@id=\"x-auto-118\"]/div[2]/table/tbody/tr[7]/td/button[1]");
		wait.until(ExpectedConditions.elementToBeClickable(ok)).click();
		
		By date = By.xpath("//*[@id=\"x-auto-96\"]/a");
		wait.until(ExpectedConditions.elementToBeClickable(date)).click();
		
		
		
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		for(int i = 177; i < 221; i++) {
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			for(int j = 1; j < 6; j++) {
			
				By day = By.xpath("//*[@id=\"x-auto-16" + j + "\"]/tbody/tr[2]/td[2]/em/button");
				
				wait.until(ExpectedConditions.elementToBeClickable(day)).click();
				try {
					Thread.sleep(2000);
					getOneDay(driver);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		
			} 
			
			
			By next = By.xpath("//*[@id=\"x-auto-174\"]/tbody/tr[2]/td[2]/em/button");
			wait.until(ExpectedConditions.elementToBeClickable(next)).click();
			
			By nextPage = By.xpath("//*[@id=\"x-auto-" + (i + 1) + "\"]/tbody/tr[2]/td[2]/em/button");
			wait.until(ExpectedConditions.elementToBeClickable(nextPage)).click();
			
		}
		 
		 
		driver.quit(); 
 	}
	
	private static void getOneDay(WebDriver driver) {
		
		WebElement dayNotFormatted = driver.findElement(By.id("4"));
		String day = dayNotFormatted.getText().split(" ")[1];
		
		System.out.println("--------------------- "+ day + " ---------------------------");
		
		List<WebElement> elements = driver.findElements(By.className("eventText"));
		
		for(WebElement we : elements) {
			String[] splited = we.getText().split("\\s+");
			
			if(splited.length > 6) {
				//System.out.println("------------------------------------------------");
				
				String module = null;
				
				if(splited[0].contains("_") && splited[0].contains("-")) {
					if(splited[0].contains("_")){
						module = splited[0].split("_")[0];
					}
				} else {
					if(splited[0].contains("_")){
						module = splited[0].split("_")[0];
					}
					if(splited[0].contains("-")){
						module = splited[0].split("-")[0];
					}
				}
				
				
				String startingHourNotFormated = splited[splited.length - 3];
				String endingHourNotFormated = splited[splited.length - 1];
				
				int number;
				try {
					number =  Integer.parseInt(splited[splited.length - 4].split("/")[0]);
				} catch (Exception e) {
					number =  1;
				}
				
				
				String startingHour = getFormattedHour(startingHourNotFormated);
				
				if(startingHour != null && !startingHour.isEmpty()) {
					char c = startingHour.charAt(0);
					if(c == '0') {
						startingHour = startingHour.substring(1, startingHour.length());
					}
				} 
				
				String endingHour = getFormattedHour(endingHourNotFormated);
				
				//System.out.println(number);
				//System.out.println(module);
				//System.out.println(startingHour);
				
				double duree;
				if(endingHour != null) {
					duree = Double.parseDouble(endingHour.replace(",", "."))   - Double.parseDouble(startingHour.replace(",", "."));
				} else {
					duree = 0.0;
				}
				//System.out.println(duree);
				
				
				String type = "";
				
				if(splited[0].contains("CM")) {
					type = "CM";
				} else if(splited[0].contains("TD")) {
					type = "TD";
				} else if(splited[0].contains("TP")) {
					type = "TP";
				} else {
					type = "Special";
				} 
				
				if(splited[0].contains("Exam") || splited[0].contains("EXAM") || splited[0].contains("EX") || splited[0].contains("Ex") || splited[0].contains("CMEX")) {
					type = "Exam";
					DatabaseRequests.addTravailExam2Bdd("Examen " + module, "Exam de module", day, module);
					
				}
				
				if(startingHour != null && !startingHour.isEmpty()) {
					DatabaseRequests.addCour2Bdd(number, module, day, Double.parseDouble(startingHour), duree, type);
				} else {
					DatabaseRequests.addCour2Bdd(number, module, day, 0.0, duree, type);
				}
				
				
				
			}
			
			
		} 
		
	}
	
	private static String getFormattedHour(String hourNotFormatted) {
		
		String hour = "";
		
		if(!hourNotFormatted.contains("h")) return null;
		
		if(hourNotFormatted.split("h")[1].equalsIgnoreCase("00")) {
			hour = hourNotFormatted.split("h")[0];
		} else {
			
			switch (hourNotFormatted.split("h")[1]) {
				case "15": {
					hour = hourNotFormatted.split("h")[0] + ".25";
					break;
				}
				case "30": {
					hour = hourNotFormatted.split("h")[0] + ".5";
					break;
				}
				case "45": {
					hour = hourNotFormatted.split("h")[0] + ".75";
					break;
				}
			
			}
		
		}
		
		return hour;
		
	}
	
	private static String[] getStudentIdentification(String texte) {
		
		String[] splited = texte.split("\\s+");
		
		return new String[]{splited[3], splited[6], splited[13], splited[15], splited[30], splited[51]};
		
		
	}
	
}
