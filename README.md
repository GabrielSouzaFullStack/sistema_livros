# 📚 Sistema de Gerenciamento de Livros

> Sistema Java com Swing modernizado e seguro para gerenciamento de registros de livros. Arquitetura MVC com suporte multi-SGBD (MySQL/PostgreSQL) e configuração externalizada.

## 🎯 Funcionalidades

- 🔄 **Multi-SGBD**: MySQL e PostgreSQL
- 🖥️ **Interface Gráfica**: Swing com consultas paginadas
- 🔧 **Seleção Dinâmica**: Escolha do banco em tempo de execução
- 🔒 **Segurança**: Credenciais externalizadas e não versionadas
- ⚡ **Automação**: Scripts PowerShell para compilação e setup
- 📋 **Diagnóstico**: Ferramentas de teste e troubleshooting

## 🏗️ Arquitetura MVC

```
src/combo/
├── bd/          # Banco de Dados (DAO Connection)
├── bo/          # Regras de Negócio (Business Objects)
├── controller/  # Controladores (MVC Controllers)
├── dao/         # Acesso a Dados (Data Access Objects)
├── gui/         # Interface Gráfica (GUI/View)
├── util/        # Utilitários (ConfigUtil)
└── vo/          # Objetos de Valor (Value Objects)
```

### 📁 Estrutura de Arquivos

```
sistema_livros/
├── src/                    # Código fonte Java
├── bin/                    # Classes compiladas
├── lib/                    # Bibliotecas JAR (MySQL, PostgreSQL)
├── config.properties       # Configurações do banco (não versionado)
├── config.properties.example # Template de configuração
├── compile-fixed.ps1       # Script de compilação
├── test-connection.ps1     # Teste de conectividade
├── setup-database.ps1      # Setup do banco de dados
├── SECURITY.md            # Guia de segurança
├── TROUBLESHOOTING.md     # Solução de problemas
├── .gitignore             # Arquivos excluídos do Git
└── .gitattributes         # Configuração de line endings
```

## 🚀 Execução Rápida

### ⚡ Método Simplificado (RECOMENDADO)

```powershell
# 0. Verificar status do projeto
.\status-projeto.ps1

# 1. Testar conexão
.\test-connection.ps1

# 2. Configurar banco de dados
.\setup-database.ps1

# 3. Compilar tudo
.\compile-fixed.ps1

# 4. Executar aplicação
java -cp "bin;lib\postgresql-42.7.7.jar" combo.principal.Principal
```

### 🔧 Compilar Manualmente (Alternativa)

```powershell
# 1. Limpar pasta bin
Remove-Item -Recurse -Force bin -ErrorAction SilentlyContinue
New-Item -ItemType Directory -Path bin

# 2. Compilar em ordem de dependência
javac -d bin src\combo\vo\VoConexao.java
javac -d bin src\combo\util\ConfigUtil.java
javac -cp "lib\mysql-connector-j-9.3.0.jar;lib\postgresql-42.7.7.jar;bin" -d bin src\combo\bd\*.java
javac -cp "lib\mysql-connector-j-9.3.0.jar;lib\postgresql-42.7.7.jar;bin" -d bin src\combo\bo\BoConexao.java
javac -cp "lib\mysql-connector-j-9.3.0.jar;lib\postgresql-42.7.7.jar;bin" -d bin src\combo\dao\DaoCombo.java
javac -cp "lib\mysql-connector-j-9.3.0.jar;lib\postgresql-42.7.7.jar;bin" -d bin src\combo\bo\BoCombo.java
javac -cp "lib\mysql-connector-j-9.3.0.jar;lib\postgresql-42.7.7.jar;bin" -d bin src\combo\gui\GuiSelecionaSGBD.java
javac -cp "lib\mysql-connector-j-9.3.0.jar;lib\postgresql-42.7.7.jar;bin" -d bin src\combo\gui\GuiCombo.java src\combo\gui\consulta\GuiConsulta.java src\combo\controller\CoCombo.java src\combo\controller\CoConsulta.java
javac -cp "lib\mysql-connector-j-9.3.0.jar;lib\postgresql-42.7.7.jar;bin" -d bin src\combo\principal\Principal.java
```

### 🚀 Executar Aplicação

```powershell
# MySQL
java -cp "bin;lib\mysql-connector-j-9.3.0.jar" combo.principal.Principal

# PostgreSQL
java -cp "bin;lib\postgresql-42.7.7.jar" combo.principal.Principal

# Ambos os drivers (RECOMENDADO)
java -cp "bin;lib\mysql-connector-j-9.3.0.jar;lib\postgresql-42.7.7.jar" combo.principal.Principal
```

### Configuração do Banco

**Use o script automatizado:**

```powershell
.\setup-database.ps1
```

**SQL básico (para referência):**

```sql
-- PostgreSQL
CREATE DATABASE livros;
CREATE TABLE livros (codigo SERIAL PRIMARY KEY, titulo VARCHAR(255), autor VARCHAR(255), editora VARCHAR(255), ano INTEGER);
CREATE TABLE livrostemp (codigo SERIAL PRIMARY KEY, titulo VARCHAR(255), autor VARCHAR(255), editora VARCHAR(255), ano INTEGER);

-- MySQL
CREATE DATABASE livros;
CREATE TABLE livros (codigo INT PRIMARY KEY AUTO_INCREMENT, titulo VARCHAR(255), autor VARCHAR(255), editora VARCHAR(255), ano INT);
CREATE TABLE livrostemp (codigo INT PRIMARY KEY AUTO_INCREMENT, titulo VARCHAR(255), autor VARCHAR(255), editora VARCHAR(255), ano INT);
```

