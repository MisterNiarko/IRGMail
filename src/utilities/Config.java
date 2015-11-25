package utilities;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import utilities.Parser;

public class Config {
	private final Properties sessionProperties;
	private Map<Property,String> config;

	public Config(Path path) throws IOException{
		this.sessionProperties = new Properties();
		this.config = new HashMap<>();
		this.init(path);
	}

	private void init(Path path) throws IOException{
		this.config = Parser.getProperties(path);
		for(String s : config.values()){
			System.out.println(s);
		}

		this.sessionProperties.put("mail.store.protocol", this.config.get(Property.PROTOCOL));
	}

	public String getProperty(Property name){
		return this.config.get(name);
	}

	public Properties getSessionProperties() {
		return this.sessionProperties;
	}

}
