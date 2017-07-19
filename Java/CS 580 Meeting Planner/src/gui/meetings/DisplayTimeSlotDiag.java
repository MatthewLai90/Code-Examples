package gui.meetings;
import java.util.Vector;
import functions.*;
import entities.*;
import javax.swing.*;
import java.sql.*;

public class DisplayTimeSlotDiag extends javax.swing.JDialog {
    private Vector<TimeSlot> ts;
    private Meeting toBeModified;
    private java.util.Date meetingDate;
    private Room meetingRoom;
    private Employee owner;
    private Vector<Employee> participantList;
    private Meeting returnMeeting;
    String[][] tableDisplay;
    //create meeting
    public DisplayTimeSlotDiag(javax.swing.JDialog parent, boolean modal,Employee theOwner,java.util.Date meetingDate,Vector<Employee> participants) {
        super(parent, modal);
        this.meetingDate=meetingDate;
        ts=MainProgram.computeTimeSlot(meetingDate,null,participants);
        tableDisplay=getTableData();
        owner=theOwner;
        participantList=participants;
        initComponents();
        setTitle("Create Meeting");
        helperInit();
    }
    //modify meeting
    public DisplayTimeSlotDiag(javax.swing.JDialog parent, boolean modal,Employee theOwner,java.util.Date meetingDate,Vector<Employee> participants,Meeting modify) {
        super(parent, modal);
        this.meetingDate=meetingDate;
        ts=MainProgram.computeTimeSlot(meetingDate,modify,participants);
        tableDisplay=getTableData();
        owner=theOwner;
        participantList=participants;
        initComponents();
        finishBtn.setText("Finish modifying");
        toBeModified=modify;
        setTitle("Modify Meeting");
        helperInit();
    }    
    
    private void helperInit(){
    java.text.DateFormat dateFormat = new java.text.SimpleDateFormat("yyyy/MM/dd");
    dateField.setText(dateFormat.format(meetingDate));
    }
    
    private String[][] getTableData(){
        Vector<String[]> tempVector=new Vector<String[]>();

            for(int j=0;j<ts.size();j++){
                String[] temp=new String[2];
                temp[0]=ts.get(j).getTimeBegin().toString();
                temp[1]=ts.get(j).getTimeEnd().toString();
                tempVector.add(temp);
            }

        String[][] arr;
        arr = (String[][])tempVector.toArray(new String[tempVector.size()][]);
        return arr;
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        timeSlotTable = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        roomAvailableBtn = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        roomTxt = new javax.swing.JTextField();
        finishBtn = new javax.swing.JButton();
        cancelBtn = new javax.swing.JButton();
        timeBeginSpin = new javax.swing.JSpinner();
        timeEndSpin = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        dateField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Create/Modify Meeting");

        jLabel1.setText("Time slot unavailable for selected employees:");

        timeSlotTable.setModel(new javax.swing.table.DefaultTableModel(
            tableDisplay,
            new String [] {
                "Time Unavailable Begin", "Time Unavailable End"
            }
        ){
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        }
    );
    jScrollPane1.setViewportView(timeSlotTable);

    jLabel3.setText("Enter Time Begin:");

    jLabel4.setText("Enter Time End:");

    roomAvailableBtn.setText("Show Rooms Available...");
    roomAvailableBtn.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            roomAvailableBtnActionPerformed(evt);
        }
    });

    jLabel5.setText("Room:");

    roomTxt.setEditable(false);
    roomTxt.setText("none");

    finishBtn.setText("Finish Creating");
    finishBtn.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            finishBtnActionPerformed(evt);
        }
    });

    cancelBtn.setText("Cancel");
    cancelBtn.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            cancelBtnActionPerformed(evt);
        }
    });

    timeBeginSpin.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), null, null, java.util.Calendar.MINUTE));
    timeBeginSpin.setEditor(new javax.swing.JSpinner.DateEditor(timeBeginSpin, "h:mm:ss a"));

    timeEndSpin.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), null, null, java.util.Calendar.MINUTE));
    timeEndSpin.setEditor(new javax.swing.JSpinner.DateEditor(timeEndSpin, "h:mm:ss a"));

    jLabel2.setText("Date of meeting:");

    dateField.setEditable(false);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(70, 70, 70)
                    .addComponent(finishBtn)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(cancelBtn))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(roomTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(roomAvailableBtn))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(timeEndSpin))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(timeBeginSpin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 145, Short.MAX_VALUE))
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel2)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(dateField, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addContainerGap())
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jLabel1)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel2)
                .addComponent(dateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(7, 7, 7)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel3)
                .addComponent(timeBeginSpin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel4)
                .addComponent(timeEndSpin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel5)
                .addComponent(roomAvailableBtn)
                .addComponent(roomTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(finishBtn)
                .addComponent(cancelBtn))
            .addContainerGap())
    );

    pack();
    }// </editor-fold>//GEN-END:initComponents