### Credenciais Padrão

- **MySQL**: root/admin20251706 (localhost:3306)
- **PostgreSQL**: postgres/20251706 (localhost:5432)

## 🔒 Configuração de Segurança

### Arquivo de Configuração Externa

As credenciais agora são carregadas de `config.properties`:

```bash
# 1. Copiar template
copy config.properties.example config.properties

# 2. Editar suas credenciais
notepad config.properties
```

### Scripts e Arquivos de Configuração

#### 📁 Scripts PowerShell

- **`compile-fixed.ps1`** - Compilação automática com ordem de dependências correta
- **`test-connection.ps1`** - Diagnóstico de conexão com banco de dados
- **`setup-database.ps1`** - Comandos SQL para criar tabelas necessárias
- **`normalize-line-endings.ps1`** - Corrige problemas CRLF/LF do Git
- **`status-projeto.ps1`** - Verificação completa do status do projeto

#### 📄 Configuração e Documentação

- **`config.properties`** - Configurações de banco (criado pelo usuário)
- **`config.properties.example`** - Template para configuração
- **`.gitignore`** - Exclui arquivos sensíveis do controle de versão
- **`.gitattributes`** - Normaliza quebras de linha no Git
- **`SECURITY.md`** - Guia completo de segurança
- **`TROUBLESHOOTING.md`** - Soluções para problemas comuns

⚠️ **IMPORTANTE**: `config.properties` está no `.gitignore` e NÃO será commitado no Git!

### 🔐 Melhorias de Segurança Implementadas

✅ **Credenciais Externalizadas** - Não mais hardcoded no código  
✅ **Arquivo .gitignore** - Exclui `config.properties` e binários do Git  
✅ **ConfigUtil.java Seguro** - Fallback com senhas VAZIAS por segurança  
✅ **Credenciais Padrão Removidas** - Código fonte não expõe senhas reais  
✅ **Template de Configuração** - `config.properties.example` para novos usuários  
✅ **Documentação Separada** - Credenciais de desenvolvimento em arquivo não versionado

## 🔧 Configuração do Banco de Dados

### Passo a Passo Completo

1. **Verificar se bancos estão rodando:**

```powershell
.\test-connection.ps1
```

2. **Criar tabelas necessárias:**

```powershell
.\setup-database.ps1
# Escolher: mysql ou postgresql
```

3. **Compilar e executar:**

```powershell
.\compile-fixed.ps1
java -cp "bin;lib\postgresql-42.7.7.jar" combo.principal.Principal
```

### SQL Completo (PostgreSQL)

```sql
-- 1. Criar base de dados
CREATE DATABASE livros;
\c livros;

-- 2. Criar tabelas
CREATE TABLE livros (
  codigo SERIAL PRIMARY KEY,
  titulo VARCHAR(255),
  autor VARCHAR(255),
  editora VARCHAR(255),
  ano INTEGER
);

CREATE TABLE livrostemp (
  codigo SERIAL PRIMARY KEY,
  titulo VARCHAR(255),
  autor VARCHAR(255),
  editora VARCHAR(255),
  ano INTEGER
);

-- 3. Dados de teste
INSERT INTO livros (titulo, autor, editora, ano) VALUES
  ('Dom Casmurro', 'Machado de Assis', 'Garnier', 1899),
  ('O Cortiço', 'Aluísio Azevedo', 'Garnier', 1890),
  ('Iracema', 'José de Alencar', 'Tipografia de Viana & Filhos', 1865);

INSERT INTO livrostemp SELECT * FROM livros;
```

## 🚨 Solução de Problemas

### Erro "Conexão com banco de dados não estabelecida"

1. Verificar se banco está rodando: `.\test-connection.ps1`
2. Verificar credenciais em `config.properties`
3. Criar tabelas: `.\setup-database.ps1`
4. Ver guia completo: `TROUBLESHOOTING.md`

### Erro de Compilação

1. Usar script automático: `.\compile-fixed.ps1`
2. Verificar se JARs estão na pasta `lib/`
3. Verificar se Java 8+ está instalado

## 📋 Requisitos do Sistema

- **Java**: JDK 8 ou superior
- **Banco de Dados**: MySQL 5.7+ ou PostgreSQL 10+
- **Sistema Operacional**: Windows (scripts PowerShell)
- **Memória**: Mínimo 512MB RAM

## 🤝 Contribuindo

1. **Fork** o projeto
2. **Clone** o repositório
3. **Configure** o ambiente:
   ```powershell
   copy config.properties.example config.properties
   # Editar config.properties com suas credenciais
   ```
4. **Teste** localmente:
   ```powershell
   .\test-connection.ps1
   .\compile-fixed.ps1
   ```
5. **Commit** suas mudanças (sem incluir `config.properties`)
6. **Push** e crie um **Pull Request**

## 📚 Documentação Adicional

- **[SECURITY.md](SECURITY.md)** - Práticas de segurança
- **[TROUBLESHOOTING.md](TROUBLESHOOTING.md)** - Solução de problemas
- **[config.properties.example](config.properties.example)** - Template de configuração

---

## 📝 Changelog

### v2.0 - Modernização e Segurança

- ✅ Externalização de credenciais com `ConfigUtil.java`
- ✅ Scripts PowerShell para automação
- ✅ Configuração Git segura (`.gitignore`, `.gitattributes`)
- ✅ Documentação completa e troubleshooting
- ✅ Suporte melhorado para MySQL e PostgreSQL

### v1.0 - Versão Original

- Interface Swing básica
- Conexão hardcoded com banco
- Suporte MySQL/PostgreSQL

---

**Sistema de Livros** - Versão Modernizada e Segura 🔒
