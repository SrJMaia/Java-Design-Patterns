Structural patterns deal with how classes and objects are arranged or composed

## Adapter
- We have an existing object which provides the functionality that client needs. But client code can't use
this object because it expects an object with different interface.
- Using adapter design pattern we make this exisitng object work with client by adapting the object to
client's expected interface.
- This pattern is also called as wrapper as it "wraps" existing objects.

#### Implementation
- We start by creating a class for Adapter
    - Adapter must implement the interface expected by client.
    - First we are going to try out a class adapter by also extending from our existing class.
    - In the class adapter implementation we're simply going to forward the method to another inherited
  from adapter.
    - Next for object adapter, we are only going to implement target interface and accept adapter as constructor
  argument in adapter i.e. make use of composition.
- An object adapter should take adapter as an argument in constructor or as a less preferred solution, you can
instantiate it in the constructor thus tightly coupling with a specific adapter.

#### Implementation Considerations
- How much work the adapter does depends upon the differences between target interface and object being
adapted. Iff method arguments are same or similar adapter has very less work to do.
- Using class adapter "allows" you to override some of the adapter's behaviour. But this has to be avoided
as you end up with adapter that behaves differently than adapter. Fixing defects is not easy anymore.
- Using object adapter allows you to potentially change the adapter object to one off its subclasses.

#### Design Considerations
- In java a "class adapter" may not be possible if both target and adapter are concrete classes. In such
cases the object adapter is the only solution. Also since there is no private inheritance in Java, it's better to
stick with object adapter.
- A class adapter is also called as a two-way adapter, since it can stand in for both the target interface
and for the adapter. That is we can use object of adapter where either target interface is expected is expected
as well as where an adapter object is expected.

#### Pitfalls
- Using target interface adn adapter class to extend our adapter we can create a "class adapter" in java.
However it creates an object which exposes unrelated methods in parts of your code, polluting it. Avoid
class adapters! It is mentioned here only for sake of completeness.
- It is tempting to do a lot of things in adapter besides simple interface translation. But this ca result
in an adapter showing a different behaviour than the adapted object.
- Not a lot other pitfalls! As long as we keep them true to their purpose of simple interface translation they are good.

#### In-A-Hurry Summary
- We have an existing object with required functionality btu the client code is expecting a different interface
than our object.
- A class adapter is one where adapter inherits from class of object which is to be adapted and implements
the interface required by the client code. This adapter type should be avoided.
- An object adapter uses composition. It'll implement the target interface and use an adapter object composition
to perform transaction. This allows us to use subclasses of adaptiv«e in 

## Bridge

- Our implementation & abstractions are generally coupled to each other in normal inheritance.
- Using bridge pattern we can decouple them so they can both change without affecting each other.
- We achieve this feat by creating two separate inheritance hierarchies; one for implementaiton
and another for abstraction.
- We use composition to bridge these two hierarchies.

#### Implementation
- We start by defining oru abstraction as needed by client.
  - We determine common base operations and define them in abstraction.
    - We can optionally also define a refined abstraction & provide more specialized operations.
    - Then we define our implementor next. Implementor methods do NOT have to match with abstractor. 
    However, abstraction can carry out its work by using implementor methods.
    - Then we write one or more concrete implementor providing implementation.
- Abstractions are created by composing them with an instance of concrete implementor which is
used by methods in abstraction.

#### Implementation Considerations
- In case we are ever going to have a single implementation when we can skip creating abstract implementor.
- Abstraction can decide on its own which concrete implementor to use in its constructor or we can delegate that decision
to a third class. In last approach abstraction remains unaware of concrete implementors & provides greater de-coupling.

#### Design Considerations
- Bridge provides great extensibility by allowing us to change abstraction and implementor independently.
You can build & package them separately to modularize overall system.
- By using abstract factory pattern to create abstraction objects with correct implementation you can
decouple concrete implementors from abstraction.

#### Pitfalls
- It is fairly complex to understand & implement bridge design pattern.
- You need to have a well thought out & fairly comprehensive design in front of you
before you can decide on bridge pattern.
- Needs to be designed up front. Adding bridge to legacy code is difficult. Event for ongoing project
adding bride at later in development may require fair amount of rework.

#### In-A-Hurry Summary
- We use bridge pattern when we want our abstractions and implementations to be decoupled.
- Bridge pattern defines separate inheritance hierarchies for abstraction & implementations and bridge these two together
using composition.
- Implementations do nto have to define methods that match up with methods in abstraction.
It is fairly common to have primitive methods; methods which do small work; in implementor. 
Abstraction uses these methods to provide its functionality.

## Decorator
- When we want to enhance behaviour of our existing object dynamically as and when
required then we can use decorator design pattern.
- Decorator wraps an object within itself and provides same interface as the wrapped object. So the client
of original object doesn't need to change.
- A decorator provides alternative to subclassing for extending functionality of existing classes.

