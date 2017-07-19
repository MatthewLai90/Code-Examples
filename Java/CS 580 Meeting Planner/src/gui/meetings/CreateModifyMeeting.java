package gui.meetings;
import entities.*;
import functions.MainProgram;
import java.util.Vector;
import javax.swing.*;

public class CreateModifyMeeting extends javax.swing.JDialog {
    Meeting createdMeeting;
    Vector<Employee> emListNotMeeting;
    Vector<Employee> emListMeeting;
    Meeting toBeModified;
    DefaultListModel leftListModel=new DefaultListModel();
    DefaultListModel rightListModel=new DefaultListModel();
    Employee emp;
    //create
    public CreateModifyMeeting(javax.swing.JDialog parent, boolean modal, Employee em) {
        super(parent, modal);
        initComponents();
        setTitle("Create Meeting");
            emListNotMeeting=MainProgram.getEmployeeListCopied();
            emListMeeting=new Vector<Employee>();
            for(int i=0;i<emListNotMeeting.size();i++){
                leftListModel.addElement("username:"+emListNotMeeting.get(i).getUserName());
            }

        emp=em;
        updateWeekValue();
    }

    //modify
    public CreateModifyMeeting(javax.swing.JDialog parent, boolean modal, Employee em, Meeting modify) {
        super(parent, modal);
        initComponents();
        setTitle("Modify Meeting");
            emListNotMeeting=MainProgram.getEmployeeListCopied();
            emListMeeting=new Vector<Employee>();
            toBeModified=modify;
            for(int i=0;i<emListNotMeeting.size();i++){
                if(MainProgram.getMPC().isEmployeeInMeeting(emListNotMeeting.get(i),toBeModified))
                {
                    Employee e = emListNotMeeting.remove(i);
                    emListMeeting.add(e);
                    rightListModel.addElement("username:"+e.getName());
                }
                else 
                    leftListModel.addElement("username:"+emListNotMeeting.get(i).getUserName());
            }
        emp=em;
        updateWeekValue();
    }    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        leftList = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        toTheRightBtn = new javax.swing.JButton();
        toTheLeftBtn = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        rightList = new javax.swing.JList();
        jLabel2 = new javax.swing.JLabel();
        computeBtn = new javax.swing.JButton();
        cancelBtn = new javax.swing.JButton();
        weekTxt = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        dateSpin = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Create/Modify Meeting");

        leftList.setModel(leftListModel);
        leftList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(leftList);

        jLabel1.setText("Employees not in meeting:");

        toTheRightBtn.setText("->");
        toTheRightBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toTheRightBtnActionPerformed(evt);
            }
        });

        toTheLeftBtn.setText("<-");
        toTheLeftBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toTheLeftBtnActionPerformed(evt);
            }
        });

        rightList.setModel(rightListModel);
        rightList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(rightList);

        jLabel2.setText("Employees to be in meeting:");

        computeBtn.setText("Compute Time Slot");
        computeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                computeBtnActionPerformed(evt);
            }
        });

        cancelBtn.setText("Cancel");
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });

        weekTxt.setEditable(false);
        weekTxt.setText("jTextField5");

        jLabel6.setText("Day of the Week:");

        dateSpin.setModel(new javax.swing.SpinnerDateModel());
        dateSpin.setEditor(new javax.swing.JSpinner.DateEditor(dateSpin, "yyyy/MM/dd"));
        dateSpin.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                dateSpinStateChanged(evt);
            }
        });

        jLabel3.setText("Enter date of meeting:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(toTheLeftBtn)
                                    .addComponent(toTheRightBtn))))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addComponent(computeBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelBtn))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateSpin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(weekTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addComponent(toTheRightBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(toTheLeftBtn))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE))))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel6)
                    .addComponent(dateSpin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(weekTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(computeBtn)
                    .addComponent(cancelBtn))
                .addGap(11, 11, 11))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void toTheRightBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toTheRightBtnActionPerformed
    toTheRight();
}//GEN-LAST:event_toTheRightBtnActionPerformed

private void toTheRight(){
    if(leftList.isSelectionEmpty()){
        JOptionPane.showMessageDialog(this, "Select something on the left first");
        return;
    }else{
        int selectedIndex=leftList.getSelectedIndex();
        emListMeeting.add(emListNotMeeting.remove(selectedIndex));
        rightListModel.addElement(leftListModel.remove(selectedIndex));
        leftList.setSelectedIndex(selectedIndex);
        leftList.ensureIndexIsVisible(selectedIndex);
    }
}

public Meeting openDialog(){
    this.setVisible(true);
    return createdMeeting;
}

private void toTheLeft(){
    if(rightList.isSelectionEmpty()){
        JOptionPane.showMessageDialog(this, "Select something on the right first");
        return;
    }else{
        int selectedIndex=rightList.getSelectedIndex();
        emListNotMeeting.add(emListMeeting.remove(selectedIndex));
        leftListModel.addElement(rightListModel.remove(selectedIndex));
        rightList.setSelectedIndex(selectedIndex);
        rightList.ensureIndexIsVisible(selectedIndex);
    }    
}

private void toTheLeftBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toTheLeftBtnActionPerformed
    toTheLeft();
}//GEN-LAST:event_toTheLeftBtnActionPerformed

private void computeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_computeBtnActionPerformed
    helperOpenCompute();
}//GEN-LAST:event_computeBtnActionPerformed

private void helperOpenCompute(){
    java.util.Date date=((javax.swing.SpinnerDateModel)dateSpin.getModel()).getDate();
    if(toBeModified==null){
        createdMeeting=new DisplayTimeSlotDiag(this,true,emp,date,emListMeeting).openDialog();
    }else{
        createdMeeting=new DisplayTimeSlotDiag(this,true,emp,date,emListMeeting,toBeModified).openDialog();
    }
    if(createdMeeting!=null) MainProgram.sendEmail(createdMeeting, toBeModified!=null);
    if(createdMeeting!=null) dispose();
}

private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
    this.dispose();
}//GEN-LAST:event_cancelBtnActionPerformed

private void dateSpinStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_dateSpinStateChanged
    updateWeekValue();
}//GEN-LAST:event_dateSpinStateChanged

private void updateWeekValue(){
    SpinnerDateModel d=(SpinnerDateModel)dateSpin.getModel();
    weekTxt.setText(WeekDay.values()[d.getDate().getDay()].toString());    
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelBtn;
    private javax.swing.JButton computeBtn;
    private javax.swing.JSpinner dateSpin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList leftList;
    private javax.swing.JList rightList;
    private javax.swing.JButton toTheLeftBtn;
    private javax.swing.JButton toTheRightBtn;
    private javax.swing.JTextField weekTxt;
    // End of variables declaration//GEN-END:variables

}
