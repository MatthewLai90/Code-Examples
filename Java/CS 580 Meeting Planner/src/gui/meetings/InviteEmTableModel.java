package gui.meetings;
import entities.*;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;

class InviteEmTableModel extends AbstractTableModel {
    private String[] columnNames = {
        "Employee ID", "User Name", "Real Name", "Address", "Phone", "Email", "Department"
    };
    
    private Vector<Employee> empList;

    public InviteEmTableModel(Vector<Employee> theEmpList){
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
            case 0: //employee ID
            return empList.get(row).getEmployeeID();            
            case 1: //username
            return empList.get(row).getUserName();
            case 2: //name
            return empList.get(row).getName();
            case 3: //address
            return empList.get(row).getAddress();         
            case 4: //phone
            return empList.get(row).getPhoneNumber();
            case 5: //email
            return empList.get(row).getEmail();
            case 6: //department
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
    
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

}

