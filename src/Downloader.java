
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

public class Downloader extends JFrame{
    private int downloaded = 0;
    private JProgressBar downloading;
    private final int fileSize;
    private final URL url;
    private final File file;
    private boolean cancel;
    private final JButton canceling;
    private final JLabel size,fileName;
    
    {
        setResizable(false);
        Container c = getContentPane();
        c.setLayout(new GridLayout(4,2,10,10));
        downloading = new JProgressBar();
        downloading.setValue(0);
        c.add(new JLabel("File Name: "));
        c.add(fileName = new JLabel(""));
        c.add(new JLabel("File Size: "));
        c.add(size = new JLabel(""));
        c.add(new JLabel("Estimation: "));
        c.add(downloading);
        c.add((canceling = new JButton("Cancel")));
        canceling.addActionListener((ActionEvent e) -> {
                cancel = true;
                setVisible(false);
        });
        addWindowListener(new WindowListener(){
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {
                cancel=true;
                setVisible(false);
            }
            @Override
            public void windowClosed(WindowEvent e) {}
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        pack();
    }
    
    public Downloader(URL url,File file) throws Exception{
        
        setTitle("Downloading " + file.getName());
        fileSize = API.getFileSize(url);
        this.file = file;
        this.url = url;
        downloading.setMaximum(fileSize);        
        fileName.setText(file.getName());
        size.setText("" + (downloaded/1048576) + "," + ((downloaded/1024)%1024) + "MB / " + (fileSize/1048576) + "," + ((fileSize/1024)%1024) +  "MB");

        Thread thread = new Thread(() -> {
                BufferedInputStream in = null;
                FileOutputStream fout = null;
                try {
                    in = new BufferedInputStream(url.openStream());
                    fout = new FileOutputStream(file);
                    final byte data[] = new byte[1024];
                    int count;
                    while ((count = in.read(data, 0, 1024)) != -1) {
                        if(cancel){
                            break;
                        }
                        fout.write(data, 0, count);
                        downloaded += count;
                        downloading.setValue(downloaded);
                        size.setText((downloaded/1048576) + "," + ((downloaded/1024)%1024) + "MB / " + (fileSize/1048576) + "," + ((fileSize/1024)%1024) +  "MB");
                        size.repaint();
                    }
                    setVisible(false);
                    if(downloaded != fileSize){
                        if (in != null) {
                            in.close();
                        }
                        if (fout != null) {
                            fout.close();
                        }
                        file.delete();
                        if(JOptionPane.showConfirmDialog(this,
                        "There was an Error during video download\n"
                            + "Do you want to try again?",
                        "Video has been corrupted",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                            new Downloader(url,file);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this,
                        "The video downloaded successfully.\n"
                            + "File: " + file.getName() + "\n"
                            + "Size: " + ((downloaded/1024)/1024) + "," + ((downloaded/1024)%1024) +" MB" +"\n",
                        "File Download Successed.",
                        JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception ex) {
                } finally {
                    try{
                        if (in != null) {
                            in.close();
                        }
                        if (fout != null) {
                            fout.close();
                        }
                    }catch(Exception ex){}
                }
        });
        thread.start();
        show();
    }
    
    
}
