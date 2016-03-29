#!/bin/bash

echo "Devices before:"
adb devices
 
echo "***"
echo "Connecting..."
adb forward tcp:4444 localabstract:/adb-hub
adb disconnect localhost:4444
adb connect 127.0.0.1:4444

echo "***"
echo "Devices afterwards:"
adb devices

