/*
 * Created by JFormDesigner on Fri Mar 30 23:29:11 CEST 2018
 */

package ziarenka;



import java.awt.event.*;
import javafx.scene.control.TextArea;

import java.awt.*;
import java.beans.*;
import java.beans.PropertyChangeListener;
import javax.swing.*;


/**
 * @author asd asd
 */
public class PlayerBean extends JPanel {
    public PlayerBean() {
        initComponents();
        changes.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent e) {
                LabelPropertyChange(e);
            }
        });
        vetoSupport.addVetoableChangeListener(new VetoableChangeListener() {
            public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
                VetoPropertyChange(evt);
            }
        });
    }

    private void VetoPropertyChange(PropertyChangeEvent evt) {
    }


    public PropertyChangeSupport changes = new PropertyChangeSupport(this);
    public void addPropertyChangeListener(PropertyChangeListener l) {
        changes.addPropertyChangeListener(l);
    }
    public void removePropertyChangeListener(PropertyChangeListener l) {
        changes.removePropertyChangeListener(l);
    }
    public VetoableChangeSupport vetoSupport = new VetoableChangeSupport(this);
    public void addVetoableChangeListener(VetoableChangeListener listener)
    {
        vetoSupport.addVetoableChangeListener(listener);
    }
    public void removeVetoableChangeListener(VetoableChangeListener listener)
    {
        vetoSupport.removeVetoableChangeListener(listener);
    }

    private void LabelPropertyChange(PropertyChangeEvent e) {
        if(e.getPropertyName()=="device1") device1Label.setText(e.getNewValue().toString());
        else if(e.getPropertyName()=="device2") device2Label.setText(e.getNewValue().toString());
        else if(e.getPropertyName()=="device3") device3Label.setText(e.getNewValue().toString());
        else if(e.getPropertyName()=="device4") device4Button.setText(e.getNewValue().toString());
        else if(e.getPropertyName()=="sizeX") scrollPane1.setSize((Integer) e.getNewValue(),device4TextArea.getHeight());
        else if(e.getPropertyName()=="sizeY") scrollPane1.setSize(device4TextArea.getWidth(), (Integer) e.getNewValue());
        else if(e.getPropertyName()=="device21Value") device21RadioButton.setText( e.getNewValue()+"%");
        else if(e.getPropertyName()=="device22Value") device22RadioButton.setText( e.getNewValue()+"%");
        else if(e.getPropertyName()=="devSet1") {
            device1Label.setEnabled((Boolean) e.getNewValue());
            device1TextField.setEnabled((Boolean) e.getNewValue());
        }
        else if(e.getPropertyName()=="devSet2") {
            device2Label.setEnabled((Boolean) e.getNewValue());
            device21RadioButton.setEnabled((Boolean) e.getNewValue());
            device22RadioButton.setEnabled((Boolean) e.getNewValue());
        }
        else if(e.getPropertyName()=="devSet3") {
            device3Label.setEnabled((Boolean) e.getNewValue());
            device4Button.setEnabled((Boolean) e.getNewValue());
        }
        else if(e.getPropertyName()=="devSet4") {
            device4TextArea.setEnabled((Boolean) e.getNewValue());
        }
    }

    public String getTextDevice1(){
        return device1TextField.getText();
    }
    public JTextArea getTextDevice4(){
        return device4TextArea;
    }

    public String getSelectedDevice2value(){
        if(device21RadioButton.isSelected()) return String.valueOf(device21Value);
        if(device22RadioButton.isSelected()) return String.valueOf(device22Value);
        return null;
    }

    private void device4ButtonActionPerformed(ActionEvent e) {
        // TODO add your code here
    }



    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - PAcper Kabian
        device1TextField = new JTextField();
        device21RadioButton = new JRadioButtonMenuItem();
        device22RadioButton = new JRadioButtonMenuItem();
        device1Label = new JLabel();
        device2Label = new JLabel();
        device4Button = new JButton();
        device3Label = new JLabel();
        scrollPane1 = new JScrollPane();
        device4TextArea = new JTextArea();

        //======== this ========

        // JFormDesigner evaluation mark
        setBorder(new javax.swing.border.CompoundBorder(
            new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
                javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
                java.awt.Color.red), getBorder())); addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

        setLayout(null);
        add(device1TextField);
        device1TextField.setBounds(430, 135, 180, device1TextField.getPreferredSize().height);
        add(device21RadioButton);
        device21RadioButton.setBounds(220, 140, 55, 20);
        add(device22RadioButton);
        device22RadioButton.setBounds(315, 140, 60, 20);
        add(device1Label);
        device1Label.setBounds(430, 100, 180, 25);
        add(device2Label);
        device2Label.setBounds(200, 100, 195, 30);

        //---- device4Button ----
        device4Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                device4ButtonActionPerformed(e);
            }
        });
        add(device4Button);
        device4Button.setBounds(35, 125, 110, 50);
        add(device3Label);
        device3Label.setBounds(10, 95, 180, 30);

        //======== scrollPane1 ========
        {

            //---- device4TextArea ----
            device4TextArea.setEditable(false);
            scrollPane1.setViewportView(device4TextArea);
        }
        add(scrollPane1);
        scrollPane1.setBounds(240, 20, 170, 35);

        { // compute preferred size
            Dimension preferredSize = new Dimension();
            for(int i = 0; i < getComponentCount(); i++) {
                Rectangle bounds = getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            setMinimumSize(preferredSize);
            setPreferredSize(preferredSize);
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents

    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - PAcper Kabian
    private JTextField device1TextField;
    private JRadioButtonMenuItem device21RadioButton;
    private JRadioButtonMenuItem device22RadioButton;
    private JLabel device1Label;
    private JLabel device2Label;
    private JButton device4Button;
    private JLabel device3Label;
    private JScrollPane scrollPane1;
    private JTextArea device4TextArea;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    String title;
    String device1;
    String device2;
    String device3;
    String device4;

    int sizeX;
    int sizeY;

    int device21Value;
    int device22Value;

    boolean devSet1;
    boolean devSet2;
    boolean devSet3;
    boolean devSet4;



    public int getDevice21Value() {
        return device21Value;
    }

    public void setDevice21Value(int device21Value) {
        Integer old =this.device21Value;
        this.device21Value = device21Value;
        changes.firePropertyChange("device21Value",old.intValue(), device21Value);
    }

    public int getDevice22Value() {
        return device22Value;
    }

    public void setDevice22Value(int device22Value) {
        Integer old=this.device22Value;
        this.device22Value = device22Value;
        changes.firePropertyChange("device22Value",old.intValue(),device22Value);
    }

    public int getSizeX() {
        return sizeX;
    }

    public void setSizeX(int sizeX) {
        int old=this.sizeX;
        this.sizeX = sizeX;
        changes.firePropertyChange("sizeX",old,sizeX);
    }

    public int getSizeY() {
        return sizeY;
    }

    public void setSizeY(int sizeY) {
        Integer old=this.sizeY;
        this.sizeY = sizeY;
        changes.firePropertyChange("sizeY",old,new Integer(sizeY));
    }

    public boolean isDevSet1() {
        return devSet1;
    }

    public void setDevSet1(boolean devSet1) {
        boolean old = this.devSet1;
        this.devSet1 = devSet1;
        changes.firePropertyChange("devSet1",old, devSet1);
    }

    public boolean isDevSet2() {
        return devSet2;
    }

    public void setDevSet2(boolean devSet2) {
        boolean old = this.devSet2;
        this.devSet2 = devSet2;
        changes.firePropertyChange("devSet2",old, devSet2);
    }

    public boolean isDevSet3() {
        return devSet3;
    }

    public void setDevSet3(boolean devSet3) {
        boolean old = this.devSet3;
        this.devSet3 = devSet3;
        changes.firePropertyChange("devSet3",old, devSet3);
    }

    public boolean isDevSet4() {
        return devSet4;
    }

    public void setDevSet4(boolean devSet4) {
        boolean old = this.devSet4;
        this.devSet4 = devSet4;
        changes.firePropertyChange("devSet4",old, devSet4);
    }


    public String getDevice1() {
        return device1;
    }

     public void setDevice1(String device1) {
        String old=this.device1;
        this.device1 = device1;
        changes.firePropertyChange("device1",old, device1);
    }

    public String getDevice2() {
        return device2;
    }

    public void setDevice2(String device2) {
        String old=this.device2;
        this.device2 = device2;
        changes.firePropertyChange("device2",old, device2);
    }

    public String getDevice3() {
        return device3;
    }

    public void setDevice3(String device3) {
        String old=this.device3;
        this.device3 = device3;
        changes.firePropertyChange("device3",old, device3);
    }

    public String getDevice4() {
        return device4;
    }

    public void setDevice4(String device4) {
        String old=this.device4;
        this.device4 = device4;
        changes.firePropertyChange("device4",old, device4);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
