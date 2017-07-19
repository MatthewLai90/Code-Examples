package gui;
import javax.swing.*;
import entities.*;

public class ModifyScheduleDialog extends javax.swing.JDialog {
    private WeeklySchedule schedule;
    private boolean updated=false;
    private SpinnerDateModel timeBeginModel;
    private SpinnerDateModel timeEndModel;    
    /** Creates new form CreateScheduleDialog */
    public ModifyScheduleDialog(javax.swing.JDialog parent, boolean modal,WeeklySchedule theSchedule) {
        super(parent, modal);
        timeBeginModel= new SpinnerDateModel(theSchedule.getTimeUnAvailBegin(), null, null, java.util.Calendar.MINUTE);
        timeEndModel= new SpinnerDateModel(theSchedule.getTimeUnAvailEnd(), null, null, java.util.Calendar.MINUTE);
        initComponents();
        whichDayCombo.setSelectedIndex(theSchedule.getWhichDay().ordinal());
        schedule=theSchedule;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        whichLbl = new javax.swing.JLabel();
        timeBeginLbl = new javax.swing.JLabel();
        timeEndLbl = new javax.swing.JLabel();
        finishBtn = new javax.swing.JButton();
        cancelBtn = new javax.swing.JButton();
        whichDayCombo = new javax.swing.JComboBox();
        timeBeginSpin = new javax.swing.JSpinner();
        timeEndSpin = new javax.swing.JSpinner();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Modify Schedule");

        whichLbl.setDisplayedMnemonic('w');
        whichLbl.setText("Which Day:");

        timeBeginLbl.setDisplayedMnemonic('b');
        timeBeginLbl.setText("Unavailable Time Begin:");

        timeEndLbl.setDisplayedMnemonic('e');
        timeEndLbl.setText("Unavailable Time End:");

        finishBtn.setMnemonic('f');
        finishBtn.setText("Finish Modifying");
        finishBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                finishBtnActionPerformed(evt);
            }
        });

        cancelBtn.setMnemonic('c');
        cancelBtn.setText("Cancel Modifying");
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });

        whichDayCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" }));

        timeBeginSpin.setModel(timeBeginModel);
        timeBeginSpin.setEditor(new javax.swing.JSpinner.DateEditor(timeBeginSpin, "h:mm:ss a"));

        timeEndSpin.setModel(timeEndModel);
        timeEndSpin.setEditor(new javax.swing.JSpinner.DateEditor(timeEndSpin, "h:mm:ss a"));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(whichLbl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(whichDayCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(timeBeginLbl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(timeBeginSpin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(timeEndLbl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(timeEndSpin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(finishBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelBtn)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(whichLbl)
                    .addComponent(whichDayCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(timeBeginLbl)
                    .addComponent(timeBeginSpin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(timeEndLbl)
                    .addComponent(timeEndSpin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(finishBtn)
                    .addComponent(cancelBtn))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void finishBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_finishBtnActionPerformed
    helperModifySchedule();
}//GEN-LAST:event_finishBtnActionPerformed

private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
    this.dispose();
}//GEN-LAST:event_cancelBtnActionPerformed

private void helperModifySchedule(){
    java.sql.Time timeBegin;
    java.sql.Time timeEnd;
    timeBegin = new java.sql.Time(timeBeginModel.getDate().getTime());
    timeEnd = new java.sql.Time(timeEndModel.getDate().getTime());
    boolean updateSuccess=false;
    if(WeeklySchedule.scheduleConflictMeeting(schedule.getEmployeeID(),WeekDay.values()[whichDayCombo.getSelectedIndex()],timeBegin,timeEnd)){
        int option=JOptionPane.showConfirmDialog(this, "schedule conflicts meetings, drop from meetings and continue?");
        if(option>0) return;
    }    
    try{
        updateSuccess=schedule.updateSchedule(WeekDay.values()[whichDayCombo.getSelectedIndex()], timeBegin, timeEnd);    
    }catch(InvalidScheduleException e){
        JOptionPane.showMessageDialog(this, e.getMessage(),null,JOptionPane.ERROR_MESSAGE); 
        return;
    }
    if(updateSuccess){
        JOptionPane.showMessageDialog(this, "Schedule updated");
        updated=true;
        this.dispose();
    }else{
        JOptionPane.showMessageDialog(this, "Failed to update schedule",null,JOptionPane.ERROR_MESSAGE);
    }
}

public boolean openDialog(){
    this.setVisible(true);
    return updated;
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelBtn;
    private javax.swing.JButton finishBtn;
    private javax.swing.JLabel timeBeginLbl;
    private javax.swing.JSpinner timeBeginSpin;
    private javax.swing.JLabel timeEndLbl;
    private javax.swing.JSpinner timeEndSpin;
    private javax.swing.JComboBox whichDayCombo;
    private javax.swing.JLabel whichLbl;
    // End of variables declaration//GEN-END:variables

}
