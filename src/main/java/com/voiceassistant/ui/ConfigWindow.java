package com.voiceassistant.ui;

import com.voiceassistant.domain.Config;
import com.voiceassistant.domain.ConfigRepository;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class ConfigWindow extends JFrame {

    private static final Color BACKGROUND = new Color(45, 45, 48);
    private static final Color FOREGROUND = new Color(241, 241, 241);
    private static final Color ACCENT = new Color(0, 122, 204);
    private static final Color TABLE_BG = new Color(30, 30, 30);
    private static final Color TABLE_HEADER_BG = new Color(37, 37, 38);
    private static final Color TABLE_GRID = new Color(62, 62, 66);
    private static final Color SELECTION_BG = new Color(9, 71, 113);

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
        getContentPane().setBackground(BACKGROUND);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(BACKGROUND);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Gerenciador de Comandos");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(FOREGROUND);
        titleLabel.setBorder(new EmptyBorder(0, 0, 10, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // palavra de ativacao
        JPanel wakePanel = new JPanel(new BorderLayout(10, 0));
        wakePanel.setBackground(BACKGROUND);
        wakePanel.setBorder(new EmptyBorder(0, 0, 10, 0));

        JLabel wakeLabel = new JLabel("Palavra de inicialização:");
        wakeLabel.setForeground(FOREGROUND);
        wakeLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));

        wakeWordField = new JTextField(15);
        wakeWordField.setText(config.wakeWord);
        wakeWordField.setBackground(new Color(30, 30, 30));
        wakeWordField.setForeground(FOREGROUND);
        wakeWordField.setCaretColor(FOREGROUND);
        wakeWordField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        wakeWordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(62, 62, 66), 1),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)
        ));

        wakePanel.add(wakeLabel, BorderLayout.WEST);
        wakePanel.add(wakeWordField, BorderLayout.CENTER);

        mainPanel.add(wakePanel, BorderLayout.BEFORE_FIRST_LINE);

        // TABELA
        tableModel = new DefaultTableModel(new Object[]{"Frase de Comando", "Caminho Executável/Atalho"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = createStyledTable();
        loadTableData();

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(TABLE_BG);
        scrollPane.setBorder(BorderFactory.createLineBorder(TABLE_GRID));

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // botoes
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(BACKGROUND);
        buttonPanel.setBorder(new EmptyBorder(10, 0, 0, 0));

        JButton addBtn = createStyledButton("Adicionar");
        JButton editBtn = createStyledButton("Editar");
        JButton removeBtn = createStyledButton("Remover");
        JButton saveBtn = createStyledButton("Salvar Tudo");
        saveBtn.setBackground(ACCENT);

        addBtn.addActionListener(e -> openDialog(null, null));
        editBtn.addActionListener(e -> editSelected());
        removeBtn.addActionListener(e -> removeSelected());
        saveBtn.addActionListener(e -> save());

        buttonPanel.add(addBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(removeBtn);
        buttonPanel.add(Box.createHorizontalStrut(20));
        buttonPanel.add(saveBtn);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        setSize(800, 500);
        setLocationRelativeTo(null);
    }

    private void loadTableData() {
        tableModel.setRowCount(0);
        config.commands.forEach((k, v) ->
                tableModel.addRow(new Object[]{k, v})
        );
    }

    private void openDialog(String key, String currentPath) {
        new CommandDialog(this, key, currentPath);
    }

    private void editSelected() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            String key = (String) table.getValueAt(row, 0);
            String value = (String) table.getValueAt(row, 1);
            openDialog(key, value);
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um comando para editar.");
        }
    }

    private void removeSelected() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Deseja remover o comando selecionado?",
                    "Remover", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                config.commands.remove(table.getValueAt(row, 0));
                tableModel.removeRow(row);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um comando para remover.");
        }
    }

    public void save() {
        config.wakeWord = wakeWordField.getText().trim();
        repository.save(config);
        repository.loadConfig();
        JOptionPane.showMessageDialog(this, "Salvo com sucesso!");
    }

    public void updateCommand(String key, String value) {
        config.commands.put(key, value);
        loadTableData();
    }

    private JTable createStyledTable() {
        JTable tbl = new JTable(tableModel);
        tbl.setBackground(TABLE_BG);
        tbl.setForeground(FOREGROUND);
        tbl.setGridColor(TABLE_GRID);
        tbl.setSelectionBackground(SELECTION_BG);
        tbl.setSelectionForeground(FOREGROUND);
        tbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tbl.setRowHeight(30);
        tbl.setShowGrid(true);
        tbl.setFillsViewportHeight(true);

        JTableHeader header = tbl.getTableHeader();
        header.setBackground(TABLE_HEADER_BG);
        header.setForeground(FOREGROUND);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setOpaque(true);
        header.setReorderingAllowed(false);
        header.setResizingAllowed(true);

        header.setDefaultRenderer((table, value, isSelected, hasFocus, row, column) -> {
            JLabel lbl = new JLabel(value.toString());
            lbl.setOpaque(true);
            lbl.setBackground(TABLE_HEADER_BG);
            lbl.setForeground(FOREGROUND);
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
            lbl.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, TABLE_GRID));
            lbl.setHorizontalAlignment(SwingConstants.LEFT);
            return lbl;
        });

        return tbl;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);

        button.setBackground(new Color(62, 62, 66));
        button.setForeground(FOREGROUND);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(false);

        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(82, 82, 86), 1),
                BorderFactory.createEmptyBorder(8, 20, 8, 20)
        ));

        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }
}
