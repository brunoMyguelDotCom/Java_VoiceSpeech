package com.voiceassistant.ui;

import com.voiceassistant.domain.Config;
import com.voiceassistant.domain.ConfigRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ConfigWindow extends JFrame {

    private final ConfigRepository repository;
    private final Config config;

    private JTextField wakeWordField;
    private final DefaultTableModel tableModel;
    private final JTable table;

    public ConfigWindow(ConfigRepository repository, Config config) {
        this.repository = repository;
        this.config = config;

        setTitle("Voice Assistant - Configurações");
        setDefaultCloseOperation(HIDE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Gerenciador de Comandos");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Palavra de ativação
        JPanel wakePanel = new JPanel(new BorderLayout(5, 5));
        JLabel wakeLabel = new JLabel("Palavra de inicialização:");
        wakeWordField = new JTextField(config.wakeWord, 15);

        wakePanel.add(wakeLabel, BorderLayout.WEST);
        wakePanel.add(wakeWordField, BorderLayout.CENTER);
        mainPanel.add(wakePanel, BorderLayout.BEFORE_FIRST_LINE);

        // Tabela
        tableModel = new DefaultTableModel(new Object[]{"Frase de Comando", "Caminho Executável/Atalho"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        loadTableData();
        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addBtn = new JButton("Adicionar");
        JButton editBtn = new JButton("Editar");
        JButton removeBtn = new JButton("Remover");
        JButton saveBtn = new JButton("Salvar Tudo");

        addBtn.addActionListener(e -> openDialog(null, null));
        editBtn.addActionListener(e -> editSelected());
        removeBtn.addActionListener(e -> removeSelected());
        saveBtn.addActionListener(e -> save());

        buttonPanel.add(addBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(removeBtn);
        buttonPanel.add(saveBtn);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        setSize(700, 450);
        setLocationRelativeTo(null);
    }

    private void loadTableData() {
        tableModel.setRowCount(0);
        config.commands.forEach((k, v) -> tableModel.addRow(new Object[]{k, v}));
    }

    private void openDialog(String key, String currentPath) {
        new CommandDialog(this, key, currentPath); // Modal: bloqueia até fechar
    }

    private void editSelected() {
        int row = table.getSelectedRow();
        if (row < 0) return;
        openDialog((String) table.getValueAt(row, 0), (String) table.getValueAt(row, 1));
    }

    private void removeSelected() {
        int row = table.getSelectedRow();
        if (row < 0) return;
        config.commands.remove(table.getValueAt(row, 0));
        tableModel.removeRow(row);
    }

    public void save() {
        config.wakeWord = wakeWordField.getText().trim();
        repository.save(config);
        loadTableData();
        JOptionPane.showMessageDialog(this, "Salvo com sucesso!");
    }

    public void updateCommand(String key, String value) {
        config.commands.put(key, value);
        loadTableData();
    }
}
