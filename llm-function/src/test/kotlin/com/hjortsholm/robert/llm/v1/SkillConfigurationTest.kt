package com.hjortsholm.robert.llm.v1

import com.hjortsholm.robert.llm.v1.skills.Skill


open class Example {
    fun foo(a: String, b: Int): String {
        return a + b
    }
}

class MockExample : Example() {

}

fun main() {
    val ex = MockExample()
    val foo = Skill(ex::foo)
    println(foo)
    println(foo.skill)
    println(foo.description)
    println(foo.invoke(mapOf("a" to "Hello", "b" to 42)))
}


//fun main() {
//    val ex = MockExample()
//    println(ex::class.supertypes.first())
//}


//fun main() {
//    val example = Example()
//    val kClass = Example::class
//
//    val foo = kClass.functions
//        .find { it.name == "foo" }!!
//    println(foo)
//    println(foo.parameters.map { it.name })
//    val result = foo.callBy(
//        mapOf(
//            foo.parameters[0] to example,
//            foo.parameters[1] to "Hello",
//            foo.parameters[2] to 42
//        )
//    )
//    println(result)
//}