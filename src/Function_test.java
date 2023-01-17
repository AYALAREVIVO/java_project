import java.io.File;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Function_test{

    public boolean verifyTableCellText(WebDriver table, int searchColumn,
    String searchText, int returnColumnText, String expectedText){
    try{ 
        assert expectedText== getTableCellTextByXpath(table,  searchColumn,searchText,  returnColumnText );
        return true;
    }
    catch(Exception e){
        return false;
    }
   
}
public String getTableCellTextByXpath(WebDriver table, int searchColumn,
String searchText, int returnColumnText) throws Exception {
    try{
        String xpath = "//*[@id='customers']/tbody/tr[td='" + searchText + "']/td[" + returnColumnText + "]";
        WebElement result = table.findElement(By.xpath(xpath));
        return result.getText();
    }
    catch(Exception e){
        throw new Exception("eror");
    }
             
    
}
public WebDriver createDriver(){
    
    System.setProperty("webdriver.chrome.driver", "./lib/chromedriver.exe");

    // Create a new instance of the Chrome driver
    WebDriver driver = new ChromeDriver();

    // Open the website
    driver.get("https://www.w3schools.com/html/html_tables.asp");
    return driver;
}
public void test(){
    WebDriver driver=createDriver();
    try{
    File xmlFile = new File("data.xml");
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    org.w3c.dom.Document doc = builder.parse(xmlFile);

    for (int index = 0; index <doc.getElementsByTagName("returnColumnText").getLength() ; index++) {
        String rColumnText=doc.getElementsByTagName("returnColumnText").item(index).getTextContent();  
        String searchText=doc.getElementsByTagName("searchText").item(index).getTextContent();  
        String sColum=doc.getElementsByTagName("searchColumn").item(index).getTextContent(); 
        String expectedText=doc.getElementsByTagName("expectedText").item(index).getTextContent(); 

        int searchColum=Integer.parseInt(sColum);
        int returnColumnText=Integer.parseInt(rColumnText);
        
        verifyTableCellText(driver,searchColum,searchText,returnColumnText,expectedText);
        if(verifyTableCellText(driver,searchColum,searchText,returnColumnText,expectedText)==true){
            System.out.println(getTableCellTextByXpath(driver,searchColum,searchText,returnColumnText));
        }
        }
    }
catch(Exception e){

}
driver.quit();
}
}