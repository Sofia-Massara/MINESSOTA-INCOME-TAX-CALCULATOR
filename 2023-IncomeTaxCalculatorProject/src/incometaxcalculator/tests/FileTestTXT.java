package incometaxcalculator.tests;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import incometaxcalculator.data.io.TXTFileReader;
import incometaxcalculator.data.io.TXTInfoWriter;
import incometaxcalculator.data.management.Company;
import incometaxcalculator.data.management.Receipt;
import incometaxcalculator.data.management.TaxpayerManager;
import incometaxcalculator.exceptions.ReceiptAlreadyExistsException;
import incometaxcalculator.exceptions.WrongFileEndingException;
import incometaxcalculator.exceptions.WrongFileFormatException;
import incometaxcalculator.exceptions.WrongReceiptDateException;
import incometaxcalculator.exceptions.WrongReceiptKindException;
import incometaxcalculator.exceptions.WrongTaxpayerStatusException;

class FileTestTXT {

  private static TXTFileReader reader;
  private static TXTInfoWriter writer;
  private static TaxpayerManager manager ;
  
  public FileTestTXT() throws IOException, WrongReceiptKindException, WrongReceiptDateException, ReceiptAlreadyExistsException, WrongTaxpayerStatusException, NumberFormatException, WrongFileFormatException, WrongFileEndingException {
    manager = new TaxpayerManager();
    manager.createTaxpayer("Bob Martin", 123123123, "Single", 1111111);   
    reader = new TXTFileReader();    
    writer = new TXTInfoWriter(manager);
  }
  
  
  @Test
  public void testCreateReceiptHappyDay() throws WrongReceiptKindException, WrongReceiptDateException, NumberFormatException, IOException, WrongFileFormatException, WrongFileEndingException, WrongTaxpayerStatusException {
    Receipt receipt =reader.createReceipt(2, "22/22/2222", 22, "Travel", "Tree", "Greece", "Ioannina", "Labridou",22, 123123123);
    assertTrue(receipt.getId()==2 && receipt.getIssueDate().equals("22/22/2222")&& receipt.getAmount()==22 && receipt.getKind().equals("Travel"));         
  }
  
  
  @Test
  public void testCreateReceiptWrongDate() throws  IOException, WrongFileFormatException, WrongFileEndingException, WrongTaxpayerStatusException, WrongReceiptKindException, WrongReceiptDateException {
    assertThrows(WrongReceiptDateException.class, () -> {  reader.createReceipt(2, "22", 22, "Travel", "Tree", "Greece", "Ioannina", "Labridou", 22, 123123123);});            
  }
  
  @Test
  public void testCreateReceiptWrongKind() throws NumberFormatException, IOException, WrongFileFormatException, WrongFileEndingException, WrongTaxpayerStatusException, WrongReceiptKindException, WrongReceiptDateException  {
    assertThrows(WrongReceiptKindException.class, () -> {  reader.createReceipt(2, "22/22/2222", 22, "Fun", "Tree", "Greece", "Ioannina", "Labridou", 22, 123123123);});            
  }
  
  @Test
  public void testCompanyInfo() throws NumberFormatException, IOException, WrongFileFormatException, WrongFileEndingException, WrongTaxpayerStatusException, WrongReceiptKindException, WrongReceiptDateException {
    reader.createReceipt(2, "22/22/2222", 22, "Travel", "Tree", "Greece", "Ioannina", "Labridou", 22, 123123123);
    Company company = new Company("Tree", "Greece", "Ioannina", "Labridou", 22);
    Receipt receipt = new Receipt(2,"22/22/2222", 22, "Travel", company); 
  
    assertTrue(writer.getCompanyName(receipt).equals("Tree") && writer.getCompanyCity(receipt).equals("Ioannina") && writer.getCompanyCountry(receipt).equals("Greece") && writer.getCompanyStreet(receipt).equals("Labridou") && writer.getCompanyNumber(receipt)==22);         

  }
  
  @Test
  public void testReceiptInfo() throws NumberFormatException, IOException, WrongFileFormatException, WrongFileEndingException, WrongTaxpayerStatusException, WrongReceiptKindException, WrongReceiptDateException {
    reader.createReceipt(2, "22/22/2222", 22, "Travel", "Tree", "Greece", "Ioannina", "Labridou", 22, 123123123);
    Company company = new Company("Tree", "Greece", "Ioannina", "Labridou", 22);
    Receipt receipt = new Receipt(2,"22/22/2222", 22, "Travel", company);    
    assertTrue(writer.getReceiptAmount(receipt)==22 && writer.getReceiptId(receipt)==2 && writer.getReceiptIssueDate(receipt).equals("22/22/2222") && writer.getReceiptKind(receipt).equals("Travel"));   
  }
  
  @Test
  public void testFileNotFound() throws FileNotFoundException, IOException {
    assertThrows(FileNotFoundException.class, () -> { BufferedReader inputStream= new BufferedReader(new java.io.FileReader("123123123_INFO"));});            
  }
  
  
}
