package app;
import CanvasObjects.CanvasObject;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class RenameDialog extends JDialog{
    public RenameDialog(JFrame parent, CanvasObject renamedObj){
        super(parent, "change object name", true);
        JTextArea name = new JTextArea();
        name.setText(renamedObj.getName());
        JButton OkBtn = new JButton("OK");
        JButton cancelBtn = new JButton("Cancel");
        JPanel panel = new JPanel();
        OkBtn.addActionListener(e -> {
            if(name.getText().equals(""))return;
            renamedObj.setName(name.getText());
            Objects.requireNonNull(AppController.getCanvas()).refresh();
            dispose();
        });
        cancelBtn.addActionListener(e-> dispose());
        panel.add(OkBtn);
        panel.add(cancelBtn);
        add(name, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);
        setSize(200, 200);
        setVisible(true);
    }
}
