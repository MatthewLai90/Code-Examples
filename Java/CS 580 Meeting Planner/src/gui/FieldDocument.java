package gui;
import javax.swing.text.*;

public class FieldDocument extends PlainDocument
{
     private int limit;
     public static final int DEFAULT_TEXT_LENGTH=50;
     public static final int PHONE_TEXT_LENGTH=10;
     public static final int SSN_TEXT_LENGTH=9;
     public FieldDocument(int theLimit)
     {
          super();
          this.limit = theLimit;
     }

     public void insertString(int offset, String s, AttributeSet attributeSet)
          throws BadLocationException
     {
          if (limit==0 || getLength() + s.length() <= limit)
          {
          // if we haven't reached the limit, insert the string
               super.insertString(offset, s, attributeSet);
          }
          else
          {
          // otherwise, just lose the string
               java.awt.Toolkit.getDefaultToolkit().beep();
          }
     }
}