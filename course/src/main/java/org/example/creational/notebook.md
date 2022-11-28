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
- Some do not consider simple factory to be a "design pattern", as its simply a method that encapsulates 
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
    - Creator itself can be concrete if it can provide a default object or it can be abstract.
    - Implementations will override the method and return an object.

