
import com.jogamp.opengl.util.FPSAnimator;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLJPanel;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class ViewerFrame extends JFrame implements KeyListener, ActionListener {
    // version ID for serialisation
    private static final long serialVersionUID = 1L;

    // jpanel, renderer and animator
    private GLJPanel panel;
    private Renderer renderer;
    private FPSAnimator animator;

    // menu items
    private JMenuItem menuQuit;
    private JMenuItem menuReset;
    private JMenuItem menuAbout;
    private JMenuItem menuRestart;
    private JMenuItem menuMusicStop;
    private JMenuItem menuMusicStart;
    private JMenuItem menuMusicChoose;
    private JMenuItem menuInstructions;

    // music
    private String musicPath = "hp.wav";
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

        panel = new GLJPanel(caps);
        panel.setDoubleBuffered(true);
        panel.setIgnoreRepaint(true);

        renderer = new Renderer(60);
        panel.addGLEventListener(renderer);
        animator = new FPSAnimator(panel, 60);
        getContentPane().add(panel);

        addMenus();
        setVisible(true);
        requestFocusInWindow();
        animator.start();
        initMusic();
    }

    private void initMusic() {
        try {
            // music stuff
            audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource(musicPath));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
        }
    }

    /**
     * action performed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            // exit
            if (e.getSource().equals(menuQuit)) {
                JOptionPane.showMessageDialog(null, "See ya!");
                System.exit(0);

                // reset location
            } else if (e.getSource().equals(menuReset)) {
                renderer.reset();

                // restart game
            } else if (e.getSource().equals(menuRestart)) {
                renderer.restartGame();

                // instructions
            } else if (e.getSource().equals(menuInstructions)) {
                renderer.showGameDetails();

                // about
            } else if (e.getSource().equals(menuAbout)) {
                renderer.showAbout();

                // music start
            } else if (e.getSource().equals(menuMusicStart)) {
                initMusic();

                // music stop
            } else if (e.getSource().equals(menuMusicStop)) {
                if (clip != null) {
                    clip.stop();
                }

                // music choose
            } else if (e.getSource().equals(menuMusicChoose)) {
                if (clip != null) {
                    clip.stop();
                }
                FileDialog fd = new FileDialog(this, "choose wav file");
                fd.setVisible(true);
                String f = fd.getFile();
                if (f.endsWith(".wav")) {
                    musicPath = f;
                    initMusic();
                }
            }
        } catch (Exception ex) {
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
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
        menuReset = new JMenuItem("reset location");
        menuReset.addActionListener(this);
        options.add(menuReset);

        // add restart
        menuRestart = new JMenuItem("restart game");
        menuRestart.addActionListener(this);
        options.add(menuRestart);

        // add quit
        menuQuit = new JMenuItem("quit");
        menuQuit.addActionListener(this);
        options.add(menuQuit);

        // add instructions
        menuInstructions = new JMenuItem("instructions");
        menuInstructions.addActionListener(this);
        info.add(menuInstructions);

        // add about
        menuAbout = new JMenuItem("about");
        menuAbout.addActionListener(this);
        info.add(menuAbout);

        // add music start
        menuMusicStart = new JMenuItem("start music");
        menuMusicStart.addActionListener(this);
        music.add(menuMusicStart);

        // add music stop
        menuMusicStop = new JMenuItem("stop music");
        menuMusicStop.addActionListener(this);
        music.add(menuMusicStop);

        // add music choose
        menuMusicChoose = new JMenuItem("choose music");
        menuMusicChoose.addActionListener(this);
        music.add(menuMusicChoose);

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
            // move the player position on the z and x axis
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

            // move the camera
            case KeyEvent.VK_UP:
                renderer.turnUp(true);
                break;
            case KeyEvent.VK_DOWN:
                renderer.turnDown(true);
                break;
            case KeyEvent.VK_LEFT:
                renderer.turnLeft(true);
                break;
            case KeyEvent.VK_RIGHT:
                renderer.turnRight(true);
                break;

            // z axis
            case KeyEvent.VK_Q:
                renderer.turnZUp(true);
                break;
            case KeyEvent.VK_E:
                renderer.turnZDown(true);
                break;

            // instructions
            case KeyEvent.VK_F1:
                renderer.showGameDetails();
                break;
            // level 2
            case KeyEvent.VK_F2:
                renderer.moveToLevelTwo();
                break;
            // about
            case KeyEvent.VK_F3:
                renderer.showAbout();
                break;
            // quit
            case KeyEvent.VK_ESCAPE: {
                JOptionPane.showMessageDialog(null, "See ya!");
                System.exit(0);
                break;
            }
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
            // move the player position on the z and x axis
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

            // move the camera
            case KeyEvent.VK_UP:
                renderer.turnUp(false);
                break;
            case KeyEvent.VK_DOWN:
                renderer.turnDown(false);
                break;
            case KeyEvent.VK_LEFT:
                renderer.turnLeft(false);
                break;
            case KeyEvent.VK_RIGHT:
                renderer.turnRight(false);
                break;
            // turn z axis
            case KeyEvent.VK_Q:
                renderer.turnZUp(false);
                break;
            case KeyEvent.VK_E:
                renderer.turnZDown(false);
                break;
            default:
                break;
        }
    }
}
