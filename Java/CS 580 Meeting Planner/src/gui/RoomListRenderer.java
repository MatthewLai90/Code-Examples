package gui;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import java.awt.Component;
import entities.Room;

public class RoomListRenderer extends DefaultListCellRenderer {
     public Component getListCellRendererComponent(
         JList list,
         Object value,
         int index,
         boolean isSelected,
         boolean cellHasFocus)
     {
         return super.getListCellRendererComponent(list, ((Room)value).getRoomName(), index, isSelected, cellHasFocus);
     }
}
