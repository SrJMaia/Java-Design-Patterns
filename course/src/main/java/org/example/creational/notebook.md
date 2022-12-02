# Creation Pattern

Creation patterns deal with the process of creation of objects of classes

## Builder
- Immutable classes with a lot of parameters
- We have a complex process to construct an object involving multiple steps, then builder design pattern can help us
- In builder, we remove the logic related to object construction from "client" code & abstract it in separate classes

#### Pitfalls
- A little complex for newcomers mainly because of "method chaining", where builder methods 
return builder object itself.
- Possibility of partially initialized object; user code can set only a few or none of properties using withXXX methods
and call build(). If required properties are missing, build method should provide suitable defaults or throw exception.

#### In-A-Hurry Summary
- Think of builder pattern when you have a complex constructor or an object is built.

## Simple Factory
- Here we simply move the instantiation logic to a separate class and most commonly to a static method of this class
- Some do not consider simple factory to be a "design pattern", as it's simply a method that encapsulates 
objects instantiation. Nothing complex goes on in that method.
- Typically, we want to do this if we have more than one option when instantiating object and a simple logic 
is used to choose correct class.

#### Implementation Considerations
- Simple factory can be just a method in existing class. Adding a separate class however allows other parts 
of your code to use simple factory more easily.

#### Design Considerations
- Simple factory will in turn may use other design pattern like builder to construct objects.
- In case you want to specialize your simple factory in subclasses, you need factory method design pattern instead.

#### Pitfalls
- The criteria used by simple factory to decide which object to instantiate can get more convoluted/complex over time. 
If you find yourself in such situation then use factory method design pattern.

#### In-A-Hurry Summary
- Simple factory encapsulates away the object instantiation in a separate method.
- We can pass an argument to this method to indicate product type and/or additional arguments to help create objects.

## Factory Method
- We want to move the object creation logic from our code to a separate class.
- We use this pattern when we do not know in advance which class we may need to instantiate beforehand & also to
allow new classes to be added to system and handle their creation without affecting client code.
- We let subclasses decide which object to instantiate by overriding the factory method.

#### Implement a Factory Method
- We start by creating a class for our creator
    - Creator itself can be concrete if it can provide a default object, or it can be abstract.
    - Implementations will override the method and return an object.

#### Implementation Considerations
- The creator can be a concrete class & provide a default implementation for the factory method. 
In such cases you'll create some "default" object in base creator.
- You can also use the simple factory way of accepting additional arguments to choose between different objects types. 
Subclasses can then override factory method to selectively create different objects for some criteria.
- Creator hierarchy in factory method pattern reflects the product hierarchy. We typically end up with
a concrete creator per object type.
- Template method design pattern makes use of factory methods.
- Another creation design pattern-called "abstract factory" makes use of factory method pattern.

#### Pitfalls
- More complex to implement. More classes involved and need unit testing.
- You have to start with factory method design pattern from the beginning. It's not easy to refactor existing code into
factory method pattern.
- Sometimes this pattern forces you to subclass just to create appropriate instance.

#### In-A-Hurry Summary
- Use factory method pattern when yu want to delegate object instantiation to subclasses, you'd want to do this
when you have "product" inheritance hierarchy and possibility of future additions to that.

## Prototype
- We have a complex object that is costly to create. To create more instance of such class, we use an existing instance 
as our prototype.
- Prototype will allow us to make copies of existing object & save us from having to recreate objects from scratch.

#### Implement a Prototype
- We start by creating a c lass which will be a prototype
  - The class must implement Cloneable interface
  - Class should override clone method and return copy itself.
  - The method should declare CloneNotSupportedException in throws clause to give subclasses 
chance to decide on whether to support cloning.
- Clone method implementation should consider the deep & shallow copy and choose whichever is applicable.

#### Design Considerations
- Prototypes are useful when you have large objects where majority of state is unchanged between
instances, and you can easily identify that state
- A prototype registry is a class where in you can register various prototypes which other code can access to clone out
instances. This solves the issue of getting access to initial instance.
- Prototypes are useful when working with Composite and Decorator patterns