#### Implementation
- We start with oru component.
  - Component defines interface needed or already used by client.
  - Concrete component implements the component.
  - We define our decorator. Decorator implements component & also needs reference
  to concrete component.
  - In decorator methods we provide additional behaviour on top that provided by concrete component instance.
- Decorator can be abstract as well & depend on subclasses to provided functionality.

#### Implementation Considerations
- Since we have decorators and concrete classes extending from common component, avoid large state in
this base class as decorators may not need all that state.
- Pay attention to equals and hashCode methods of decorator. When using decorators, you have to
decide if decorated object is equal to same instance without decorator.
- Decorators support recursive composition, and so this pattern lends itself to creation of lots
of small objects that add "just a little bit" functionality. Code using these objects become difficult to debug.
- Decorators are more flexible & powerful than inheritance. Inheritance is static by definition but
decorators allow you to dynamically compose behaviour using objects at runtime.
- Decorators should act like additional skin over your object. They should add helpful small behaviours
to object's original behaviour. Do not change meaning of operations.

#### Pitfalls
- Often results in large number of classes being added to system, where each class adds a small amount
of functionality. You often end up with lots of objects, one nested inside another and so on.
- Sometimes newcomers will start using it as a replacement of inheritance in every scenario. Think of decorators
as a thin skin over existing object.

#### In-A-Hurry Summary
- We use decorator when we want to add small behaviour on top of existing object.
- A decorator has same interface as the object it decorates or contains.
- Decorators allow you to dynamically construct behaviour by using composition. A decorator can wrap
another decorator which in turn wraps original object.
- Client of object is unaware of existence of decorator.

## Composite
- We have a part-whole relationship or hierarchy of objects and we want to be able to treat all objects
in this hierarchy uniformly.
- This is not a simple composition concept from object oriented programming but a further
enhancement to that principal.
- Think of composite pattern when dealing with tree structure of objects.

#### Implementation
- We start by creating an abstract class/interface for Component.
  - Component must declare all methods that are applicable to both leaf and composite.
  - We have to choose who defines the children management operations, component or composite.
  - Then we implement the composite. An operation invoked on composite is propagated to all its children.
  - In leaf nodes we have to handle the non applicable operations like add/remove a child if they are defined 
in component.
- In the end, a composite pattern implementation will allow you to write algorithms without worrying about
whether node is leaf or composite.

#### Implementation Considerations
- You can provide a method to access parent of a node. This will simplify
transversal of the entire tree.
- You can define the collection field to maintain children in base component instead
of a composite but again that field has no use in leaf class.
- If leaf objects can be repeated in the hierarchy then shared leaf nodes can be used
to save memory and initialization costs. But again the number of nodes is major deciding
factor as using a cache for small total number of nodes may cost more.

#### Design Considerations
- Decision needs to be made about where child management operations are defined.
Defining them on component provides transparency but leaf nodes are forced to implement those methods.
Defining them on composite is safer but client needs to be made aware of composite.
- Overall goal of design should be to make client code easier to implement when
using composite. This is possible if client code can work with component interface only
and doesn't need to worry about leaf-composite distinction.

#### Pitfalls
- Difficult to restrict what is added to hierarchy. If multiple types of leaf nodes are
present in system then client code ends up doing runtime checks to ensure the
operation is available on a node.
- Creating the original hierarchy can still be complex implementation especially if
you are using caching to reuse nodes and number of nodes are quite high.

#### In-A-Hurry Summary
- We have a parent-child or whole-part relation between objects. We can use composite pattern
to simplify dealing with such object arrangements.
- Goal fo composite pattern is to simplify the client code by allowing it to treat the composites
and leaf nodes in same way.
- Composites will delegate the operations to its children while leaf nodes implement the functionality.
- You have to decide which methods the base component will define. Adding all methods here will
allow client to treat all nodes same. But it may force classes to implement behaviour which they don't have.

## Facade
- Client has to interact with a large number of interfaces and classes in a subsystem
to get result. So client gets tightly coupled with those interfaces & classes. Façade solves this problem.
- Façade provides a simple and unified interface to a subsystem. Client interacts with just the façade
now to get same result.
- Façade is not just a one to tone method forwarding to other classes.

#### Implementation
- We start by creating a class that will serve as a façade
  - We determine the overall "use cases"/tasks that the subsystem is used for.
  - We write a method that exposes each "use case" or tasks.
  - This method takes care of working with different classes of subsystem.

#### Implementation Considerations
- A façade should minimize the complexity of subsystem and provide usable interface.
- You can have an interface or abstract class for façade and client can use different subclasses
to talk to different subsystem implementations.
- A façade is not replacement for regular usage of classes in subsystem. Those can be still
used outside of façade. Your subsystem class implementations should not make assumptions of usage
of façade by client code.

