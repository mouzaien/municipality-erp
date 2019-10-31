package com.bkeryah.managedBean;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Iterator;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.codehaus.plexus.util.IOUtil;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.bkeryah.service.IDataAccessService;

@ManagedBean
@ApplicationScoped
public class ImagesBean {
	
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;

	public StreamedContent getImage() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the view. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        }
        else {
            // So, browser is requesting the image. Return a real StreamedContent with the image bytes.
//            String id = context.getExternalContext().getRequestParameterMap().get("id");
        	
            byte[] myPicture = null;//dataAccessService.loadArcPeoplePic(2125472791L);//Integer.parseInt(id));
            
            InputStream inputStream = new ByteArrayInputStream(myPicture);
            
            byte[] pBlob = IOUtil.toByteArray(inputStream);
			Blob bild;
			try {
				bild = new SerialBlob(pBlob);
				InputStream stream = bild.getBinaryStream();
				
				
				
				byteToImage(myPicture, new File(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"\\myImages\\"));
				
				return new DefaultStreamedContent(stream, "image/jpg");
			} catch (SerialException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
			
//            return new DefaultStreamedContent(new ByteArrayInputStream(myPicture));
            
//                BinaryStreamImpl bs = new BinaryStreamImpl(myPicture);  
        //picture is byte[] property in user class      
                
                
                /*try {

        			InputStream inputStream = new ByteArrayInputStream(myPicture);
        			byte[] buffer = new byte[inputStream.available()];
        			inputStream.read(buffer);

        			File targetFile = new File("immmmg.jpeg");
        			OutputStream outStream = new FileOutputStream(targetFile);
        			outStream.write(myPicture);  		    
        		    
        			 return new DefaultStreamedContent(new ByteArrayInputStream(bFile));
        		} catch (Exception e) {
        			e.printStackTrace();
        		}*/
//                return new DefaultStreamedContent(bs.getInputStream(), "image/jpeg");
               
            }
 return null;
    }
	
	private void byteToImage(byte [] bytes,File imageFile) throws IOException{
	     
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        Iterator readers = ImageIO.getImageReadersByFormatName("jpg");
 
        ImageReader reader = (ImageReader) readers.next();
        Object source = bis; // File or InputStream, it seems file is OK
 
        ImageInputStream iis = ImageIO.createImageInputStream(source);

        reader.setInput(iis, true);
        ImageReadParam param = reader.getDefaultReadParam();
 
        Image image = reader.read(0, param);
        //got an image file
 
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
      
        Graphics2D g2 = bufferedImage.createGraphics();
        g2.drawImage(image, null, null);
 
        ImageIO.write(bufferedImage, "jpeg", imageFile);
        //"jpeg" is the format of the image
        //imageFile is the file to be written to.
 
        System.out.println(imageFile.getPath());
     
 
    }

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}
}
