# Assistente de Voz em Java (Vosk Speech Recognition)

Este projeto implementa um **assistente de voz em Java** utilizando o **Vosk** para reconhecimento de fala offline. Ele permite ativar um “modo comando” por voz e executar programas, pastas e atalhos do Windows através de palavras-chave. O sistema foi atualizado nesta versão para incluir comandos via executáveis diretos e também via atalhos `.lnk` executados pelo `explorer.exe`, permitindo maior flexibilidade no controle dos programas.

O assistente possui:

* Reconhecimento de voz offline (Vosk)
* Ativação por palavra-chave ("computador")
* Execução de programas instalados ou atalhos `.lnk`
* Sons de ativação e desativação
* Sistema de comandos modular e expansível de gerenciamento de comandos.

---

## Estrutura do Projeto

```
Main.java
Executor.java
GerenciadorComandos.java
ReconhecedorVoz.java
Som.java
```

---

## Como Funciona

### 1. **Main**

Inicializa o gerenciador de comandos e o reconhecedor de voz.

### 2. **Executor**

Executa comandos do sistema via `Runtime.getRuntime().exec()`.

### 3. **GerenciadorComandos**

* Mantém um mapa de comandos → ações.
* Ativa/desativa o "modo comando".
* Dispara sons de confirmação.

### 4. **ReconhecedorVoz**

* Configura o microfone.
* Carrega o modelo Vosk.
* Processa áudio continuamente.
* Envia o texto reconhecido para o `GerenciadorComandos`.

### 5. **Som**

Toca arquivos `.wav` usados para indicar ativação e desativação.

---

##  Palavra de Ativação

O assistente entra no modo de escuta de comandos quando identifica a palavra:

```
computador
```

Após isso, qualquer comando registrado será aceito até a execução do programa.

---

## Comandos Suportados (Exemplo)

| Palavra‑chave | Ação executada           |
| ------------- | ------------------------ |
| photoshop     | Abre o Adobe Photoshop   |
| desenho       | Abre o CorelDRAW         |
| navegador     | Abre o Firefox           |
| música        | Abre o Spotify           |
| java          | Abre o IntelliJ          |
| código        | Abre o VS Code           |
| arquivos      | Abre Downloads           |
| trabalho      | Abre a pasta de trabalho |

---

## Sons

Dois sons são usados:

* **ativar.wav** → quando o assistente entra em modo comando
* **desativar.wav** → após executar um comando

---

## Dependências

* Java 8+
* Biblioteca **Vosk Java**
* Modelo Vosk (ex.: `vosk-model-small-pt-0.3`)

Adicione o `.jar` do Vosk ao seu classpath.

---

##  Configuração do Microfone

O áudio é capturado em:

```
16000 Hz, 16 bits, mono
```

Ideal para modelos do Vosk.

---

##  Como Executar

1. Baixe um modelo PT do Vosk
2. Configure o caminho no código:

```
new Model("CAMINHO_DO_MODELO");
```

3. Compile tudo:

```
javac *.java
```

4. Execute:

```
java Main
```

---

## Adicionando Novos Comandos

No arquivo `GerenciadorComandos.java`, basta adicionar:

```java
comandos.put("palavra", () -> Executor.exec("caminho do programa"));
```

---

## Observações Importantes

* Todos os caminhos devem existir no sistema do usuário.
* O Vosk funciona totalmente offline.
* Caso o microfone não abra, verifique permissões do Windows.

---

## Licença

Uso livre, modifique conforme desejar.

---