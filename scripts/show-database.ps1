param(
    [string]$Root = (Split-Path -Parent $PSScriptRoot)
)

$ErrorActionPreference = 'Stop'
$ProgressPreference = 'SilentlyContinue'

Set-Location $Root

$toolsDir = Join-Path $Root '.tools'
$outDir = Join-Path $Root 'out-db'
$mybatisJar = Join-Path $toolsDir 'mybatis-3.5.19.jar'
$h2Jar = Join-Path $toolsDir 'h2-2.4.240.jar'
$mysqlJar = Join-Path $toolsDir 'mysql-connector-j-9.6.0.jar'

New-Item -ItemType Directory -Force -Path $toolsDir | Out-Null
New-Item -ItemType Directory -Force -Path $outDir | Out-Null

if (-not (Test-Path $mybatisJar)) {
    Invoke-WebRequest -Uri 'https://repo1.maven.org/maven2/org/mybatis/mybatis/3.5.19/mybatis-3.5.19.jar' -OutFile $mybatisJar
}

if (-not (Test-Path $h2Jar)) {
    Invoke-WebRequest -Uri 'https://repo1.maven.org/maven2/com/h2database/h2/2.4.240/h2-2.4.240.jar' -OutFile $h2Jar
}

if (-not (Test-Path $mysqlJar)) {
    Invoke-WebRequest -Uri 'https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/9.6.0/mysql-connector-j-9.6.0.jar' -OutFile $mysqlJar
}

$classpath = @($mybatisJar, $h2Jar, $mysqlJar) -join ';'
$sourceFiles = (Get-ChildItem -Recurse -Filter *.java src\main\java).FullName

javac -encoding UTF-8 -cp $classpath -d $outDir $sourceFiles
java -cp "$outDir;src/main/resources;$classpath" com.example.student.DbInspector
