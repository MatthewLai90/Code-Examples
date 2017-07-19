package gui;
import javax.swing.text.*;

public class NumFieldDocument extends PlainDocument
{
     private int limit;

     public NumFieldDocument(int theLimit)
     {
          super();
          this.limit = theLimit;
     }

     public void insertString(int offset, String s, AttributeSet attributeSet)
          throws BadLocationException
     {
          if ((limit==0 || getLength() + s.length() <= limit)&&Character.isDigit(s.charAt(0)))
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