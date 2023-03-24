package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class ButtonGroupEffect implements MouseListener {
    private static final ArrayList<ButtonGroupEffect> buttonEffects = new ArrayList<>();
    private final JButton button;
    private boolean selected;

    public ButtonGroupEffect(JButton button){
        this.button = button;
        button.setFocusPainted(false);
        button.setSelected(false);
        buttonEffects.add(this);
        button.addActionListener(e->{
            for(ButtonGroupEffect buttonEffect : buttonEffects){
                buttonEffect.button.setBackground(Color.white);
                buttonEffect.selected = false;
            }
            selected = true;
            button.setBackground(new Color(59, 59, 59, 255));
        });
        selected = false;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        button.setBackground(Color.GRAY);
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        button.setBackground(Color.LIGHT_GRAY);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(!selected)button.setBackground(Color.white);
        else button.setBackground(new Color(59, 59, 59, 255));
    }
}
