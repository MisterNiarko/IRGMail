package utilities;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

public enum Property {
	ADDRESS,
	SERVER,
	PROTOCOL,
	LOGIN,
	PASSWORD;
	
	public String toString() {
		switch(this){
		case ADDRESS:
			return "ADDRESS";
		case SERVER:
			return "SERVER";
		case PROTOCOL:
			return "PROTOCOL";
		case LOGIN:
			return "LOGIN";
		case PASSWORD:
			return "PASSWORD";
		default:
			return null;
		}
	};
	
	static boolean contains(String property){
		Objects.requireNonNull(property);
		for(Property p : Property.values()){
			if(property.equals(p.toString())){
				return true;
			}
		}
		return false;
	}
	
	static Property get(String property){
		Objects.requireNonNull(property);
		for(Property p : Property.values()){
			if(property.equals(p.toString())){
				return p;
			}
		}
		throw new NoSuchElementException("This property does not exist");
	}
	
	/*static boolean checkMap(Map<Property,String> properties){
		for(Property)
	}*/
}
