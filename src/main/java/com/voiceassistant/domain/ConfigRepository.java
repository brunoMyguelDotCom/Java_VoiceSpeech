package com.voiceassistant.domain;

import com.google.gson.Gson;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ConfigRepository {

    // Pasta segura no Windows para salvar configs do usuário
    private static final Path USER_CONFIG_DIR = Paths.get(System.getenv("APPDATA"), "VoiceAssistant");
    private static final Path CONFIG_PATH = USER_CONFIG_DIR.resolve("commands.json");
    private static final Gson gson = new Gson().newBuilder().setPrettyPrinting().create();

    public Config loadConfig() {
        try {
            // cria a pasta se não existir
            if (!Files.exists(USER_CONFIG_DIR)) {
                Files.createDirectories(USER_CONFIG_DIR);
            }

            // se o arquivo não existe, cria padrão
            if (!Files.exists(CONFIG_PATH)) {
                Config defaultConfig = loadDefaultFromResources();
                save(defaultConfig);
                return defaultConfig;
            }

            // lê o JSON
            String json = Files.readString(CONFIG_PATH);
            return gson.fromJson(json, Config.class);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar config", e);
        }
    }

    public void save(Config config) {
        try {
            // garante que a pasta existe antes de salvar
            if (!Files.exists(USER_CONFIG_DIR)) {
                Files.createDirectories(USER_CONFIG_DIR);
            }
            Files.writeString(CONFIG_PATH, gson.toJson(config));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar config", e);
        }
    }

    private Config loadDefaultFromResources() {
        try (InputStream is = getClass().getResourceAsStream("/commands.json")) {
            if (is != null) {
                return gson.fromJson(new String(is.readAllBytes()), Config.class);
            }
        } catch (Exception ignored) {
        }
        return createDefault();
    }

    private Config createDefault() {
        Config config = new Config();
        config.wakeWord = "computador";
        config.commands = new HashMap<>();
        return config;
    }
}
