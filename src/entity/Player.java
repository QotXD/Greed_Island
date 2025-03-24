package entity;

//import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {

    KeyHandler keyH;
    public final int screenX;
    public final int screenY;
    int standCounter = 0;
    boolean moving = false;
    int pixelCounter = 0;

    public Player(GamePanel gp, KeyHandler keyH) {

        super(gp);
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle(1, 1, 46 , 46);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValues();
        getPlayerImage();

    }
    public void setDefaultValues() {

        worldX = gp.tileSize * 44;
        worldY = gp.tileSize * 64;
        speed = 4;  
        direction = "down";
    }
    public void getPlayerImage() {
        try {

        BufferedImage spriteSheet = ImageIO.read(getClass().getResourceAsStream("/player/Char.png"));

        int spriteWidth = 16;  // Width of each sprite
        int spriteHeight = 16; // Height of each sprite

        // Extract sprites correctly (assuming they are in order: UP, DOWN, LEFT, RIGHT)

        //0, 16, 32, 48, 64, 80, 96, 112, 128

        up1 = spriteSheet.getSubimage(64, 0, spriteWidth, spriteHeight);
        up2 = spriteSheet.getSubimage(80, 0, spriteWidth, spriteHeight);

        down1 = spriteSheet.getSubimage(0, 0, spriteWidth, spriteHeight);
        down2 = spriteSheet.getSubimage(16, 0, spriteWidth, spriteHeight);

        left1 = spriteSheet.getSubimage(32, 0, spriteWidth, spriteHeight);
        left2 = spriteSheet.getSubimage(48, 0, spriteWidth, spriteHeight);

        right1 = spriteSheet.getSubimage(96, 0, spriteWidth, spriteHeight);
        right2 = spriteSheet.getSubimage(112, 0, spriteWidth, spriteHeight);

    } catch (Exception e) {
        e.printStackTrace();
    }
}
    public void update () {

        if (moving == false) {

            if (keyH.upPressed == true || keyH.downPressed == true || 
         keyH.leftPressed == true || keyH.rightPressed == true) {
            
            if(keyH.upPressed == true) {
                direction = "up";
            }
            else if(keyH.downPressed == true) {
                direction = "down";
            }
            else if(keyH.leftPressed == true) {
                direction = "left";
            }
            else if(keyH.rightPressed == true) {
                direction = "right";
            }

            moving = true;
            

            //CHECK TILE COLLISION
            collisionOn = false;
            gp.cChecker.checkTile(this);

            // CHECK OBJECT COLLISION
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            //CHECK NPC COLLISION
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

        }

            else {
                standCounter++;
    
                if (standCounter == 20) {
                    spriteNum = 1;
                    standCounter = 0;
                }
            }
            
        }

        if(moving == true) {
            //IF COLLISION IS FALSE, PLAYER CAN MOVE
            if (collisionOn == false) {

                switch (direction) {
                    case "up":  worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;

                }
            }

            spriteCounter++;
            if (spriteCounter > 24) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                    
                }
                else if (spriteNum == 2) {
                    spriteNum = 1;
                    spriteCounter = 0;
                }
            }

            pixelCounter += speed; 

            if (pixelCounter == 48) {
                moving = false;
                pixelCounter = 0;
                
            }

        }
        

        }
 
        
    
            

    public void pickUpObject(int i) {
        
        if (i !=999) {

        }
    }
    public void interactNPC(int i) { 

        if (i !=999) {

            if (gp.keyH.zPressed == true) {
                
            
            gp.gameState = gp.dialogueState;
            gp.npc[i].speak();
        }
        }
        gp.keyH.zPressed = false;
    }
    public void draw(Graphics2D g2) {

//        g2.setColor(Color.white);
//       g2.fillRect(x, y, gp.tileSize, gp.tileSize);

        BufferedImage image = null;

        switch (direction) {
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                }
                if (spriteNum == 2) {
                    image = up2;
                }
                break;
        
                case "down":
                if (spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2) {
                    image = down2;
                }
                break;
        
                case "left":
                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
                break;
        

                case "right":
                if (spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                }
                break;
        
        }
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

        //HITBOX TOOL
        //g2.setColor(Color.red); 
        //g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);





    }

}


    