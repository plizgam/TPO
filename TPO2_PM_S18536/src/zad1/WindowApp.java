package zad1;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class WindowApp extends JFrame {

    Service service;


    public WindowApp(Service service) {
        super("Service");

        this.service = service;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);

        setSize(400, 300);
        setLocation(50,50);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();

        JLabel country = new JLabel(" Kraj: " + service.country);
        country.setFont(new Font("Serif", Font.BOLD, 20));
        JLabel city = new JLabel(" Miasto: " + service.city);
        city.setFont(new Font("Serif", Font.BOLD, 20));
        JLabel weather = new JLabel(" Pogoda: " + service.temperature + " °C" + "  " + service.pressure + " hPa" );
        weather.setFont(new Font("Serif", Font.BOLD, 20));
        JLabel rate = new JLabel(" Kurs " + service.currency + ": " + service.rate);
        rate.setFont(new Font("Serif", Font.BOLD, 20));
        JLabel nbp = new JLabel(" Kurs złotego względem " + service.currency + ": " + service.nbp);
        nbp.setFont(new Font("Serif", Font.BOLD, 20));

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(country);
        panel.add(city);
        panel.add(weather);
        panel.add(rate);
        panel.add(nbp);

        add(panel, BorderLayout.NORTH);

        JFXPanel fxPanel = new JFXPanel();
        add(fxPanel, BorderLayout.CENTER);

        Platform.runLater(() -> {
            WebEngine engine;
            WebView wv = new WebView();
            engine = wv.getEngine();
            fxPanel.setScene(new Scene(wv));
            engine.load("https://en.wikipedia.org/wiki/" + service.city);
        });


        setVisible(true);
    }

}
