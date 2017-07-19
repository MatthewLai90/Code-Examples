package gui;
import javax.swing.*;
import java.sql.*;
import functions.*;
import entities.Employee;

public class LoginWindow extends javax.swing.JDialog {
    Employee returnval;
    /** Creates new form LoginWindow */
    public LoginWindow(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        userNameLbl = new javax.swing.JLabel();
        passLbl = new javax.swing.JLabel();
        userNameTxt = new javax.swing.JTextField();
        passTxt = new javax.swing.JPasswordField();
        loginBtn = new javax.swing.JButton();
        cancelBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Login");

        userNameLbl.setDisplayedMnemonic('u');
        userNameLbl.setLabelFor(userNameTxt);
        userNameLbl.setText("User Name:");

        passLbl.setDisplayedMnemonic('p');
        passLbl.setLabelFor(passTxt);
        passLbl.setText("Password:");

        userNameTxt.setDocument(new FieldDocument(FieldDocument.DEFAULT_TEXT_LENGTH));
        userNameTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userNameTxtActionPerformed(evt);
            }
        });

        passTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passTxtActionPerformed(evt);
            }
        });

        loginBtn.setMnemonic('l');
        loginBtn.setText("Login");
        loginBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginBtnActionPerformed(evt);
            }
        });

        cancelBtn.setMnemonic('c');
        cancelBtn.setText("Cancel");
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(userNameLbl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(userNameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(passLbl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(passTxt)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(43, Short.MAX_VALUE)
                .addComponent(loginBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cancelBtn)
                .addGap(33, 33, 33))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(userNameLbl)
                    .addComponent(userNameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passLbl)
                    .addComponent(passTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(loginBtn)
                    .addComponent(cancelBtn))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void loginBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginBtnActionPerformed
    doLogin();
}//GEN-LAST:event_loginBtnActionPerformed

private void doLogin(){
    returnval=null;
    if(userNameTxt.getText().length()==0) {
        JOptionPane.showMessageDialog(this, "Please enter username",null,JOptionPane.ERROR_MESSAGE);
        return;
    }else if(passTxt.getPassword().length==0){
        JOptionPane.showMessageDialog(this, "Please enter password",null,JOptionPane.ERROR_MESSAGE);
        return;
    }else{
        try{
            returnval=MainProgram.login(userNameTxt.getText(), new String(passTxt.getPassword()));
        }catch(InvalidLoginException e){
            JOptionPane.showMessageDialog(this, e.getMessage(),null,JOptionPane.ERROR_MESSAGE);
            return;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage(),null,JOptionPane.ERROR_MESSAGE);
            return;
        }
    }
    //if login successful, return the employee object
    if(returnval!=null){
        dispose();
    }    
}

private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
    dispose();
}//GEN-LAST:event_cancelBtnActionPerformed

private void passTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passTxtActionPerformed
// TODO add your handling code here:
    doLogin();
}//GEN-LAST:event_passTxtActionPerformed

private void userNameTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userNameTxtActionPerformed
// TODO add your handling code here:
    doLogin();
}//GEN-LAST:event_userNameTxtActionPerformed

public Employee openDialog(){
    //call this function to open the login dialog and return the employee object
    this.setLocationRelativeTo(null);
    this.setVisible(true);
    return returnval;
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelBtn;
    private javax.swing.JButton loginBtn;
    private javax.swing.JLabel passLbl;
    private javax.swing.JPasswordField passTxt;
    private javax.swing.JLabel userNameLbl;
    private javax.swing.JTextField userNameTxt;
    // End of variables declaration//GEN-END:variables

}
