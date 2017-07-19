package gui;

import entities.*;
import java.text.SimpleDateFormat;
import javax.swing.table.AbstractTableModel;

class EmpScheduleTableModel extends AbstractTableModel {

    private String[] columnNames = {"Which Day", "Time Unavailable Begin", "Time Unavailable End"};
    private Employee emp;

    public EmpScheduleTableModel(Employee theEmp) {
        this.emp = theEmp;
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return emp.getMySchedule().size();
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("h:mm:ss a");
        switch (col) {
            case 0:
                return emp.getMySchedule().get(row).getWhichDay();
            case 1:
                return sdf.format(emp.getMySchedule().get(row).getTimeUnAvailBegin());
            case 2:
                return sdf.format(emp.getMySchedule().get(row).getTimeUnAvailEnd());
        }
        return null;
    }

    public void addRow(WeeklySchedule rowData) {
        emp.getMySchedule().add(rowData);
        fireTableRowsInserted(getRowCount(), getRowCount());
    }

    public void removeRow(int row) {
        emp.DeleteWeeklySchedule(emp.getMySchedule().get(row).getRecordID());
        fireTableRowsDeleted(row, row);
    }

    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
}