private void roomAvailableBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roomAvailableBtnActionPerformed
    helperShowRoomAvailable();
}//GEN-LAST:event_roomAvailableBtnActionPerformed

private void finishBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_finishBtnActionPerformed
    helperCreate();
}//GEN-LAST:event_finishBtnActionPerformed

private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
    this.dispose();
}//GEN-LAST:event_cancelBtnActionPerformed

private void helperCreate(){
    if(meetingRoom==null){
        JOptionPane.showMessageDialog(this, "select a room by clicking room available first",null,JOptionPane.ERROR_MESSAGE);
        return;
    }
    try{
        SpinnerDateModel b=(SpinnerDateModel)timeBeginSpin.getModel();
        SpinnerDateModel e=(SpinnerDateModel)timeEndSpin.getModel();
        if(toBeModified==null){
        returnMeeting=owner.createMeeting(meetingRoom, new java.sql.Date(meetingDate.getTime()), 
               new Time(b.getDate().getTime()), new Time(e.getDate().getTime()), ts, participantList);
            JOptionPane.showMessageDialog(this,"Meeting created");
            this.dispose();
        }else{
            toBeModified.updateMeeting(meetingRoom, new java.sql.Date(meetingDate.getTime()), 
                    new Time(b.getDate().getTime()), new Time(e.getDate().getTime()), ts, participantList);
            returnMeeting=toBeModified;
            JOptionPane.showMessageDialog(this,"Meeting modified");
        }
        dispose();
        
    }catch(SQLException e){
        e.printStackTrace();
    }catch(ScheduleConflictException e){
        JOptionPane.showMessageDialog(this,"schedule conflict"+e.getMessage(),null,JOptionPane.ERROR_MESSAGE);
    }catch(InvalidScheduleException e){
        JOptionPane.showMessageDialog(this,e.getMessage(),null,JOptionPane.ERROR_MESSAGE);
    }
 
}

public Meeting openDialog(){
    this.setVisible(true);
    return returnMeeting;
}



private void helperShowRoomAvailable(){
    SpinnerDateModel b=(SpinnerDateModel)timeBeginSpin.getModel();
    SpinnerDateModel e=(SpinnerDateModel)timeEndSpin.getModel();
    Vector<Room> ra=MainProgram.getRoomsAvailable(meetingDate,b.getDate(),e.getDate(),participantList.size());
//    Vector<Room> ra=MainProgram.getRoomsAvailable(meetingDate,b.getDate(),e.getDate());
    Room returned=new RoomAvailableDialog(this,true,ra).openDialog();
    if(returned!=null){
        meetingRoom=returned;
        roomTxt.setText(returned.getRoomName());
    }
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelBtn;
    private javax.swing.JTextField dateField;
    private javax.swing.JButton finishBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton roomAvailableBtn;
    private javax.swing.JTextField roomTxt;
    private javax.swing.JSpinner timeBeginSpin;
    private javax.swing.JSpinner timeEndSpin;
    private javax.swing.JTable timeSlotTable;
    // End of variables declaration//GEN-END:variables

}
