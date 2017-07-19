package gui;
import java.awt.event.*;
import javax.swing.*;
import functions.*;
import java.util.Vector;
import entities.*;
import java.sql.SQLException;

public class AdminDialog extends javax.swing.JDialog {
    private AdminEmpTableModel aetm;
    private Vector<Employee> empList;
    private RoomListModel theRoomListModel;
    public AdminDialog(javax.swing.JDialog parent, boolean modal) {
        super(parent, modal);
        empList=MainProgram.getEmployeeList();
        aetm=new AdminEmpTableModel(empList);
        theRoomListModel=new RoomListModel(MainProgram.getRoomList());
        initComponents();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        accountPanel = new javax.swing.JPanel();
        createAccBtn = new javax.swing.JButton();
        delAccBtn = new javax.swing.JButton();
        resetPassBtn = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        accInfoTable = new javax.swing.JTable();
        roomPanel = new javax.swing.JPanel();
        createRoomBtn = new javax.swing.JButton();
        delRoomBtn = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        roomTable = new javax.swing.JTable();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Administrative Functions");

        createAccBtn.setMnemonic('c');
        createAccBtn.setText("Create Account...");
        createAccBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createAccBtnActionPerformed(evt);
            }
        });

        delAccBtn.setMnemonic('d');
        delAccBtn.setText("Delete Account");
        delAccBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delAccBtnActionPerformed(evt);
            }
        });

        resetPassBtn.setMnemonic('r');
        resetPassBtn.setText("Reset Password");
        resetPassBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetPassBtnActionPerformed(evt);
            }
        });

        accInfoTable.setModel(aetm);
        accInfoTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane2.setViewportView(accInfoTable);

        javax.swing.GroupLayout accountPanelLayout = new javax.swing.GroupLayout(accountPanel);
        accountPanel.setLayout(accountPanelLayout);
        accountPanelLayout.setHorizontalGroup(
            accountPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(accountPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(createAccBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(delAccBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(resetPassBtn)
                .addContainerGap(30, Short.MAX_VALUE))
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE)
        );
        accountPanelLayout.setVerticalGroup(
            accountPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(accountPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(accountPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(createAccBtn)
                    .addComponent(delAccBtn)
                    .addComponent(resetPassBtn))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Employees", accountPanel);
        jTabbedPane1.setMnemonicAt(0, KeyEvent.VK_A);

        createRoomBtn.setMnemonic('c');
        createRoomBtn.setText("Create New Room...");
        createRoomBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createRoomBtnActionPerformed(evt);
            }
        });

        delRoomBtn.setMnemonic('d');
        delRoomBtn.setText("Delete Selected Room");
        delRoomBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delRoomBtnActionPerformed(evt);
            }
        });

        roomTable.setModel(theRoomListModel);
        javax.swing.table.TableColumn column = null;
        for(int i = 0; i < roomTable.getColumnCount(); i++)
        {
            column = roomTable.getColumnModel().getColumn(i);
            switch(i){
                case 0:
                column.setPreferredWidth(30);
                break;
                case 1:
                column.setPreferredWidth(240);
                break;
                case 2:
                column.setPreferredWidth(30);
                break;
            }
        }
        jScrollPane4.setViewportView(roomTable);

        javax.swing.GroupLayout roomPanelLayout = new javax.swing.GroupLayout(roomPanel);
        roomPanel.setLayout(roomPanelLayout);
        roomPanelLayout.setHorizontalGroup(
            roomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roomPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(createRoomBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(delRoomBtn)
                .addContainerGap(101, Short.MAX_VALUE))
            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        roomPanelLayout.setVerticalGroup(
            roomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roomPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(roomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(createRoomBtn)
                    .addComponent(delRoomBtn))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Rooms", roomPanel);
        jTabbedPane1.setMnemonicAt(1, KeyEvent.VK_O);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 286, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void createAccBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createAccBtnActionPerformed
    helperCreateAcc();
}//GEN-LAST:event_createAccBtnActionPerformed

private void helperCreateAcc(){
    Employee newEm=new CreateAccountDialog(this,true).openDialog();
    if(newEm!=null){
        aetm.rowAdded();
    }
}

private void createRoomBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createRoomBtnActionPerformed
    helperCreateRoomWithCapacity();
}//GEN-LAST:event_createRoomBtnActionPerformed

private void helperCreateRoom(){
    String name=JOptionPane.showInputDialog(this,"Enter room name:");
    try{
        Room newRoom=AdminActions.createRoom(name);
        theRoomListModel.addElement(newRoom);
        JOptionPane.showMessageDialog(this, "Room created");
    }catch(RoomExistsException e){
        JOptionPane.showMessageDialog(this, "Room already exists",null,JOptionPane.ERROR_MESSAGE); 
    }catch(SQLException e){
        JOptionPane.showMessageDialog(this, "Error creating: "+e.getMessage(),null,JOptionPane.ERROR_MESSAGE); 
    }
  
}

