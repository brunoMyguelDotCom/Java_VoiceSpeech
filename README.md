# Assistente de Voz em Java (Vosk Speech Recognition)

##  **- Vídeo demonstrativo:**
[![Assista ao vídeo no YouTube](https://img.youtube.com/vi/MhRuIbfOKZs/hqdefault.jpg)](https://youtu.be/MhRuIbfOKZs)

---

Este projeto implementa um **assistente de voz offline em Java** utilizando o **Vosk Speech Recognition**.  
Ele permite a ativação por palavra-chave, execução de comandos do Windows (programas, pastas, atalhos `.lnk`) e inclui um sistema robusto de controle de erros com feedback sonoro.

---

## Principais Recursos

* Reconhecimento de voz totalmente **offline** (Vosk)
* Palavra de ativação: **“computador”**
* Execução de programas, pastas e atalhos `.lnk`
* Sons de ativação, desativação e erro
* Sistema de detecção de comando inválido:
    * 1 erro → toca **erro.wav**
    * Após **3 erros**, o assistente toca **desativar.wav**, desliga o modo comando e só volta com “computador”
* Arquitetura modular e expansível para novos comandos
* Captura de áudio configurada para **16000 Hz, 16 bits, mono**

---

## Estrutura do Projeto

main.java.com.voiceassistant.app.VoiceAssistantApp.java
main.java.com.voiceassistant.infrastructure.CommandExecutor.java
main.java.com.voiceassistant.application.CommandService.java
main.java.com.voiceassistant.infrastructure.VoiceRecognizer.java
main.java.com.voiceassistant.audio.SoundPlayer.java


---

## Funcionamento Geral

### 1. main.java.com.voiceassistant.app.VoiceAssistantApp
Carrega o modelo Vosk, inicializa o gerenciador e inicia o loop de reconhecimento.

### 2. main.java.com.voiceassistant.infrastructure.CommandExecutor
Executa programas, atalhos e pastas através de `Runtime.getRuntime().exec()`.

### 3. main.java.com.voiceassistant.application.CommandService
Responsável por:

* Armazenar comandos em um mapa `Map<String, Runnable>`
* Ativar e desativar o modo de comandos
* Controlar tentativas inválidas
* Reproduzir sons adequados (ativação, desativação, erro)

### 4. main.java.com.voiceassistant.infrastructure.VoiceRecognizer
* Configuração do microfone
* Processamento contínuo de áudio
* Envio do texto reconhecido para o gerenciador

### 5. main.java.com.voiceassistant.audio.SoundPlayer
Toca arquivos WAV locais via `Clip`.

---

## Palavra de Ativação

O assistente entra no modo comando ao detectar: "computador"


Enquanto esse modo estiver ativo, qualquer palavra-chave registrada será interpretada como ação.

---

## Comportamento para Comandos Inválidos

Quando o assistente está no modo comando:

1. Se o texto reconhecido **não corresponder a nenhum comando**, toca `erro.wav`.
2. Cada erro incrementa o contador `numTentativas`.
3. Ao atingir **3 erros**, o assistente:
    * toca **desativar.wav**
    * sai do modo comando
    * zera o contador de tentativas
    * só reativa com “computador”

---

## Exemplos de Comandos

| Palavra-chave | Ação |
|---------------|------|
| photoshop | Abre o Adobe Photoshop |
| desenho | Abre o CorelDRAW |
| navegador | Abre o Firefox |
| som | Abre o Spotify via `.lnk` |
| java | Abre o IntelliJ IDEA |
| arquivos | Abre uma pasta específica |
| trabalho | Abre o VS Code via `.lnk` |
| brilho | Abre o Dimmer via atalho |

---

## Adicionando Novos Comandos

No arquivo main.java.com.voiceassistant.application.CommandService.java, basta adicionar:

    comandos.put("palavraChave", () -> main.java.com.voiceassistant.infrastructure.CommandExecutor.exec("caminho ou comando aqui"));

---

## Dependências

-   Java 8+
-   Biblioteca Vosk Java
-   Modelo PT do Vosk (ex.: vosk-model-small-pt-0.3)
-   JAR do Vosk incluído no classpath

---

## Configuração do Microfone

    16000 Hz  
    16 bits  
    Mono


---

## Como Executar

1.  Baixe um modelo PT do Vosk.
2.  Ajuste o caminho no código:

    new Model("CAMINHO_DO_MODELO");

3.  Compile:

    javac *.java

4.  Execute:

    java main.java.com.voiceassistant.app.VoiceAssistantApp

---

# Licença: Livre para uso, modificação e distribuição.

