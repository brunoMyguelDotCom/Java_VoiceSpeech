package com.voiceassistant.ui;

import com.voiceassistant.audio.SoundPlayer;

import javax.swing.*;
import java.awt.*;

public class TrayIconManager {

    private TrayIcon trayIcon;
    private ConfigWindow window;

    public TrayIconManager(ConfigWindow window) {
        this.window = window;
    }

    public void init() {
        if (!SystemTray.isSupported()) {
            System.err.println("System tray não suportado.");
            return;
        }

        SystemTray tray = SystemTray.getSystemTray();

        Image image = Toolkit.getDefaultToolkit()
                .getImage(getClass().getResource("/icon.png"));

        PopupMenu menu = new PopupMenu();

        MenuItem openItem = new MenuItem("Abrir configurações");
        openItem.addActionListener(e ->
                SwingUtilities.invokeLater(() -> window.setVisible(true))
        );

        MenuItem exitItem = new MenuItem("Sair");
        exitItem.addActionListener(e -> {
            tray.remove(trayIcon);
            SoundPlayer.play("src/main/resources/sounds/shutdown.wav");
            System.exit(0);
        });

        menu.add(openItem);
        menu.addSeparator();
        menu.add(exitItem);

        trayIcon = new TrayIcon(image, "Voice Assistant", menu);
        trayIcon.setImageAutoSize(true);

        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
}
