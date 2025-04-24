package incometaxcalculator.data.io;


public class TXTFileReader extends FileReader {

 
  protected String getValueOfField(String fieldsLine) {
    String values[] = fieldsLine.split(":", 2);
    values[1] = values[1].trim();
    return values[1];
  }
  

}