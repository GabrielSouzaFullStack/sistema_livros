# Script para verificar status geral do projeto

Write-Host "=== Status do Sistema de Livros ===" -ForegroundColor Green

# Verificar estrutura de arquivos
Write-Host "" 
Write-Host "Estrutura de Arquivos:" -ForegroundColor Cyan

$arquivosEssenciais = @(
    "src/combo/util/ConfigUtil.java",
    "config.properties.example",
    "compile-fixed.ps1",
    "test-connection.ps1",
    "setup-database.ps1",
    "SECURITY.md",
    "TROUBLESHOOTING.md",
    ".gitignore",
    ".gitattributes"
)

foreach ($arquivo in $arquivosEssenciais) {
    if (Test-Path $arquivo) {
        Write-Host "  [OK] $arquivo" -ForegroundColor Green
    }
    else {
        Write-Host "  [AUSENTE] $arquivo" -ForegroundColor Red
    }
}

# Verificar config.properties
Write-Host ""
Write-Host "Configuracao:" -ForegroundColor Cyan
if (Test-Path "config.properties") {
    Write-Host "  [OK] config.properties existe" -ForegroundColor Green
    
    # Verificar se tem as propriedades necessárias
    $content = Get-Content "config.properties"
    $requiredProps = @("mysql.host", "mysql.database", "postgresql.host", "postgresql.database")
    
    foreach ($prop in $requiredProps) {
        if ($content -match "^$prop=") {
            Write-Host "  [OK] $prop configurado" -ForegroundColor Green
        }
        else {
            Write-Host "  [AVISO] $prop nao encontrado" -ForegroundColor Yellow
        }
    }
}
else {
    Write-Host "  [AVISO] config.properties nao existe" -ForegroundColor Yellow
}

# Verificar compilação
Write-Host ""
Write-Host "Compilacao:" -ForegroundColor Cyan
if (Test-Path "bin") {
    $classFiles = Get-ChildItem -Path "bin" -Recurse -Filter "*.class" | Measure-Object
    $count = $classFiles.Count
    if ($count -gt 0) {
        Write-Host "  [OK] Projeto compilado ($count classes)" -ForegroundColor Green
    }
    else {
        Write-Host "  [AVISO] Pasta bin existe mas esta vazia" -ForegroundColor Yellow
    }
}
else {
    Write-Host "  [ERRO] Pasta bin nao existe" -ForegroundColor Red
}

# Verificar bibliotecas
Write-Host ""
Write-Host "Bibliotecas:" -ForegroundColor Cyan
$jars = @("mysql-connector-j-9.3.0.jar", "postgresql-42.7.7.jar")
foreach ($jar in $jars) {
    if (Test-Path "lib/$jar") {
        Write-Host "  [OK] $jar" -ForegroundColor Green
    }
    else {
        Write-Host "  [AUSENTE] $jar" -ForegroundColor Red
    }
}

# Verificar Java
Write-Host ""
Write-Host "Java:" -ForegroundColor Cyan
try {
    $javaVersion = java -version 2>&1
    if ($javaVersion -match "version") {
        Write-Host "  [OK] Java instalado" -ForegroundColor Green
    }
}
catch {
    Write-Host "  [ERRO] Java nao encontrado no PATH" -ForegroundColor Red
}

Write-Host ""
Write-Host "=== Resumo ===" -ForegroundColor Green
Write-Host "Para comecar:" -ForegroundColor White
Write-Host "1. Configurar: copy config.properties.example config.properties" -ForegroundColor Gray
Write-Host "2. Testar:     .\test-connection.ps1" -ForegroundColor Gray
Write-Host "3. Compilar:   .\compile-fixed.ps1" -ForegroundColor Gray
Write-Host "4. Executar:   java -cp bin;lib\postgresql-42.7.7.jar combo.principal.Principal" -ForegroundColor Gray
