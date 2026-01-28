
set DELAY=5

echo %DELAY%

adb uninstall org.navigatebyfaith.rrreadings

for %%f in ("%HOMEDRIVE%%HOMEPATH%\Dropbox\Android\Readings\org.navigatebyfaith.rrreadings.api14\*.apk") DO call _upgrade.bat "%%f" %DELAY%
REM for %%f in ("%HOMEDRIVE%%HOMEPATH%\Dropbox\Android\Readings\org.navigatebyfaith.rrreadings.api3\*.apk") DO call adb install -r "%%f" & adb shell am start -n org.navigatebyfaith.rrreadings/org.navigatebyfaith.rrreadings.ReadingsActivity & ping -n %DELAY% 127.0.0.1