package com.voiceassistant.infrastructure;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class JsonCommandsReader {

    private static final Path COMMANDS_PATH = Paths.get("src/main/resources/commands.json");
    private static final Gson gson = new Gson().newBuilder().setPrettyPrinting().create();

    private Config config;

    public Config loadConfig() {
        try {
            if (!Files.exists(COMMANDS_PATH)) {
                createDefaultConfig(); // se nao existir o arquivo cria a config padrao
            }
            String json = Files.readString(COMMANDS_PATH);
            config = gson.fromJson(json, Config.class);
            return config;
        } catch (Exception e) {
            throw new RuntimeException("erro ao carregar arquivo de comandos", e);
        }
    }

    public  void createDefaultConfig() throws IOException {
        Config defaultConfig = new Config();
        defaultConfig.wakeWord = "computador";
        defaultConfig.commands = Map.of();

        String json = gson.toJson(defaultConfig);
        Files.writeString(COMMANDS_PATH, json);
    }
}
