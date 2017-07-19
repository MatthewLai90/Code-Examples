package gui;
import javax.swing.*;
import entities.*;
import javax.swing.SpinnerDateModel;

public class CreateScheduleDialog extends javax.swing.JDialog {
    private WeeklySchedule createdSchedule;
    private Employee emp;
    //2000/1/1 12:00:00 AM
    private SpinnerDateModel timeBeginModel = new SpinnerDateModel(new java.util.Date(949392000000L), null, null, java.util.Calendar.MINUTE);
    private SpinnerDateModel timeEndModel = new SpinnerDateModel(new java.util.Date(949392000000L), null, null, java.util.Calendar.MINUTE);
    /** Creates new form CreateScheduleDialog */
    public CreateScheduleDialog(javax.swing.JDialog parent, boolean modal,Employee e) {
        super(parent, modal);
        emp=e;
        initComponents();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        whichDayLbl = new javax.swing.JLabel();
        timeBeginLbl = new javax.swing.JLabel();
        timeEndLbl = new javax.swing.JLabel();
        finishBtn = new javax.swing.JButton();
        cancelBtn = new javax.swing.JButton();
        whichDayCombo = new javax.swing.JComboBox();
        timeBeginSpin = new javax.swing.JSpinner();
        timeEndSpin = new javax.swing.JSpinner();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Create Schedule");

        whichDayLbl.setDisplayedMnemonic('w');
        whichDayLbl.setText("Which Day:");

        timeBeginLbl.setDisplayedMnemonic('b');
        timeBeginLbl.setText("Unavailable Time Begin:");

        timeEndLbl.setDisplayedMnemonic('e');
        timeEndLbl.setText("Unavailable Time End:");

        finishBtn.setMnemonic('f');
        finishBtn.setText("Finish Creating");
        finishBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                finishBtnActionPerformed(evt);
            }
        });

        cancelBtn.setMnemonic('c');
        cancelBtn.setText("Cancel Creating");
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(whichDayLbl)
                        .addGap(4, 4, 4)
                        .addComponent(whichDayCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(finishBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelBtn))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(timeEndLbl)
                            .addComponent(timeBeginLbl))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(timeBeginSpin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(timeEndSpin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(72, 72, 72))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(whichDayLbl)
                    .addComponent(whichDayCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
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
                .addContainerGap(25, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void finishBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_finishBtnActionPerformed
    helperCreateSchedule();
}//GEN-LAST:event_finishBtnActionPerformed

private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
    dispose();
}//GEN-LAST:event_cancelBtnActionPerformed

public WeeklySchedule openDialog(){
    this.setVisible(true);
    return createdSchedule;
}

private void helperCreateSchedule(){
    java.sql.Time timeBegin;
    java.sql.Time timeEnd;
    timeBegin = new java.sql.Time(timeBeginModel.getDate().getTime());
    timeEnd = new java.sql.Time(timeEndModel.getDate().getTime());
    if(WeeklySchedule.scheduleConflictMeeting(emp.getEmployeeID(),WeekDay.values()[whichDayCombo.getSelectedIndex()],timeBegin,timeEnd)){
        int option=JOptionPane.showConfirmDialog(this, "schedule conflicts meetings, drop from meetings and continue?");
        if(option>0) return;
    }
    
    try{
        createdSchedule=emp.createSchedule(WeekDay.values()[whichDayCombo.getSelectedIndex()], timeBegin, timeEnd);
    JOptionPane.showMessageDialog(this, "Schedule created");
    
    this.dispose();        
    }catch(InvalidScheduleException e){
        JOptionPane.showMessageDialog(this, e.getMessage(),null,JOptionPane.ERROR_MESSAGE);
        return;
    }

}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelBtn;
    private javax.swing.JButton finishBtn;
    private javax.swing.JLabel timeBeginLbl;
    private javax.swing.JSpinner timeBeginSpin;
    private javax.swing.JLabel timeEndLbl;
    private javax.swing.JSpinner timeEndSpin;
    private javax.swing.JComboBox whichDayCombo;
    private javax.swing.JLabel whichDayLbl;
    // End of variables declaration//GEN-END:variables

}
