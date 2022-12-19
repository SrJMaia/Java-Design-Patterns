Behavioral patterns describe how classes and objects interact & communicate with each other

## Chain of Responsibility
- We need to avoid coupling the code which sends request to the code which handles that request.
- Typically the code which wants some request handled calls the exact method on an exact object to process it, thus
the tight coupling. Chain of responsibility solves this problem by giving more than one object, chance to process the
request.
- We create objects which are chained together by one object knowing reference of object which is next in chain. We give
request to first object in chain, if it can't handle that it simply passes teh request down the chain.

#### Implementation
- We start by defining handler interface/abstract class
    - Handler must define a method to accept incoming request
    - Handler can define method to access successor in chain. If it's an abstract class then we can even maintain successor
- Next we implement handler in one or more concrete handlers. Concrete handler should check if it can handle the request.
If not then it should pass request to next handler.
- We have to create our chain of objects next. We can do it in client. Typically in real world this job will be done by
some framework or initialization code written by you.
  - Client needs to know only the first object in chain. It'll pass on request to this object. 

#### Implementation & Design Considerations
- Prefer defining handler as interface as it allows you to implement chain of responsibility without worrying about
single inheritance rule of Java.
- Handlers can allow the request to propagate even if they handle the request. Servlet filter chains allow request
to flow to next filter even if they perform some action on request.
- Chain  can be described using XML or JSON as well so that you can add & remove handlers from chain without 
modifying code.
- Sometimes you can think of using existing connections or chains in objects. For example if you are using composite
pattern you already have a chain which can be used to implement this behavior.

#### Pitfalls
- There is no guarantee provided in the pattern that a request will be handled. Request can traverse whole chain
and fall of at the other end without ever being processed, and we don't know it.
- It is easy to misconfigure the chain when we are connecting successors. There is nothing in the pattern that will
let us know of any such problems. Some handlers may be left unconnected to chain.

#### Summary
- When we want to decouple sender of request from the object which handles teh request, we use chain of responsibility.
- We want this decoupling because we want to give multiple objects chance to handle the request & we don't know 
all objects beforehand.
- A handler checks if it can handle the request. If it can't then it'll pass the request on to next handler in chain.
- You can pass the request down the cian even fi a handler handles teh request. Design pattern doesn't prevent that
from happening.

## Command
- We want to represent a request or a method call as an object. Information about parameters passed and the actual
operation is encapsulated in a object called command.
- Advantage of command pattern is that, what would have been a method call is now an object which can be stored 
for alter execution or sent to other parts of code.
- We can now even queue our command objects and execute them later.

#### Implementation
- We start by writing command interface
  - It must define method which executes the command
- We next implement this interface in class for each request or operation type we want to implement. Command should also
allow for undo operation if your system needs it.
- Each concrete command knows exactly which operation it needs. All it needs is parameters for the operation if required
and the receiver instance on which operation is involved.
- Client creates the command instance and sets up receiver and all required parameters.
- The command instance is then ready to be sent to other parts of code. Invoker is the code that actually uses command
instance and invokes to execute on the command.

#### Implementation & Design Considerations
- You can support "undo" & "redo" in your commands. This makes them really useful for system with complex user
interactions like workflow designers.
- If your command is simple i.e. if it doesn't have undo feature, doesn't have any state & simply hides a particular
function & its arguments then you can reuse same command object for same type of request.
- For commands that are going to be queued for long durations, pay attention to size of state maintained by them. 
- Command can be inherited from other commands to reuse portions of code and build upon the base.
- You can also compose commands with other commands as well. These "macro" commands will have one or more sub-commands
executed in sequence to complete a request.
- For implementing undo feature in your command you can make use of memento design pattern, which allows command 
to store the state information of receiver without knowing about internal objects used by receiver.

#### Pitfalls
- Things get a bit controversial when it comes to returning values & error handling with command.
- Error handling is difficult ot implement without coupling the command with the client. In cases where client needs to
know a return value of execution it's the same situation.
- In code where invoker is running in a different thread, which is very common in situations where command pattern is
useful, error handling & return values get lot more complicated to handle.

#### Summary
- Command pattern allows you to treat requests for operations as objects. This allows you to send these objects
to different parts of code for later execution or to a different thread.
- Commands typically invoke the actual operation on a receiver but contain parameters or information needed for invocation.
- Client code is responsible for creating instances of command & providing it with receiver and request information.
- Commands can also implement an undo feature. here command itself stores a snapshot of receiver.