#### Design Considerations
- Façade is a great solution to simplify dependencies. It allows you to have a weak coupling between subsystem.
- If your only concern is coupling of client code to subsystem specific classes and not worried about
simplification provided by a façade, then you can use abstract factory pattern in place of façade.

#### Pitfalls
- Not a pitfall of the pattern itself but needing a façade in a new design should
warrant another look at API design.
- It is often overused or misused pattern & can hide improperly designed API. A common misuse is to
use them as "containers of related methods". So be on the lookout for such cases during code reviews.

#### In-A-Hurry Summary
- We use façade when using our subsystem requires dealing with lots of classes & interfaces
for client. Using façade we provide a simple interface which provides same functinality.
- Façade is not a simple method forwarding but façade methods encapsulate the subsyste
- 
class interactions which otherwise would have been done by client code.
- Façades are often added over existing legacy code to simplify code usage & reduce coupling
of client code to legacy code.

## Flyweight

#### Implementation
-
#### Implementation Considerations
- A factory is necessary with flyweight design pattern as client code needs easy way to get hold of shared flyweight.
Also number of shared instances can be large so a central place is good t o keep track of all
- Flyweight's  inttrisic state should be immutable for successful use of flyweight pattern.
#### Design Considerations

#### Pitfalls
- Runtime cost may be added for maintaining extrinsic state. Client code has to either maintain it or
comute it every time it needs to use flyweight.
- It is often difficult to find perfect candidate objects for flyweight. Graphical applications benefit
heavily from this pattern however a typical web application may not have a lot of use for this pattern.

#### In-A-Hurry Summary
- We use flyweight design pattern if we need a large number of objects of class where we can easily separate
out state that can be shared and state that can be externalized.
- Flyweights store only "intrinsic" state of state that can be shared in any context.
- Code using flyweight instance provides the extrinsic state when calling methods on flyweight.
Flyweight object then uses this state along with its inner state to carry out the work
- CLient code can store extrinsic per flyweight instance it uses or compute it on the fly.

## Proxy
- We need to provide a placeholder or surrogate to another object.
- Proxy acts on behalf of the object and is used for lots of reasons some of the main reasons are:
  - Protection Proxy: Control access to original object's operations
  - Remote Proxy - Provides a local representation of a remote object
  - Virtual Proxy - Delays construction of original object until absolutely necessary
- Client is unaware of existence of proxy. Proxy performs its work transparently.
- Lazy loading of collections by hibernate, APO based method level security, RMI/Web service subs
are examples of real life proxy usage.
 
#### Implementation
- We start by implementing proxy
  - Proxy must implement same interface as the real subject
  - We can either create actual object later when required or ask for one in constructor
  - In method implementations of proxy we implement proxy's functionality before delegating to real object.
- How to provide client with proxies instance in decided by application. We can provide a factory or 
compose client code with proxies instance.
- What we are implementing above is also called aas static proxy. Java also provides "dynamic proxies"

#### Implementation Considerations
- How proxy gets hold of the real object depends on what purpose proxy serves. For creation on demand
type of proxies, actual object is created only when proxy can't handle client request. Authentication
proxies use pre-built objects s othey are provided with object during construction of roxy,
- Proxy itself can maintain/cache state on behalf of real object in creation on demand use cases.
- Pay attention to performance cost of proxies as well synchronization issues added by proxy itself.

#### Design Considerations
- Proxies typically do not need to know about the actual concrete implementation of real object.
- With Java you can use dynamic proxy allowing you to create proxies for any object at runtime.
- Proxies are great for implementing security or as stand-ins for real objects which may be a costly object
that you want to defer loading. Proxies also make working with remote service/APIs easy by representing
them as regular objects and possibly handling network communications begind the scene. 

#### Pitfalls
- Java's dynamic proxy only works if your class is implementing one or more interfaces. Proxy is created by
implementing these interfaces.
- If you need proxie for handling multiple resposabilities like auditing, authentication, as a stand-in
for the same instance, then it's better to have a single proxy to handle all these requirements. Due to
the way some proxies create object on their own, it becomes quite difficult to manage them.
- Static proxies look quite similar toother patterns like decoretor & adapter patterns. It can be
confusing to figure it out from code alone for someone not familiar with all these patterns

#### In-A-Hurry Summary
- We want a stand in or placeholder object or we want control access to our objects method, then
we can use proxy pattern.
- Proxy implements same interface as expected of real object. It delegates actual functionality to
real object. Proxies are either given real object or they create one when needed. Some proxies talk 
to remote service behind teh scene.
- In Java we can also use dynamic proxies. These are created on the fly at runtime.