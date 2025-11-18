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

    /**
     * Constructor
     * Loads configuration values from the given file.
     *
     * @param filename path to the properties file (e.g. MassiveMotion.txt)
     * @throws RuntimeException if the file cannot be read
     */
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

    /**
     * Returns the string value associated with the given key, or the default
     * value if the key is missing.
     *
     * @param key configuration key
     * @param defaultValue value to use if key is not present
     * @return the resolved string value
     */
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

    /**
     * Returns the integer value associated with the given key, or the default
     * value if the key is missing or not a valid integer.
     *
     * @param key configuration key
     * @param defaultValue value to use if key is not present or invalid
     * @return parsed integer value
     */
    public int getInt(String key, int defaultValue) {
        String value = getString(key, String.valueOf(defaultValue));

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            System.err.println("Warning: Invalid integer for key '" + key + "': " + value);
            return defaultValue;
        }
    }


    /**
     * Returns the double value associated with the given key, or the default
     * if missing or invalid.
     *
     * @param key configuration key
     * @param defaultValue value to use if key is not present or invalid
     * @return parsed double value
     */
    public double getDouble(String key, double defaultValue) {
        String value = getString(key, String.valueOf(defaultValue));
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            System.err.println("Warning: Invalid double for key '" + key + "': " + value);
            return defaultValue;
        }
    }

    /** @return window width in pixels. */
    public int windowW() { 
        return getInt("window_size_x", 640); 
    }

    /** @return window height in pixels. */
    public int windowH() { 
        return getInt("window_size_y", 480); 
    }

    /** @return which list implementation to use (e.g. "array", "linked"). */
    public String listType() { 
        return getString("list", "arraylist").toLowerCase(); 
    }

    /** @return initial x-position of the central star. */
    public int starX() { 
        return getInt("star_position_x", windowW()/2); 
    }

    /** @return initial y-position of the central star. */
    public int starY() { 
        return getInt("star_position_y", windowH()/2); 
    }

    /** @return horizontal velocity of the star. */
    public int starVx() { 
        return getInt("star_velocity_x", 0); 
    }

    /** @return vertical velocity of the star. */
    public int starVy() { 
        return getInt("star_velocity_y", 0); 
    }

    /** @return size (radius or diameter) of secondary bodies. */
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
    
    /**
     * @return x-coordinate at which new bodies are generated
     *         (often randomized along the edge).
     */
    public double genX() { 
        return clampRange01( getDouble("gen_x", 0.02) ); 
    }
    
    /**
     * @return y-coordinate at which new bodies are generated.
     */
    public double genY() { 
        return clampRange01( getDouble("gen_y", 0.02) ); 
    }

    // Math.max enforces a safe minimum for things that must be positive
    
    /** @return timer delay in milliseconds between animation frames. */
    public int timerDelayMs() { 
        return Math.max(1, getInt("timer_delay", 16) ); 
    }

    /** @return size of the central star in pixels. */
    public int starSize() { 
        return Math.max(1, getInt("star_size", 10) ); 
    }

    /** @return range of velocities used for spawned bodies. */
    public int bodyVelRange() { 
        return Math.max(1, getInt("body_velocity", 5) ); 
    }
}