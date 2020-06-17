package com.GameState;

import com.pacman_game.Game;
import com.pacman_game.Map;
import com.pacman_game.StartHandler;

import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class MenuState extends GameState implements KeyListener, MouseListener, MouseInputListener {
    Font titleFont;
    Font smallFont;
    private final int titleX = 170;
    private final int titleY = 150;
    private final int smallX = 250;
    private final int smallY = 200;
    private int opacity = 249;
    private int changeOpacity = 5;
    private Color smallColor = Color.WHITE;
    private Color[] titleColors = new Color[2];
    private int colorIndex = 0;
    private boolean mapChosen = true;
    private MapSelection[] mapSelections = new MapSelection[2];
    private static int currentSelection;

    public static int num = 0;


    public MenuState(Game game){
        super(game);
        titleFont = new Font("Goudy Stout", Font.BOLD, 45);
        smallFont = new Font("Goudy Stout", Font.PLAIN, 10);
        titleColors[0] = Color.RED;
        titleColors[1] = Color.ORANGE;
        currentSelection = 0;
        setMapSelections();
        num++;
    }

    private void setMapSelections(){
        StartHandler[] startHandlers = new StartHandler[]{game.getStartHandlers(0), game.getStartHandlers(1)};
        String[] names = new String[]{"New", "Classic"};
        int[][] coordinates = new int[][]{{90, 300}, {350, 300}};
        for (int i = 0; i < startHandlers.length ; i++){
            mapSelections[i] = new MapSelection(names[i], new Map(startHandlers[i].mapPath,
                    startHandlers[i].getBorder()[0], startHandlers[i].getBorder()[1]), coordinates[i]);
        }
    }

    @Override
    public void update() {
        stringOpacity();
    }

    private void stringOpacity(){
        if (opacity <= 5 || opacity >= 250){
            changeOpacity = - changeOpacity;
            colorIndex = 1 - colorIndex;
        }
            opacity -= changeOpacity;

    }

    @Override
    public void render(Graphics2D g) {
        renderWords(g);
        renderMapSelections(g);
    }

    private void renderMapSelections(Graphics2D g){
        for (MapSelection mapSelection: mapSelections){
            mapSelection.render(g);
        }
    }

    private void renderWords(Graphics2D g){
        g.setFont(titleFont);
        g.setColor(titleColors[colorIndex]);
        g.drawString("PACMAN", titleX + 5, titleY + 5);
        g.setColor(titleColors[1 - colorIndex]);
        g.drawString("PACMAN", titleX, titleY);
        if (mapChosen) {
            g.setFont(smallFont);
            g.setColor(new Color(smallColor.getRed(), smallColor.getGreen(), smallColor.getBlue(), opacity));
            g.drawString("Press s to start", smallX, smallY);
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_S){
            game.setStartHandler(currentSelection);
            game.newgame();
            game.setPlayState(new PlayState(game));
            game.getPlayState().resetTimer();
            GameState.setCurrentState(game.getPlayState());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private MapSelection checkMapSelection(Point coordinates, boolean selected){
        for (MapSelection mapSelection: mapSelections) {
            if (selected) {
                mapSelection.resetSelected();
            }
            else {
                mapSelection.resetHover();
            }
        }
        for (int i = 0; i < mapSelections.length; i++) {
            MapSelection mapSelection = mapSelections[i];
            if (coordinates.x >= mapSelection.coordinates[0] &&
                    coordinates.x <= mapSelection.coordinates[0] + mapSelection.size[0]
                    && coordinates.y >= mapSelection.coordinates[1] &&
                    coordinates.y <= mapSelection.coordinates[1] + mapSelection.size[1]) {
                if (selected){
                    currentSelection = i;
                }
                return mapSelection;
            }
        }

        return null;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {

        try {
            checkMapSelection(e.getPoint(), true).setSelected();

        } catch (NullPointerException ignored){

        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        try{
            checkMapSelection(e.getPoint(), false).setHover();
        }
        catch (NullPointerException ignored){

        }
    }
}
