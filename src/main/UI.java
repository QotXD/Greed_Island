package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;



public class UI {
    
    GamePanel gp;
    Graphics2D g2;
    Font arial_35, arial_80B;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    public String currentDialogue = "";
    public int commandNum = 0;




    public UI(GamePanel gp) {
        this.gp = gp;

        arial_35 = new Font("Arial", Font.PLAIN, 35);
        arial_80B = new Font("Arial", Font.BOLD, 80);

    }

    public void showMessage(String text) {
        
        message = text;
        messageOn = true;
    }
    public void draw (Graphics2D g2) { 

      this.g2 = g2;

    g2.setFont(arial_35);
    g2.setColor(Color.white);
    
    //TITLE STATE
    if (gp.gameState == gp.titleState) {
        drawTitleScreen();

        
    }

    //PLAY STATE
    if (gp.gameState == gp.playState) {
        //DO stuff later;
        
    }
    //PAUSE STATE
    if (gp.gameState == gp.pauseState) {
        drawPauseScreen();
        
    }
    //DIALOGUE STATE
    if(gp.gameState == gp.dialogueState) {
        drawDialogueScreen();

    } 
    }
    public void drawTitleScreen() {
        
        //TITLE SCREEN IMAGE
        try {
            BufferedImage backgroundImage = ImageIO.read(getClass().getResourceAsStream("/tiles/titlescreen.jpg"));
            g2.drawImage(backgroundImage, 0, 0, gp.screenWidth, gp.screenHeight, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //TITLE NAME
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80F));
        String text = "Greed Island";
        int x = getXforCenteredText(text);
        int y = gp.tileSize*3;

        //SHADOW
        g2.setColor(Color.black);
        g2.drawString(text, x+5, y+5);

        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        //CHAR IMAGE

        x = gp.screenWidth/2 - (gp.tileSize*2)/2;
        y += gp.tileSize*2;
        g2.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2, null);

        //MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,42F));;

        text = "NEW GAME";
        x = getXforCenteredText(text);
        y += gp.tileSize*3.5;
        g2.drawString(text, x, y);
        if(commandNum == 0) {
            g2.drawString(">", x-gp.tileSize, y);
        }

        text = "LOAD GAME";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if(commandNum == 1) {
            g2.drawString(">", x-gp.tileSize, y);
        }

        text = "QUIT";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if(commandNum == 2) {
            g2.drawString(">", x-gp.tileSize, y);
        }


    }

    public void drawPauseScreen() {

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
        String text = "PAUSED";
        int x = getXforCenteredText(text);       
        int y = gp.screenHeight/2;

        g2.drawString(text, x, y);
    }
    public void drawDialogueScreen() {

        //WINDOW
        int x = gp.tileSize*2;
        int y = gp.tileSize/2;
        int width = gp.screenWidth - (gp.tileSize*4);
        int height = gp.tileSize*5;
        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32));
        x += gp.tileSize;
        y += gp.tileSize;

        for(String line : currentDialogue.split("\n")){
            g2.drawString(line, x, y);
            y += 40;
    }
    }


    public void drawSubWindow(int x, int y, int width, int height) {

        Color c = new Color(0,0,0,110);
        g2.setColor(Color.black);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }
    public int getXforCenteredText(String text) {

        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
    }
}
