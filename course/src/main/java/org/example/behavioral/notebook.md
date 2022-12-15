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
#### Pitfalls
#### Summary

##

#### Implementation
#### Implementation & Design Considerations
#### Pitfalls
#### Summary

##

#### Implementation
#### Implementation & Design Considerations
#### Pitfalls
#### Summary

##

#### Implementation
#### Implementation & Design Considerations
#### Pitfalls
#### Summary

##

#### Implementation
#### Implementation & Design Considerations
#### Pitfalls
#### Summary

##

#### Implementation
#### Implementation & Design Considerations
#### Pitfalls
#### Summary

##

#### Implementation
#### Implementation & Design Considerations
#### Pitfalls
#### Summary