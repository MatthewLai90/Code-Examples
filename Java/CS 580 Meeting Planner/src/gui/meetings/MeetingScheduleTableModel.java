package gui.meetings;
import javax.swing.table.AbstractTableModel;
import java.util.Vector;
import entities.*;

public class MeetingScheduleTableModel extends AbstractTableModel {
    Vector<Meeting> dataVector;
    String[] columnNames= {"Owner", "Room", "Date", "TimeBegin", "TimeEnd"};
    public MeetingScheduleTableModel(Vector<Meeting> theData){
        dataVector=theData;
    }

    public void setDataVector(Vector<Meeting> dataVector) {
        this.dataVector = dataVector;
        this.fireTableDataChanged();
    }    

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return dataVector.size();
    }

    public void fireRowAdded() {
        fireTableRowsInserted(getRowCount(), getRowCount());
    }    

    public void addRow(Meeting rowData) {
        dataVector.add(rowData);
        fireTableRowsInserted(getRowCount(), getRowCount());
    }    
    
    public void removeRow(int row) {
        fireTableRowsDeleted(row, row);
    }    
    
    public String getColumnName(int col) {
        return columnNames[col];
    }
    
    public Object getValueAt(int row, int col) {
         switch(col){
            case 0:
            return dataVector.get(row).getOwner().getUserName();
            case 1:
            return dataVector.get(row).getMeetingRoom().getRoomName();
            case 2:
            return dataVector.get(row).getMeetingDate();
            case 3:
            return dataVector.get(row).getTimeBegin();
            case 4:
            return dataVector.get(row).getTimeEnd();
        }
         return null;
    }
    
}
