package de.prosiebensat1digital.oasisjsbridge

import org.junit.Test
import kotlin.test.assertEquals

private fun String.removeWhiteSpaces() = this.replace(" ", "")

class JsonObjectWrapperTest {

    private val objectString =
        """{
          "key1": "value1",
          "key2": 2,
          "key3": ["one", 2]
        }"""

    private val keyValuePairs = arrayOf(
        "key1" to "value1",
        "key2" to 2,
        "key3" to JsonObjectWrapper(objectString),
        "key4" to true,
        "key5" to false,
        "key6" to JsonObjectWrapper.Undefined,
        "key7" to null
    )
    private val array = arrayOf(1, JsonObjectWrapper.Undefined, "three")

    @Test
    fun constructorWithUndefined() {
        val undefinedWrapper = JsonObjectWrapper(JsonObjectWrapper.Undefined)
        assertEquals(undefinedWrapper.jsonString, "")
    }

    @Test
    fun constructorWithJsonString() {
        val objectWrapper = JsonObjectWrapper(objectString)
        val undefinedWrapper = JsonObjectWrapper("")

        assertEquals(objectWrapper.jsonString, objectString)
        assertEquals(undefinedWrapper.jsonString, "")
    }

    @Test
    fun constructorWithPairs() {
        val wrapperFromPairs = JsonObjectWrapper(*keyValuePairs)

        val value3 = objectString.removeWhiteSpaces()
        assertEquals(wrapperFromPairs.jsonString.removeWhiteSpaces(), """{"key1":"value1","key2":2,"key3":$value3,"key4":true,"key5":false,"key7":null}""")
    }

    @Test
    fun constructorWithArrayItems() {
        val wrapperFromArrayItems = JsonObjectWrapper(array)

        assertEquals(wrapperFromArrayItems.jsonString.removeWhiteSpaces(), """[1,"three"]""")
    }

    @Test
    fun toJsString() {
        val undefinedWrapper = JsonObjectWrapper(JsonObjectWrapper.Undefined)
        val objectWrapper = JsonObjectWrapper(objectString)
        val pairsWrapper = JsonObjectWrapper(*keyValuePairs)
        val arrayWrapper = JsonObjectWrapper(array)

        assertEquals(undefinedWrapper.toJsString(), "undefined")
        assertEquals(objectWrapper.toJsString().removeWhiteSpaces(),
            """JSON.parse("{\"key1\":\"value1\",\"key2\":2,\"key3\":[\"one\",2]}")""")
        assertEquals(pairsWrapper.toJsString().removeWhiteSpaces(),
            """JSON.parse("{\"key1\":\"value1\",\"key2\":2,\"key3\":{\"key1\":\"value1\",\"key2\":2,\"key3\":[\"one\",2]},\"key4\":true,\"key5\":false,\"key7\":null}")""")
        assertEquals(arrayWrapper.toJsString().removeWhiteSpaces(),
            """JSON.parse("[1,\"three\"]")""")
    }
}
