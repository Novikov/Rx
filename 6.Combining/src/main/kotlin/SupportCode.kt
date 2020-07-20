fun exampleOf(description: String, action: () -> Unit) {
  println("\n--- Example of: $description ---")
  action()
}

fun stringFrom(minutes: Int): String {
  val hours = minutes / 60
  val min = minutes % 60
  return String.format("%d:%02d", hours, min)
}
