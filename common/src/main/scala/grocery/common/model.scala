package grocery.common

/**
 * *NOTE Explain case classes
 *  - Creates a class and it's companion object
 *  - implements apply (factory creation without new!) and unapply (awesome for case matching) methods
 *  - prefixes all arguments with val
 *  - Adds natural implementations of hashCode, equals and toString
 *  - Creates a copy method with default arguments
 *  - Adds curried and tupled
 *  - productArity, productElement and productIterator (iterate through the arguments to this class!!!)
 */
case class Aisle(name: String)
case class GroceryItem(name: String,
  aisle: Aisle,
  qty: Int = 1,
  units: String = "ea",
  details: String = "",
  comment: String = "")
