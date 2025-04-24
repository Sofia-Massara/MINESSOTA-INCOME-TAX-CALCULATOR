package incometaxcalculator.data.io;

import incometaxcalculator.data.management.Receipt;
import incometaxcalculator.data.management.TaxpayerManagerFactory;

public class TXTInfoWriter extends InfoWriter implements FileWriter {
  
  
  
  public TXTInfoWriter(TaxpayerManagerFactory taxpayerManagerFactory) {
    super(taxpayerManagerFactory);
  }


  public String[] createStringForGenerateFile() {
    String outputs[] = {"_INFO.txt", "Name: ","AFM: ","Status: ","Income: ","Receipts: "};
    
    return outputs;
  }
  
  public String[] createStringForGenerateTaxpayerReceipts() {
    String outputs[] = {"Receipt ID: ","Date: ","Kind: ","Amount: ","Company: ","Country: ","City: ","Street: ","Number: "};
    
    return outputs ;
    
  }
  
  
  public int getReceiptId(Receipt receipt) {
    return receipt.getId();
  }
  
  public String getReceiptIssueDate(Receipt receipt) {
    return receipt.getIssueDate();
  }

  public String getReceiptKind(Receipt receipt) {
    return receipt.getKind();
  }

  public float getReceiptAmount(Receipt receipt) {
    return receipt.getAmount();
  }

  public String getCompanyName(Receipt receipt) {
    return receipt.getCompany().getName();
  }

  public String getCompanyCountry(Receipt receipt) {
    return receipt.getCompany().getCountry();
  }

  public String getCompanyCity(Receipt receipt) {
    return receipt.getCompany().getCity();
  }

  public String getCompanyStreet(Receipt receipt) {
    return receipt.getCompany().getStreet();
  }

  public int getCompanyNumber(Receipt receipt) {
    return receipt.getCompany().getNumber();
  }

}