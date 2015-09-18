import javax.imageio.ImageIO;
import javax.swing.*;

import org.json.*;
//import org.json.simple.*;
import org.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
public class GoogleImageSearch {
	
	
	// This will find something on google images based on the search parameters and grab the first image
	public static void main(String[] args) {
        try{
            URL url = new URL("https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=sen+triplets");
            URLConnection connection = url.openConnection();

            String line;
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while((line = reader.readLine()) != null) {
                builder.append(line);
            }

            JSONObject json = new JSONObject(builder.toString());
            String imageUrl = json.getJSONObject("responseData").getJSONArray("results").getJSONObject(0).getString("url");
            String imageDest = "test3.jpg";
            
            saveImage(imageUrl, imageDest);
            
//            BufferedImage image = ImageIO.read(new URL(imageUrl));
            
            BufferedImage image = ImageIO.read(new File("test3.jpg"));
            ImageIcon icon = new ImageIcon(image);
            JFrame frame=new JFrame();
          frame.setLayout(new FlowLayout());
          frame.setSize(200,300);
          JLabel lbl=new JLabel();
          lbl.setIcon(icon);
          frame.add(lbl);
          frame.setVisible(true);
          frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          
          // Delete the file when closing
          File file = new File(imageDest);
          file.deleteOnExit();
            
            
            JOptionPane.showMessageDialog(null, "", "", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(image));
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Failure", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    
    
    public static void saveImage(String imageUrl, String destinationFile) throws IOException {
	    URL url = new URL(imageUrl);
	    InputStream is = url.openStream();
	    OutputStream os = new FileOutputStream(destinationFile);

	    byte[] b = new byte[2048];
	    int length;

	    while ((length = is.read(b)) != -1) {
	        os.write(b, 0, length);
	    }

	    is.close();
	    os.close();
	}
	
	
}
