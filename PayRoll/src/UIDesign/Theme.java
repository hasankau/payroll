/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIDesign;

import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author HASANKA
 */
public class Theme {
    private Color navColorDefault = new Color(51, 51, 51);
    private Color navColorSelect = new Color(28, 28, 28);
    private Color statusGreen = new Color(204,255,204);
    private Color statusBlue = new Color(153,204,230);
    private Image newImg;
    
    private String imagePath = "/img/user.png";

    public Theme() {
        ImageIcon iconI = new javax.swing.ImageIcon(getClass().getResource(getImagePath()));
        Image img = iconI.getImage();
         newImg= img.getScaledInstance(79, 79, Image.SCALE_SMOOTH);
    }

    /**
     * @return the navColorDefault
     */
    public Color getNavColorDefault() {
        return navColorDefault;
    }

    /**
     * @param navColorDefault the navColorDefault to set
     */
    public void setNavColorDefault(Color navColorDefault) {
        this.navColorDefault = navColorDefault;
    }

    /**
     * @return the navColorSelect
     */
    public Color getNavColorSelect() {
        return navColorSelect;
    }

    /**
     * @param navColorSelect the navColorSelect to set
     */
    public void setNavColorSelect(Color navColorSelect) {
        this.navColorSelect = navColorSelect;
    }

    /**
     * @return the statusGreen
     */
    public Color getStatusGreen() {
        return statusGreen;
    }

    /**
     * @param statusGreen the statusGreen to set
     */
    public void setStatusGreen(Color statusGreen) {
        this.statusGreen = statusGreen;
    }

    /**
     * @return the statusBlue
     */
    public Color getStatusBlue() {
        return statusBlue;
    }

    /**
     * @param statusBlue the statusBlue to set
     */
    public void setStatusBlue(Color statusBlue) {
        this.statusBlue = statusBlue;
    }

    /**
     * @return the newImg
     */
    public Image getNewImg() {
        return newImg;
    }

    /**
     * @param newImg the newImg to set
     */
    public void setNewImg(Image newImg) {
        this.newImg = newImg;
    }

    /**
     * @return the imagePath
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * @param imagePath the imagePath to set
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    
    
}
