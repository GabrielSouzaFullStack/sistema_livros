# 🔒 Configuração de Segurança - Credenciais de Banco de Dados

## ⚠️ IMPORTANTE: SEGURANÇA DAS CREDENCIAIS

Este projeto foi configurado para **NÃO** expor credenciais sensíveis no código fonte ou no Git.

## 📋 Como Configurar

### 1. Arquivo de Configuração

Copie o arquivo `config.properties.example` para `config.properties`:

```bash
copy config.properties.example config.properties
```

### 2. Edite suas Credenciais

Abra `config.properties` e configure suas credenciais reais:

```properties
# MySQL (CONFIGURE SUAS CREDENCIAIS)
mysql.host=localhost
mysql.usuario=root
mysql.senha=SUA_SENHA_MYSQL_AQUI

# PostgreSQL (CONFIGURE SUAS CREDENCIAIS)
postgresql.host=localhost
postgresql.usuario=postgres
postgresql.senha=SUA_SENHA_POSTGRESQL_AQUI
```

### 3. Verificação do .gitignore

O arquivo `.gitignore` está configurado para **NUNCA** commitar:

- `config.properties` (suas credenciais reais)
- `*.env`
- `database.properties`
- `conexao.properties`

## 🛡️ Boas Práticas de Segurança

### ✅ O que FAZER:

- ✅ Manter `config.properties` **FORA** do Git
- ✅ Usar `config.properties.example` como template
- ✅ Configurar credenciais diferentes para desenvolvimento/produção
- ✅ Usar senhas fortes e únicas
- ✅ Revisar regularmente o `.gitignore`

### ❌ O que NÃO fazer:

- ❌ **NUNCA** commitar `config.properties`
- ❌ **NUNCA** colocar senhas no código fonte
- ❌ **NUNCA** usar credenciais de produção em desenvolvimento
- ❌ **NUNCA** compartilhar credenciais em chat/email

## 🔍 Verificar se está Seguro

Execute estes comandos para verificar:

```bash
# Verificar se config.properties está no .gitignore
git check-ignore config.properties

# Verificar arquivos que serão commitados
git status

# Ver histórico para garantir que não há credenciais
git log --oneline -10
```

## 🚨 Se Credenciais foram Expostas

1. **Mude IMEDIATAMENTE** todas as senhas
2. **Revogue** acessos comprometidos
3. **Limpe** o histórico do Git (se necessário)
4. **Audit** logs de acesso ao banco

## 📞 Suporte

Em caso de dúvidas sobre segurança, consulte:

- Administrador de sistema
- Equipe de segurança da informação
- Documentação oficial do SGBD
