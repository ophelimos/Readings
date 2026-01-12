# Daily Bible Readings App (2026 update) #

This is James Robinsons' fork of Andy Joiner's excellent Readings app (https://github.com/tekkies/Readings) which no longer works properly on newer Android versions.  Currently, it is just a very limited hack to get it working on my personal device (Pixel 5), but Lord willing we'll be able to get it back on the Play Store eventually.

# Plugins #

The app uses a [sister app](https://play.google.com/store/apps/details?id=uk.co.tekkies.plugin.kjv) to provide content.  Although it no longer is on the Google Play store, a manual apk install (from one of the many websites that have copied apks from the play store) does seem to work properly on a Pixel 5.  It does not work properly on newer Android versions though, so it will need to be replaced.

# Build #

Build using Android Studio - Just open a project and point to this folder.

# Summaries #

I do not have permission to use the original summaries, so this fork contains AI-generated ones.

## Libraries ##

I disabled all the Google Analytics, so it does not currently require it.  There's still a lot of Google Analytics code, but it currently doesn't do anything.

## Test automation ##

I have not updated any of the tests.  I have tested it on my device though, and it seems to work for what I care about (showing the Readings and the associated KJV text).
