import FileManipulation.AsymmetricCryptography;

import javax.swing.*;

public class Main {
    public static void main (String args[]) throws Exception {

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        }  catch(Exception e){
        }
        JFrame fileManipulationFrame =new JFrame ("Manipulator");
        FileManipulationGui fileManipulationGui=new FileManipulationGui();
        fileManipulationFrame.setContentPane(fileManipulationGui.MainPanel);
        fileManipulationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fileManipulationFrame.pack();
        fileManipulationFrame.setVisible(true);
        fileManipulationFrame.setLocationRelativeTo(null);
    }
}
