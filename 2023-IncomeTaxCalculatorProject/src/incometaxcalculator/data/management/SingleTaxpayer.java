package incometaxcalculator.data.management;

public class SingleTaxpayer extends Taxpayer {

  
  public SingleTaxpayer(String fullname, int taxRegistrationNumber, float income) {
    super(fullname, taxRegistrationNumber, income);
    double[] incomeCompareNew = {24680,81080,90000,152540};
    this.incomeCompare= incomeCompareNew;
    double[] taxReturnNew = {0.0535 * income,1320.38 + 0.0705 * (income - 24680),5296.58 + 0.0785 * (income - 81080),5996.80 + 0.0785 * (income - 90000),10906.19 + 0.0985 * (income - 152540)};
    this.taxReturn = taxReturnNew ;
  }

}