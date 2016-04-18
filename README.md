![vrJASS Logo](http://i.imgur.com/UyuRc3b.jpg?1)

#vrJASS
_Because you deserve a better yet familiar flavor_

_Even better flavor than before_

##What is vrJASS?
A new and just-for-fun/learning-intended-to-project compiler which aims 
to improve and rebuild from scratch the old and loved vJASS.

##Manual/wiki?
Here https://github.com/Ruk33/vrJASS/wiki

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

##How to compile the grammar?
```bash
$ antlr4 vrjass.g4 -no-listener -visitor -o src/main/java/com/ruke/vrjassc/vrjassc/antlr4 -package com.ruke.vrjassc.vrjassc.antlr4 -encoding UTF-8
```

##How to compile the project?
(Requires compiled grammar)
```bash
$ gradle build
```

##How to use the compiled vrjass?
DO remember that the compiler requires a folder called "resources" with the
common.j and Blizzard.j files.

```bash
resources
|--common.j
|--Blizzard.j
vrjassc.jar
```

Use no options to get all the available commands.

```bash
$ cd path/to/vrjassc.jar
$ java -jar vrjassc.jar file1.j [file2.j file3.w3x file4.w3m]
```

You can pass .j files as well as entire maps.
If you want the compiled source code to be applied in the map, just use the -result option

```bash
$ java -jar vrjassc.jar file1.j file2.j -result=map.w3x
```

If no -result is passed the compiled output will be written in compiled-vrjass.j 
(which is going to be created on the folder where you are).

Errors are going to be written in the log-vrjass.j file (you can change it by 
using the -log option, same as the -result option).

##How to add new features?
**Rule of gold:** write the tests before you write any code (check tests folder
for some examples).

###New keyword/statement/expression
See vrjass.g4

Modify and then compile the grammar.

Example: lets allow users to call a function/method using a 'doCall' keyword

In the vrjass.g4 file, look functionStatement:

```
functionStatement:
	CALL expression NL;
 ```
 
Now add the new call alternative:
 
 ```
functionStatement:
	CALL|'doCall' expression NL;
 ```
 
Save, compile the grammar and boala!
 
##Validation
For the most part, validations are executed in the reference phase (see 
ReferencePhase.java file) (although you might also need the DefinitionPhase) but
the logic of these validations is stored in the Validator class.

##Translation
For this, you might wanna check the com.ruke.vrjassc.translator.expression package  
and the TranslationPhase class.
