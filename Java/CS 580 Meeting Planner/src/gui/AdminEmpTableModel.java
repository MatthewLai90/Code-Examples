package gui;
import entities.*;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;

class AdminEmpTableModel extends AbstractTableModel {
    private String[] columnNames = {
        "ID", "User Name", "SSN", "Real Name", "Address", "Phone Number", "Email", "Department"
    };
    
    private Vector<Employee> empList;

    public AdminEmpTableModel(Vector<Employee> theEmpList){
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
            case 1: //username
            return empList.get(row).getUserName();
            case 2: //ssn
            return empList.get(row).getSSN();
            case 3: //real name
            return empList.get(row).getName();                
            case 4: //address              
            return empList.get(row).getAddress();                
            case 5: //phone number
            return empList.get(row).getPhoneNumber();                
            case 6: //email               
            return empList.get(row).getEmail();                
            case 7: //department               
            return empList.get(row).getDepartment();                
        }
        return null;
    }

    public void rowAdded() {
            fireTableRowsInserted(getRowCount(), getRowCount());
    }    
    
    public void removeRow(int row) {
        fireTableRowsDeleted(row, row);
    }
    
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

}

