package incometaxcalculator.data.management;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import incometaxcalculator.data.io.FileReader;
import incometaxcalculator.data.io.FileWriter;
import incometaxcalculator.data.io.TXTFileReader;
import incometaxcalculator.data.io.TXTInfoWriter;
import incometaxcalculator.data.io.TXTLogWriter;
import incometaxcalculator.data.io.XMLFileReader;
import incometaxcalculator.data.io.XMLInfoWriter;
import incometaxcalculator.data.io.XMLLogWriter;
import incometaxcalculator.exceptions.WrongFileEndingException;
import incometaxcalculator.exceptions.WrongFileFormatException;
import incometaxcalculator.exceptions.WrongReceiptDateException;
import incometaxcalculator.exceptions.WrongReceiptKindException;
import incometaxcalculator.exceptions.WrongTaxpayerStatusException;

public class TaxpayerManagerFactory {
  
  
  protected static HashMap<Integer, Taxpayer> taxpayerHashMap = new HashMap<Integer, Taxpayer>(0);
  protected static HashMap<Integer, Integer> receiptOwnerTRN = new HashMap<Integer, Integer>(0);

  
  public Taxpayer createTaxpayerFactory(String status, String fullname,int taxRegistrationNumber, float income ) throws WrongTaxpayerStatusException{
    
    if(status.equals("Married Filing Jointly")) {
      return new MarriedFilingJointlyTaxpayer(fullname, taxRegistrationNumber, income);
    }
    else if(status.equals("Married Filing Separately")) {
      return new MarriedFilingSeparatelyTaxpayer(fullname, taxRegistrationNumber, income);
    }
    else if(status.equals("Single")) {
      return new SingleTaxpayer(fullname, taxRegistrationNumber, income);
    }
    else if(status.equals("Head of Household")) {
      return new HeadOfHouseholdTaxpayer(fullname, taxRegistrationNumber, income);
    }else {
      throw new WrongTaxpayerStatusException();
    }
    
  }
  
  public FileWriter updateFilesFactory(int taxRegistrationNumber) {
    
    if (new File(taxRegistrationNumber + "_INFO.xml").exists()) {
      return new XMLInfoWriter(this);
    }else if (new File(taxRegistrationNumber + "_INFO.txt").exists()) {
      return new TXTInfoWriter(this);
    } 
    else {
      return new TXTInfoWriter(this);  
    }   
  }
  
  public FileWriter saveLogFileFactory(int taxRegistrationNumber, String fileFormat)  throws IOException, WrongFileFormatException  {
    if (fileFormat.equals("txt")) {
      return new TXTLogWriter(this);
    } else if (fileFormat.equals("xml")) {
      return new XMLLogWriter(this);
    } else {
      throw new WrongFileFormatException();
    }
        
  }
  
  public FileReader loadTaxpayerFactory(String fileName)
      throws NumberFormatException, IOException, WrongFileFormatException, WrongFileEndingException,
      WrongTaxpayerStatusException, WrongReceiptKindException, WrongReceiptDateException {
       
    String ending[] = fileName.split("\\.");
    if (ending[1].equals("txt")) {
      return new TXTFileReader();
     
    } else if (ending[1].equals("xml")) {
      return new XMLFileReader();
     
    } else {
      throw new WrongFileEndingException();
    }

  }
  

  public String getTaxpayerName(int taxRegistrationNumber) {
    return taxpayerHashMap.get(taxRegistrationNumber).getFullname();
  }

  public String getTaxpayerStatus(int taxRegistrationNumber) {
    if (taxpayerHashMap.get(taxRegistrationNumber) instanceof MarriedFilingJointlyTaxpayer) {
      return "Married Filing Jointly";
    } else if (taxpayerHashMap
        .get(taxRegistrationNumber) instanceof MarriedFilingSeparatelyTaxpayer) {
      return "Married Filing Separately";
    } else if (taxpayerHashMap.get(taxRegistrationNumber) instanceof SingleTaxpayer) {
      return "Single";
    } else {
      return "Head of Household";
    }
  }

  public String getTaxpayerIncome(int taxRegistrationNumber) {
    return "" + taxpayerHashMap.get(taxRegistrationNumber).getIncome();
  }
  

  public HashMap<Integer, Receipt> getReceiptHashMap(int taxRegistrationNumber) {
    return taxpayerHashMap.get(taxRegistrationNumber).getReceiptHashMap();
  }
  

  public double getTaxpayerVariationTaxOnReceipts(int taxRegistrationNumber) {
    return taxpayerHashMap.get(taxRegistrationNumber).getVariationTaxOnReceipts();
  }

  public int getTaxpayerTotalReceiptsGathered(int taxRegistrationNumber) {
    return taxpayerHashMap.get(taxRegistrationNumber).getTotalReceiptsGathered();
  }

  public float getTaxpayerAmountOfReceiptKind(int taxRegistrationNumber, short kind) {
    return taxpayerHashMap.get(taxRegistrationNumber).getAmountOfReceiptKind(kind);
  }

  public double getTaxpayerTotalTax(int taxRegistrationNumber) {
    return taxpayerHashMap.get(taxRegistrationNumber).getTotalTax();
  }

  public double getTaxpayerBasicTax(int taxRegistrationNumber) {
    return taxpayerHashMap.get(taxRegistrationNumber).getBasicTax();
  }




  //123456789
 

}
