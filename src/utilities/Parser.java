package utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Parser {
	
	private static void addPropertyFromLine(Map<Property, String> properties, String line){
		String[] props = line.split(":");
		if(Property.contains(props[0])){
			System.out.println("here"+props);
			properties.put(Property.get(props[0]), props[1]);
		}
	}
	
	public static Map<Property,String> getProperties(Path path) throws IOException{
		final Map<Property, String> properties = new HashMap<>();
		try(Stream<String> lines = Files.lines(path);){
			lines.forEach(line -> {
				addPropertyFromLine(properties, line);
			});
		}
		System.out.println("LALLALA");
		for(String s : properties.values()){
			System.out.println(s);
		}
		System.out.println("TATATA");
		return properties;
	}
	
	
}