## Interpreter
- We use interpreter when want to process a simple "language" with rules or grammar.
  - E.g. File access requires user role and admin role.
- Interpreter allows us to represent the rules of language or grammar in a data structure and then interpret
sentences in that language.
- Each class in this pattern represents a rule in the language. Classes also provide a method to interpret
an expression.

#### Implementation
- We start by studying rules of the language for which we want to build interpreter
  - We define an abstract class or interface to represent an expression & define a method in it which
  interprets the expression.
  - Each rule in the class becomes an expression. Expressions which do not need other expressions to
  interpret become terminal expressions.
  - We then cr eate non-terminal expression classes which contain other expressions. These will in turn call
  interpret on children as well as perform interpretation of their own if needed.
- Building the abstract syntax tree using these classes can be done by client itself or we can create a
separate class to do it.
- Client will then use this tree to interpret a sentence.
- A context is passed to interpreter. It typically will have the sentence to be interpreted & optionally
it may also be used by interpreter to store any values which expressions need or modify or populate.

#### Implementation & Design Considerations
- Apart from interpreting expressions you can also do other things like pretty printing that use already
built interpreter in new way.
- You still hae to do the parsing. This pattern doesn't talk about how to actually parse the language &
build the abstract syntax tree.
- Context object can be used to store & access state of the interpreter.
- You can use visitor pattern to interpret instead of adding interpret method in expression classes.
Benefit of this is that if you are using multiple operations on the abstract syntax tree then visitor
allow you to put these operations in a separate class.
- You can also use flyweight pattern for terminal expressions. You'll often find that terminal expressions can be reused.

#### Pitfalls
- Class por rule can quickly result in large number of classes, even for moderately complex grammar.
- Not really suitable for languages with complex grammar rules.
- This design pattern is very specific to a particular kind of problem of interpreting language.

#### Summary
- When we want to parse a language with rules we can use the interpreter pattern.
- Each rule in the language becomes an expression class in the interpreter pattern. A terminal
expression provides implementation fo interpret method. A non-terminal expression holds other expressions
and calls interpret on its children.
- This pattern doesn't provide any solution for actual parsing and building of the abstract syntax tree.
We have to do it outside this pattern.

## Mediator
- Mediator encapsulates how a set of objects interact with each other. Due to this encapsulation there is
a loose coupling between the interacting objects.
- Typically, an object explicitly knows about other object to which it wants to interact i.e. to call a 
method. In mediator pattern this interaction is within the mediator object & interacting objects only know
about the mediator object.
- Benefit of this arrangement is that the interaction can now change without needing modifications to
participating objects. Changing the mediator allows to add/remove participants in an interaction.

#### Implementation
- We start by defining mediator
  - Mediators define a generic method which is called by other objects.
  - This method typically needs to know which object changed and optionally the exact property which
  has changed in that object.
  - We implement this method in which notify rest of the objects about the state change.
- Mediator needs to know about all participants in the collaboration it is mediating. To solve this problem
we can either have objects register with mediator or mediator itself can be the creator of these objects.
- Depending upon your particular implementation you may need to handle the infinite loop of change-notify-change which
can result if object's value change handler is called for every value change whether from an external source
as well as mediator. 

#### Implementation & Design Considerations
- It's important that mediator can identify which object has sent change notifications to avoid sending
that object the changed value again.
- If an object method took a very long time to process the change it can affect overall performance of
mediator severely. In fact this is a common problem in any notification system, so pay attention to
synchronization in mediator methods.
- We often end up with a complex mediator since it becomes a central point which ends up handling all routing
between objects. This can make it a very difficult to maintain the mediator as the complexity grows.
- We can extend a mediator and create variations to be used in different situation like platform dependent interacitons.
- Abstract mediator is often not required if the participating objects only work with that one mediator.
- We can use observer design pattern to implement the notification mechanism through which object notify
the mediator.

#### Pitfalls
- Mediator becomes a central control object. As complexity of interaction grows, mediator complexity can quickly get
out of hand.
- Making a reusable mediator, one which can be used with multiple sets of different objects is quite difficult. They
are typically very specific to the collaboration. Another competing pattern called Observer is much more reusable.

#### Summary
- When we want to decouple a group of objects which communicate with each other than we can use the
mediator design pattern.
- Each object only knows about the mediator object and notifies it about change in its state. Mediator in
turn will notify other objects on its behalf.
- Mediators are typically specific to a collaboration. It's difficult to write a reusable mediator. Observer
design pattern solves this problem. However mediators are easy to implement and extend.

