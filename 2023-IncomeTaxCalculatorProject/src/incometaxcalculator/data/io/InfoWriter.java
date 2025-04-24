package incometaxcalculator.data.io;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;

import incometaxcalculator.data.management.Receipt;
import incometaxcalculator.data.management.TaxpayerManagerFactory;

public abstract class InfoWriter implements FileWriter  {
  
  abstract public String[] createStringForGenerateFile();
  
  abstract public String[] createStringForGenerateTaxpayerReceipts();
  
  protected TaxpayerManagerFactory taxpayer;
  
   
  public InfoWriter(TaxpayerManagerFactory taxpayer) {
 
    this.taxpayer = taxpayer;
  }
  

  
  public void generateFile(int taxRegistrationNumber) throws IOException {
   
  
    String extraStringsForXML[] = {" "," "," "," "};

    
    String mainStrings[] = createStringForGenerateFile();
    
    if (mainStrings.length == 10) {
      int j=0;
      for(int i=6; i<mainStrings.length; i++) {
        extraStringsForXML[j] = mainStrings[i];
        j++;
      }    
    }
    
    PrintWriter outputStream;
    File file = new File(taxRegistrationNumber + mainStrings[0]);
    
    if (file.exists()) {
      outputStream = new PrintWriter(file);
    }else {
      outputStream = new PrintWriter(
          new java.io.FileWriter(taxRegistrationNumber + mainStrings[0], true));
    }
       
    outputStream.println(mainStrings[1] + taxpayer.getTaxpayerName(taxRegistrationNumber) + extraStringsForXML[0]);
    outputStream.println(mainStrings[2] + taxRegistrationNumber + extraStringsForXML[1]);
    outputStream.println(mainStrings[3] + taxpayer.getTaxpayerStatus(taxRegistrationNumber) + extraStringsForXML[2]);
    outputStream.println(mainStrings[4] + taxpayer.getTaxpayerIncome(taxRegistrationNumber) + extraStringsForXML[3]);
    outputStream.println();// den mas emfanize to \n se aplo notepad
    outputStream.println(mainStrings[5]);
    outputStream.println();
    generateTaxpayerReceipts(taxRegistrationNumber, outputStream);
    outputStream.close();
    
  }
  



  
  
  public void generateTaxpayerReceipts(int taxRegistrationNumber, PrintWriter outputStream) {
    

    String extraStringsForXML[] = {" "," "," "," "," "," "," "," "," "};
    
    String mainStrings[] = createStringForGenerateTaxpayerReceipts();
    
    if (mainStrings.length == 18) {
      int j=0;
      for(int i=9; i<mainStrings.length; i++) {
        extraStringsForXML[j].equals(mainStrings[i]);
        j++;
      }    
    }

    
    HashMap<Integer, Receipt> receiptsHashMap = taxpayer.getReceiptHashMap(taxRegistrationNumber);
    Iterator<HashMap.Entry<Integer, Receipt>> iterator = receiptsHashMap.entrySet().iterator();
    while (iterator.hasNext()) {
      HashMap.Entry<Integer, Receipt> entry = iterator.next();
      Receipt receipt = entry.getValue();
      outputStream.println(mainStrings[0] + getReceiptId(receipt) + extraStringsForXML[0]);
      outputStream.println(mainStrings[1] + getReceiptIssueDate(receipt) + extraStringsForXML[1]);
      outputStream.println(mainStrings[2] + getReceiptKind(receipt) + extraStringsForXML[2]);
      outputStream.println(mainStrings[3] + getReceiptAmount(receipt) + extraStringsForXML[3]);
      outputStream.println(mainStrings[4] + getCompanyName(receipt) + extraStringsForXML[4]);
      outputStream.println(mainStrings[5] + getCompanyCountry(receipt) + extraStringsForXML[5]);
      outputStream.println(mainStrings[6] + getCompanyCity(receipt) + extraStringsForXML[6]);
      outputStream.println(mainStrings[7] + getCompanyStreet(receipt) + extraStringsForXML[7]);
      outputStream.println(mainStrings[8] + getCompanyNumber(receipt) + extraStringsForXML[8]);
      outputStream.println();
    }
  }


}
