package tw.com.rex.txt2epub;

import tw.com.rex.txt2epub.frame.MainFrame;

import javax.swing.*;

public class TxtToEpubApplication {

    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        SwingUtilities.invokeLater(() -> frame.setVisible(true));
    }

}
