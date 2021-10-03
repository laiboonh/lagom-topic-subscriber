import com.example.helloworld.api.{GreetingMessage, Morning}
import play.api.libs.json.Json

val msg = GreetingMessage("Hi", Morning)

val json = Json.toJson(msg)

println(json)