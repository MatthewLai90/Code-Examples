package gui;
import gui.meetings.*;
import javax.swing.*;
import java.awt.event.*;
import entities.*;
import java.sql.SQLException;

public class EmployeeDialog extends javax.swing.JDialog {
    private Employee emp;
    private EmpScheduleTableModel empScheduleModel;
    private boolean isAdmin;
    /** Creates new form EmployeeDialog */
    public EmployeeDialog(javax.swing.JDialog parent, boolean modal, Employee theEmployee) {
        super(parent, modal);
        emp=theEmployee;
        empScheduleModel=new EmpScheduleTableModel(emp);
        initComponents();
        if(theEmployee!=null){
            ssnTxt.setText(theEmployee.getSSN());
            nameTxt.setText(theEmployee.getName());
            addrTxt.setText(theEmployee.getAddress());
            phoneTxt.setText(theEmployee.getPhoneNumber());
            emailTxt.setText(theEmployee.getEmail());
            deptTxt.setText(theEmployee.getDepartment());
            isAdmin=theEmployee.isItAdmin();
            isAdminTxt.setText(isAdmin?"Yes":"No");
            this.setTitle("Employee ID:"+theEmployee.getEmployeeID()+", UserName:"
                    +theEmployee.getUserName()+", Admin:"+(isAdmin?"Yes":"No"));
            if(theEmployee.isItAdmin()){
                adminFuncMI.setEnabled(true);
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        weeklyScheduleTable = new javax.swing.JTable();
        editScheduleBtn = new javax.swing.JButton();
        createScheduleBtn = new javax.swing.JButton();
        deleteScheduleBtn = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        nameTxt = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        ssnTxt = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        addrTxt = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        phoneTxt = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        emailTxt = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        editInfoBtn = new javax.swing.JButton();
        saveInfoBtn = new javax.swing.JButton();
        changePassBtn = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        isAdminTxt = new javax.swing.JTextField();
        deptTxt = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        adminFuncMI = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Employee ID:???, UserName:???, Admin:Yes/No");

        weeklyScheduleTable.setModel(empScheduleModel);
        jScrollPane1.setViewportView(weeklyScheduleTable);

        editScheduleBtn.setMnemonic('e');
        editScheduleBtn.setText("Edit Selected...");
        editScheduleBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editScheduleBtnActionPerformed(evt);
            }
        });

