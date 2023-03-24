package app;

import CanvasObjects.*;

import javax.swing.*;
import java.awt.*;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class Main{
    public static void main(String[] args) {
        AppController.setupGUI();
        UIManager.put("Button.select", new Color(0,0,0,0));
        ArrayList<Map.Entry<String, Runnable>> buttons = new ArrayList<>();
        buttons.add(new AbstractMap.SimpleEntry<>("cursor.png", ()->{
            if(AppController.getModeBehavior()==ModeBehavior.SELECT)return;
            AppController.setModeBehavior(ModeBehavior.SELECT);
        }));
        buttons.add(new AbstractMap.SimpleEntry<>("left_empty_arrow.png", ()->{
            AppController.setModeBehavior(ModeBehavior.CONNECT);
            ModeBehavior.CONNECT.setTargetObjCreator(()->new AssociationLine(AppController.getCanvas()));
        }));
        buttons.add(new AbstractMap.SimpleEntry<>("left_filled_arrow.png", ()->{
            AppController.setModeBehavior(ModeBehavior.CONNECT);
            ModeBehavior.CONNECT.setTargetObjCreator(()->new GeneralizationLine(AppController.getCanvas()));
        }));
        buttons.add(new AbstractMap.SimpleEntry<>("left_square.png", ()->{
            AppController.setModeBehavior(ModeBehavior.CONNECT);
            ModeBehavior.CONNECT.setTargetObjCreator(()->new CompositionLine(AppController.getCanvas()));
        }));
        buttons.add(new AbstractMap.SimpleEntry<>("form.png", ()->{
            AppController.setModeBehavior(ModeBehavior.CREATE);
            ModeBehavior.CREATE.setTargetObjCreator(()->new ClassCanvasObj(AppController.getCanvas()));
        }));
        buttons.add(new AbstractMap.SimpleEntry<>("eclipse.png", ()->{
            AppController.setModeBehavior(ModeBehavior.CREATE);
            ModeBehavior.CREATE.setTargetObjCreator(()->new UseCase(AppController.getCanvas()));
        }));
        AppController.setupButtons(buttons);
        ArrayList<Map.Entry<String, Runnable>> editMenu = new ArrayList<>();
        editMenu.add(new AbstractMap.SimpleEntry<>("group", ()-> Objects.requireNonNull(AppController.getCanvas()).group()));
        editMenu.add(new AbstractMap.SimpleEntry<>("ungroup", ()-> Objects.requireNonNull(AppController.getCanvas()).ungroup()));
        editMenu.add(new AbstractMap.SimpleEntry<>("change object name", ()->{
            Canvas canvas = AppController.getCanvas();
            assert canvas != null;
            if(canvas.selectedObjs.size()==1){
                AppController.newRenameDialog(canvas.selectedObjs.get(0));
            }
        }));
        AppController.setupEditMenu(editMenu);
    }
}