#### Pitfalls
- Usability depends upon the number of properties in state that are immutable or can be shallow copied. An object where
state comprises large number of mutable objects is complicated to clone.
  - In java the default clone operation will only perform the shallow copy so if 
you need a deep copy you've to implement it.
  - Subclasses may not be able to support clone and so the code becomes complicated as you have to code for situations 
where an implementation may not support clone.

#### In-A-Hurry Summary
- Think of prototype pattern when you have an object where construction of a new instance is costly
or not possible (object is supplied to your code).
- In Java, we typically implement this pattern with clone method.
- Objects which have a majority of their state as immutable are good candidates for prototypes.
- When implementing clone method pay attention to the requirement of deep or shallow copy object state.
- Also, we've to ensure that clone is "initialized"; that is appropriate states are reset before 
returning the copy to outside world.

## Abstract Factory
- Abstract factory is used when we have two or more objects which work together forming a kit or set and there
can be multiple sets or kits that can be created by client code.
- So we separate client code from concrete objects forming such a set and also from the code which creates these sets.

#### Implementation
- We start by using the product "sets"
  - Create abstract factory as an abstract class or an interface.
  - Abstract factory defines abstract methods for creating products.
  - Provide concrete implementation of factory for each set of products.
- Abstract factory makes use of factory method pattern. You can think of abstract factory as an object with
multiple factory methods...

#### Considerations
- Factories can be implemented as singletons, we typically ever need only one instance of it anyway. But make sure
to familiarize yourself with drawbacks fo singletons.
- Adding a new product type requires changes to the base factory as well as all implementations of factory.
- We provide the client code with concrete factory so that it can create objects.

#### Design Consideration
- When you want to constrain object creations so that they all work together then abstract factory is good design pattern.
- Abstract factory uses factory method pattern.
- If objects are expensive to create then you can transparently switch factory implementations to use 
prototype design pattern to create objects.

#### Pitfalls
- A lot more complex to implement than factory method.
- Adding a new product requires changes to base factory as well as all implementations of factory.
- Difficult to visualize the need at start of development and usually starts out as a factory method.
- Abstract factory design pattern is very specific to the problem of "product families".

#### In-A-Hurry Summary
- When you have multiple sets of objects where objects in one set work together then you can use abstract
factory pattern to isolate client code from concrete objects & their factories.
- Abstract factory itself uses factory method pattern, and you can think of them as objects with multiple factory methods.
- Adding a new product type needs changes to base factory and all its implementations.
- We provide client code with concrete factory instance. Factories can be changed at runtime.

## Singleton
- A single ton class has only one instance, accessible globally through a single point (via a method/field)
- Main problem this pattern solves is to ensure that only a single instance of this class exists.
- Any state you add in your singleton becomes part of "global state" of your application.

#### Implementation
- Controlling instance creation
  - Class constructor(s) must be not be accessible globally
  - Subclassing/inheritance must not be allowed
- Keeping track of instance 
  - Class itself is a good place to track the instance
- Giving access to the singleton instance
  - A public static method is a good choice
  - Can expose instance as final public static field, but it won't work for all singleton implementation.

- Two options for implementing a singleton
  - Early initialization - Eager Singleton
    - Create singleton as soon as class is loaded
  - Lazy initialization - Lazy Singleton
    - Singleton is created when it is first required

#### Consideration
- Singleton creation does not need any parameters. If you find yourself in need of support for constructor arguments,
you need a simple factory or factory method pattern instead.
- Make sure that your singletons are not carrying a lot of mutable global state.

#### Pitfalls
- Singleton pattern can deceive you about true dependencies! Since they are globally accessible 
its easy to miss dependencies.
- They are hard to unit test. You can not easily mock the instance that is returned.
- Most common way to implement singletons in Java is through static variables, and they are held per
class loader and not per JVM. So they may not be truly Singleton in an OSGi or web application.
- A singleton carrying around a large mutable global state is a good indication of an abused Singleton Pattern.