## Iterator
- Iterator allows a way to access elements/children of an aggregate object
in sequence while hiding the actual internal data structure used.
- In java language iterators are integral part of collection frameworks, and they
are implementations of this design pattern.
- Iterators are stateful, meaning an iterator object remembers its position while
iterating.
- Iterators can become out of sync if the underlying collection is changed while a code is using iterator.

#### Implementation
- We start by defining iterator interface
  - Iterator has methods to check whether there is an element available in sequence & to get that element.
- We then implement the iterator in a class. This is typically an inner class in our concrete aggregate.
Making it an inner class makes it easy to access internal data structures.
- Concrete iterator needs to maintain state to tell its position in collection of aggregate. If the inner
collection changes it can throw an exception to indicate invalid state.

#### Implementation & Design Considerations
- Detecting change to underlying data structure while some code is using an iterator is important to
notify to the client because then our iterator may not work correctly.
- Having our iterator implementation as inner class makes it easy to access internal collection of aggregate objects.
- Always prefer iterator interface so you can change the implementation without affecting client.
- Iterators have many applications where a collection is not directly used, but we still want to give sequential access to information for example may be for reading lines from file, from entwork.

#### Pitfalls
- Access to index during iteration is not readily available like we have in a for loop.
- Making modifications to the collection while someone is using an iterator often makes that iterator
instance invalid as its state may not be valid.

#### Summary
- When we want to iterate or give sequential access to elements of aggregate object we can use iterator
design pattern.
- Iterator needs access to internal data structure of aggregator to provide its functionality. This usually
means it's quite common to have iterator implemented as inner class.
- Iterator allows the client code to check whether there is an element available to consume and give
next available element.
- We can also provide reverse, or bi-directional (forward + backward) iterator depending on underlying data structure.

## Memento
- When we want to store object's state without exposing internal details about the state then we can use
memento design pattern.
- The main intent behind saving state is often because we want to restore the object to a saved state.
- Using memento we can ask an object to give its state as a single, "sealed" object & store it for later use.
This object should not expose teh state for modification.
- This pattern is often combined with Command design pattern to provide undo functionality in application.

#### Implementation
- We start by finding originator state which is to be "stored" in memento.
- We then implement the memento with requirement that it can't be changed & read outside the originator.
- Originator provides a method to get its current snapshot out, which will return an instance of memento.
- Another method in originator takes a memento object as argument and the originator object resets itself to
match with the state stored in memento.

#### Implementation & Design Considerations
- It is important to keep an eye on the size of state stored in memento. A solution for discarding older state may be
needed to handle large memory consumption scenarios.
- Memento often ends up being an inner class due to the requirement that it must encapsulate all details of what
is stored in its instance.
- Resetting to previous state should consider effects on states of other objects/services.
- If there is definite, fixed way in which mementos are created then we can only store incremental state in mementos.
This is especially true if we are using command design pattern where every command stored a memento before execution.
- Mementos can be stored internally by originator as well but this complicates the originator. An external caretaker 
with fully encapsulated Memento provides you with more flexibility in implementation.
 
#### Pitfalls
- In practice creating a snapshot of state may not be easy if other objects are part of originator's state.
- Resetting a state may not be as simple as copying references. If a state change of originator is tied with other
parts of application then those parts may become out of sync/invalid due to resetting state.

#### Summary
- We cab use memento design pattern to take a snapshot of object's state which can be then used to restore object to that
particular state.
- Memento itself is created such that it doesn't expose any state stored in it to any other class aside from the originator.
- Originator provides a method to get a memento out of it. And another method to assign it a memento, which results in
getting the originator's state reset to the one in memento.
- Mementos need to be saved for them to be of any use. Originator can save them but it adds complexity.
- Memento works well with command pattern. Each commands saves a memento as part of execution.

## Observer
- Using observer design pattern we can notify multiple objects whenever an object changes state.
- This design pattern is also called as publisher-subscriber or pub-sub.
- We are defining one-to-many dependency between objects, where many objects are listening for state
change of a single object, without tightly coupling all of them together.
- This pattern is often implemented where listener only gets notification that "something" has changed in the 
object's state. Listeners query back to find out more information if needed. This makes it mroe generic as different
listeners may be interested in different states.