        createScheduleBtn.setMnemonic('c');
        createScheduleBtn.setText("Create Schedule...");
        createScheduleBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createScheduleBtnActionPerformed(evt);
            }
        });

        deleteScheduleBtn.setMnemonic('d');
        deleteScheduleBtn.setText("Delete Selected");
        deleteScheduleBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteScheduleBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(createScheduleBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editScheduleBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(deleteScheduleBtn)
                .addContainerGap(28, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 387, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editScheduleBtn)
                    .addComponent(createScheduleBtn)
                    .addComponent(deleteScheduleBtn))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Weekly Schedule", jPanel1);
        jTabbedPane1.setMnemonicAt(0, KeyEvent.VK_F);

        jLabel1.setDisplayedMnemonic('n');
        jLabel1.setLabelFor(nameTxt);
        jLabel1.setText("Name:");

        nameTxt.setDocument(new FieldDocument(FieldDocument.DEFAULT_TEXT_LENGTH));
        nameTxt.setEditable(false);
        nameTxt.setText("jTextField1");

        jLabel2.setDisplayedMnemonic('s');
        jLabel2.setLabelFor(ssnTxt);
        jLabel2.setText("SSN:");

        ssnTxt.setDocument(new NumFieldDocument(FieldDocument.SSN_TEXT_LENGTH));
        ssnTxt.setEditable(false);
        ssnTxt.setText("jTextField2");

        jLabel3.setDisplayedMnemonic('d');
        jLabel3.setLabelFor(addrTxt);
        jLabel3.setText("Address:");

        addrTxt.setDocument(new FieldDocument(FieldDocument.DEFAULT_TEXT_LENGTH));
        addrTxt.setEditable(false);
        addrTxt.setText("jTextField3");

        jLabel4.setDisplayedMnemonic('h');
        jLabel4.setLabelFor(phoneTxt);
        jLabel4.setText("Phone Number:");

        phoneTxt.setDocument(new NumFieldDocument(FieldDocument.PHONE_TEXT_LENGTH));
        phoneTxt.setEditable(false);
        phoneTxt.setText("jTextField4");

        jLabel5.setDisplayedMnemonic('l');
        jLabel5.setLabelFor(emailTxt);
        jLabel5.setText("Email:");

        emailTxt.setDocument(new FieldDocument(FieldDocument.DEFAULT_TEXT_LENGTH));
        emailTxt.setEditable(false);
        emailTxt.setText("jTextField5");

        jLabel6.setDisplayedMnemonic('t');
        jLabel6.setText("Department:");

        editInfoBtn.setMnemonic('e');
        editInfoBtn.setText("Edit Info");
        editInfoBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editInfoBtnActionPerformed(evt);
            }
        });

        saveInfoBtn.setMnemonic('v');
        saveInfoBtn.setText("Save Info");
        saveInfoBtn.setEnabled(false);
        saveInfoBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveInfoBtnActionPerformed(evt);
            }
        });

        changePassBtn.setMnemonic('c');
        changePassBtn.setText("Change Password...");
        changePassBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changePassBtnActionPerformed(evt);
            }
        });

        jLabel8.setDisplayedMnemonic('i');
        jLabel8.setLabelFor(isAdminTxt);
        jLabel8.setText("Is Admin?:");

        isAdminTxt.setEditable(false);
        isAdminTxt.setText("Yes/No");

        deptTxt.setDocument(new FieldDocument(FieldDocument.DEFAULT_TEXT_LENGTH));
        deptTxt.setEditable(false);
        deptTxt.setText("jTextField1");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ssnTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addrTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(phoneTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(emailTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deptTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(isAdminTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(editInfoBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(saveInfoBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(changePassBtn)))
                .addContainerGap(82, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(ssnTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(addrTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(phoneTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(emailTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(deptTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(isAdminTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editInfoBtn)
                    .addComponent(saveInfoBtn)
                    .addComponent(changePassBtn))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Personal Info", jPanel2);
        jTabbedPane1.setMnemonicAt(1, KeyEvent.VK_P);

        jMenu1.setMnemonic('a');
        jMenu1.setText("Actions");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem1.setText("Display Meetings...");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        adminFuncMI.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.ALT_MASK));
        adminFuncMI.setText("Administrative Functions...");
        adminFuncMI.setEnabled(false);
        adminFuncMI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminFuncMIActionPerformed(evt);
            }
        });
        jMenu1.add(adminFuncMI);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void changePassBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changePassBtnActionPerformed
    new ChangePasswdDialog(this,true,emp).setVisible(true);
}//GEN-LAST:event_changePassBtnActionPerformed

private void deleteScheduleBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteScheduleBtnActionPerformed
    helperDeleteSchedule();
}//GEN-LAST:event_deleteScheduleBtnActionPerformed

private void helperDeleteSchedule(){
    int selectedIndex=weeklyScheduleTable.getSelectedRow();
    if(selectedIndex<0) {
        JOptionPane.showMessageDialog(this, "Nothing selected",null,JOptionPane.ERROR_MESSAGE);
         return;
    }else{
        int option=JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the schedule?");   
        if(option==0){
            empScheduleModel.removeRow(selectedIndex);
            JOptionPane.showMessageDialog(this, "Schedule deleted");
        }
    }    
}

private void saveInfoBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveInfoBtnActionPerformed
    updateInfo();
}//GEN-LAST:event_saveInfoBtnActionPerformed

