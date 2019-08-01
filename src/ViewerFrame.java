
import com.jogamp.opengl.util.FPSAnimator;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLJPanel;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class ViewerFrame extends JFrame implements KeyListener, ActionListener {
    // version ID for serialisation
    private static final long serialVersionUID = 1L;

    // jpanel, renderer and animator
    private GLJPanel panel;
    private ViewRenderer renderer;
    private FPSAnimator animator;

    // menu items
    private JMenuItem menuQuit;
    private JMenuItem menuReset;
    private JMenuItem menuAbout;
    private JMenuItem menuCredits;
    private JMenuItem menuRestart;
    private JMenuItem menuMusicStop;
    private JMenuItem menuMusicStart;
    private JMenuItem menuInstructions;

    // music
    private String musicPath = "Theme.wav";
    private AudioInputStream audioInputStream;
    private Clip clip;

    /**
     * main
     */
    public static void main(String[] args) {
        // ogl init
        GLProfile.initSingleton();
        // build this
        new ViewerFrame("The Deadly Labyrinth", GLProfile.getDefault());
    }

    /**
     * constructor
     */
    public ViewerFrame(String title, GLProfile profile) {
        super(title);

        // set and init stuff
        setSize(800, 600);
        setFocusable(true);
        addKeyListener(this);
        setLocationRelativeTo(null);
        setFocusTraversalKeysEnabled(false);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GLCapabilities caps = new GLCapabilities(profile);

        this.panel = new GLJPanel(caps);
        this.panel.setDoubleBuffered(true);
        this.panel.setIgnoreRepaint(true);

        this.renderer = new ViewRenderer(60);
        this.panel.addGLEventListener(this.renderer);
        this.animator = new FPSAnimator(this.panel, 60);
        getContentPane().add(this.panel);

        addMenus();
        setVisible(true);
        requestFocusInWindow();
        this.animator.start();

        try {
            // music stuff
            this.audioInputStream = AudioSystem.getAudioInputStream(this.getClass().getResource(this.musicPath));
            this.clip = AudioSystem.getClip();
            this.clip.open(this.audioInputStream);
        } catch (Exception e) {
        }
    }

    /**
     * adds menus to gui
     */
    private void addMenus() {
        // menu bar
        JMenuBar menuBar = new JMenuBar();

        // menus
        JMenu options = new JMenu("options");
        JMenu music = new JMenu("music");
        JMenu info = new JMenu("info");

        // add reset location
        this.menuReset = new JMenuItem("reset location");
        this.menuReset.addActionListener(this);
        options.add(this.menuReset);

        // add restart
        this.menuRestart = new JMenuItem("restart game");
        this.menuRestart.addActionListener(this);
        options.add(this.menuRestart);

        // add quit
        this.menuQuit = new JMenuItem("quit");
        this.menuQuit.addActionListener(this);
        options.add(this.menuQuit);

        // add instructions
        this.menuInstructions = new JMenuItem("instructions");
        this.menuInstructions.addActionListener(this);
        info.add(this.menuInstructions);

        // add about
        this.menuAbout = new JMenuItem("about");
        this.menuAbout.addActionListener(this);
        info.add(this.menuAbout);

        // add about
        this.menuCredits = new JMenuItem("credits");
        this.menuCredits.addActionListener(this);
        info.add(this.menuCredits);

        // add music start
        this.menuMusicStart = new JMenuItem("start music");
        this.menuMusicStart.addActionListener(this);
        music.add(this.menuMusicStart);

        // add music stop
        this.menuMusicStop = new JMenuItem("stop music");
        this.menuMusicStop.addActionListener(this);
        music.add(this.menuMusicStop);

        // add to menu bar and add menu bar
        menuBar.add(options);
        menuBar.add(music);
        menuBar.add(info);
        setJMenuBar(menuBar);
    }

    /**
     * key pressed
     */
    @Override
    public void keyPressed(KeyEvent key) {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_W:
                renderer.moveForward(true);
                break;
            case KeyEvent.VK_S:
                renderer.moveBackward(true);
                break;
            case KeyEvent.VK_A:
                renderer.moveLeft(true);
                break;
            case KeyEvent.VK_D:
                renderer.moveRight(true);
                break;
            // move camera
            case KeyEvent.VK_LEFT:
                renderer.turnLeft(true);
                break;
            case KeyEvent.VK_RIGHT:
                renderer.turnRight(true);
                break;
//            case KeyEvent.VK_UP:
//                renderer.turnUp(true);
//                break;
//            case KeyEvent.VK_DOWN:
//                renderer.turnDown(true);
//                break;
            // z axis
//            case KeyEvent.VK_Q:
//                renderer.turnZUp(true);
//                break;
//            case KeyEvent.VK_E:
//                renderer.turnZDown(true);
//                break;
            // jump
            case KeyEvent.VK_SPACE:
                renderer.jump();
                break;
            default:
                break;
        }
    }

    /**
     * key released
     */
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                renderer.moveForward(false);
                break;
            case KeyEvent.VK_S:
                renderer.moveBackward(false);
                break;
            case KeyEvent.VK_A:
                renderer.moveLeft(false);
                break;
            case KeyEvent.VK_D:
                renderer.moveRight(false);
                break;
            // move camera
            case KeyEvent.VK_LEFT:
                renderer.turnLeft(false);
                break;
            case KeyEvent.VK_RIGHT:
                renderer.turnRight(false);
                break;
//            case KeyEvent.VK_UP:
//                renderer.turnUp(false);
//                break;
//            case KeyEvent.VK_DOWN:
//                renderer.turnDown(false);
//                break;
            // z axis
//            case KeyEvent.VK_Q:
//                renderer.turnZUp(false);
//                break;
//            case KeyEvent.VK_E:
//                renderer.turnZDown(false);
//                break;
            default:
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * action performed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            // exit
            if (e.getSource().equals(this.menuQuit)) {
                JOptionPane.showMessageDialog(null, "See ya!");
                System.exit(0);

                // reset location
            } else if (e.getSource().equals(this.menuReset)) {
                this.renderer.reset();

                // restart game
            } else if (e.getSource().equals(this.menuRestart)) {
                this.renderer.restartGame();

                // instructions
            } else if (e.getSource().equals(this.menuInstructions)) {
                this.renderer.showGameDetails();

                // about TODO show about image
            } else if (e.getSource().equals(this.menuAbout)) {
                this.renderer.showGameDetails();

                // credits for oren, the music etc TODO
            } else if (e.getSource().equals(this.menuCredits)) {
                this.renderer.showGameDetails();

                // music start
            } else if (e.getSource().equals(this.menuMusicStart)) {
                this.clip.start();
                this.clip.loop(Clip.LOOP_CONTINUOUSLY);

                // music stop
            } else if (e.getSource().equals(this.menuMusicStop)) {
                this.clip.stop();// about TODO show about image
            }
        } catch (Exception ex) {
        }
    }
}
