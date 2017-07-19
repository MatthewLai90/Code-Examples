package gui.meetings;
import entities.*;
import java.util.Vector;
import functions.MainProgram;
import javax.swing.*;

public class MeetingParticipantList extends javax.swing.JDialog {
    Meeting theMeeting;
    Vector<Employee> participants;
    PartTableModel plm;
    Employee whoOpened;
    /** Creates new form MeetingParticipantList */
    public MeetingParticipantList(javax.swing.JDialog parent, boolean modal, Employee whoOpened, Meeting m) {
        super(parent, modal);
        theMeeting=m;
        participants=MainProgram.getMPC().getParticipantList(m);
        plm=new PartTableModel(participants);
        this.whoOpened=whoOpened;
        initComponents();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        participantListTable = new javax.swing.JTable();
        inviteBtn = new javax.swing.JButton();
        participantListLbl = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Participant List");

        participantListTable.setModel(plm);
        jScrollPane1.setViewportView(participantListTable);

        inviteBtn.setMnemonic('i');
        inviteBtn.setText("Invite more Employees...");
        inviteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inviteBtnActionPerformed(evt);
            }
        });

        participantListLbl.setDisplayedMnemonic('p');
        participantListLbl.setLabelFor(participantListTable);
        participantListLbl.setText("Participant List:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(participantListLbl)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inviteBtn))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(participantListLbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inviteBtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void inviteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inviteBtnActionPerformed
    helperInvite();
}//GEN-LAST:event_inviteBtnActionPerformed

private void helperInvite(){
    if(theMeeting.getOwner().getEmployeeID()!=whoOpened.getEmployeeID()){
        JOptionPane.showMessageDialog(this, "You are not the owner",null,JOptionPane.ERROR_MESSAGE);
    }else{
        boolean changed=new InviteEmployeeDialog(this,true,whoOpened,theMeeting).openDialog();    
        if(changed) plm.setDataVector(MainProgram.getMPC().getParticipantList(theMeeting));
    }    
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton inviteBtn;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel participantListLbl;
    private javax.swing.JTable participantListTable;
    // End of variables declaration//GEN-END:variables

}
