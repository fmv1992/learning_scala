val firstArg = if (!args.isEmpty) args(0) else ""
val friend = firstArg match {
  case "salt" => "pepper"
  case "chips" => "salsa"
  case "eggs" => "bacon"
  case _ => "huh?"
}
println(friend)

for (a <- List(1, 2, 3)) {
  println(a)
  // a = 10
}
