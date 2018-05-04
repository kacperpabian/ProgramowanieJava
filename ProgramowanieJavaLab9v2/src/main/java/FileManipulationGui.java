import FileManipulation.AsymmetricCryptography;
import FileManipulation.GenerateKeys;

import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Scanner;

public class FileManipulationGui extends Component {
    private JButton buttonEnc;
    private JButton buttonGenerate;
    private JButton buttonDec;
    JPanel MainPanel;

    String getFilePath()
    {
        final JFileChooser fc = new JFileChooser();
        fc.showOpenDialog(this);
        File selfile;

        // Open an input stream
        selfile = fc.getSelectedFile();

        return selfile.getAbsolutePath();
    }

    public FileManipulationGui() {
        buttonGenerate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {
                    GenerateKeys gk = new GenerateKeys(1024);
                    gk.generateKeys();
                } catch (NoSuchAlgorithmException e1) {
                    e1.printStackTrace();
                } catch (NoSuchProviderException e1) {
                    e1.printStackTrace();
                }


            }
        });
        buttonEnc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String encFileName = getFilePath();

                try {
                    AsymmetricCryptography as = new AsymmetricCryptography();
                    as.encryptFile(encFileName);
                } catch (NoSuchAlgorithmException e1) {
                    e1.printStackTrace();
                } catch (NoSuchPaddingException e1) {
                    e1.printStackTrace();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        buttonDec.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String decFileName = getFilePath();

                try {
                    AsymmetricCryptography as = new AsymmetricCryptography();
                    as.decryptFile(decFileName);
                } catch (NoSuchAlgorithmException e1) {
                    e1.printStackTrace();
                } catch (NoSuchPaddingException e1) {
                    e1.printStackTrace();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
}
