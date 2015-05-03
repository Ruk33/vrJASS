![vrJASS Logo](http://i.imgur.com/UyuRc3b.jpg?1)

#vrJASS
_Because you deserve a better yet familiar flavor_

_Even better flavor than before_

##What is vrJASS?
A new and just-for-fun/learning-intended-to project which aims 
to improve and rebuild from scratch the old and loved vJASS.

##Its there a beta or something to try it?
Download the pre-compiled here:

https://github.com/Ruk33/vrJASS/raw/master/vrjassc.jar

And follow the instruction of **How to use the compiled vrjass?**

##How can I help you?
If you know Java or Antlr4 you can check the repository and send 
me pull requests (don't worry if you don't know too much about them, 
I'm new too!). If you don't know these languages, let me know your 
suggestions, I'm open to them :)

##What does vr mean?
Pick the one you like the most:

- Beer (default), because everyone loves beer
- Vexorian r$~%d-newbetterversionftw
- Vexorian (creator of vJASS, respect) Ruke

##How to compile it?
###Eclipse
- Right click to the project
- Import
- Archive File
- Browse
- Select all .jar files in lib folder
- Click ok
- Click finish
- Right click on vrjass.g4
- Run as
- External tool configurations...
- Click on New launch configuration...
- Paste this: -no-listener -visitor -encoding UTF-8 -o src/antlr4 -package antlr4
- Click Run
- Right click to the project
- Export
- JAR file
- Click Next
- Click Next
- Click Browse... where it says Main class
- Select util.vrjassc
- Finish
- Done!

##How to use the compiled vrjass?
It is very simple, just type:

```bash
$ java -jar path/to/vrjassc.jar file1 file2
```

The output will be writed to output.j file (which will be created to the path 
where you execute the jar file).
