package incometaxcalculator.data.management;

public class MarriedFilingSeparatelyTaxpayer extends Taxpayer {
  
  
  public MarriedFilingSeparatelyTaxpayer(String fullname, int taxRegistrationNumber, float income) {
    super(fullname, taxRegistrationNumber, income);
    double[] incomeCompareNew = {18040,71680,90000,127120};
    this.incomeCompare= incomeCompareNew;
    double[] taxReturnNew = {0.0535 * income,965.14 + 0.0705 * (income - 18040),4746.76 + 0.0785 * (income - 71680),6184.88 + 0.0785 * (income - 90000),9098.80 + 0.0985 * (income - 127120)};
    this.taxReturn = taxReturnNew;
  }
  
  
}