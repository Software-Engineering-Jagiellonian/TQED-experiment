@echo off
echo Usuwanie plików author.txt w podkatalogach...
for /r %%G in (author.txt) do (
    if exist "%%G" (
        echo Usuwanie pliku: %%G
        del /f /q "%%G"
    )
)
echo Usuwanie zakoñczone.
pause
