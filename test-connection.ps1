# Script para testar conexão com banco de dados

Write-Host "=== Testando Conexão com Banco de Dados ===" -ForegroundColor Green

# Verificar se config.properties existe
if (Test-Path "config.properties") {
    Write-Host "✅ Arquivo config.properties encontrado" -ForegroundColor Green
    
    # Mostrar configurações carregadas
    Write-Host "`nConfigurações atuais:" -ForegroundColor Cyan
    Get-Content config.properties | Where-Object { $_ -match "^(mysql|postgresql)\.(host|porta|database|usuario)=" } | ForEach-Object {
        if ($_ -notmatch "senha") {
            Write-Host "  $_" -ForegroundColor White
        }
    }
} else {
    Write-Host "❌ Arquivo config.properties não encontrado!" -ForegroundColor Red
    Write-Host "Copie config.properties.example para config.properties" -ForegroundColor Yellow
}

Write-Host "`n=== Verificando Bancos de Dados ===" -ForegroundColor Yellow

# Testar MySQL
Write-Host "`n🔍 Testando MySQL (localhost:3306)..." -ForegroundColor Cyan
try {
    $mysqlTest = Test-NetConnection -ComputerName localhost -Port 3306 -WarningAction SilentlyContinue
    if ($mysqlTest.TcpTestSucceeded) {
        Write-Host "✅ MySQL está rodando em localhost:3306" -ForegroundColor Green
    } else {
        Write-Host "❌ MySQL NÃO está rodando em localhost:3306" -ForegroundColor Red
        Write-Host "   Inicie o MySQL server primeiro" -ForegroundColor Yellow
    }
} catch {
    Write-Host "❌ Erro ao testar MySQL: $($_.Exception.Message)" -ForegroundColor Red
}

# Testar PostgreSQL
Write-Host "`n🔍 Testando PostgreSQL (localhost:5432)..." -ForegroundColor Cyan
try {
    $pgTest = Test-NetConnection -ComputerName localhost -Port 5432 -WarningAction SilentlyContinue
    if ($pgTest.TcpTestSucceeded) {
        Write-Host "✅ PostgreSQL está rodando em localhost:5432" -ForegroundColor Green
    } else {
        Write-Host "❌ PostgreSQL NÃO está rodando em localhost:5432" -ForegroundColor Red
        Write-Host "   Inicie o PostgreSQL server primeiro" -ForegroundColor Yellow
    }
} catch {
    Write-Host "❌ Erro ao testar PostgreSQL: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host "`n=== Comandos para Iniciar Serviços ===" -ForegroundColor Yellow
Write-Host "MySQL:" -ForegroundColor White
Write-Host "  net start mysql" -ForegroundColor Gray
Write-Host "  # ou via xampp/wamp/docker" -ForegroundColor Gray

Write-Host "`nPostgreSQL:" -ForegroundColor White
Write-Host "  net start postgresql-x64-13" -ForegroundColor Gray
Write-Host "  # ou pg_ctl start -D <data_directory>" -ForegroundColor Gray

Write-Host "`n=== Teste de Aplicação ===" -ForegroundColor Yellow
Write-Host "Executar sistema:" -ForegroundColor White
Write-Host "java -cp `"bin;lib\mysql-connector-j-9.3.0.jar;lib\postgresql-42.7.7.jar`" combo.principal.Principal" -ForegroundColor Gray

Write-Host "`n=== Teste Concluído ===" -ForegroundColor Green
