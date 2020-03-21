package zad1;

import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.swing.*;
import java.awt.*;

public class WindowApp extends JFrame {

    Service service;


    public WindowApp(Service service) {
        super("Service");

        this.service = service;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(400, 300);
        setLocation(50,50);
        setLayout(new FlowLayout());

        JPanel panel = new JPanel();

        JLabel country = new JLabel("Kraj: " + service.country);
        JLabel city = new JLabel("Miasto: " + service.city);
        JLabel weather = new JLabel("Pogoda: " + service.temperature + " °C" + "  " + service.pressure + " hPa" );
        JLabel rate = new JLabel("Kurs " + service.currency + ": " + service.rate);
        JLabel nbp = new JLabel("Kurs złotego względem " + service.currency + ": " + service.nbp);

        WebView browser = new WebView();
        WebEngine webEngine = browser.getEngine();
        webEngine.load("http://eclipse.com");

        panel.add(country);
        panel.add(city);
        panel.add(weather);
        panel.add(rate);
        panel.add(nbp);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));


        add(panel);



        setVisible(true);
        setLocationRelativeTo(null);
    }

}
