package incometaxcalculator.data.io;

import java.io.IOException;

import incometaxcalculator.data.management.Receipt;

public interface FileWriter {
  

  public abstract void generateFile(int taxRegistrationNumber) throws IOException;

 
  public int getReceiptId(Receipt receipt) ;
  
  public String getReceiptIssueDate(Receipt receipt) ;

  public String getReceiptKind(Receipt receipt);

  public float getReceiptAmount(Receipt receipt) ;

  public String getCompanyName(Receipt receipt) ;

  public String getCompanyCountry(Receipt receipt);
  
  public String getCompanyCity(Receipt receipt);

  public String getCompanyStreet(Receipt receipt) ;

  public int getCompanyNumber(Receipt receipt) ;
  
  
  
}