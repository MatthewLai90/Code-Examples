package gui.meetings;
import javax.swing.*;
import entities.*;
import functions.MainProgram;
import java.util.Vector;
import java.sql.SQLException;

public class InviteEmployeeDialog extends javax.swing.JDialog {
    private Meeting m;
    InviteEmTableModel tmodel;
    Employee whoOpened;
    Vector<Employee> empList;
    boolean changed=false;
    /** Creates new form NewJDialog */
    public InviteEmployeeDialog(javax.swing.JDialog parent, boolean modal, Employee whoOpened, Meeting me) {
        super(parent, modal);
        empList=MainProgram.getEmployeeList();
        tmodel=new InviteEmTableModel(empList);
        this.m=me;
        this.whoOpened=whoOpened;
        initComponents();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        employeeListLbl = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        inviteEmTable = new javax.swing.JTable();
        inviteBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Invite Employee");

        employeeListLbl.setDisplayedMnemonic('e');
        employeeListLbl.setLabelFor(inviteEmTable);
        employeeListLbl.setText("Employee List:");

        inviteEmTable.setAutoCreateRowSorter(true);
        inviteEmTable.setModel(tmodel);
        inviteEmTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane1.setViewportView(inviteEmTable);

        inviteBtn.setMnemonic('i');
        inviteBtn.setText("Invite Selected");
        inviteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inviteBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(employeeListLbl)
                    .addComponent(inviteBtn))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(employeeListLbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inviteBtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void inviteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inviteBtnActionPerformed
    helperInviteEmployee();
}//GEN-LAST:event_inviteBtnActionPerformed

private void helperInviteEmployee(){
    int selectedIndex=inviteEmTable.getSelectedRow();
    if(selectedIndex<0){
        JOptionPane.showMessageDialog(this, "nothing selected");
        return;
    }else{
        try{
            m.inviteEmployee(empList.get(selectedIndex));
            JOptionPane.showMessageDialog(this, "Employee invited");
            changed=true;
        }catch(SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,e.getMessage(),null,JOptionPane.ERROR_MESSAGE); 
        }catch(EmployeeAlreadyInMeetingException e){
            JOptionPane.showMessageDialog(this, "Employee already in meeting",null,JOptionPane.ERROR_MESSAGE); 
        }catch(ScheduleConflictException e){
            JOptionPane.showMessageDialog(this, "Schedule conflict:"+e.getMessage(),null,JOptionPane.ERROR_MESSAGE); 
        }
    }
}

public boolean openDialog(){
    setVisible(true);
    return changed;
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel employeeListLbl;
    private javax.swing.JButton inviteBtn;
    private javax.swing.JTable inviteEmTable;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

}