private void updateInfo(){
    boolean updatesuccess=true;
    try{
        emp.updateInfo(addrTxt.getText(), deptTxt.getText(), emailTxt.getText(),
                nameTxt.getText(), phoneTxt.getText(), ssnTxt.getText());
    }catch(InvalidInfoException e){
        JOptionPane.showMessageDialog(this, e.getMessage(),null,JOptionPane.ERROR_MESSAGE);
        updatesuccess=false;
    }catch(SQLException e){
        JOptionPane.showMessageDialog(this, e.getMessage(),null,JOptionPane.ERROR_MESSAGE);
        updatesuccess=false;
    }
    if(updatesuccess){
        JOptionPane.showMessageDialog(this, "Personal info updated");
    }
    toggleEditSave(false);
}

private void editInfoBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editInfoBtnActionPerformed
    toggleEditSave(true);
}//GEN-LAST:event_editInfoBtnActionPerformed

private void editScheduleBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editScheduleBtnActionPerformed
    helperEditSchedule();
}//GEN-LAST:event_editScheduleBtnActionPerformed

private void helperEditSchedule(){
    int selectedIndex=weeklyScheduleTable.getSelectedRow();
    if(selectedIndex<0) {
        JOptionPane.showMessageDialog(this, "Nothing selected",null,JOptionPane.ERROR_MESSAGE);
        return;
    }
    boolean updateSuccess=new ModifyScheduleDialog(this,true,emp.getMySchedule().get(selectedIndex)).openDialog();
    if(updateSuccess){
        empScheduleModel.fireTableRowsUpdated(selectedIndex, selectedIndex);
    }
}

private void createScheduleBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createScheduleBtnActionPerformed
    helperCreateSchedule();
}//GEN-LAST:event_createScheduleBtnActionPerformed

private void helperCreateSchedule(){
    WeeklySchedule newSchedule= new CreateScheduleDialog(this,true,emp).openDialog();
    if(newSchedule!=null){
        empScheduleModel.addRow(newSchedule);
    }
}

private void adminFuncMIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminFuncMIActionPerformed
    if(emp==null || !emp.isItAdmin()){
        JOptionPane.showMessageDialog(this, "you are not admin",null,JOptionPane.ERROR_MESSAGE);
        return;
    }else{
        AdminDialog diag=new AdminDialog(this,true);
        diag.setVisible(true);
    }
}//GEN-LAST:event_adminFuncMIActionPerformed

private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
    new DisplayMeetingDiag(this,true,emp).setVisible(true);
}//GEN-LAST:event_jMenuItem1ActionPerformed

private void toggleEditSave(boolean enableEdit){
    if(enableEdit){
        ssnTxt.setEditable(true);
        nameTxt.setEditable(true);
        addrTxt.setEditable(true);
        phoneTxt.setEditable(true);
        emailTxt.setEditable(true);
        deptTxt.setEditable(true);
        editInfoBtn.setEnabled(false);
        saveInfoBtn.setEnabled(true);
    }else{
        ssnTxt.setEditable(false);
        nameTxt.setEditable(false);
        addrTxt.setEditable(false);
        phoneTxt.setEditable(false);
        emailTxt.setEditable(false);
        deptTxt.setEditable(false);
        editInfoBtn.setEnabled(true);
        saveInfoBtn.setEnabled(false);
    }
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField addrTxt;
    private javax.swing.JMenuItem adminFuncMI;
    private javax.swing.JButton changePassBtn;
    private javax.swing.JButton createScheduleBtn;
    private javax.swing.JButton deleteScheduleBtn;
    private javax.swing.JTextField deptTxt;
    private javax.swing.JButton editInfoBtn;
    private javax.swing.JButton editScheduleBtn;
    private javax.swing.JTextField emailTxt;
    private javax.swing.JTextField isAdminTxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField nameTxt;
    private javax.swing.JTextField phoneTxt;
    private javax.swing.JButton saveInfoBtn;
    private javax.swing.JTextField ssnTxt;
    private javax.swing.JTable weeklyScheduleTable;
    // End of variables declaration//GEN-END:variables

}
