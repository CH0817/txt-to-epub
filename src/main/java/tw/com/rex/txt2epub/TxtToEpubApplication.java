package tw.com.rex.txt2epub;

import tw.com.rex.txt2epub.frame.MainFrame;

import javax.swing.*;

public class TxtToEpubApplication {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }

}
