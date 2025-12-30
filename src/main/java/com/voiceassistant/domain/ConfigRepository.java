package com.voiceassistant.domain;

import com.google.gson.Gson;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class ConfigRepository {

    private static final Path CONFIG_PATH = Paths.get("src/main/resources/commands.json");
    private static final Gson gson = new Gson().newBuilder().setPrettyPrinting().create();

    public Config loadConfig() {
        try {
            if (!Files.exists(CONFIG_PATH)) {
                Config defaultConfig = createDefault();
                save(defaultConfig);
                return defaultConfig;
            }

            String json = Files.readString(CONFIG_PATH);
            return gson.fromJson(json, Config.class);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar config", e);
        }
    }

    public void save(Config config) {
        try {
            Files.writeString(CONFIG_PATH, gson.toJson(config));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar config", e);
        }
    }

    private Config createDefault() {
        Config config = new Config();
        config.wakeWord = "computador";
        config.commands = Map.of();
        return config;
    }

}
