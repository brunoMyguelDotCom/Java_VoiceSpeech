# Assistente de Voz em Java (Vosk Speech Recognition)

##  **- Vídeo demonstrativo:**
[![Assista ao vídeo no YouTube](https://img.youtube.com/vi/MhRuIbfOKZs/hqdefault.jpg)](https://youtu.be/MhRuIbfOKZs)

---

Este projeto implementa um **assistente de voz offline em Java** utilizando o **Vosk Speech Recognition**.  
O projeto foi modernizado para utilizar **Maven** para gerenciamento de dependências e possui um sistema de configuração dinâmica via **JSON**, permitindo adicionar comandos sem precisar recompilar o código.

---

## Principais Recursos

* Reconhecimento de voz totalmente **offline** (Vosk).
* Gerenciamento de projeto via **Maven**.
* Configuração de comandos externa via arquivo `commands.json` (GSON).
* Palavra de ativação: **“computador”** (configurável).
* Execução de programas, pastas e atalhos `.lnk`.
* Sistema de feedback sonoro (ativação, desativação, erro).
* Sistema de tolerância a falhas:
    * 1 erro → toca **erro.wav**
    * Após **3 erros**, o assistente toca **desativar.wav**, encerra o modo de escuta ativa e aguarda a palavra de ativação novamente.

---

## Estrutura do Projeto

A arquitetura segue o padrão de separação de responsabilidades:

* `com.voiceassistant.app.VoiceAssistantApp` - Ponto de entrada (Main).
* `com.voiceassistant.application.CommandService` - Lógica de negócio e controle de estados.
* `com.voiceassistant.infrastructure.JsonCommandsReader` - Leitura e parse do arquivo JSON de configuração.
* `com.voiceassistant.domain.Config` - Classe modelo para as configurações.
* `com.voiceassistant.infrastructure.VoiceRecognizer` - Integração com a API do Vosk e microfone.
* `com.voiceassistant.infrastructure.CommandExecutor` - Execução de comandos do sistema operacional.
* `com.voiceassistant.audio.SoundPlayer` - Reprodução de efeitos sonoros.

---

## Funcionamento Geral

### 1. Inicialização
A classe `VoiceAssistantApp` utiliza o `JsonCommandsReader` para carregar as configurações do arquivo `src/main/resources/commands.json`. Se o arquivo não existir, um padrão é criado automaticamente.

### 2. Reconhecimento (VoiceRecognizer)
O microfone é configurado (16k Hz, Mono) e o modelo Vosk processa o áudio em tempo real. As frases detectadas são verificadas e enviadas para **CommandService**.

### 3. Processamento (CommandService)
O serviço verifica se a palavra de ativação foi dita.
* **Modo Ativo:** Compara o texto falado com as chaves do Map carregado do JSON.
* **Execução:** Se houver correspondência, o `CommandExecutor` roda o comando associado.
* **Erros:** Gerencia o contador de tentativas falhas e reproduz os sons correspondentes.

---

## Configuração de Comandos (commands.json)

Não é mais necessário alterar o código Java para adicionar comandos. Basta editar o arquivo `src/main/resources/commands.json`.

**Exemplo de estrutura do JSON:**

    {
        "wakeWord": "computador",
        "commands": {
        "photoshop": "\"C:\\Program Files\\Adobe\\Adobe Photoshop 2023\\Photoshop.exe\"",
        "navegador": "\"C:\\Program Files\\Mozilla Firefox\\firefox.exe\"",
        "som": "explorer.exe \"C:\\Users\\User\\Desktop\\Spotify.lnk\""
        }
    }
    
### Exemplos de Comandos

| Palavra-chave | Descrição da Ação |
|---------------|-------------------|
| photoshop     | Abre o Adobe Photoshop |
| desenho       | Abre o CorelDRAW |
| navegador     | Abre o Firefox |
| som           | Abre o Spotify |
| java          | Abre o IntelliJ IDEA |
| arquivos      | Abre a pasta de trabalho |
| trabalho      | Abre o Visual Studio Code |
| brilho        | Abre o Dimmer (Controle de brilho) |

---

## Dependências

* **Java 11+** (Necessário para `Files.readString` e `Map.of`)
* **Maven** (Gerenciamento de dependências)
* **Vosk** (Reconhecimento de voz)
* **GSON** (Google Gson para manipulação de JSON)

As dependências são baixadas automaticamente pelo Maven conforme o `pom.xml`.

---

## Como Executar

1.  **Baixe o Modelo Vosk:**
    * Baixe um modelo de idioma (ex: `vosk-model-small-pt-0.3`) no site oficial do Vosk.
    * Extraia na raiz do projeto ou ajuste o caminho na classe `VoiceRecognizer`.

2.  **Compile e Instale com Maven:**
    Na raiz do projeto (onde está o `pom.xml`):

    mvn clean install

3.  **Execute:**
    Você pode rodar diretamente pela sua IDE (IntelliJ/Eclipse) ou via linha de comando (se configurado o plugin exec no pom):

    mvn exec:java -Dexec.mainClass="com.voiceassistant.app.VoiceAssistantApp"

---

# Licença: Livre para uso, modificação e distribuição.