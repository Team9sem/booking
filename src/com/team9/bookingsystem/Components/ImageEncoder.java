package com.team9.bookingsystem.Components;

import com.team9.bookingsystem.MysqlUtil;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * Created by pontuspohl on 25/11/15.
 * All Content by Pontus Pohl
 */
public class ImageEncoder {


    public static String encodeToString(BufferedImage image, String type) {
        String returnImageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, type, bos);

            byte[] imageBytes = bos.toByteArray();
            System.out.println(imageBytes.length);
            BASE64Encoder encoder = new BASE64Encoder();
            returnImageString = encoder.encode(imageBytes);


            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnImageString;
    }



    public static BufferedImage decodeString(String image){
            BufferedImage bufferedImage = null;
            byte[] imageBytes;
        try{
            BASE64Decoder base64Decoder = new BASE64Decoder();
            imageBytes = base64Decoder.decodeBuffer(image);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageBytes);
            bufferedImage = ImageIO.read(byteArrayInputStream);
            byteArrayInputStream.close();

        
        }catch(IOException e){
            e.printStackTrace();
        }
        return bufferedImage;
    }

    public static void main(String [] args){
        String path = System.getProperty("user.home") + File.separator + "Documents";


        path += File.separator + "testImage.jpg";
        int i = path.lastIndexOf('.');
        String extension = path.substring(i+1);
        System.out.println(extension);
        File img = new File(path);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();



        byte[] imageBytes = bos.toByteArray();

        try{
            BufferedImage bufferedImage = ImageIO.read(img);
            ImageIO.write(bufferedImage, extension, bos);
            byte[] byteArray = bos.toByteArray();
            System.out.println(byteArray.length);
            MysqlUtil util = new MysqlUtil();
//            util.uploadPicture(img);

        }catch(IOException e){
            e.printStackTrace();
        }

//        try{
//            BufferedImage bufferedImage = ImageIO.read(img);
//            System.out.println(bufferedImage.getHeight());
//            System.out.println(bufferedImage.toString());
//            String image = ImageEncoder.encodeToString(bufferedImage,extension);
//            if(image.isEmpty()){
//                System.out.println("is empty");
//            }
//            MysqlUtil util = new MysqlUtil();
//            util.uploadPicture(image);
//
//        }catch(IOException e){
//            e.printStackTrace();
//        }





    }

}