#### In-A-Hurry Summary
- Singleton pattern is used when you want to ensure that only one instance fo a class exists in application.
- In Java, we achieve this by making constructor private, this also prevents inheritance & providing a public static 
method which returns the singleton instance.
- Implementation wise we have two broad choices:
  - In eager loading singleton, we create instance as soon as class is loaded by classloader
  - In lazy loading singleton, we defer creation until some code actually requests the instance.
- Always prefer the eager loading instance unless creation cost is high and start-up time impact is noticeable.
- There are very few situations where a Singleton is really a good choice.
- Application configuration values can be tracked in a singleton. Typically, these are read from file at start
and then referred to by other parts of application.
- Logging frameworks also make use of Singleton pattern.
- Spring framework treats all beans by default as singletons. In spring, we don't have to make any changes to ensure single
instance, Spring handles that for us.

## Object Pool
- In our system if cost of creating an instance of a class is high, and we need a large number of objects of 
this class for short duration, then we can use an object pool.
- Here we either pre-create objects of the class or collect unused instances in an in memory cache. 
When code needs an object of this class we provide it from this cache.
- One of the most complicated patterns to implement efficiently and without defects.

#### Implementation
- We start by creating class for object pool.
  - A thread-safe caching of objects should be done in pool.
  - Methods to acquire and release objects should be provided & pool should reset cached objects before giving them out.
- The reusable object must provide methods to reset its state upon "release" by code.
- We have to decide whether to create new pooled objects when pool is empty or to wait until an object 
becomes available. Choice is influenced by whether the object is tied to a fixed number of external resources.

#### Considerations
- Resetting object state should NOT be costly operation otherwise you may end up losing your performance savings.
- Pre-caching objects; meaning creating objects in advance can be helpful as it won't slow down the
code using these objects. However, it may add-up to start up time & memory consumption.
- Object pool's synchronization should consider the reset time needed & avoid resetting in 
synchronized context if possible.

#### Design Considerations
- Object pool can be parameterized to cache & return multiple objects and the acquire method can provide
selection criteria.
- Pooling objects is only beneficial if they involve costly initialization because of initialization of
external resource like a connection or a thread. Don't pool objects JUST to save memory, unless you are
running into out of memory errors.
- Do not pool long-lived objects or only to save frequent call to new. Pooling may actually negatively
impact performance in such cases.

#### Pitfalls
- Successful implementation depends on correct use by the client code. Releasing objects back to pool can be
vital for correct working.
- The reusable object needs to take care of resetting its state in efficient way. Some objects may not be
suitable for pooling due to this requirement.
- Difficult to use in refactoring legacy code as the client code & reusable object both need to be aware
of object pool.
- You have to decide what happens when pool is empty and there is a demand for an object. You can either wait
for an object to become free or create a new object. Both options have issues. Waiting can have severe negative 
impact on performance.
- If you create new objects when code asks for an object and none are available then you have to do additional
work to maintain or trim the pool size or else you'll end up with very large pool.

#### In-A-Hurry Summary
- If cost of creating instances of a class is very high, and you need many such objects throughout your application
for short duration then you can pool them with object pool.
- Typically, objects that represent fixed external system resources like threads, connections or other system resources 
are good candidates for pooling.
- Objects to be pooled should provide a method to "reset" their state, so they can be reused. This operation
should be efficient as well, otherwise release operation will be costly.
- Pool must handle synchronization issues efficiently and reset object state before adding them to pool for reuse.
- Client code must release pooled objects back into the pool, so they can be reused. Failing to do so will break
the system. Thread pools can work around this since the thread can know when its work is done.
- Difficult to optimize as pools are sensitive to system load at runtime (demand of pooled objects).
- Pools are good choice when the pooled objects represent a fixed quantity of externally available resource like
thread or a connection.