package gui.meetings;
import javax.swing.*;
import java.util.Vector;
import entities.*;
import functions.MainProgram;
import java.sql.SQLException;

public class DisplayMeetingDiag extends javax.swing.JDialog {
    private Vector<Meeting> meetingList;
    private MeetingScheduleTableModel tm;
    private Employee em;
    /** Creates new form DisplayMeetingDiag */
    public DisplayMeetingDiag(javax.swing.JDialog parent, boolean modal, Employee em) {
        super(parent, modal);
        meetingList=MainProgram.getMeetingList();
        tm=new MeetingScheduleTableModel(meetingList);
        this.em=em;
        initComponents();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        meetingTable = new javax.swing.JTable();
        showParticipatingRadio = new javax.swing.JRadioButton();
        showAllMeetingRadio = new javax.swing.JRadioButton();
        showOwnedRadio = new javax.swing.JRadioButton();
        modifyMeetingBtn = new javax.swing.JButton();
        cancelMeetingBtn = new javax.swing.JButton();
        createMeetingBtn = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        selectedMeetingLbl = new javax.swing.JLabel();
        showParticipantBtn = new javax.swing.JButton();
        inviteEmployeeBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Meeting List");

        meetingTable.setModel(tm);
        jScrollPane3.setViewportView(meetingTable);

        buttonGroup1.add(showParticipatingRadio);
        showParticipatingRadio.setText("Show Participating Meetings Only");
        showParticipatingRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showParticipatingRadioActionPerformed(evt);
            }
        });

        buttonGroup1.add(showAllMeetingRadio);
        showAllMeetingRadio.setSelected(true);
        showAllMeetingRadio.setText("Show All Meetings");
        showAllMeetingRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showAllMeetingRadioActionPerformed(evt);
            }
        });

        buttonGroup1.add(showOwnedRadio);
        showOwnedRadio.setText("Show Owned Meetings Only");
        showOwnedRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showOwnedRadioActionPerformed(evt);
            }
        });

        modifyMeetingBtn.setMnemonic('o');
        modifyMeetingBtn.setText("Modify Selected Meeting...");
        modifyMeetingBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modifyMeetingBtnActionPerformed(evt);
            }
        });

        cancelMeetingBtn.setMnemonic('n');
        cancelMeetingBtn.setText("Cancel Selected Meeting");
        cancelMeetingBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelMeetingBtnActionPerformed(evt);
            }
        });

        createMeetingBtn.setMnemonic('c');
        createMeetingBtn.setText("Create Meeting...");
        createMeetingBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createMeetingBtnActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        selectedMeetingLbl.setText("For the selected meeting:");

        showParticipantBtn.setText("Show Participants...");
        showParticipantBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showParticipantBtnActionPerformed(evt);
            }
        });

        inviteEmployeeBtn.setText("Invite Employee...");
        inviteEmployeeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inviteEmployeeBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(selectedMeetingLbl)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(showParticipantBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(inviteEmployeeBtn)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(selectedMeetingLbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(showParticipantBtn)
                    .addComponent(inviteEmployeeBtn))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addComponent(createMeetingBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(modifyMeetingBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelMeetingBtn))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(showParticipatingRadio)
                            .addComponent(showOwnedRadio)
                            .addComponent(showAllMeetingRadio))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(showParticipatingRadio)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(showOwnedRadio)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(showAllMeetingRadio))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(modifyMeetingBtn)
                    .addComponent(cancelMeetingBtn)
                    .addComponent(createMeetingBtn))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        //jTabbedPane1.setMnemonicAt(2, KeyEvent.VK_M);

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void modifyMeetingBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modifyMeetingBtnActionPerformed
    helperModifyMeeting();
}//GEN-LAST:event_modifyMeetingBtnActionPerformed

private void cancelMeetingBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelMeetingBtnActionPerformed
    helperCancelMeeting();
}//GEN-LAST:event_cancelMeetingBtnActionPerformed

private void createMeetingBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createMeetingBtnActionPerformed
    helperCreateMeeting();
}//GEN-LAST:event_createMeetingBtnActionPerformed

private void showParticipantBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showParticipantBtnActionPerformed
    helperOpenParticipantsDialog();
}//GEN-LAST:event_showParticipantBtnActionPerformed

private void inviteEmployeeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inviteEmployeeBtnActionPerformed
    helperInviteEmployee();
}//GEN-LAST:event_inviteEmployeeBtnActionPerformed

