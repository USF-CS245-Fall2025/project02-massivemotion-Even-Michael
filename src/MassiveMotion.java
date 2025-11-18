import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class MassiveMotion extends JPanel implements ActionListener {

    protected Timer tm;
 
    
    private static class CelestialBody {
        double x, y;      // center position
        double vx, vy;    // velocity per tick
        int r;            // radius
        Color color;      // draw color

        CelestialBody(double x, double y, double vx, double vy, int r, Color c) {
            this.x = x;
            this.y = y;
            this.vx = vx;
            this.vy = vy;
            this.r = r;
            this.color = c;
        }
    }

    private List<CelestialBody> bodies;
    private final Random rng = new Random();


    // Holds all configuration values read from the properties file
    private Config cfg;


    public MassiveMotion(String propfile) {
        cfg = new Config(propfile);

        int width = cfg.windowW();
        int height = cfg.windowH();
        int delay = cfg.timerDelayMs();

        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        
        String listType = cfg.listType(); // "arraylist" | "single" | "double" | "dummyhead"

        if (listType.equals("arraylist")) {
            bodies = new ListImpl_ArrayList<>();
        } else if (listType.equals("single")) {
            bodies = new ListImpl_Linked<>();
        } else if (listType.equals("double")) {
            bodies = new ListImpl_Double<>();
        } else if (listType.equals("dummyhead")) {
            bodies = new ListImpl_DummyHead<>();
        } else {
            System.err.println("Unknown list type '" + listType + "', defaulting to ArrayList_ListImpl.");
            bodies = new ListImpl_ArrayList<>();
        }

        // Seeding the red star from config
        double sx  = cfg.starX();      // star_position_x
        double sy  = cfg.starY();      // star_position_y
        int    sr  = cfg.starSize();   // star_size
        double svx = cfg.starVx();     // star_velocity_x
        double svy = cfg.starVy();     // star_velocity_y
        bodies.add(0, new CelestialBody(sx, sy, svx, svy, sr, Color.RED));

            
        System.out.printf("Loaded: %dx%d window, %dms delay, using list=%s%n", width, height, delay, listType);

        tm = new Timer(delay, this); 
        tm.start();

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g); 
 
        for (int i = 0; i < bodies.size(); i++) {
            CelestialBody b = bodies.get(i);
            g.setColor(b.color);
            int d = b.r * 2;
            g.fillOval((int)(b.x - b.r), (int)(b.y - b.r), d, d);
        }

        // IMPORTANT: tm.start() no started here. Started in constructor.
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        int W = getWidth();
        int H = getHeight();

        // moving all bodies
        for (int i = 0; i < bodies.size(); i++) {
            CelestialBody b = bodies.get(i);
            b.x += b.vx;
            b.y += b.vy;
        }

        // Probabilistically spawning from top/bottom (gen_x) and left/right (gen_y)
        maybeSpawn(true,  cfg.genX(), W, H); // top/bottom
        maybeSpawn(false, cfg.genY(), W, H); // left/right

        // Removing off-screen bodies (from end to start)
        for (int i = bodies.size() - 1; i >= 0; i--) {
            CelestialBody b = bodies.get(i);
            if (isOffscreen(b, W, H)) {
                bodies.remove(i);
            }
        }

        // Redraw
        repaint();
    }


    // Helpers

    // Checks if the entire circle is outside the screen.
    private boolean isOffscreen(CelestialBody b, int W, int H) {
        double left   = b.x - b.r;
        double right  = b.x + b.r;
        double top    = b.y - b.r;
        double bottom = b.y + b.r;
        return right < 0 || left > W || bottom < 0 || top > H;
    }

    // Generates a non-zero random integer between -maxAbs and +maxAbs
    private int nonZeroRandSpeed(int maxAbs) {
        if (maxAbs <= 0) return 0;
        int v = 0;
        while (v == 0) {
            v = rng.nextInt(maxAbs * 2 + 1) - maxAbs; // [-maxAbs, +maxAbs]
        }
        return v;
    }

    // Probabilistically spawns one body on either the top/bottom (alongX=true) * or left/right (alongX=false) edge
    private void maybeSpawn(boolean alongX, double prob, int W, int H) {
        if (rng.nextDouble() >= prob) {
            return;
        }

        int r    = cfg.bodySize();
        int vmax = cfg.bodyVelRange();
        boolean fromMinSide = rng.nextBoolean(); // top/left if true, bottom/right if false

        // Random position inside visible span (so the whole circle is on-screen)
        double x, y;
        int vx = nonZeroRandSpeed(vmax);
        int vy = nonZeroRandSpeed(vmax);

        if (alongX) {
            // Spawn on top/bottom edge; push inward vertically
            if (fromMinSide) {
                y = r;          // top
            } else {
                y = H - r;      // bottom
            }

            x = r + rng.nextInt(Math.max(1, W - 2 * r));

            vy = Math.abs(vy);

            if (!fromMinSide) {
                vy = -vy;       // from bottom: push upward
            }
        } else {
            // Spawn on left/right edge; push inward horizontally
            if (fromMinSide) {
                x = r;          // left
            } else {
                x = W - r;      // right
            }
            y = r + rng.nextInt(Math.max(1, H - 2 * r));

            vx = Math.abs(vx);
            if (!fromMinSide) {
                vx = -vx;       // from right: push leftward
            }
        }

        Color c;
        if (alongX) {
            c = Color.WHITE;
        } else {
            c = Color.CYAN;
        }

        bodies.add(bodies.size(), new CelestialBody(x, y, vx, vy, r, c));
    }


    public static void main(String[] args) {
        System.out.println("Massive Motion starting...");
        if (args.length == 0) {
            System.err.println("Usage: java MassiveMotion <path-to-properties>");
            System.exit(1);
        }

        SwingUtilities.invokeLater(() -> {
            MassiveMotion mm = new MassiveMotion(args[0]);

            JFrame jf = new JFrame();
            jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            // jf.setResizable(false);
            jf.setTitle("Massive Motion");
            jf.setSize(mm.cfg.windowW(), mm.cfg.windowH()); 
            jf.add(mm);
            jf.setVisible(true);
        } );
    
    }
}