#### Implementation
- We define an interface for observer. Observer is usually a very simple interface and defines a method used
by "subject" to notify about state change.
- Subject can be an interface if we are expecting our observes to listen to multiple objects or else subject can be
any concrete class.
- Implementing subject means taking care of handling attach, detach of observers, notifying all registered observers &
providing a methods to provide state information requested by observers.
- Concrete observers use a reference passed to them to call "subject" for getting more information about the state.
If we are passing changed state in notify method then this is not required.

#### Implementation & Design Considerations
- In some rare scenarios you may end with a circular update loop. i.e. - an update to observable's state results in notification
being sent to a observer which then takes some action and that action results in state change of our observable,
triggering another notification and so on. Watch for these!
- An observer object can listen for changes in multiple subjects. It becomes quite easy to identify originator for
the notification if subjects pass a reference to themselves in notification to observer.
- Performance can become an issue if number of observers are higher and if one or many of them need noticeable time
to process notification. This can also cause pile up of pending notifications or missed notifications.
- To reduce number of notifications sent on each state update, we can also have observers register for a specific
property or event. This improves performance as on an event, subject notifies only the interested observers instead of all
registered observers.
- Typically, notification are sent by observable when someone changes its state, but we can also make the client code,
which is changing subject's state, send notification too. This way we get notification when all state changes are done.
However, client code get this additional responsibility which they may forget to carry out.

#### Pitfalls
- Every setter method triggering updates may be too much if we have client settign properties one after another
on our observable.
- Also each update becomes expensive as no. of observers increase and we have one or more "slow" observers in the list.
- If observers call back the subject to find what changed then this can add up to quite a bit of overhead.

#### Summary
- Observer pattern allows to define one-to-many dependency between objects where many objects are interested in state
change of a object. 
- Observers register themselves with the subject which then notifies all registered observers if any state change occurs.
- In the notification sent to observers it is common to only send reference of subject instead of state values. Observers will
call the subject back for more information if needed.
- We can also register observers for a specific event only, resulting in improved performance of sending notifications in the
subject.
- This design pattern is also known as publisher-subscriber pattern.Java messaging uses this pattern but istead of 
registering with subject, listeners register with a JMS broker, which acts a middleman.

## State
- State design pattern allows our objects to behave differently based on its current internal state.
- This pattern allows to define the state specific behaviors in separate classes.
- Operations defined in the class delegate to the current state object's implementation of that behavior.
- State transitions can be triggered by states themselves in which case each state knows about at least one other state's
existence.
- A benefit of this pattern is that new states and thus new behaviors can be added without changing our main class.

#### Implementation
- Identify distinct values for state of our object (context). Each state value will be a separate class
in our implementation. These classes will provide behavior specific to the state value they represent.
- In our main/context class method implementations we'll delegate the operation to current state object.
- We have to decide how our state transition is going to happen. States can themselves transition to next
state based on input received in a method. Other options in context itself can initiate transition.
- Client interacts with out main class or context and is unaware of existence of state.

#### Implementation & Design Considerations
- In some implementations clients themselves can configure context with initial state, However after that 
the state transition is handled either by states or context.
- If state transitions are done by state object itself then it has to know about at least one state. This adds
to the amount of code change needed when adding new states.
- Using flyweight pattern we can share states which do not have any instance variables and only encapsulate behaviour
specific to that state.
- State deign pattern is nto the same as a state machine. S state machine is loose terms focuses on state transitions
based on input values & using some table to map these inputs to states. A state design pattern focuses on providing
a behaviour specific to a state value of context object.

#### Pitfalls
- A lot more classes are created for providing functionality of context & all those need unit testing as well.
- State transitions can be a bit tricky to implement. This becomes more complicated if there multiple possible
states to which object can transition from current state. And if states are responsible for triggering transitions then
we have a lto more coupling between states.
- We may not realize all possible states we need at teh beginning of our design. As our design evolves we may need
to add more states to handle a particular behavior.

#### Summary
- If we have an object whose behavior is completely tied to its internal state which can be expressed as an
object we can use the state pattern.
- Each possible state value now becomes a class providing behavior specific to a state value.
- Our main object (aka context) delegates the actual operation to its current state. States will implement behavior
which is specific to a particular state value.
- Context object's state change is explicit now, since we change the entire state object.
- State transitions are handled either by states themselves or context can trigger them.
- We can reuse state objects if they don't have any instance variables and only provide behavior.

