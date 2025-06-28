# Script de Compilacao para Sistema de Livros
# Execute este script para compilar todo o projeto

Write-Host "=== Compilando Sistema de Livros ===" -ForegroundColor Green

# Limpar pasta bin
Write-Host "Limpando pasta bin..." -ForegroundColor Cyan
Remove-Item -Recurse -Force bin -ErrorAction SilentlyContinue
New-Item -ItemType Directory -Path bin -Force | Out-Null

# Obter todos os arquivos Java
Write-Host "Encontrando arquivos Java..." -ForegroundColor Cyan
$javaFiles = Get-ChildItem src -Recurse -Filter "*.java" | Select-Object -ExpandProperty FullName

Write-Host "Encontrados $($javaFiles.Count) arquivos Java" -ForegroundColor Yellow

# Definir classpath
$classpath = "lib\mysql-connector-j-9.3.0.jar;lib\postgresql-42.7.7.jar"

# Compilar todos os arquivos de uma vez
Write-Host "Compilando arquivos..." -ForegroundColor Cyan
try {
    # Criar string com todos os arquivos
    $allFiles = $javaFiles -join " "
    
    # Executar javac
    $cmd = "javac -cp `"$classpath`" -d bin $allFiles"
    Write-Host "Executando: javac -cp classpath -d bin [todos os arquivos]" -ForegroundColor Gray
    
    Invoke-Expression $cmd
    
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
        exit 1
    }
} catch {
    Write-Host "Erro na compilacao: $($_.Exception.Message)" -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "=== Compilacao Finalizada ===" -ForegroundColor Green
