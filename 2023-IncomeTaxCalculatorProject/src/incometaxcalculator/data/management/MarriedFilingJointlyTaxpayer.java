package incometaxcalculator.data.management;

public class MarriedFilingJointlyTaxpayer extends Taxpayer {
  
  
  public MarriedFilingJointlyTaxpayer(String fullname, int taxRegistrationNumber, float income) {
    super(fullname, taxRegistrationNumber, income);
    double[] incomeCompareNew = {36080,90000,143350,254240};
    this.incomeCompare= incomeCompareNew;
    double[] taxReturnNew = {0.0535 * income,1930.28 + 0.0705 * (income - 36080),5731.64 + 0.0705 * (income - 90000),9492.82 + 0.0785 * (income - 143350),18197.69 + 0.0985 * (income - 254240)};
    this.taxReturn = taxReturnNew ;
  }
  
}
