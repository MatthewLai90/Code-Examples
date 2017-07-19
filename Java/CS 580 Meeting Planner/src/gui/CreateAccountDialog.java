package gui;
import javax.swing.*;
import entities.*;
import functions.AdminActions;
import java.sql.SQLException;

public class CreateAccountDialog extends javax.swing.JDialog {
    private Employee newEm;
    /** Creates new form CreateAccountDialog */
    public CreateAccountDialog(javax.swing.JDialog parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nameLbl = new javax.swing.JLabel();
        nameTxt = new javax.swing.JTextField();
        ssnLbl = new javax.swing.JLabel();
        ssnTxt = new javax.swing.JTextField();
        addrLbl = new javax.swing.JLabel();
        addrTxt = new javax.swing.JTextField();
        phoneLbl = new javax.swing.JLabel();
        phoneTxt = new javax.swing.JTextField();
        emailLbl = new javax.swing.JLabel();
        emailTxt = new javax.swing.JTextField();
        deptLbl = new javax.swing.JLabel();
        userNameLbl = new javax.swing.JLabel();
        userTxt = new javax.swing.JTextField();
        msgLbl = new javax.swing.JLabel();
        createBtn = new javax.swing.JButton();
        cancelBtn = new javax.swing.JButton();
        deptTxt = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Create Account");

        nameLbl.setDisplayedMnemonic('n');
        nameLbl.setLabelFor(nameTxt);
        nameLbl.setText("Name:");

        nameTxt.setDocument(new FieldDocument(FieldDocument.DEFAULT_TEXT_LENGTH));

        ssnLbl.setDisplayedMnemonic('s');
        ssnLbl.setLabelFor(ssnTxt);
        ssnLbl.setText("SSN:");

        ssnTxt.setDocument(new NumFieldDocument(FieldDocument.SSN_TEXT_LENGTH));

        addrLbl.setDisplayedMnemonic('a');
        addrLbl.setLabelFor(addrTxt);
        addrLbl.setText("Address:");

        addrTxt.setDocument(new FieldDocument(FieldDocument.DEFAULT_TEXT_LENGTH));

        phoneLbl.setDisplayedMnemonic('p');
        phoneLbl.setLabelFor(phoneTxt);
        phoneLbl.setText("Phone Number:");

        phoneTxt.setDocument(new NumFieldDocument(FieldDocument.PHONE_TEXT_LENGTH));

        emailLbl.setDisplayedMnemonic('e');
        emailLbl.setLabelFor(emailTxt);
        emailLbl.setText("Email:");

        emailTxt.setDocument(new FieldDocument(FieldDocument.DEFAULT_TEXT_LENGTH));

        deptLbl.setDisplayedMnemonic('d');
        deptLbl.setText("Department:");

        userNameLbl.setDisplayedMnemonic('u');
        userNameLbl.setLabelFor(userTxt);
        userNameLbl.setText("User Name:");

        userTxt.setDocument(new FieldDocument(FieldDocument.DEFAULT_TEXT_LENGTH));

        msgLbl.setText("Initial password is the same as initial user name");

        createBtn.setMnemonic('t');
        createBtn.setText("Create Account");
        createBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createBtnActionPerformed(evt);
            }
        });

        cancelBtn.setMnemonic('c');
        cancelBtn.setText("Cancel");
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });

        deptTxt.setDocument(new FieldDocument(FieldDocument.DEFAULT_TEXT_LENGTH));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(emailLbl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(emailTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(msgLbl)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(createBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelBtn))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(userNameLbl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(userTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addrLbl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addrTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(nameLbl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(ssnLbl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ssnTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(deptLbl)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(deptTxt))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(phoneLbl)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(phoneTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(userNameLbl)
                    .addComponent(userTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ssnLbl)
                    .addComponent(ssnTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameLbl)
                    .addComponent(nameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addrLbl)
                    .addComponent(addrTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(phoneLbl)
                    .addComponent(phoneTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(emailLbl)
                    .addComponent(emailTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(deptLbl)
                    .addComponent(deptTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(msgLbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(createBtn)
                    .addComponent(cancelBtn))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void createBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createBtnActionPerformed
    helperCreate();
}//GEN-LAST:event_createBtnActionPerformed

private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
    dispose();
}//GEN-LAST:event_cancelBtnActionPerformed

private void helperCreate(){
    try{
        newEm=AdminActions.createEmployee(userTxt.getText(), ssnTxt.getText(), nameTxt.getText(), addrTxt.getText(), phoneTxt.getText(), emailTxt.getText(), deptTxt.getText());
        this.dispose();
        JOptionPane.showMessageDialog(this, "Account Created");
    }catch(SQLException e){
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, e.getMessage(),null, JOptionPane.ERROR_MESSAGE);
    }catch(InvalidInfoException e){
        JOptionPane.showMessageDialog(null,e.getMessage(),null, JOptionPane.ERROR_MESSAGE);
    }
}

public Employee openDialog(){
    this.setVisible(true);
    return newEm;
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel addrLbl;
    private javax.swing.JTextField addrTxt;
    private javax.swing.JButton cancelBtn;
    private javax.swing.JButton createBtn;
    private javax.swing.JLabel deptLbl;
    private javax.swing.JTextField deptTxt;
    private javax.swing.JLabel emailLbl;
    private javax.swing.JTextField emailTxt;
    private javax.swing.JLabel msgLbl;
    private javax.swing.JLabel nameLbl;
    private javax.swing.JTextField nameTxt;
    private javax.swing.JLabel phoneLbl;
    private javax.swing.JTextField phoneTxt;
    private javax.swing.JLabel ssnLbl;
    private javax.swing.JTextField ssnTxt;
    private javax.swing.JLabel userNameLbl;
    private javax.swing.JTextField userTxt;
    // End of variables declaration//GEN-END:variables

}
