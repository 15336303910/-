package base.util;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class StringConverter implements JsonSerializer<String>, JsonDeserializer<String> {

	@Override
	public String deserialize(JsonElement json, Type type,
			JsonDeserializationContext context) throws JsonParseException {
		return json.getAsJsonPrimitive().getAsString(); 
	}

	@Override
	public JsonElement serialize(String str, Type type,
			JsonSerializationContext context) {
		/*if(TextUtil.isNotNull(str)){
			
		}else{
			return new JsonPrimitive(); 
		}*/
		return new JsonPrimitive(str.toString()); 
	}

}
