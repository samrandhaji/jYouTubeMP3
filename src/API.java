
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;


public class API {

    public static String getVideoID(String videoURL){
        if(videoURL.contains("watch?v=")){
        int index = videoURL.lastIndexOf("watch?v=") + 8;
        videoURL = videoURL.substring(index, index+11);
        return videoURL;
        } else {
            return null;
        }
    }
    
    public static URL getVideoURL(String videoID) throws Exception{
        String video = "http://www.youtubeinmp3.com/fetch/?format=text&video=http://www.youtube.com/watch?v=" + videoID;
        URL url = new URL(video);
        Scanner c = new Scanner(url.openStream());
        video = c.nextLine();
        if(video.equals("No video was found")){
            return null;
        } else {
            int index = video.lastIndexOf("Link: ") + 6;
            video = video.substring(index);
            url = new URL(video);
            return url;
        }
    }
    
    public static String getVideoTitle(String videoID) throws Exception{
        
        String info = "http://www.youtubeinmp3.com/fetch/?format=text&video=http://www.youtube.com/watch?v=" + videoID;
        URL url = new URL(info);
        Scanner c = new Scanner(url.openStream());
        info = c.nextLine();
        int index = info.indexOf("Title: ") + 7;
        int end = info.indexOf("<br") -1;
        try{                                        //
        info = info.substring(index,end);           //
        } catch(Exception ex){                      //  PATCHES
            info = info.substring(index,64);        //
        }                                           //
        return info;
        
    }
    
    public static int getFileSize(URL url) {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("HEAD");
            conn.getInputStream();
            return conn.getContentLength();
        } catch (IOException ex) {
            return -1;
        } finally {
            conn.disconnect();
        }
    }
    
}
