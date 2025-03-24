package main;

import entity.NPC_Kil;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;

    }
    public void setObject() {

      
        

    }

    public void setNPC() {

        gp.npc[0] = new NPC_Kil(gp);
        gp.npc[0].worldX = gp.tileSize*45;
        gp.npc[0].worldY = gp.tileSize*67;
        
    }
}
