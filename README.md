# ASSISTENTE DE VOZ OFFLINE EM JAVA
(Vosk Speech Recognition)

---

## VÍDEO DEMONSTRATIVO:

[![Assista ao vídeo no YouTube](https://img.youtube.com/vi/MhRuIbfOKZs/hqdefault.jpg)](https://youtu.be/MhRuIbfOKZs)=

---

## VISÃO GERAL

Este projeto implementa um assistente de voz offline em Java utilizando o Vosk Speech Recognition, com foco em arquitetura limpa, estabilidade operacional e extensibilidade.

O sistema foi projetado para operar de forma contínua, com processamento assíncrono, gerenciamento explícito de estado, execução segura de comandos do sistema operacional e interface gráfica dedicada para configuração.

A aplicação funciona de maneira totalmente offline, sem qualquer dependência de serviços externos, garantindo previsibilidade, desempenho e privacidade.

---

## PRINCIPAIS CARACTERÍSTICAS

- Arquitetura modular orientada a domínio
- Execução de comandos por voz
- Atualização dinâmica de comandos em tempo de execução
- Interface gráfica dedicada para configuração
- Event loop assíncrono e não bloqueante
- Feedback sonoro baseado no estado do sistema
- Execução segura de executáveis, atalhos e diretórios
- Persistência automática de configurações

---

## ATUALIZAÇÃO DINÂMICA DE COMANDOS

A lista de comandos é armazenada em um arquivo commands.json e pode ser modificada durante a execução da aplicação.

Características:

- Alterações aplicadas imediatamente, sem necessidade de reinicialização
- Inclusão, edição e remoção de comandos em tempo real
- Atualização segura do estado interno
- Ideal para ajustes rápidos, testes e manutenção contínua

---

## INTERFACE GRÁFICA DE CONFIGURAÇÃO

A aplicação possui uma interface gráfica dedicada ao gerenciamento dos comandos, desenvolvida com foco em clareza operacional e usabilidade técnica.

O aplicativo disponibiliza um ícone na área de ícones ocultos da bandeja do sistema (system tray), acessível através da seta de expansão próxima ao relógio do sistema operacional.

Por meio desse ícone, é possível:

- Abrir a interface gráfica de configuração a qualquer momento

- Encerrar o assistente de forma segura

Funcionalidades de configuração:

- Inclusão de novos comandos
- Edição de comandos existentes
- Remoção de comandos
- Atualização imediata do arquivo de configuração
- Seletor visual de arquivos e diretórios para facilitar a definição de caminhos

O seletor de arquivos reduz erros de configuração e melhora significativamente a experiência de uso.

---

## PROCESSAMENTO ASSÍNCRONO

O sistema utiliza um loop de eventos assíncrono responsável por:

- Captura contínua de áudio
- Processamento de comandos
- Execução segura de ações
- Comunicação controlada com a interface gráfica

Essa abordagem garante estabilidade, previsibilidade e operação contínua mesmo sob uso prolongado.

---

## FEEDBACK SONORO

O assistente fornece feedback auditivo para os seguintes eventos:

- Inicialização do sistema
- Ativação do modo de escuta
- Processamento de comandos
- Ocorrência de erros

---

## ESTRUTURA DO PROJETO

**Aplicação:**

- AssistantController: gerenciamento central do estado do assistente
- CommandService: processamento e despacho de comandos reconhecidos
- EventLoop: loop assíncrono responsável pela orquestração dos eventos

**Domínio:**

- Config: modelo de configuração
- ConfigRepository: leitura e persistência do arquivo JSON
- AssistantState: estados internos do assistente
- VoiceEvent: eventos de voz processados

**Infraestrutura:**

- VoiceRecognizer: integração com o motor de reconhecimento Vosk
- CommandExecutor: execução segura de comandos do sistema operacional

**Áudio:**

- SoundPlayer: reprodução de sons de feedback

**Interface:**

- ConfigWindow: interface gráfica principal
- CommandDialog: diálogo de criação e edição de comandos
- TrayIconManager: integração com a bandeja do sistema

---

## FUNCIONAMENTO GERAL

**Inicialização:**

- Carregamento das configurações via json
- Inicialização da interface gráfica
- Reprodução do som de inicialização

**Reconhecimento de voz:**

- Captura contínua do microfone em 16kHz (mono)

**Processamento:**

- O EventLoop interpreta eventos de voz
- O CommandService executa as ações correspondentes

**Interface:**

- Permite alteração dos comandos sem necessidade de recompilação

---

## DIAGRAMA DE FLUXO DE DADOS

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

---


## CONFIGURAÇÃO DE COMANDOS (commands.json)

Exemplo:

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

## EXEMPLOS DE COMANDOS

| Palavra-chave | Ação |
|---|---|
| photoshop | Abre o Adobe Photoshop |
| desenho | Abre o CorelDRAW |
| navegador | Abre o Firefox |
| som | Abre o Spotify |

---

## DEPENDÊNCIAS

- Java 11 ou superior
- Maven
- Vosk API
- GSON

---

## COMPILAÇÃO 

1. Baixar o modelo Vosk:
   vosk-model-small-pt-0.3

2. Extrair o modelo na raiz do projeto

3. Compilar:
   mvn clean install

---

## EXECUÇÃO

```
mvn exec:java -Dexec.mainClass="com.voiceassistant.app.VoiceAssistantApp"
```

---

## LICENÇA

Livre para uso, modificação e distribuição.

