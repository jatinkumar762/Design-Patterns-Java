#### Introduction
* We can notify multiple objects whenever an object changes state.
* This design pattern also known as publisher-subscriber or pub-sub
* We are defining one-to-maqny dependency between objects, where many objects are listening for state change of a single object, without tightly coupling all of them together.
* This pattern is often implemented where listener only gets notification that "something" has changed in the object's state. Listener query back to find out more information if needed. This makes it more generic as different listeners may be interested in different states.

#### Implementation Steps
* We define an interface for observer. Observer is usually a very simple interface and defines a method used by "subject" to notify about state change.
* Subject can be an interface if we are expecting our observers to listen to multiple objects or else subject can be any concrete class.
* Implementing subject means taking care of handling attach, detach of observers, notifying all registered observers & providing methods to provide state information requested by observers.
* Concrete observers uses a reference passed to them to call "subject" for getting more information about the state. If we are passing changed state in notify method then its not required.

#### Implementation Consideration
* In some rare scenarios you may end with a circular update loop. i.e. - an update to observable's state results in notification being sent to a observer which then takes some action and that action results in state change of our observable,triggering another notificat on. Watch for these!
* An observer object can listen for changes in multiple subjects. It becomes quite easy the notification if subjects pass a reference to themselves in notification to observer.
* Performance can become an issue if number of observers are higher and if one or many of them need noticeable time to process notification. This can also cause pile up of pending notifications or missed notifications.

#### Design Considerations
* To reduce number of notifications sent on each state update, we can also have observers register for a specific property or event. This improves performance as on an event, subject notifies only the interested observers instead of all registered observers. (Seprate list for each event)

#### Drawbacks
* Also each update becomes expensive as no. of observers increase and we have one or more "slow" observers in the list.
* If observers call back the subject to find what changed then this can add up to quite a bit of overhead.
