package app;

import CanvasObjects.CanvasObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Map;

public class AppController {
    private static AppController instance = new AppController();
    private AppController(){}
    private ModeBehavior modeBehavior = ModeBehavior.SELECT;
    public static ModeBehavior getModeBehavior(){return instance.modeBehavior;}
    public static void setModeBehavior(ModeBehavior behavior){
        instance.modeBehavior.modeChanged(getCanvas());
        instance.modeBehavior = behavior;
    }
    private GUI gui = null;
    private Canvas canvas;
    public static void setupGUI(){
        instance.gui = new GUI();
        instance.canvas = instance.gui.canvas;
    }

    public static void setupButtons(AbstractList<Map.Entry<String, Runnable>> buttonsInfo){
        if(instance.gui==null){
            System.out.println("setup gui first");
            return;
        }
        instance.gui.setupButtons(buttonsInfo);
    }
    public static void setupEditMenu(AbstractList<Map.Entry<String, Runnable>> menuInfo){
        if(instance.gui==null){
            System.out.println("setup gui first");
            return;
        }
        instance.gui.setupEditMenu(menuInfo);
    }

    public static Canvas getCanvas() {
        if(instance.gui==null){
            System.out.println("setup gui first");
            return null;
        }
        return instance.canvas;
    }

    public static void newRenameDialog(CanvasObject canvasObject) {
        if(instance.gui==null){
            System.out.println("setup gui first");
            return;
        }
        new RenameDialog(instance.gui, canvasObject);
    }
}
