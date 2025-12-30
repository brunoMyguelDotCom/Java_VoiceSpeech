# Assistente de Voz em Java (Vosk Speech Recognition)

##  **- Vídeo demonstrativo:**
[![Assista ao vídeo no YouTube](https://img.youtube.com/vi/MhRuIbfOKZs/hqdefault.jpg)](https://youtu.be/MhRuIbfOKZs)

---
# Assistente de Voz Offline em Java (v2)

### Este projeto implementa um assistente de voz offline em Java utilizando o Vosk Speech Recognition. A versão atual traz arquitetura modular, UI moderna para configuração, execução segura de comandos, e event loop assíncrono, garantindo desempenho, estabilidade e facilidade de manutenção.

---

## Principais Melhorias na v2

- **Arquitetura modular e orientada a domínio:** Separação clara entre infraestrutura, aplicação, domínio e UI.
- **Interface de configuração GUI completa (ConfigWindow):** Adição, edição e remoção de comandos com atualização dinâmica do arquivo commands.json.
- **Gerenciamento de estado:** AssistantController para controlar estados (IDLE, LISTENING, PROCESSING).
- **Event Loop assíncrono:** Processamento contínuo de comandos sem bloqueios.
- **Feedback sonoro aprimorado:** Sons distintos para ativação, erro, desativação e startup.
- **Execução de comandos:** Suporta caminhos de executáveis (.exe) e pastas com tratamento de falhas.
- **Totalmente offline:** Sem dependência de servidores externos.
- **Maven:** Gerenciamento de dependências e compilação simplificada.

---

## Estrutura do Projeto

### Aplicação
- **AssistantController:** Gerencia o estado do assistente.
- **CommandService:** Processa comandos reconhecidos e executa ações.
- **EventLoop:** Loop assíncrono para consumo de eventos de voz.

### Domínio
- **Config:** Modelo de configuração.
- **ConfigRepository:** Leitura e gravação de JSON.
- **AssistantState:** Estados do assistente.
- **VoiceEvent:** Eventos de voz capturados.

### Infraestrutura
- **VoiceRecognizer:** Integração com Vosk.
- **CommandExecutor:** Execução de comandos do SO.

### Áudio
- **SoundPlayer:** Reprodução de sons para feedback.

### UI
- **ConfigWindow:** Interface de gerenciamento de comandos.
- **CommandDialog:** Diálogo de adição/edição.
- **TrayIconManager:** Integração com system tray.

---

## Funcionamento Geral

### Inicialização
Carrega configurações JSON, inicializa GUI e reproduz som de startup.

### Reconhecimento de Voz
Microfone em 16kHz (mono) com captura contínua de áudio.

### Processamento de Comandos
EventLoop consome eventos e CommandService executa ações correspondentes.

### Interface de Configuração
Permite criar/editar comandos sem recompilar o código.

---

## Configuração de Comandos (commands.json)

```json
{
    "wakeWord": "computador",
    "commands": {
        "photoshop": "C:\\Program Files\\Adobe\\Adobe Photoshop 2023\\Photoshop.exe",
        "navegador": "C:\\Program Files\\Mozilla Firefox\\firefox.exe",
        "som": "explorer.exe C:\\Users\\User\\Desktop\\Spotify.lnk"
    }
}
```

---

## Exemplos de comandos:

| Palavra-chave | Ação |
|---|---|
| photoshop | Abre Adobe Photoshop |
| desenho | Abre CorelDRAW |
| navegador | Abre Firefox |
| som | Abre Spotify |

---

## Dependências

- Java 11+
- Maven
- Vosk API
- GSON

---

## Como Compilar e Executar

### 1. Baixar modelo Vosk
Baixe `vosk-model-small-pt-0.3` e extraia na raiz do projeto.

### 2. Compilar
```bash
mvn clean install
```

### 3. Executar
```bash
mvn exec:java -Dexec.mainClass="com.voiceassistant.app.VoiceAssistantApp"
```

---

# _Licença: Livre para uso, modificação e distribuição._

