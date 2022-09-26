#### Introduction
* We need to avoid coupling the code which sends request to the code which handles that request.
* Typically the code which wants some requested calls the exact method on an exact object to process it,thus the tight coupling. Chain of responsibility solves this problem by giving more than one object, chance to process the request.
* We create objects which are chained together by one object knowing reference of object which is next in chain.We give request to first object in chain, if it can't handle that it simply passes the request down the chain.