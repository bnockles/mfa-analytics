package menu;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class About implements com.apple.eawt.AboutHandler {

    public About() {
        // comment these two lines to see the default About dialog
        com.apple.eawt.Application app = com.apple.eawt.Application.getApplication();
        app.setAboutHandler(this);

        JFrame myFrame = new JFrame();
        myFrame.setSize(200, 200);
        myFrame.setVisible(true);
    }



    @Override
    public void handleAbout(com.apple.eawt.AppEvent.AboutEvent ae) {
        JFrame aboutFrame = new JFrame();
        aboutFrame.setSize(200, 200);
        aboutFrame.add(new JLabel("About"));
        aboutFrame.pack();
        aboutFrame.setVisible(true);
    }



    public static void main(String[] args) {
        new About();
    }
}