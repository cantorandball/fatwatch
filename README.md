# fatwatch
A repository to contain CAST watch work. Consists of two branches:

master - The current release candidate. Should be tagged with a release number
dev - Work in progress. Likely to be buggy, half-baked and held together with spit and string. Pull at your own risk. 

## Setting up Android Studio, to run the app in an emulator:
 - Go to [developer.android.com](developer.adroid.com/sdk/index.html) and download the software. It's 1.1GB, I suggest using this step to go have lunch.
 - Open the Android Studio application, install all components.
 - If you've got seetings to import, import them, select a theme, etc. 
 - Once various additional modules are done installing, either 'Open an existing project' and point it at this folder, or check it straight 'out of VCS'.
 - You should be able to see a directory tree in the 'Project' bar to the left of the screen, containing a folder called 'Hellogo world', containing the minimal 'Hello World' app.
 - In order to run this application inside an emulator, you'll need to set one up. There are good, Google written instructions for this [here](https://developer.android.com/training/wearables/apps/creating.html), which you should follow to the point of pairing your handheld with the emulator (no need for this, unless you really want to do it.) The version of Andriod Wear I'm developing for is 4.4. After these instructions, you should now have a running, emulated device.
 - In the bar at the top of the screen, there should be a dropdown menu which, when moused over, says 'Select Run/Debug configuration.' This is where you set which program the green 'play' button will run. With any luck, this will be displaying the name 'app'. If not, add a run configuration with hellogo-world/app as a module.
 - Hit the green 'Play' button, and choose your running Android watch emulator. If this isn't running, you can launch one here.
 - That's it! With any luck, the C&B logo should slide coyly in from the right.


## Connecting to an Android Wear device for the first time:
 - Enable debugging on phone and watch
 - Connect via Wear app on phone
 - Plug phone into development machine
 - Run these commands:

   $ adb forward tcp:4444 localabstract:/adb-hub
   $ adb connect localhost:4444
   connected to localhost:4444

*Authorise on watch, select 'Always for this computer'*

To check:
   $ adb devices
   localhost:4444	device

 - If you don't see 'connected to localhost:4444' then disconnect, reconnect, and try accepting debugging again. 
