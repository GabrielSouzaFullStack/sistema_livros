# Script de Compilacao para Sistema de Livros

Write-Host "=== Compilando Sistema de Livros ===" -ForegroundColor Green

# Limpar pasta bin
Write-Host "Limpando pasta bin..." -ForegroundColor Cyan
Remove-Item -Recurse -Force bin -ErrorAction SilentlyContinue
New-Item -ItemType Directory -Path bin -Force | Out-Null

# Compilar por ordem de dependencia
Write-Host "Compilando por ordem de dependencia..." -ForegroundColor Cyan

# 1. VoConexao
Write-Host "1. Compilando VoConexao..." -ForegroundColor Yellow
javac -d bin src\combo\vo\VoConexao.java

# 2. Utilitarios
Write-Host "2. Compilando ConfigUtil..." -ForegroundColor Yellow
javac -d bin src\combo\util\ConfigUtil.java

# 3. BD (banco de dados)
Write-Host "3. Compilando classes de banco..." -ForegroundColor Yellow
javac -cp "lib\mysql-connector-j-9.3.0.jar;lib\postgresql-42.7.7.jar;bin" -d bin src\combo\bd\E_BD.java
javac -cp "lib\mysql-connector-j-9.3.0.jar;lib\postgresql-42.7.7.jar;bin" -d bin src\combo\bd\DaoStringConexao.java
javac -cp "lib\mysql-connector-j-9.3.0.jar;lib\postgresql-42.7.7.jar;bin" -d bin src\combo\bd\DaoStringConexaoMySQL.java
javac -cp "lib\mysql-connector-j-9.3.0.jar;lib\postgresql-42.7.7.jar;bin" -d bin src\combo\bd\DaoStringConexaoPostgreSQL.java
javac -cp "lib\mysql-connector-j-9.3.0.jar;lib\postgresql-42.7.7.jar;bin" -d bin src\combo\bd\DaoStringConexaoFactory.java
javac -cp "lib\mysql-connector-j-9.3.0.jar;lib\postgresql-42.7.7.jar;bin" -d bin src\combo\bd\DaoConectarBD.java
javac -cp "lib\mysql-connector-j-9.3.0.jar;lib\postgresql-42.7.7.jar;bin" -d bin src\combo\bd\DaoConsultarBD.java

# 4. BO (business objects)
Write-Host "4. Compilando classes de negocio..." -ForegroundColor Yellow
javac -cp "lib\mysql-connector-j-9.3.0.jar;lib\postgresql-42.7.7.jar;bin" -d bin src\combo\bo\BoConexao.java

# 5. DAO
Write-Host "5. Compilando DAOs..." -ForegroundColor Yellow
javac -cp "lib\mysql-connector-j-9.3.0.jar;lib\postgresql-42.7.7.jar;bin" -d bin src\combo\dao\DaoCombo.java

# 6. BO dependente
javac -cp "lib\mysql-connector-j-9.3.0.jar;lib\postgresql-42.7.7.jar;bin" -d bin src\combo\bo\BoCombo.java

# 7. GUI
Write-Host "6. Compilando interfaces..." -ForegroundColor Yellow
javac -cp "lib\mysql-connector-j-9.3.0.jar;lib\postgresql-42.7.7.jar;bin" -d bin src\combo\gui\GuiSelecionaSGBD.java
javac -cp "lib\mysql-connector-j-9.3.0.jar;lib\postgresql-42.7.7.jar;bin" -d bin src\combo\gui\consulta\GuiMontarJTable.java

# 8. Controllers e GUI circular
Write-Host "7. Compilando controllers e GUIs..." -ForegroundColor Yellow
javac -cp "lib\mysql-connector-j-9.3.0.jar;lib\postgresql-42.7.7.jar;bin" -d bin src\combo\gui\GuiCombo.java src\combo\gui\consulta\GuiConsulta.java src\combo\controller\CoCombo.java src\combo\controller\CoConsulta.java

# 9. Principal
Write-Host "8. Compilando Principal..." -ForegroundColor Yellow
javac -cp "lib\mysql-connector-j-9.3.0.jar;lib\postgresql-42.7.7.jar;bin" -d bin src\combo\principal\Principal.java

if ($LASTEXITCODE -eq 0) {
    Write-Host "Compilacao concluida com sucesso!" -ForegroundColor Green
    
    # Contar classes compiladas
    $classFiles = Get-ChildItem bin -Recurse -Filter "*.class"
    Write-Host "Total de classes compiladas: $($classFiles.Count)" -ForegroundColor Cyan
    
    Write-Host ""
    Write-Host "=== Comandos para Executar ===" -ForegroundColor Yellow
    Write-Host "MySQL:" -ForegroundColor White
    Write-Host "java -cp `"bin;lib\mysql-connector-j-9.3.0.jar`" combo.principal.Principal" -ForegroundColor Gray
    Write-Host ""
    Write-Host "PostgreSQL:" -ForegroundColor White  
    Write-Host "java -cp `"bin;lib\postgresql-42.7.7.jar`" combo.principal.Principal" -ForegroundColor Gray
    Write-Host ""
    Write-Host "Ambos:" -ForegroundColor White
    Write-Host "java -cp `"bin;lib\mysql-connector-j-9.3.0.jar;lib\postgresql-42.7.7.jar`" combo.principal.Principal" -ForegroundColor Gray
    
} else {
    Write-Host "Erro na compilacao!" -ForegroundColor Red
}

Write-Host ""
Write-Host "=== Compilacao Finalizada ===" -ForegroundColor Green
