@echo off
echo Usuwanie plik�w author.txt w podkatalogach...
for /r %%G in (author.txt) do (
    if exist "%%G" (
        echo Usuwanie pliku: %%G
        del /f /q "%%G"
    )
)
echo Usuwanie zako�czone.
pause
