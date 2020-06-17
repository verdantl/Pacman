package com.pacman_game;
import javax.swing.JFrame;
import java.awt.*;

public class Display {
    private JFrame frame;
    public Canvas canvas;

    private String title;
    private int width, height;
    private Dimension dimension;

    public Display(String title, int width, int height){
        this.title = title;
        this.width = width;
        this.height = height;
        dimension = new Dimension(width, height);
        createDisplay();
    }


    private void createDisplay(){
        frame = new JFrame(title);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);


        canvas = new Canvas();
        canvas.setPreferredSize(dimension);
        canvas.setMaximumSize(dimension);
        canvas.setMinimumSize(dimension);
        canvas.setBackground(Color.black);
        canvas.setFocusable(false);

        frame.add(canvas);
        frame.pack();
        frame.setVisible(true);
    }

    public Canvas getCanvas(){
        return canvas;
    }

    public JFrame getFrame(){
        return frame;
    }
}
