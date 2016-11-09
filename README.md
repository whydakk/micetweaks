# micetweaks v1.0
Mouse and touchpad automatic detector and sensitivity setter for Linux.

Download: http://gasionet.pl/app/micetweaks.jar

![micetweaks](http://i.imgur.com/cHjLgPP.png)

Application detects the moment when device is plugged into the USB port and sets individual config (speed and deceleration) to it.  
"Must have app" especially when using <B>KDE</B>, where you can set mouse acceleration only.  

At the begining it detects all connected pointers (like mice and touchpads) and shows window with those devices. There you can adjust it's speed and deceleration.
Every change is activated immediately. To save configuration just click "Save config" button.

You may hide the window into system tray by clicking on the tray icon. Click again to show it back on.

If application is closed when hidden in system tray it remembers that state, so when you'll start it again the 
application will be running in the background.

Dependency: xinput package and udevadm installed in your operating system.