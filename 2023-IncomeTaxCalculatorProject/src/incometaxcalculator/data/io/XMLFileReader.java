package incometaxcalculator.data.io;

public class XMLFileReader extends FileReader {


  protected String getValueOfField(String fieldsLine) {
    String valueWithTail[] = fieldsLine.split(" ", 2);
    String valueReversed[] = new StringBuilder(valueWithTail[1]).reverse().toString().trim()
        .split(" ", 2);
    return new StringBuilder(valueReversed[1]).reverse().toString();
  }
  

}
  
  
  
