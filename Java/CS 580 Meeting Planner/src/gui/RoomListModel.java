package gui;
import javax.swing.AbstractListModel;
import entities.Room;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;

public class RoomListModel extends AbstractTableModel {
    private String[] columnNames = {
            "Room ID", "Room Name", "Capacity"};
    
    private Vector<Room> data;
    public RoomListModel(Vector<Room> theData){
        data=theData;
    }
    
    public int getColumnCount() {
        return columnNames.length;
    }
    
    public int getRowCount() {
        return data.size();
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public void addElement() {
        fireTableRowsInserted(getRowCount(), getRowCount());
    }    

    public void addElement(Room obj) {
        int index = data.size();
        fireTableRowsInserted(getRowCount(), getRowCount());
    }
    
    public void remove(int row) {
        fireTableRowsDeleted(row, row);
    }
    
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }


    public Object getValueAt(int row, int col) {
        switch(col){
            case 0: // ID
                return data.get(row).getRoomID();
            case 1: //name
                return data.get(row).getRoomName();
            case 2: //Capacity
                return data.get(row).getRoomCapacity();
        }
        return null;
    }

    public Room getElementAt(int index) {
        return data.elementAt(index);
    }

    public Vector<Room> getData() {
        return data;
    }
}
//public class RoomListModel extends AbstractListModel {
//    private Vector<Room> data;
//    public RoomListModel(Vector<Room> theData){
//        data=theData;
//        this.fireIntervalAdded(this, 0, data.size()-1);
//    }
//
//    public void addElement(Room obj) {
//        int index = data.size();
//        fireIntervalAdded(this, index, index);
//    }
//
//    public void remove(int index) {
//        fireIntervalRemoved(this, index, index);
//    }
//
//    public Room getElementAt(int index) {
//        return data.elementAt(index);
//    }
//    
//    public int getSize(){
//        return data.size();
//    }
//
//    public Vector<Room> getData() {
//        return data;
//    }
//    
//    
//}
