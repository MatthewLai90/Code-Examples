package gui;
import javax.swing.*;
import entities.*;

public class ChangePasswdDialog extends javax.swing.JDialog {
    private static final int PASSWDFIELDDISPLAYSIZE=20;
    private Employee emp;
    /** Creates new form ChangePasswdDialog */
    public ChangePasswdDialog(javax.swing.JDialog parent, boolean modal,Employee theEmp) {
        super(parent, modal);
        initComponents();
        emp=theEmp;
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        curPassLbl = new javax.swing.JLabel();
        curPassTxt = new javax.swing.JPasswordField(PASSWDFIELDDISPLAYSIZE);
        newPassLbl = new javax.swing.JLabel();
        newPassTxt = new javax.swing.JPasswordField(PASSWDFIELDDISPLAYSIZE);
        newPassAgainLbl = new javax.swing.JLabel();
        newPassAgainTxt = new javax.swing.JPasswordField(PASSWDFIELDDISPLAYSIZE);
        okBtn = new javax.swing.JButton();
        cancelBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Change Password");
        getContentPane().setLayout(new java.awt.GridBagLayout());

        curPassLbl.setDisplayedMnemonic('c');
        curPassLbl.setLabelFor(curPassTxt);
        curPassLbl.setText("Enter current password:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 0);
        getContentPane().add(curPassLbl, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 2);
        getContentPane().add(curPassTxt, gridBagConstraints);

        newPassLbl.setDisplayedMnemonic('n');
        newPassLbl.setLabelFor(newPassTxt);
        newPassLbl.setText("Enter new password:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 0);
        getContentPane().add(newPassLbl, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 2);
        getContentPane().add(newPassTxt, gridBagConstraints);

        newPassAgainLbl.setDisplayedMnemonic('r');
        newPassAgainLbl.setLabelFor(newPassAgainTxt);
        newPassAgainLbl.setText("Retype new password:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 0);
        getContentPane().add(newPassAgainLbl, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 2);
        getContentPane().add(newPassAgainTxt, gridBagConstraints);

        okBtn.setMnemonic('o');
        okBtn.setText("OK");
        okBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okBtnActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        getContentPane().add(okBtn, gridBagConstraints);

        cancelBtn.setMnemonic('c');
        cancelBtn.setText("Cancel");
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        getContentPane().add(cancelBtn, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void okBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okBtnActionPerformed
    helperChangePass();
}//GEN-LAST:event_okBtnActionPerformed

private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
    this.dispose();
}//GEN-LAST:event_cancelBtnActionPerformed

private void helperChangePass(){
    if(!java.util.Arrays.equals(newPassTxt.getPassword(),newPassAgainTxt.getPassword())){
        JOptionPane.showMessageDialog(this, "New password does not match",null,JOptionPane.ERROR_MESSAGE);
        return;
    }else if(!emp.updatePassword(new String(curPassTxt.getPassword()), new String(newPassTxt.getPassword()))){
        JOptionPane.showMessageDialog(this, "Wrong current password",null,JOptionPane.ERROR_MESSAGE);    
        return;
    }else{
        JOptionPane.showMessageDialog(this, "Password updated");
        this.dispose();
    }    
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelBtn;
    private javax.swing.JLabel curPassLbl;
    private javax.swing.JPasswordField curPassTxt;
    private javax.swing.JLabel newPassAgainLbl;
    private javax.swing.JPasswordField newPassAgainTxt;
    private javax.swing.JLabel newPassLbl;
    private javax.swing.JPasswordField newPassTxt;
    private javax.swing.JButton okBtn;
    // End of variables declaration//GEN-END:variables

}