private void showAllMeetingRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showAllMeetingRadioActionPerformed
    tm.setDataVector(MainProgram.getMeetingList());
}//GEN-LAST:event_showAllMeetingRadioActionPerformed

private void showParticipatingRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showParticipatingRadioActionPerformed
    tm.setDataVector(MainProgram.getMeetingList(Meeting.SHOW_PARTICIPANT, em.getEmployeeID()));
}//GEN-LAST:event_showParticipatingRadioActionPerformed

private void showOwnedRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showOwnedRadioActionPerformed
    tm.setDataVector(MainProgram.getMeetingList(Meeting.SHOW_OWNED, em.getEmployeeID()));
}//GEN-LAST:event_showOwnedRadioActionPerformed

private void helperInviteEmployee(){
    int selectedIndex=meetingTable.getSelectedRow();
    if(selectedIndex<0){
        JOptionPane.showMessageDialog(this, "nothing selected",null,JOptionPane.ERROR_MESSAGE);
        return;
    }else{
        Meeting met=meetingList.get(selectedIndex);
        if(met.getOwner().getEmployeeID()!=em.getEmployeeID()){
            JOptionPane.showMessageDialog(this, "You are not the owner",null,JOptionPane.ERROR_MESSAGE);
        }else{
            new InviteEmployeeDialog(this,true,em,met).setVisible(true);    
        }            
    }
}

private void helperOpenParticipantsDialog(){
    int selectedIndex=meetingTable.getSelectedRow();
    if(selectedIndex<0){
        JOptionPane.showMessageDialog(this, "nothing selected",null,JOptionPane.ERROR_MESSAGE);
        return;
    }else{
        new MeetingParticipantList(this,true,em,meetingList.get(selectedIndex)).setVisible(true);
    }
}

private void helperCreateMeeting() {                                                 
    Meeting created=new CreateModifyMeeting(this,true,em).openDialog();
    if(created!=null){
        if(showParticipatingRadio.isSelected()){
            tm.setDataVector(MainProgram.getMeetingList(Meeting.SHOW_PARTICIPANT, em.getEmployeeID()));
        }else if(showOwnedRadio.isSelected()){
            tm.addRow(created);
        }else{
            tm.fireRowAdded();
        }
    }
}

private void helperModifyMeeting() {          
    int selectedIndex=meetingTable.getSelectedRow();
    if(selectedIndex<0){
        JOptionPane.showMessageDialog(this, "nothing selected",null,JOptionPane.ERROR_MESSAGE);
        return;
    }else if(meetingList.get(selectedIndex).getOwner().getEmployeeID()!=em.getEmployeeID()){
        JOptionPane.showMessageDialog(this, "You are not the meeting owner",null,JOptionPane.ERROR_MESSAGE);
        return;
    }else{
        Meeting met=meetingList.get(selectedIndex);
        met=new CreateModifyMeeting(this,true,em,met).openDialog();
            tm.fireTableRowsUpdated(selectedIndex, selectedIndex);
    }
    
}

private void helperCancelMeeting() {      
    int selectedIndex=meetingTable.getSelectedRow();
    if(selectedIndex<0){
        JOptionPane.showMessageDialog(this, "nothing selected",null,JOptionPane.ERROR_MESSAGE);
        return;
    }
    Meeting m=meetingList.get(selectedIndex);
    if(m.getOwner().getEmployeeID()!=em.getEmployeeID()){
        JOptionPane.showMessageDialog(this, "You are not the meeting owner",null,JOptionPane.ERROR_MESSAGE);
        return;
    }
    if(JOptionPane.showConfirmDialog(this, "Are you sure you want to cancel the meeting?")>0) return;   
    try{
        Employee.cancelMeeting(m.getMeetingID());
        tm.removeRow(selectedIndex);
        JOptionPane.showMessageDialog(this, "Meeting cancelled");
    }catch(SQLException e){
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, e.getMessage(),null,JOptionPane.ERROR_MESSAGE);
    }
    
}


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton cancelMeetingBtn;
    private javax.swing.JButton createMeetingBtn;
    private javax.swing.JButton inviteEmployeeBtn;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable meetingTable;
    private javax.swing.JButton modifyMeetingBtn;
    private javax.swing.JLabel selectedMeetingLbl;
    private javax.swing.JRadioButton showAllMeetingRadio;
    private javax.swing.JRadioButton showOwnedRadio;
    private javax.swing.JButton showParticipantBtn;
    private javax.swing.JRadioButton showParticipatingRadio;
    // End of variables declaration//GEN-END:variables

}
