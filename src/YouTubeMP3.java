
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.net.URL;
import javax.swing.JScrollPane;
import javax.swing.UnsupportedLookAndFeelException;


public class YouTubeMP3 extends JFrame {
 
    private final String version = "1.1b";
    private final BufferedImage icon,logo;
    private final JLabel videoTitle,videoLink,videoCapture;
    private final JTextField videoURL;
    private final JButton check,videoSave;
    private final JPanel searchPanel,videoInfoPanel;
    private String videoID;
    private final FileNameExtensionFilter extFilter = new FileNameExtensionFilter("Audio Files | *.mp3", "mp3");
    private int x;
    
    static {                                                                                //
        try {                                                                               //
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());            // 
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {                                                            //
            JOptionPane.showMessageDialog(null,                                             //
            "Unfurtunately, There was an error while getting video information.\n"          //  PATCHES
            + "Please contact me to fix this error!\n"                                      //
            + "Email: Samrand@engineer.com.\n"                                              //
            + "Please while Sending your email send this text bellow.\n"                    //
            + ex.toString() + "\n"                                                          //
            + "With BEST REGARDS,",                                                         //
            "Getting Core Component Error",                                                 //
            JOptionPane.ERROR_MESSAGE);                                                     //
        }
    }
    
    YouTubeMP3() throws Exception{
        setTitle("YouTubeMP3 - " + version);
        setResizable(false);
        setLocationRelativeTo(null);                                                        //  PATCHES
        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        icon = ImageIO.read(getClass().getResource("icon.png"));
        logo = ImageIO.read(getClass().getResource("logo.png"));
        setIconImage(icon);
        JLabel Logo = new JLabel(new ImageIcon(logo.getScaledInstance(200, 250, Image.SCALE_DEFAULT)));
        Logo.setHorizontalAlignment(JLabel.CENTER);
        c.add(Logo,BorderLayout.NORTH);
        
        searchPanel = new JPanel();
        
        searchPanel.setLayout(new FlowLayout());
        check = new JButton("Check Video");
        videoURL = new JTextField(20);
        searchPanel.add(videoURL);
        searchPanel.add(check);
        c.add(searchPanel,BorderLayout.CENTER);
        
        
        videoInfoPanel = new JPanel(new BorderLayout());
        JScrollPane videoInfoPane = new JScrollPane(videoInfoPanel);
        JPanel texts = new JPanel();
        texts.setLayout(new BoxLayout(texts,BoxLayout.Y_AXIS));
        JPanel links = new JPanel();
        links.setLayout(new BoxLayout(links,BoxLayout.Y_AXIS));
        texts.add(new JLabel("Video Title: "));
        links.add((videoTitle = new JLabel("")));
        videoTitle.setHorizontalAlignment(JLabel.LEFT);
        texts.add(new JLabel("Video Link: "));
        links.add((videoLink = new JLabel("")));
        texts.add(new JLabel("Video Link: "));
        links.add((videoCapture = new JLabel("")));
        videoLink.setHorizontalAlignment(JLabel.LEFT);
        videoInfoPanel.add(texts,BorderLayout.WEST);
        videoInfoPanel.add(links,BorderLayout.EAST);
        videoInfoPanel.add((videoSave = new JButton("Save")),BorderLayout.SOUTH);
        videoInfoPanel.setVisible(false);
        c.add(videoInfoPanel,BorderLayout.SOUTH);
        
        
        
        check.addActionListener((ActionEvent e) -> {
            if(videoURL.getText().equals(""))                       //
                JOptionPane.showMessageDialog(null,                 //
                        "Please, Enter a link to check it!",        //  PATCHES
                        "Youtube Link Error",                       //
                        JOptionPane.ERROR_MESSAGE);                 //

            else if((videoID = API.getVideoID(videoURL.getText())) == (null)){
                JOptionPane.showMessageDialog(null,
                        "Unfurtunately, We couldn't find the video that you entered",
                        "Youtube Link Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                try{
                    videoTitle.setText(API.getVideoTitle(videoID));
                    videoLink.setText("http://youtu.be/" + videoID);
                    x=0;
                    Thread thread = new Thread(() -> {
                         while(true){
                             if(x==4){
                                 try {
                                     videoCapture.setIcon(new ImageIcon(ImageIO.read(new URL("http://img.youtube.com/vi/"+  videoID +"/default.jpg")).getScaledInstance(150, 100, 1)));
                                     x = 0;
                                     pack();
                                 } catch (Exception ex) {
                                     JOptionPane.showMessageDialog(null,                                             //
                                        "Unfurtunately, There was an error while getting video capture.\n"          //  PATCHES
                                        + "Please contact me to fix this error!\n"                                      //
                                        + "Email: Samrand@engineer.com.\n"                                              //
                                        + "Please while Sending your email send this text bellow.\n"                    //
                                        + ex.toString() + "\n"                                                          //
                                        + "With BEST REGARDS,",                                                         //
                                        "Getting Capture Error",                                                 //
                                        JOptionPane.ERROR_MESSAGE);
                                 }
                             } else {
                                 try {
                                     videoCapture.setIcon(new ImageIcon(ImageIO.read(new URL("http://img.youtube.com/vi/"+  videoID +"/"+ x + ".jpg")).getScaledInstance(150, 100, 1)));
                                     x++;
                                     pack();
                                 } catch (Exception ex) {
                                     JOptionPane.showMessageDialog(null,                                             //
                                        "Unfurtunately, There was an error while getting video capture.\n"          //  PATCHES
                                        + "Please contact me to fix this error!\n"                                      //
                                        + "Email: Samrand@engineer.com.\n"                                              //
                                        + "Please while Sending your email send this text bellow.\n"                    //
                                        + ex.toString() + "\n"                                                          //
                                        + "With BEST REGARDS,",                                                         //
                                        "Getting Capture Error",                                                 //
                                        JOptionPane.ERROR_MESSAGE);
                                }
                             }
                             try {
                                    Thread.sleep(3000);
                                 } catch (InterruptedException ex) {
                                     JOptionPane.showMessageDialog(null,                                             //
                                        "Unfurtunately, There was an error while getting video capture.\n"          //  PATCHES
                                        + "Please contact me to fix this error!\n"                                      //
                                        + "Email: Samrand@engineer.com.\n"                                              //
                                        + "Please while Sending your email send this text bellow.\n"                    //
                                        + ex.toString() + "\n"                                                          //
                                        + "With BEST REGARDS,",                                                         //
                                        "Getting Capture Error",                                                 //
                                        JOptionPane.ERROR_MESSAGE);
                             }
                         }
                    });
                    thread.start();
                    videoInfoPanel.setVisible(true);
                    pack();
                } catch (Exception ex){
                    JOptionPane.showMessageDialog(null,
                            "Unfurtunately, There was an error while getting video information.\n"
                                    + "Please contact me to fix this error!\n"
                                    + "Email: Samrand@engineer.com.\n"
                                    + "Please while Sending your email send this text bellow.\n"
                                    + ex.toString() + "\n"
                                    + "With BEST REGARDS,",
                            "Getting Video Info. Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        videoSave.addActionListener((ActionEvent e) -> {
            JFileChooser saver = new JFileChooser();
            saver.setSelectedFile(new File(videoTitle.getText()));                                  //  PATCHES
            saver.setFileFilter(extFilter);
            int result = saver.showSaveDialog(null);
            if(result == JFileChooser.APPROVE_OPTION){
                if(!(saver.getSelectedFile().getName().endsWith(".mp3")))                           //  PATCHES
                    saver.setSelectedFile(new File(saver.getSelectedFile().getPath() + ".mp3"));    //
                try{
                    new Downloader(API.getVideoURL(videoID),saver.getSelectedFile());               //  PATCHES
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(null,
                            "Unfurtunately, There was an error due to download video.\n"
                                    + "Please contact me to fix this error!\n"
                                    + "Email: Samrand@engineer.com.\n"
                                    + "Please while Sending your email send this text bellow.\n"
                                    + ex.toString() + "\n"
                                    + "With BEST REGARDS,",
                            "Getting Video Info. Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else if(result == JFileChooser.ABORT){
                JOptionPane.showMessageDialog(null,
                        "You have canceled the video save operation.",
                        "Save has been abroted",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        show();
    }
    
    public static void main(String [] args) throws Exception{
        new YouTubeMP3();
    }
    
}
