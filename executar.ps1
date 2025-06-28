# Script para executar o Sistema de Livros

Write-Host "=== Executando Sistema de Livros ===" -ForegroundColor Green

# Verificar se projeto foi compilado
if (-not (Test-Path "bin")) {
    Write-Host "[ERRO] Projeto nao compilado! Execute: .\compile-fixed.ps1" -ForegroundColor Red
    exit 1
}

# Verificar se config.properties existe
if (-not (Test-Path "config.properties")) {
    Write-Host "[AVISO] config.properties nao encontrado!" -ForegroundColor Yellow
    Write-Host "Criando a partir do template..." -ForegroundColor Cyan
    if (Test-Path "config.properties.example") {
        Copy-Item "config.properties.example" "config.properties"
        Write-Host "[OK] config.properties criado!" -ForegroundColor Green
        Write-Host "EDITE o arquivo config.properties com suas credenciais antes de continuar!" -ForegroundColor Yellow
        Write-Host "Pressione ENTER depois de configurar..." -ForegroundColor Yellow
        Read-Host
    } else {
        Write-Host "[ERRO] Template config.properties.example nao encontrado!" -ForegroundColor Red
        exit 1
    }
}

# Carregar configuracoes
Write-Host "Carregando configuracoes..." -ForegroundColor Cyan
$config = @{}
Get-Content "config.properties" | ForEach-Object {
    if ($_ -match "^([^#].+?)=(.+)$") {
        $config[$matches[1]] = $matches[2]
    }
}

# Escolher banco de dados
Write-Host "`nBancos disponiveis:" -ForegroundColor Cyan
Write-Host "1. MySQL (localhost:3306)" -ForegroundColor White
Write-Host "2. PostgreSQL (localhost:5432)" -ForegroundColor White
Write-Host "3. Ambos os drivers" -ForegroundColor White

$escolha = Read-Host "Escolha o banco (1/2/3) [default: 2]"
if ([string]::IsNullOrEmpty($escolha)) { $escolha = "2" }

# Definir classpath baseado na escolha
switch ($escolha) {
    "1" { 
        $classpath = "bin;lib\mysql-connector-j-9.3.0.jar"
        Write-Host "Executando com MySQL..." -ForegroundColor Yellow
    }
    "2" { 
        $classpath = "bin;lib\postgresql-42.7.7.jar"
        Write-Host "Executando com PostgreSQL..." -ForegroundColor Yellow
    }
    "3" { 
        $classpath = "bin;lib\mysql-connector-j-9.3.0.jar;lib\postgresql-42.7.7.jar"
        Write-Host "Executando com ambos os drivers..." -ForegroundColor Yellow
    }
    default { 
        $classpath = "bin;lib\postgresql-42.7.7.jar"
        Write-Host "Opcao invalida, usando PostgreSQL..." -ForegroundColor Yellow
    }
}

# Executar aplicacao
Write-Host "`nIniciando aplicacao..." -ForegroundColor Green
Write-Host "Comando: java -cp `"$classpath`" combo.principal.Principal" -ForegroundColor Gray
Write-Host ""

try {
    java -cp $classpath combo.principal.Principal
} catch {
    Write-Host "[ERRO] Falha ao executar aplicacao: $($_.Exception.Message)" -ForegroundColor Red
    Write-Host "`nVerifique:" -ForegroundColor Yellow
    Write-Host "1. Se o Java esta instalado" -ForegroundColor Gray
    Write-Host "2. Se o projeto foi compilado (.\compile-fixed.ps1)" -ForegroundColor Gray
    Write-Host "3. Se as bibliotecas estao na pasta lib/" -ForegroundColor Gray
    Write-Host "4. Se as credenciais em config.properties estao corretas" -ForegroundColor Gray
}
