# Script para normalizar quebras de linha no Windows
# Execute este script se encontrar problemas com CRLF/LF

Write-Host "=== Normalizando Quebras de Linha ===" -ForegroundColor Green

# Configurar Git para Windows
git config core.autocrlf true
git config core.safecrlf false

Write-Host "Configurações do Git atualizadas:" -ForegroundColor Cyan
Write-Host "  core.autocrlf = true (converte LF para CRLF no Windows)"
Write-Host "  core.safecrlf = false (remove warnings desnecessários)"

# Normalizar todos os arquivos
Write-Host "`nNormalizando arquivos..." -ForegroundColor Cyan
git add --renormalize .

Write-Host "`nStatus do Git após normalização:" -ForegroundColor Cyan
git status --short

Write-Host "`n=== Normalização Concluída ===" -ForegroundColor Green
Write-Host "Os warnings de CRLF/LF devem ter sido resolvidos."