## Strategy
- Strategy pattern allows us to encapsulate an algorithm in a class. So now we can configure oru context or
main object with an object of this class, to change the algorithm used to perform given operation.
- This is really helpful if you have many possible variations of an algorithm.
- A good indication for applicability of strategy pattern is if we find different algorithms/behaviors in our
methods which are selected with conditional statements like if-else of witch-case.
- Strategy classes are usually implemented in an inheritance hierarchy so that we can choose any one implementation
and it'll work with our main object/context as the interface is same for all implementations.

#### Implementation
- We start by defining strategy interface which is used by our main/context class. Context class provides
strategy with all the data that it needs.
- We provide implementations for various algorithms by implementing strategy interface a class per algorithm.
- Our context class provides a way to configure it with one of the strategy implementations. Client code will create
context with one of the strategy object.

#### Implementation & Design Considerations
- We can implement our context in a way where strategy object is optional. This makes context usable for client codes
who do not want to deal with concrete strategy objects.
- Strategy objects should be given all data they need as arguments to its method. If number of arguments are high
then we can pass strategy an interface reference which it requires for data. Context object can implement this interface
and pass itself to strategy.
- Strategies typically end up being stateless objects making them perfect candidates for sharing between context
objects.
- Strategy implementations can make use of inheritance to factor out common parts of algorithms in base classes making
child implementation simpler.
- Since strategy objects often end up with no state of their own, we can use flyweight pattern to share them between
multiple context objects.

#### Pitfalls
- Since client code configure context object with appropriate strategy object, clients know about all implementations
of strategy, Introducing new algorithm means changing client code as well.

#### Summary
- Strategy pattern allows us to encapsulate algorithms in separate classes. The class using these algorithms 
(called context) can now be configured with desired implementation of an algorithm.
- It is typically the responsibility of client code which is using oru context object to configure it.
- Strategy objects are given all data they need by the context object. We can pass data either in form of
arguments or pass on context object itself.
- Strategy objects typically end up being stateless making them great candidates for flyweight pattern.
- Client code ends up knowing about all implementations of strategy since it has no create their objects.

## Template
- Using template method design pattern we define an algorithm in a method as a series of steps (method calls) and
provide a chance for subclasses to define or redefine some of these steps.
- The pattern works by defining abstract methods which then have to be implemented by concrete subclasses.
These methods are like hooks which are then called by template method.
- This pattern allows yo uto defer implementation of parts of your algorithm which can vary or change.
- Template methods are an example of inversion of control principle - Don't call us, we'll call you! And this if 
of course referring to the way template method calls other methods implemented in subclass.

#### Implementation
- We start by defining oru algorithm in template method. We try to break algorithm in multiple steps where each
step will become an abstract method.
- While breaking down algorithm the number of steps should not be too many or it can become quite tedious to
implement all of them in subclasses.
- Next we implement the abstract steps in one or more subclasses.

#### Implementation & Design Considerations
- A balance must be kept in how much granular we want to keep our steps. Too many steps means too many methods to override
for subclass where each one may be just a primitive operation. Too few steps on the other hand would mean
subclasses end up defining the major parts of algorithm.
- If needed the templated method can be made final to prevent subclasses for changing base algorithm.
- We can use inheritance within subclasses to reuse parts from already implemented steps. This approach
allows subclasses to only change steps they need.
- Factory method design pattern uses template method. Actual factory method is often called as part of another template
method.

#### Pitfalls
- Tracking down what code executed as part of our algorithm requires looking up multiple classes. The problem becomes
more apparent if subclasses themselves start using inheritance themselves to reuse only some of the existing steps
& customize a few.
- Unit testing can become a little more difficult as the individual steps may require some specific state 
values to be present.

#### Summary
- Template method allow us to define a skeleton of an algorithm in base class. Steps of algorithm are defined
as abstract methods in base class.
- Subclasses of our abstract class will provide implementation of steps. This way we can have different implementations
for same algorithm.
- Client will create object of any of the concrete subclasses and use the algorithm.
- Factory method design pattern is often implemented as part of template method design pattern.
- One drawback of template method is algorithm implementation is now spread across multiple classes so
it makes it slightly difficult to understand.

