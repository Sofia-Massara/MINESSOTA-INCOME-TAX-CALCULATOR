package incometaxcalculator.data.io;


import incometaxcalculator.data.management.Receipt;
import incometaxcalculator.data.management.TaxpayerManagerFactory;


public class TXTLogWriter extends LogWriter implements FileWriter  {

  
  public TXTLogWriter(TaxpayerManagerFactory taxpayer) {
    super(taxpayer);
  }




  public String[] createStringForGenerateFile() {
    
    String outputs[] = {"_LOG.txt","Name: ","AFM: ","Income: ","Basic Tax: ","Tax Increase: ","Tax Decrease: ","Total Tax: ", "TotalReceiptsGathered: ","Entertainment: ","Basic: ","Travel: ","Health: ","Other: "};
    
    return outputs;
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
