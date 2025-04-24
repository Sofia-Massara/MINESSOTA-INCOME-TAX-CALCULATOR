package incometaxcalculator.data.management;

import java.util.HashMap;
import incometaxcalculator.exceptions.WrongReceiptKindException;

public abstract class Taxpayer {

  protected final String fullname;
  protected final int taxRegistrationNumber;
  protected final float income;
  private float amountPerReceiptsKind[] = new float[5];
  private int totalReceiptsGathered = 0;
  private HashMap<Integer, Receipt> receiptHashMap = new HashMap<Integer, Receipt>(0);
  
  private String[] receiptKind = {"Entertainment","Basic","Travel","Health","Other"};
 
  protected double[] incomeCompare;
  protected double[] taxReturn;

  
   protected Taxpayer(String fullname, int taxRegistrationNumber, float income) {
    incomeCompare= new double[4];
    taxReturn = new double[5];
    this.fullname = fullname;
    this.taxRegistrationNumber = taxRegistrationNumber;
    this.income = income;
  }
   
   


  public void addReceipt(Receipt receipt) throws WrongReceiptKindException {
    int needException=0;
    for(int i=0; i< receiptKind.length; i++){
      
      if (receipt.getKind().equals(receiptKind[i])){
        
        needException+=1;
        amountPerReceiptsKind[i] += receipt.getAmount(); 
      }
    }
    if (needException==0) {
      throw new WrongReceiptKindException();
    }
    receiptHashMap.put(receipt.getId(), receipt);
    totalReceiptsGathered++;
  }
  
  
  public void removeReceipt(int receiptId) throws WrongReceiptKindException {
    Receipt receipt = receiptHashMap.get(receiptId);
    int needException=0;
    for(int i=0; i< receiptKind.length; i++) {
      if (receipt.getKind().equals(receiptKind[i])) {
        needException+=1;   
        amountPerReceiptsKind[i] -= receipt.getAmount();  
      }     
    }
    if (needException==0) {
      throw new WrongReceiptKindException();
    }
    totalReceiptsGathered--;
    receiptHashMap.remove(receiptId);
  }

  public String getFullname() {
    return fullname;
  }

  public int getTaxRegistrationNumber() {
    return taxRegistrationNumber;
  }

  public float getIncome() {
    return income;
  }

  public HashMap<Integer, Receipt> getReceiptHashMap() {
    return receiptHashMap;
  }

  public double getVariationTaxOnReceipts() {
    float totalAmountOfReceipts = getTotalAmountOfReceipts();
    double[] finalIncome = { (0.2 * income), (0.4 * income), (0.6 * income)};
    double[] finalBasicTax = {calculateBasicTax(incomeCompare,taxReturn) *0.08, calculateBasicTax(incomeCompare,taxReturn) *0.04,calculateBasicTax(incomeCompare,taxReturn) *(-0.15)};
    
    for(int i=0; i< finalIncome.length-1; i++) {
      if (totalAmountOfReceipts < finalIncome[i]) {
        return finalBasicTax[i];
      }
    }
    return -calculateBasicTax(incomeCompare,taxReturn) * 0.3;
  }
  
  public double calculateBasicTax(double[] incomeCompare,double[] taxReturn) {
    incomeCompare= this.incomeCompare;
    taxReturn= this.taxReturn;
    
    for(int i=0;i<incomeCompare.length;i++) {
      if (income<incomeCompare[i]) {
        return taxReturn[i];
      }
    }
    int lastElem = taxReturn.length;
    return taxReturn[lastElem-1];    
  }

  private float getTotalAmountOfReceipts() {
    int sum = 0;
    for (int i = 0; i < 5; i++) {
      sum += amountPerReceiptsKind[i];
    }
    return sum;
  }

  public int getTotalReceiptsGathered() {
    return totalReceiptsGathered;
  }

  public float getAmountOfReceiptKind(short kind) {
    return amountPerReceiptsKind[kind];
  }

  public double getTotalTax() {
    return calculateBasicTax(incomeCompare,taxReturn) + getVariationTaxOnReceipts();
  }

  public double getBasicTax() {
    return calculateBasicTax(incomeCompare,taxReturn);
  }

}