package incometaxcalculator.tests;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import incometaxcalculator.data.management.TaxpayerManager;
import incometaxcalculator.exceptions.ReceiptAlreadyExistsException;
import incometaxcalculator.exceptions.WrongFileEndingException;
import incometaxcalculator.exceptions.WrongFileFormatException;
import incometaxcalculator.exceptions.WrongReceiptDateException;
import incometaxcalculator.exceptions.WrongReceiptKindException;
import incometaxcalculator.exceptions.WrongTaxpayerStatusException;


class TaxpayerManagerTest {

  private static TaxpayerManager manager = new TaxpayerManager();;   
  
  public TaxpayerManagerTest() throws IOException, WrongReceiptKindException, WrongReceiptDateException, ReceiptAlreadyExistsException, WrongTaxpayerStatusException {
    
    manager.createTaxpayer("Bob Martin", 123456789, "Single", 1111111);
  }
  
  @Test
  public void addReceiptTest() throws IOException, WrongReceiptKindException, WrongReceiptDateException, ReceiptAlreadyExistsException {  
    manager.addReceipt(3, "33/33/3333", 33, "Travel", "Tree", "Greece", "Ioannina", "Labridou", 33, 123456789);
    boolean contains;
    contains = manager.containsReceipt(3);
    assertTrue(contains);
  }
  
  @Test
  public void containsReceiptTest()  {   
    boolean contains;
    contains = manager.containsReceipt(4);
    assertTrue(!contains);
  }
  
  @Test
  public void removeReceiptTest() throws IOException, WrongReceiptKindException, WrongReceiptDateException, ReceiptAlreadyExistsException {  
    manager.addReceipt(5, "55/55/5555", 55, "Travel", "Tree", "Greece", "Ioannina", "Labridou", 55, 123456789);
    manager.removeReceipt(5);
    boolean contains;
    contains = manager.containsReceipt(5);
    assertTrue(!contains);
  }
  
  @Test
  public void containsReceiptTestRemoveWrong() throws IOException, WrongReceiptKindException, WrongReceiptDateException, ReceiptAlreadyExistsException {  
    manager.addReceipt(6, "22/22/2222", 66, "Travel", "Tree", "Greece", "Ioannina", "Labridou", 66, 123456789);
    manager.removeReceipt(6);
    boolean contains;
    contains = manager.containsReceipt(6);
    assertFalse(contains);
  }
  
  @Test
  public void addReceiptTestAlreadExists() throws IOException, WrongReceiptKindException, WrongReceiptDateException, ReceiptAlreadyExistsException {  
    manager.addReceipt(7, "22/22/2222", 77, "Travel", "Tree", "Greece", "Ioannina", "Labridou", 77, 123456789);
    
    assertThrows(ReceiptAlreadyExistsException.class, () -> {manager.addReceipt(7, "22/22/2222", 77, "Travel", "Tree", "Greece", "Ioannina", "Labridou", 77, 123456789);});
  }
  
  @Test
  public void loadTaxpayerTestIOException() throws NumberFormatException, IOException, WrongFileFormatException, WrongFileEndingException, WrongTaxpayerStatusException, WrongReceiptKindException, WrongReceiptDateException {     
    assertThrows(IOException.class, () -> {  manager.loadTaxpayer("223456789.txt");});
  }
  
  @Test
  public void containsTaxpayerTestHappyDay() {
    assertTrue(manager.containsTaxpayer(123456789));
  }
  
  @Test
  public void containsTaxpayerTestNotFound() {
    assertFalse(!manager.containsTaxpayer(123456789));    
  }

  @Test
  public void containsTaxpayerTestNotExisting() {
    assertTrue(!manager.containsTaxpayer(223456789));    
  }
  
  @Test
  public void removeTaxpayerTestHappyDay() {
    manager.removeTaxpayer(123456789);
    assertTrue(!manager.containsTaxpayer(123456789));    
  }
  
  @Test
  public void removeTaxpayerTestWrong() {
    manager.removeTaxpayer(123456789);
    assertFalse(manager.containsTaxpayer(123456789));    
  }
  
  @Test
  public void createTaxpayerTestWrongStatus() throws WrongTaxpayerStatusException {
    assertThrows(WrongTaxpayerStatusException.class, () -> {  manager.createTaxpayer("Bob", 123456789, "Alone4Ever", 1111111);});   
  }
     
}