private void helperCreateRoomWithCapacity(){
    JPanel roomCreatePanel = new JPanel();
    JTextField roomName = new JTextField(10);
    JTextField roomCap = new JTextField(10);
    roomCreatePanel.add(new JLabel("Enter Room Name: "));
    roomCreatePanel.add(roomName);
    roomCreatePanel.add(Box.createHorizontalStrut(15));
    roomCreatePanel.add(new JLabel("Enter Room Capacity: "));
    roomCreatePanel.add(roomCap);
//    String name=JOptionPane.showInputDialog(this,"Enter room name:");
    int result = JOptionPane.showConfirmDialog(null, roomCreatePanel, "New Room", JOptionPane.OK_CANCEL_OPTION);
    String name = roomName.getText();
    int capacity = Integer.valueOf(roomCap.getText());
    try{
        Room newRoom=AdminActions.createRoom(name, capacity);
        theRoomListModel.addElement(newRoom);
        JOptionPane.showMessageDialog(this, "Room created");
    }catch(RoomExistsException e){
        JOptionPane.showMessageDialog(this, "Room already exists",null,JOptionPane.ERROR_MESSAGE); 
    }catch(SQLException e){
        JOptionPane.showMessageDialog(this, "Error creating: "+e.getMessage(),null,JOptionPane.ERROR_MESSAGE); 
    }
  
}

private void delAccBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delAccBtnActionPerformed
    helperDeleteAcc();
}//GEN-LAST:event_delAccBtnActionPerformed

private void helperDeleteAcc(){
    int selectedIndex=accInfoTable.getSelectedRow();
    if(selectedIndex<0) {
        JOptionPane.showMessageDialog(this, "Nothing selected",null,JOptionPane.ERROR_MESSAGE);
        return;
    }
    if(JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the account?")>0){
        return;
    }
        if(empList.get(selectedIndex).stillHasMeeting()){
            int option=JOptionPane.showConfirmDialog(this, "Warning! the employee still own meetings, continue to delete?");  
            if(option>0) return;
        }
    try{
        AdminActions.deleteEmployee(empList.get(selectedIndex).getEmployeeID());
        aetm.removeRow(selectedIndex);
        JOptionPane.showMessageDialog(this, "Account deleted");
    }catch(SQLException e){
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, e.getMessage(),null,JOptionPane.ERROR_MESSAGE);
    }catch(DeleteAdminException e){
        JOptionPane.showMessageDialog(this, "cannot delete admin",null,JOptionPane.ERROR_MESSAGE);
    }
}

private void resetPassBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetPassBtnActionPerformed
    helperResetPass();
}//GEN-LAST:event_resetPassBtnActionPerformed

private void helperResetPass(){
    int selectedIndex=accInfoTable.getSelectedRow();
    if(selectedIndex<0){
        JOptionPane.showMessageDialog(this, "Nothing selected",null,JOptionPane.ERROR_MESSAGE);
    }else{
        int option=JOptionPane.showConfirmDialog(this, "Are you sure you want to reset the password?");       
        if(option==0 && AdminActions.resetPass(empList.get(selectedIndex))) {
            JOptionPane.showMessageDialog(this, "Password reset to username");
        } 
    }
       
}

private void delRoomBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delRoomBtnActionPerformed
    helperDeleteRoom();
}//GEN-LAST:event_delRoomBtnActionPerformed

private void helperDeleteRoom(){
    int selectedIndex=roomTable.getSelectedRow();
    if(selectedIndex<0) {
        JOptionPane.showMessageDialog(this, "Nothing selected",null,JOptionPane.ERROR_MESSAGE);
        return;
    }
    if(JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the room?")>0){
        return;
    }
        if(theRoomListModel.getData().get(selectedIndex).stillHasMeeting()){
            int option=JOptionPane.showConfirmDialog(this, "Room has meetings, delete room and cancel all meetings in the room?");  
            if(option>0) return;
        }    
    try{
        AdminActions.deleteRoom(theRoomListModel.getElementAt(selectedIndex).getRoomID());
        theRoomListModel.remove(selectedIndex);
        JOptionPane.showMessageDialog(this, "Room deleted");
    }catch(SQLException e){
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, e.getMessage(),null,JOptionPane.ERROR_MESSAGE);
    }
    
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable accInfoTable;
    private javax.swing.JPanel accountPanel;
    private javax.swing.JButton createAccBtn;
    private javax.swing.JButton createRoomBtn;
    private javax.swing.JButton delAccBtn;
    private javax.swing.JButton delRoomBtn;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton resetPassBtn;
    private javax.swing.JPanel roomPanel;
    private javax.swing.JTable roomTable;
    // End of variables declaration//GEN-END:variables

}