## Visitor
- Visitor pattern allows us to define new operations that can be performed on an object without changing the class
definition of the object. 
- Think of this pattern as an object ("visitor") that visits all nodes in an object structure. Each time our visitor
visits a particular object from the object structure, that object calls a specific method on visitor, passing itself
as an argument.
- Each time we need a new operation we create a subclass of visitor, implement the operation in that class and
visit the object structure.
- Objects themselves only implement an "accept" visit where the visitor is pass as an argument. Object know about the
method in visitor created specifically for it and invoke that method inside the accept method.

#### Implementation
- We create visitor interface by defining "visit" methods for each class we want to support.
- The classes who want functionalities provided by visitor define "accept" method which accepts a visitor.
These methods are defined using the visitor interface paramater type so that we can pass any class implementing
the visitor for these methods.
- In the accept method implementation we'll call a method on visitor which is defined specificalyl for that class.
- Next we implement the visitor interface in one or more classes. Each implementation provides a specific functionality
for interested classes. If want another feature we create new implementation of visitor.

#### Implementation & Design Considerations
- Visitor can work with objects of classes which do not have a common parent. So having a common interface for those
classes is optional. however the code which passes our visitor to these objects must be aware of these individual classes.
- Often visitors need access to internal state of objects to carry our their work. So we may have to expose the state
using getters/setters.
- One effect of this pattern is that related functionality if grouped in a single visitor class instead of spread
across multiple classes. So adding new functionality is as simple as adding a new visitor class.
- Visitors can also accumulate state. So along with behavior we can also have state per object in oru visitor. We don't
have to add new state to objects for behavior defined in visitor.
- Visitor can be used to add new functionality to object structure implemented using composite or can be used for
doing interpretation in interpreter design pattern.

#### Pitfalls
- Often visitors need access to object's state. So we end up exposing a lot of state through getter methods, weakening the
encapsulation.
- Supporting a new class in our visitors requires changes to all visitor implementations.
- If the classes themselves change then all visitors have to change as well since they have to work with changed class.
- A little confusing to understand and implement.

#### Summary
- Visitor pattern allows to add new operation that work on objects without modifying class definitions of these
objects.
- Visitors define class specific methods which work with an object of that class to provide new functionality.
- To use this pattern classes define a simple accept method which gets a reference to a visitor and inside this
method, objects class method on visitor which is defined for that specific class.
- Adding a new functionality means creating a new visitor and implementing new functionality in that class instead of
modifying each class where this functionality is needed.
- This pattern is often used where we have an object structure and then another class or visitor itself iterates over
this structure passing oru visitor object to each object.

## Null Object
- We use "null" value to represent an absence of object. Using "Null Object" pattern we can provide  an alternate
representation to indicate an absence of object.
- Most important characteristic of a null object is that it'll basically do nothing & store nothing when an operations
called on it.
- Null object seems like a proxy as it stands in for a real object, however a proxy at some point will use real object
or transform to a real object & e ven in absence of the real object proxy will provide some behaviour with side effect.
Null object will not do any such thing. Null objects don't transform into real objects.
- We use this pattern when we want to treat absence of a collaborator transparently without null checks.

#### Implementation
- We create a new class that represents our null object by extending from base class or implementing given interface.
- In the null object implementation, for each method we'll not do anything. however doing nothing can mean different things
in different implementations. E.g. if a method in a null object return something then we can either return another null object,
a predefined default value or null.

#### Implementation & Design Considerations
- Class which is using Null Object should no have todo anything special when working with this object.
- What "do nothing" means for an operation can be different in different classes. This is especially true
where methods in null objects are expected to return values.
- If you find a need where your null object has to transform into a real object then you better use something like
state pattern with a null object as one of the states.
- Since null objects don't have a state & no complex behavior they are good candidates for singleton pattern.
We can use a single instance of a null object everywhere.
- Null objects are useful in many other design patterns like state - to represent a null state, in strategy pattern
to provide a strategy where no action is taken on input.

#### Pitfalls
- Creating a proper Null object may not be possible for all classes. Some classes may be expected to cause a 
change, and absence of that change may cause other class operations to fail.
- Finding what "do nothing" means may not be easy or possible. If our null object method is expected to return
another object then this problem is more apparent.

#### Summary
- Null object pattern allows us to represent absence of real object as a do nothing object.
- Method implementations in a Null object will not do anything. In case a return value is expected, these methods
will return a sensible, hard-coded default value.
- Classes which use Null object won't be aware of presence of this special implementation. Whole purpose of the
pattern is to avoid null checks in other classes.
- Null objects do not transform into real objects, nor do they use indirection to real objects.