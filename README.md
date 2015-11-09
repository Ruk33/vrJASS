![vrJASS Logo](http://i.imgur.com/UyuRc3b.jpg?1)

#vrJASS
_Because you deserve a better yet familiar flavor_

_Even better flavor than before_

##What is vrJASS?
A new and just-for-fun/learning-intended-to project which aims 
to improve and rebuild from scratch the old and loved vJASS.

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
- Right click to pom.xml
- Run as...
- Maven install


##How to use the compiled vrjass?
```bash
$ cd path/to/vrjassc-jar-with-dependencies.jar
$ java -jar vrjassc-jar-with-dependencies.jar file1.j [file2.j file3.w3x file4.w3m]
```

You can pass .j files as well as entire maps.
If you want the compiled source code to be applied in the map, just use the -result option

```bash
$ java -jar vrjassc-jar-with-dependencies.jar file1.j file2.j -result=map.w3x
```

If no -result is passed the compiled output will be written in compiled-vrjass.j (which is going to be created on the folder where you are).

Errors are going to be written in the log-vrjass.j file (you can change it by using the -log option, same as the -result option).
