package entity;


import java.util.Random;

import main.GamePanel;

public class NPC_Kil extends Entity {


    public NPC_Kil(GamePanel gp) {
        
        super(gp);

        direction = "down";
        speed = 1;

        getImage();
        setDialogue();
    }
    public void setDialogue() {

        dialogues[0] = "What's up?";
        dialogues[1] = "I don't see any towns around, \n do you?";
        dialogues[2] = "Come on!";

    }

    public void getImage() {

        down1 = setup("/npc/NPC01");
        down2 = setup("/npc/NPC02");
    }
    public void setAction() {
       actionLockCounter++;
       if (actionLockCounter == 120) {
        Random random = new Random();
        int i = random.nextInt(100)+1;
        if (i <= 25) {
            direction = "down";
        }
        actionLockCounter = 0;
       }
    }
    public void speak() {

        // DO this character specific stuff
        super.speak();
    }
}

