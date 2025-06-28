# Script para verificar segurança - Detecta credenciais expostas nos arquivos versionados

Write-Host "=== Verificacao de Seguranca - Credenciais Expostas ===" -ForegroundColor Green

# Verificar se há credenciais nos arquivos versionados
Write-Host "`nVerificando arquivos versionados..." -ForegroundColor Cyan

$foundCredentials = $false

# Verificar README.md
if (Test-Path "README.md") {
    $content = Get-Content "README.md" | Out-String
    if ($content -match "admin20251706|20251706") {
        Write-Host "❌ CREDENCIAIS ENCONTRADAS em README.md" -ForegroundColor Red
        $foundCredentials = $true
    }
    else {
        Write-Host "✅ README.md seguro" -ForegroundColor Green
    }
}

# Verificar SECURITY.md
if (Test-Path "SECURITY.md") {
    $content = Get-Content "SECURITY.md" | Out-String
    if ($content -match "admin20251706|20251706") {
        Write-Host "❌ CREDENCIAIS ENCONTRADAS em SECURITY.md" -ForegroundColor Red
        $foundCredentials = $true
    }
    else {
        Write-Host "✅ SECURITY.md seguro" -ForegroundColor Green
    }
}

# Verificar arquivos DAO
$daoFiles = @(
    "src\combo\bd\DaoStringConexaoMySQL.java",
    "src\combo\bd\DaoStringConexaoPostgreSQL.java",
    "src\combo\util\ConfigUtil.java"
)

foreach ($file in $daoFiles) {
    if (Test-Path $file) {
        $content = Get-Content $file | Out-String
        if ($content -match "admin20251706|20251706") {
            Write-Host "❌ CREDENCIAIS ENCONTRADAS em $file" -ForegroundColor Red
            $foundCredentials = $true
        }
        else {
            Write-Host "✅ $file seguro" -ForegroundColor Green
        }
    }
}

Write-Host "`n=== Resultado da Verificacao ===" -ForegroundColor Green

if ($foundCredentials) {
    Write-Host "❌ ATENCAO: Credenciais encontradas nos arquivos versionados!" -ForegroundColor Red
    Write-Host "   Remova as credenciais antes de commitar no GitHub!" -ForegroundColor Yellow
}
else {
    Write-Host "✅ SEGURO: Nenhuma credencial encontrada nos arquivos versionados!" -ForegroundColor Green
    Write-Host "   O projeto pode ser commitado com seguranca no GitHub!" -ForegroundColor Cyan
}

Write-Host "`nArquivos NAO versionados (no .gitignore):" -ForegroundColor Gray
if (Test-Path "config.properties") {
    Write-Host "  config.properties (contem credenciais locais)" -ForegroundColor Gray
}
if (Test-Path "DESENVOLVIMENTO.md") {
    Write-Host "  DESENVOLVIMENTO.md (contem credenciais de referencia)" -ForegroundColor Gray
}
