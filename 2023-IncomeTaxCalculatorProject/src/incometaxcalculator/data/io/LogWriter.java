package incometaxcalculator.data.io;

import java.io.IOException;
import java.io.PrintWriter;

import incometaxcalculator.data.management.TaxpayerManagerFactory;

public abstract class LogWriter implements FileWriter {
  
  private static final short ENTERTAINMENT = 0;
  private static final short BASIC = 1;
  private static final short TRAVEL = 2;
  private static final short HEALTH = 3;
  private static final short OTHER = 4;
  
  protected TaxpayerManagerFactory taxpayer;
  
  
  abstract public String[] createStringForGenerateFile();
  
  
  public LogWriter(TaxpayerManagerFactory taxpayer) {
    taxpayer = new TaxpayerManagerFactory();
    this.taxpayer = taxpayer;
  }
  

  public void generateFile(int taxRegistrationNumber) throws IOException {
  
  
    String extraStringsForXML[] = {" "," "," "," "," "," "," "," "," "," "," "," "," "};
  
    String mainStrings[] = createStringForGenerateFile();
  
    if (mainStrings.length == 27) {
      int j=0;
      for(int i=14; i<mainStrings.length; i++) {
        extraStringsForXML[j] = mainStrings[i];
        j++;
      }    
    }
    

    PrintWriter outputStream = new PrintWriter(
        new java.io.FileWriter(taxRegistrationNumber + mainStrings[0]));
    outputStream.println(mainStrings[1] + taxpayer.getTaxpayerName(taxRegistrationNumber) + extraStringsForXML[0]);
    outputStream.println(mainStrings[2] + taxRegistrationNumber + extraStringsForXML[1]);
    outputStream.println(mainStrings[3] + taxpayer.getTaxpayerIncome(taxRegistrationNumber) + extraStringsForXML[2]);
    outputStream
        .println(mainStrings[4] + taxpayer.getTaxpayerBasicTax(taxRegistrationNumber) + extraStringsForXML[3]);
    if (taxpayer.getTaxpayerVariationTaxOnReceipts(taxRegistrationNumber) > 0) {
      outputStream.println(mainStrings[5]
          + taxpayer.getTaxpayerVariationTaxOnReceipts(taxRegistrationNumber) + extraStringsForXML[4]);
    } else {
      outputStream.println(mainStrings[6]
          + taxpayer.getTaxpayerVariationTaxOnReceipts(taxRegistrationNumber) + extraStringsForXML[5]);
    }
    outputStream
        .println(mainStrings[7] + taxpayer.getTaxpayerTotalTax(taxRegistrationNumber) + extraStringsForXML[6]);
    outputStream.println(
        mainStrings[8] + taxpayer.getTaxpayerTotalReceiptsGathered(taxRegistrationNumber) + extraStringsForXML[7]);
    outputStream.println(
        mainStrings[9] + taxpayer.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, ENTERTAINMENT)
            + extraStringsForXML[8]);
    outputStream.println(
        mainStrings[10] + taxpayer.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, BASIC) + extraStringsForXML[9]);
    outputStream.println(
        mainStrings[11] + taxpayer.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, TRAVEL) + extraStringsForXML[10]);
    outputStream.println(
        mainStrings[12] + taxpayer.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, HEALTH) + extraStringsForXML[11]);
    outputStream.println(
        mainStrings[13] + taxpayer.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, OTHER) + extraStringsForXML[12]);
    outputStream.close();
  }
  

}
