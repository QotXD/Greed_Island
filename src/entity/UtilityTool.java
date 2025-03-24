package entity;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class UtilityTool {

    public BufferedImage scaleImage(BufferedImage original, int width, int height) {
        BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(original.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, width, height, null);
        g2.dispose();
        return scaledImage;
    }
}
