# MediaPlayerDemoWithAudiofocus
Demo for my students to show them basic MediaPlayer functionality with a sound file.

This app contains an unlicensed mp3 audio file, and uses a MediaPlayer to play and pause it. 
It also handles AudioFocus, using instances of AudioManger, AudioFocusRequest, and AudioAttributes.
The MediaPlayer is released when the sound file has finished playing, when the app loses audio focus, or when the activity has been stopped.
When the app temporarily loses audio focus, the audio file is paused and then resumed once the app regains audiofocus.

![Screenshot of app](https://github.com/alj968/MediaPlayerDemoWithAudioFocus/blob/master/MediaPlayerScreenshot.png "Screenshot of app")
