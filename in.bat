call adb install -r "app\build\outputs\apk\app-debug-unaligned.apk"
adb shell am start -n org.navigatebyfaith.rrreadings/org.navigatebyfaith.rrreadings.activity.ReadingsActivity 
pause