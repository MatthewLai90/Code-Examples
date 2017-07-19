package gui.meetings;
import entities.Room;
import java.util.Vector;
import javax.swing.*;

public class RoomAvailableDialog extends javax.swing.JDialog {
    Vector<Room> ra;
    Room returnRoom;
    DefaultListModel roomListModel;
    /** Creates new form RoomAvailableDialog */
    public RoomAvailableDialog(javax.swing.JDialog parent, boolean modal,Vector<Room> ravail) {
        super(parent, modal);
        ra=ravail;
        roomListModel=new DefaultListModel();
        for(int i=0;i<ravail.size();i++){
            roomListModel.addElement(ravail.get(i).getRoomName());
        }
        initComponents();
    }

    public Room openDialog(){
        this.setVisible(true);
        return returnRoom;
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        roomAvailableList = new javax.swing.JList();
        selectBtn = new javax.swing.JButton();
        cancelBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setDisplayedMnemonic('r');
        jLabel1.setLabelFor(roomAvailableList);
        jLabel1.setText("Rooms Available for the meeting schedule:");

        roomAvailableList.setModel(roomListModel);
        jScrollPane1.setViewportView(roomAvailableList);

        selectBtn.setMnemonic('s');
        selectBtn.setText("Select this room for meeting");
        selectBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectBtnActionPerformed(evt);
            }
        });

        cancelBtn.setMnemonic('c');
        cancelBtn.setText("Cancel");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(selectBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelBtn)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(selectBtn)
                    .addComponent(cancelBtn))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void selectBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectBtnActionPerformed
    if(roomAvailableList.isSelectionEmpty()){
        JOptionPane.showMessageDialog(this, "select room first",null,JOptionPane.ERROR_MESSAGE);
        return;
    }else{
        returnRoom=ra.get(roomAvailableList.getSelectedIndex());
        dispose();
    }
}//GEN-LAST:event_selectBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList roomAvailableList;
    private javax.swing.JButton selectBtn;
    // End of variables declaration//GEN-END:variables

}
