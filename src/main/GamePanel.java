package main;
import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GamePanel extends JPanel implements Runnable {
       
    //SCREEN SETTINGS (game screen size)
    final int originalTileSize = 16; //16x16 tile
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; //48x48 tile
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; //768x768 px
    public final int screenHeight = tileSize * maxScreenRow; // 576x576 px

    //WORLD SETTINGS
    public final int maxWorldCol = 100;
    public final int maxWorldRow = 100;


    //FPS

    int FPS = 60;

    //SYSTEM
    TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Thread gameThread;

    // ENTITY AND OBJECT
    public Player player = new Player(this, keyH);
    public SuperObject obj[] = new SuperObject[10];
    public Entity npc[] = new Entity[10];

    // GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;




    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {

        aSetter.setObject();
        aSetter.setNPC();
        playMusic(4);
        gameState = titleState;
    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();

    }

    @Override
    public void run() {
        
        double drawInterval = 1000000000/FPS; // 0,01666 seconds
        double nextDrawTime = System.nanoTime() + drawInterval;
        long timer  = 0;
        int drawCount = 0;



        while(gameThread != null) {

        
//      System.out.println("The game loop is running");

        // 1 UPDATE: update information such as character position 
            update();
        // 2 DRAW: draw the screen with the update information
            repaint();
            drawCount++;
            
            
            //Sleep game loop, can also use delta game loop
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1_000_000;
                if (remainingTime <0) {
                    remainingTime = 0;
                    
                }


                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //calculate FPS every second
            if (System.currentTimeMillis() - timer >=1000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = System.currentTimeMillis();
            }
        }

    }

    public void update() {


        if (gameState == playState) {
            //PLAYER
            player.update();
            //NPC
            for(int i = 0; i < npc.length; i++) {
                if(npc[i] != null) {
                    npc[i].update();
                }
            }
            
        }
        if (gameState == pauseState) {
            //nothing

            
        }
    }
    
    
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        //DRAW ORDER BELOW

        //TITLE SCREEN
        if (gameState == titleState) {

            ui.draw(g2);
        }



        // OTHERS
        else {
                //TILE
                tileM.draw(g2);

                //OBJECT
                for(int i = 0; i < obj.length; i++) {
                    if (obj[i] != null) {
                        obj[i].draw(g2, this);
                    }

                }

                //NPC
                for(int i = 0; i < npc.length; i++) {
                    if (npc[i] != null) {
                        npc[i].draw(g2);
                    }

                }

                //PLAYER
                player.draw(g2);

                //UI 
                ui.draw(g2);
                    
                g2.dispose();


        }

        
    }
    public void playMusic(int i) {

        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic() {

        music.stop();
    }
    public void playSE(int i) {

        se.setFile(i);
        se.play();
    }


}
