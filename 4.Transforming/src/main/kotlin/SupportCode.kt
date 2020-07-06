import io.reactivex.subjects.BehaviorSubject

class Student(val score: BehaviorSubject<Int>)

fun exampleOf(description: String, action: () -> Unit) {
  println("\n--- Example of: $description ---")
  action()
}

fun String.romanNumeralIntValue(): Int {
  return when (this) {
    "I" -> 1
    "V" -> 5
    "X" -> 10
    "L" -> 50
    "C" -> 100
    "D" -> 500
    "M" -> 1000
    else -> -1
  }
}