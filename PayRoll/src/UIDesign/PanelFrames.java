/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIDesign;

import java.awt.Color;
import java.awt.HeadlessException;
import javax.swing.JFrame;

/**
 *
 * @author HASANKA
 */
public class PanelFrames extends JFrame{

    public PanelFrames() throws HeadlessException {
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 153));
        setAlwaysOnTop(true);
    }
    
}
