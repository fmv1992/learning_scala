# Chapter 04: Classes and Objects

* In this chapter the classes and singleton concepts were presented. The key passage is:  
    "One difference between classes and singleton objects is that singleton objects cannot take parameters, whereas classes can. Because you can’t instantiate a singleton object with the new keyword, you have no way to pass parameters to it. Each singleton object is implemented as an instance of a synthetic class referenced from a static variable, so they have the same initialization semantics as Java statics. 4 In particular, a singleton object is initialized the first time some code accesses it. A singleton object that does not share the same name with a companion class is called a standalone object. You can use standalone objects for many purposes, including collecting related utility methods together or defining an entry point to a Scala application."

* Then why not define the methods `add` and `checksum` inside the singleton?
    * *Answer*: Trying it out in code says: `./ChecksumAccumulator.scala:36: error: not found: value sum`.
    * Thus we can conclude that `sum` is state of an instance of the `ChecksumAccumulator` `class` and as such it must not be shared (via singleton). As Scala is a compiled language we probably have to keep methods that reference to this variable in the same class thus forcing the aforementioned methods to be declared there.
