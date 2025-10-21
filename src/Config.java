import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/*
Config.java : utility class meant to fullfill Requirement 1 in a cleaner way.
  * Reads key–value pairs from a MassiveMotion.txt file.
  * Store them internally in memory.
  * Provides a clean way for MassiveMotion.java to get these values
*/

public class Config {

    // Instance variable : holds all key=value pairs
    private final Properties props = new Properties();

    // Constructor : Loads file into Properties object 
    public Config (String filename) {
        // filInpStrm : short-hand for file input stream
        try (FileInputStream filInpStrm = new FileInputStream(filename)) {    
            props.load(filInpStrm); // Loading all key=value pairs
        } catch (IOException e) { 
            System.err.println("Error reading properties file: " + filename);
            e.printStackTrace();
        }   
    }

    // Getter Methods

    public String getString(String key, String defaultValue) {
        String value = props.getProperty(key);

        if (value != null) {
            value = value.trim(); // trim returns a new String
            if (value.isEmpty()) {
                return defaultValue;
            } else {
                return value;
            } 
        } else {
            return defaultValue;
        }
    }

    
    public int getInt(String key, int defaultValue) {
        String value = getString(key, String.valueOf(defaultValue));

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            System.err.println("Warning: Invalid integer for key '" + key + "': " + value);
            return defaultValue;
        }
    }

    public double getDouble(String key, double defaultValue) {
        String value = getString(key, String.valueOf(defaultValue));
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            System.err.println("Warning: Invalid double for key '" + key + "': " + value);
            return defaultValue;
        }
    }


    public int windowW() { 
        return getInt("window_size_x", 640); 
    }
    
    public int windowH() { 
        return getInt("window_size_y", 480); 
    }
    
    public String listType() { 
        return getString("list", "arraylist").toLowerCase(); 
    }

    public int starX() { 
        return getInt("star_position_x", windowW()/2); 
    }
    
    public int starY() { 
        return getInt("star_position_y", windowH()/2); 
    }
    
    public int starVx() { 
        return getInt("star_velocity_x", 0); 
    }
    
    public int starVy() { 
        return getInt("star_velocity_y", 0); 
    }

    public int bodySize() { 
        return getInt("body_size", 4); 
    }

    // This clamp method keeps probabilities in a legal range ( 0 - 1 )
    private static double clampRange01(double v) {
        if (v < 0) {
            return 0.0;
        }
        if (v > 1) {
            return 1.0;
        }
        return v;
    }

    public double genX() { 
        return clampRange01( getDouble("gen_x", 0.02) ); 
    }
    
    public double genY() { 
        return clampRange01( getDouble("gen_y", 0.02) ); 
    }

    // Math.max enforces a safe minimum for things that must be positive
    
    public int timerDelayMs() { 
        return Math.max(1, getInt("timer_delay", 16) ); 
    }

    public int starSize() { 
        return Math.max(1, getInt("star_size", 10) ); 
    }

    public int bodyVelRange() { 
        return Math.max(1, getInt("body_velocity", 5) ); 
    }
}