/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Home;

/**
 *
 * @author HASANKA
 */
public class LeavePage {

    public boolean doneHere = true;
    private static LeavePage lp = new LeavePage();

    public static LeavePage getInstance() {
        if (lp == null) {
            lp = new LeavePage();
        }
        return lp;
    }
}
