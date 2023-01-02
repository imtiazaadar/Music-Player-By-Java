import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
/*
 * Author : Imtiaz Adar
 * Date : 02/01/2023
 * Language : Java
 * Twitter : /@imtiazaadar
*/
public class MusicPlayer extends JFrame implements ActionListener {
    private Container c;
    private JButton chooseMusic, playMusic, pauseMusic, stopMusic, resetMusic;
    private JLabel intro, songName;
    private Font font1 = new Font("Segoe UL Black", Font.BOLD, 40);
    private Font font2 = new Font("Segoe UL Black", Font.PLAIN, 30);
    private Color color1 = new Color(102, 0, 51);
    private static Clip clip;
    public int i = 0, j = 0; float currentVolume = -17f, previousVolume = 0f;
    public boolean mute = false;
    public FloatControl fc;
    public static String path = "";
    private ImageIcon icon, muteI;
    private JButton loop, muteB;
    private JSlider slider, slider1;
    public MusicPlayer() {
        initComponents();
    }
    public void initComponents() {
        c = new Container();
        c = this.getContentPane();
        c.setLayout(null);
        c.setBackground(new Color(20, 20, 20));
        icon = new ImageIcon(getClass().getResource("music.png"));
        this.setIconImage(icon.getImage());
        //muteI = new ImageIcon(getClass().getResource("mute.png"));
        Cursor cursor1 = new Cursor(Cursor.HAND_CURSOR);
        intro = new JLabel("MUSIC PLAYER - IMTIAZ ADAR");
        intro.setBackground(Color.RED);
        intro.setForeground(Color.WHITE);
        intro.setBounds(45, 20, 600, 50);
        intro.setFont(font1);
        c.add(intro);
        songName = new JLabel();
        songName.setBackground(Color.RED);
        songName.setForeground(new Color(248, 255, 17));
        songName.setBounds(100, 370, 680, 40);
        songName.setFont(new Font("Segoe UL Black", Font.PLAIN, 20));
        c.add(songName);
        chooseMusic = new JButton("CHOOSE MUSIC");
        chooseMusic.setBounds(186, 130, 304, 40);
        chooseMusic.setBackground(color1);
        //chooseMusic.setToolTipText("CLICK TO CHOOSE MUSIC FROM PC");
        chooseMusic.setBorder(BorderFactory.createBevelBorder(2));
        chooseMusic.setBorderPainted(false);
        chooseMusic.setFocusPainted(false);
        chooseMusic.setForeground(Color.WHITE);
        chooseMusic.setFont(font2);
        chooseMusic.setCursor(cursor1);
        c.add(chooseMusic);
        playMusic = new JButton();
        playMusic.setText("PLAY");
        playMusic.setBounds(268, 250, 150, 30);
        playMusic.setBackground(new Color(102, 0, 102));
        playMusic.setForeground(Color.BLACK);
        //playMusic.setToolTipText("CLICK TO PLAY THE MUSIC");
        playMusic.setFont(new Font("Segoe UL Black", Font.PLAIN, 25));
        playMusic.setCursor(cursor1);
        playMusic.setBorderPainted(false);
        playMusic.setFocusPainted(false);
        c.add(playMusic);
        stopMusic = new JButton("STOP");
        stopMusic.setBounds(420, 250, 150, 30);
        stopMusic.setBackground(new Color(0, 102, 51));
        stopMusic.setForeground(Color.BLACK);
        //stopMusic.setToolTipText("CLICK TO STOP THE MUSIC");
        stopMusic.setFont(new Font("Segoe UL Black", Font.PLAIN, 25));
        stopMusic.setCursor(cursor1);
        stopMusic.setBorderPainted(false);
        stopMusic.setFocusPainted(false);
        c.add(stopMusic);
        resetMusic = new JButton("RESET");
        resetMusic.setBounds(116, 250, 150, 30);
        resetMusic.setBackground(new Color(0, 102, 102));
        resetMusic.setForeground(Color.BLACK);
       // resetMusic.setToolTipText("CLICK TO RESET THE MUSIC");
        resetMusic.setFont(new Font("Segoe UL Black", Font.PLAIN, 25));
        resetMusic.setCursor(cursor1);
        resetMusic.setBorderPainted(false);
        resetMusic.setFocusPainted(false);
        c.add(resetMusic);
        loop = new JButton("L");
        loop.setBounds(573, 300, 50, 30);
        loop.setBackground(new Color(255, 255, 255));
        loop.setForeground(Color.BLACK);
        loop.setFont(new Font("Segoe UL Black", Font.PLAIN, 15));
        loop.setCursor(cursor1);
        loop.setBorderPainted(false);
        loop.setFocusPainted(false);
        c.add(loop);
        muteB = new JButton("M");
        muteB.setBounds(521, 300, 50, 30);
        muteB.setBackground(new Color(255, 0, 0));
        muteB.setForeground(Color.BLACK);
        //muteB.setIcon(muteI);
        muteB.setFont(new Font("Segoe UL Black", Font.PLAIN, 15));
        muteB.setCursor(cursor1);
        muteB.setBorderPainted(false);
        muteB.setFocusPainted(false);
        c.add(muteB);
        slider = new JSlider(-40, 6);
        slider.setBounds(55, 305, 440, 20);
        slider.setMinorTickSpacing(10);
        slider.setBackground(new Color(20, 20, 20));
        slider.setPaintTrack(true);
        slider.setMajorTickSpacing(10);
        c.add(slider);
        chooseMusic.addActionListener(this);
        playMusic.addActionListener(this);
        stopMusic.addActionListener(this);
        resetMusic.addActionListener(this);
        muteB.addActionListener(this);
        loop.addActionListener(this);
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                currentVolume = slider.getValue();
                if(currentVolume == -40)
                    currentVolume = -80;
                System.out.println(slider.getValue());
                fc.setValue(currentVolume);
            }
        });
        chooseMusic.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                chooseMusic.setBackground(Color.WHITE);
                chooseMusic.setForeground(color1);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                chooseMusic.setBackground(Color.WHITE);
                chooseMusic.setForeground(color1);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                chooseMusic.setBackground(color1);
                chooseMusic.setForeground(Color.WHITE);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                chooseMusic.setBackground(Color.WHITE);
                chooseMusic.setForeground(color1);
            }
        });
        muteB.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                muteB.setBackground(Color.BLACK);
                muteB.setForeground(new Color(255, 0, 0));
            }
            @Override
            public void mousePressed(MouseEvent e){
                muteB.setBackground(Color.BLACK);
                muteB.setForeground(new Color(255, 0, 0));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                muteB.setBackground(new Color(255, 0, 0));
                muteB.setForeground(Color.BLACK);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                muteB.setBackground(Color.BLACK);
                muteB.setForeground(new Color(255, 0, 0));
            }
        });
        loop.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                loop.setBackground(Color.BLACK);
                loop.setForeground(new Color(255, 255, 255));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                loop.setBackground(new Color(255, 255, 255));
                loop.setForeground(Color.BLACK);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                loop.setBackground(Color.BLACK);
                loop.setForeground(new Color(255, 255, 255));
            }
        });
        playMusic.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                playMusic.setBackground(Color.BLACK);
                playMusic.setForeground(new Color(102, 0, 102));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                playMusic.setBackground(Color.BLACK);
                playMusic.setForeground(new Color(102, 0, 102));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                playMusic.setBackground(new Color(102, 0, 102));
                playMusic.setForeground(Color.BLACK);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                playMusic.setBackground(Color.BLACK);
                playMusic.setForeground(new Color(102, 0, 102));
            }
        });
        stopMusic.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                stopMusic.setBackground(Color.BLACK);
                stopMusic.setForeground(new Color(0, 102, 51));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                stopMusic.setBackground(Color.BLACK);
                stopMusic.setForeground(new Color(0, 102, 51));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                stopMusic.setBackground(new Color(0, 102, 51));
                stopMusic.setForeground(Color.BLACK);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                stopMusic.setBackground(Color.BLACK);
                stopMusic.setForeground(new Color(0, 102, 51));
            }
        });
        resetMusic.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                resetMusic.setBackground(Color.BLACK);
                resetMusic.setForeground(new Color(0, 102, 102));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                resetMusic.setBackground(Color.BLACK);
                resetMusic.setForeground(new Color(0, 102, 102));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                resetMusic.setBackground(new Color(0, 102, 102));
                resetMusic.setForeground(Color.BLACK);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                resetMusic.setBackground(Color.BLACK);
                resetMusic.setForeground(new Color(0, 102, 102));
            }
        });
        intro.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                intro.setForeground(new Color(248, 255, 17));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                intro.setForeground(Color.WHITE);
            }
        });
        songName.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                songName.setForeground(Color.WHITE);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                songName.setForeground(new Color(248, 255, 17));
            }
        });
    }
    public void audioSetup() throws Exception{
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(path));
        clip = AudioSystem.getClip();
        clip.open(audioStream);
        fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        fc.setValue(currentVolume);
    }
    public static void main(String[] args) throws Exception {
        MusicPlayer frame = new MusicPlayer();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //frame.setLayout(null);
        frame.setBounds(360, 70, 700, 500);
        frame.setTitle("MUSIC PLAYER BY IMTIAZ ADAR");
        frame.setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == chooseMusic){
            File file = new File(".");
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(file);
            FileNameExtensionFilter filter = new FileNameExtensionFilter(".wav files only", "wav");
            chooser.setFileFilter(filter);
            int result = chooser.showOpenDialog(null);
            if(result == JFileChooser.APPROVE_OPTION){
                File file1 = new File(chooser.getSelectedFile().getAbsolutePath());
                path = file1.getAbsolutePath();
                int startInd = path.lastIndexOf('\\');
                String songTitle = path.substring(startInd + 1, path.length());
                songName.setText("SONG : " + songTitle.toUpperCase());
            }
            System.out.println(path);
            try {
                audioSetup();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        if(e.getSource() == playMusic){
            i++;
            if(i % 2 != 0) {
                playMusic.setText("PAUSE");
                try {
                    //Thread.sleep(1000);
                    clip.start();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

            }
            else {
                playMusic.setText("PLAY");
                try {
                    //Thread.sleep(1000);
                    clip.stop();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        if(e.getSource() == resetMusic){
            if(clip.isRunning())
                playMusic.setText("PAUSE");
            clip.setMicrosecondPosition(0);
        }
        if(e.getSource() == stopMusic){
            playMusic.setText("PLAY");
            i = 0;
            clip.setMicrosecondPosition(0);
            clip.stop();
        }
        if(e.getSource() == muteB){
            if(mute == false){
                previousVolume = currentVolume;
                currentVolume = -80.0f;
                System.out.println("current vol : "+currentVolume);
                slider.setValue((int)currentVolume);
                fc.setValue(currentVolume);
                mute = true;
            }
            else if(mute == true){
                currentVolume = previousVolume;
                slider.setValue((int)currentVolume);
                fc.setValue(currentVolume);
                System.out.println("current vol : "+currentVolume);
                mute = false;
            }
        }
        if(e.getSource() == loop){
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }
}
