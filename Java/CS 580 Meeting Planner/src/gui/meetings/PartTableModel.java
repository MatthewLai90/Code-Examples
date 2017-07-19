package gui.meetings;
import entities.*;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;

class PartTableModel extends AbstractTableModel {
    private String[] columnNames = {
        "EmployeeID", "EmployeeName", "Department"
    };
    
    private Vector<Employee> empList;

    public PartTableModel(Vector<Employee> theEmpList){
        this.empList=theEmpList;
    }
    
    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return empList.size();
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        switch(col){
            case 0: //id
            return empList.get(row).getEmployeeID();
            case 1: //name
            return empList.get(row).getName();
            case 2: //department
            return empList.get(row).getDepartment();                
        }
        return null;
    }

    public void addRow(Employee rowData) {
        empList.add(rowData);
            fireTableRowsInserted(getRowCount(), getRowCount());
    }    
    
    public void removeRow(int row) {
        empList.remove(row);
        fireTableRowsDeleted(row, row);
    }
    
    public void setDataVector(Vector<Employee> em) {
        empList=em;
        fireTableStructureChanged();
    }
    
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

}

