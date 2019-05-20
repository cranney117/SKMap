package SKMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import com.fasterxml.jackson.databind.JsonNode;

public class SKMap {

    private String testWord;
    private String apiKey;

    public SKMap(){
        final Frame f = new Frame("Address Lookup");
        final Label label0 = new Label();
        label0.setBounds(50,100,300,30);
        label0.setSize(300, 30);
        label0.setText("Please enter Google API Key before proceeding:");
        final TextField apiField = new TextField(apiKey);
        apiField.setBounds(50,150, 300, 30);
        final Button b0 = new Button("Set Key");
        b0.setBounds(100,200,200, 30);
        final Label label1 = new Label();
        label1.setBounds(50,150,100,30);
        label1.setSize(100, 30);
        final Label label2 = new Label();
        label2.setBounds(200,150,100,30);
        label2.setSize(100, 30);
        final JTextArea jText1 = new JTextArea();
        jText1.setBounds(20,250,350,75);
        jText1.setSize(350, 75);
        jText1.setLineWrap(true);
        jText1.setWrapStyleWord(true);
        final JTextArea jText2 = new JTextArea();
        jText2.setBounds(25,325,350,80);
        jText2.setSize(350, 80);
        jText2.setLineWrap(true);
        jText2.setWrapStyleWord(true);
        final TextField testField = new TextField(testWord);
        testField.setBounds(50,70, 300, 30);
        final Button b1 = new Button("Get Results");
        b1.setBounds(100,120,200, 30);

        f.add(label0);
        f.add(apiField);
        f.add(b0);
        f.add(jText1);
        f.add(jText2);

        b0.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                apiKey = apiField.getText();
                HttpRequest H0 = new HttpRequest(apiKey);
                try {
                    JsonNode errTest = H0.getFirstResult("1600 pennsylvania avenue washington dc");
                    label1.setText("Latitude: ");
                    label2.setText("Longitude: ");
                    jText1.setText("Full Address:");
                    jText2.setText("");
                    label0.setBounds(50,45,200,25);
                    label0.setText("Please enter an address to lookup");
                    f.remove(apiField);
                    f.remove(b0);
                    f.add(label1);
                    f.add(label2);
                    f.add(testField);
                    f.add(b1);
                }
                catch (Exception err) {
                    jText1.setText(err.toString());
                    jText2.setText("How to get Google API Key: https://developers.google.com/maps/documentation/javascript/get-api-key");
                }
            }
        });

        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                testWord = testField.getText();
                HttpRequest H1 = new HttpRequest(apiKey);
                try {
                    JsonNode firstRes = H1.getFirstResult(testWord);
                    JsonNode thing1 = firstRes.get("geometry");
                    JsonNode thing2 = thing1.get("location");
                    double lat = thing2.get("lat").asDouble();
                    double lng = thing2.get("lng").asDouble();
                    label1.setText("Latitude: " + Double.toString(lat));
                    label2.setText("Latitude: " + Double.toString(lng));
                    jText1.setText("Full Address: " + firstRes.get("formatted_address").textValue());
                }
                catch (Exception err) {
                    jText1.setText(err.toString());
                    err.printStackTrace();
                }
            }
        });
        f.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent we)
            {
                System.exit(0);
            }
        });
        f.setSize(400, 400);
        f.setLayout(null);
        f.setVisible(true);
    }

    public static void main(String args[]){
        new SKMap();
    }
}

