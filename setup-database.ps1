# Script para criar base de dados e tabelas necessárias

Write-Host "=== Configuração de Base de Dados ===" -ForegroundColor Green

$dbType = Read-Host "Qual banco usar? (mysql/postgresql)"

if ($dbType -eq "mysql") {
    Write-Host "`nConfigurando MySQL..." -ForegroundColor Yellow
    Write-Host "Comandos SQL para executar no MySQL:" -ForegroundColor Cyan
    Write-Host ""
    Write-Host "-- 1. Criar base de dados" -ForegroundColor Gray
    Write-Host "CREATE DATABASE livros;" -ForegroundColor White
    Write-Host ""
    Write-Host "-- 2. Usar a base" -ForegroundColor Gray
    Write-Host "USE livros;" -ForegroundColor White
    Write-Host ""
    Write-Host "-- 3. Criar tabelas" -ForegroundColor Gray
    Write-Host "CREATE TABLE livros (" -ForegroundColor White
    Write-Host "  codigo INT PRIMARY KEY AUTO_INCREMENT," -ForegroundColor White
    Write-Host "  titulo VARCHAR(255)," -ForegroundColor White
    Write-Host "  autor VARCHAR(255)," -ForegroundColor White
    Write-Host "  editora VARCHAR(255)," -ForegroundColor White
    Write-Host "  ano INT" -ForegroundColor White
    Write-Host ");" -ForegroundColor White
    Write-Host ""
    Write-Host "CREATE TABLE livrostemp (" -ForegroundColor White
    Write-Host "  codigo INT PRIMARY KEY AUTO_INCREMENT," -ForegroundColor White
    Write-Host "  titulo VARCHAR(255)," -ForegroundColor White
    Write-Host "  autor VARCHAR(255)," -ForegroundColor White
    Write-Host "  editora VARCHAR(255)," -ForegroundColor White
    Write-Host "  ano INT" -ForegroundColor White
    Write-Host ");" -ForegroundColor White
    Write-Host ""
    Write-Host "-- 4. Inserir dados de teste" -ForegroundColor Gray
    Write-Host "INSERT INTO livros (titulo, autor, editora, ano) VALUES" -ForegroundColor White
    Write-Host "  ('Dom Casmurro', 'Machado de Assis', 'Garnier', 1899)," -ForegroundColor White
    Write-Host "  ('O Cortiço', 'Aluísio Azevedo', 'Garnier', 1890)," -ForegroundColor White
    Write-Host "  ('Iracema', 'José de Alencar', 'Tipografia de Viana & Filhos', 1865);" -ForegroundColor White
    Write-Host ""
    Write-Host "INSERT INTO livrostemp SELECT * FROM livros;" -ForegroundColor White
    Write-Host ""
    Write-Host "Como executar:" -ForegroundColor Yellow
    Write-Host "1. Abra MySQL Workbench, phpMyAdmin ou linha de comando" -ForegroundColor White
    Write-Host "2. Cole e execute os comandos acima" -ForegroundColor White
    Write-Host "3. Execute o sistema:" -ForegroundColor White
    Write-Host "   java -cp `"bin;lib\mysql-connector-j-9.3.0.jar`" combo.principal.Principal" -ForegroundColor Gray
    
}
elseif ($dbType -eq "postgresql") {
    Write-Host "`nConfigurando PostgreSQL..." -ForegroundColor Yellow
    Write-Host "Comandos SQL para executar no PostgreSQL:" -ForegroundColor Cyan
    Write-Host ""
    Write-Host "-- 1. Criar base de dados (execute como superuser)" -ForegroundColor Gray
    Write-Host "CREATE DATABASE livros;" -ForegroundColor White
    Write-Host ""
    Write-Host "-- 2. Conectar à base livros e criar tabelas" -ForegroundColor Gray
    Write-Host "\c livros;" -ForegroundColor White
    Write-Host ""
    Write-Host "-- 3. Criar tabelas" -ForegroundColor Gray
    Write-Host "CREATE TABLE livros (" -ForegroundColor White
    Write-Host "  codigo SERIAL PRIMARY KEY," -ForegroundColor White
    Write-Host "  titulo VARCHAR(255)," -ForegroundColor White
    Write-Host "  autor VARCHAR(255)," -ForegroundColor White
    Write-Host "  editora VARCHAR(255)," -ForegroundColor White
    Write-Host "  ano INTEGER" -ForegroundColor White
    Write-Host ");" -ForegroundColor White
    Write-Host ""
    Write-Host "CREATE TABLE livrostemp (" -ForegroundColor White
    Write-Host "  codigo SERIAL PRIMARY KEY," -ForegroundColor White
    Write-Host "  titulo VARCHAR(255)," -ForegroundColor White
    Write-Host "  autor VARCHAR(255)," -ForegroundColor White
    Write-Host "  editora VARCHAR(255)," -ForegroundColor White
    Write-Host "  ano INTEGER" -ForegroundColor White
    Write-Host ");" -ForegroundColor White
    Write-Host ""
    Write-Host "-- 4. Inserir dados de teste" -ForegroundColor Gray
    Write-Host "INSERT INTO livros (titulo, autor, editora, ano) VALUES" -ForegroundColor White
    Write-Host "  ('Dom Casmurro', 'Machado de Assis', 'Garnier', 1899)," -ForegroundColor White
    Write-Host "  ('O Cortiço', 'Aluísio Azevedo', 'Garnier', 1890)," -ForegroundColor White
    Write-Host "  ('Iracema', 'José de Alencar', 'Tipografia de Viana & Filhos', 1865);" -ForegroundColor White
    Write-Host ""
    Write-Host "INSERT INTO livrostemp SELECT * FROM livros;" -ForegroundColor White
    Write-Host ""
    Write-Host "Como executar:" -ForegroundColor Yellow
    Write-Host "1. Abra pgAdmin, psql ou linha de comando PostgreSQL" -ForegroundColor White
    Write-Host "2. Cole e execute os comandos acima" -ForegroundColor White
    Write-Host "3. Execute o sistema:" -ForegroundColor White
    Write-Host "   java -cp `"bin;lib\postgresql-42.7.7.jar`" combo.principal.Principal" -ForegroundColor Gray
    
}
else {
    Write-Host "Opção inválida. Use 'mysql' ou 'postgresql'" -ForegroundColor Red
}

Write-Host "`n=== Teste de Aplicação ===" -ForegroundColor Green
Write-Host "Após criar as tabelas, execute o sistema e teste:" -ForegroundColor White
Write-Host "1. Clique em 'Carregar' para listar livros no ComboBox" -ForegroundColor White
Write-Host "2. Clique em 'Consulta' para ver dados paginados" -ForegroundColor White
