[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/J_c8sizy)
# MassiveMotion
CS 245 Project 02
    - Even Michael

# How to Run :
 **First make sure you are in the ** src ** folder**
Compile
    -->| javac *.java
Then run :
    -->| java MassiveMotion ../MassiveMotion.txt
---------------------------------------------
# Files Included : 

    * MassiveMotion.java -->  Creates the window, handles drawing, motion, spawning, and removal of celestial bodies.
    * Config.java -->  Reads configuration values from MassiveMotion.txt (properties file).
    * List.java -->  Generic list interface used by the simulation.
    * ListImpls -->  
         -->  ListImpl_ArrayList.java
         -->  ListImpl_Linked.java  
         -->  ListImpl_Double.java 
         -->  ListImpl_DummyHead.java 
---------------------------------------------
# How every requirement is met :

    * Req 1 : Read from property file
        -->  Config.java loads and validates key–value pairs.

    * Req 2 : List Implementations
        -->  List.java and the four ListImpl_* classes
        -->  MassiveMotion selects the implementation at runtime
    
    * Req 3 : Display / Animation Window
        -->  MassiveMotion.java creates a JPanel with timer-based repainting
    
    * Req 4 : Simulation Logic
        -->  MassiveMotion.actionPerformed() moves bodies, spawns new ones (maybeSpawn()), and removes off-screen bodies.