# Check Java is installed
if (-not (Get-Command java -ErrorAction SilentlyContinue)) {
    Write-Host "Java 21+ is required. Download from https://adoptium.net"
    exit 1
}

# Check Java version is 21+
$javaVersion = (java -version 2>&1 | Select-String "version").ToString()
Write-Host "Found: $javaVersion"

# Create directory
New-Item -ItemType Directory -Force -Path "$HOME\.ccms\bin"

# Download JAR
Write-Host "Downloading CCMS CLI..."
Invoke-WebRequest -Uri "https://github.com/ReebanAustrive/AI-Assitant-Micro-Saas/releases/latest/download/ccms.jar" `
    -OutFile "$HOME\.ccms\bin\ccms.jar"

# Create wrapper batch file
@"
@echo off
java -jar "%USERPROFILE%\.ccms\bin\ccms.jar" %*
"@ | Out-File -FilePath "$HOME\.ccms\bin\ccms.bat" -Encoding ASCII

# Add to PATH
$path = [Environment]::GetEnvironmentVariable("PATH", "User")
if ($path -notlike "*\.ccms\bin*") {
    [Environment]::SetEnvironmentVariable("PATH", "$path;$HOME\.ccms\bin", "User")
    Write-Host "Added CCMS to PATH"
}

Write-Host ""
Write-Host "CCMS CLI installed successfully!"
Write-Host ""
Write-Host "Open a new terminal and run:"
Write-Host "  ccms init --repo <github-repo-url> --arch <path-to-arch-doc>"
Write-Host ""
Write-Host "Example:"
Write-Host "  ccms init --repo https://github.com/org/repo --arch C:/Users/you/docs/arch.md"
Write-Host "  ccms review --pr 42"
