/**
 * 
 */
package psd.parser;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import java.util.Map;
import javax.imageio.ImageIO;

import psdtool.TreeLayerModel;
import psd.model.Psd;
import psd.model.Layer;
import psd.parser.object.PsdDescriptor;
import psd.parser.object.PsdTextData;

import java.io.File;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import psd.parser.object.PsdEnum;
import psd.parser.object.PsdUnitFloat;


public class CreateAndroid {

    
   public static FileOutputStream out;
   public static FileOutputStream color;
   public static FileOutputStream string;
   public static int counter=0;
   
   public static String dir= System.getProperty("user.dir");
    
    public static void create(Psd psd) throws IOException{
        


File output= new File(dir+"/src/psd/parser/android/output.xml");
output.getParentFile().mkdirs();
output.createNewFile();
out=new FileOutputStream(output);

File colors= new File(dir+"/src/psd/parser/android/values/colors.xml");
colors.getParentFile().mkdirs();
colors.createNewFile();
color = new FileOutputStream(colors);

File strings= new File(dir+"/src/psd/parser/android/values/strings.xml");
strings.getParentFile().mkdirs();
strings.createNewFile();
string = new FileOutputStream(strings);

     
        
        TreeLayerModel tree = new TreeLayerModel();
        
        tree.setPsd(psd);

      try{        
 String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
         out.write(xml.getBytes());
         
        String scroll="<ScrollView xmlns:android=\"http://schemas.android.com/apk/res/android\"\n\tandroid:orientation=\"vertical\" android:layout_width=\"match_parent\"\n\tandroid:layout_height=\"match_parent\">\n";
         out.write(scroll.getBytes());
         String rl="<RelativeLayout android:orientation=\"vertical\" android:layout_width=\"match_parent\"\n\tandroid:layout_height=\"match_parent\">\n";
         out.write(rl.getBytes());
         
          String colorstart="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +"<resources>\n";     
          color.write(colorstart.getBytes());
          string.write(colorstart.getBytes());
          
         
          
                 
         
      }catch(IOException e){
      }      
  
        struct(tree, tree.getRoot(),tree.getChildCount(tree.getRoot()));
        
        
        
        
        String endrl="</RelativeLayout>";
       String endscroll="</ScrollView>";
        out.write(endrl.getBytes());
        out.write(endscroll.getBytes());
        
        out.close();
        
        String endcolor="</resources>";
        color.write(endcolor.getBytes());
        color.close();
        
        string.write(endcolor.getBytes());
        string.close();
        
       

}
    public static void struct(TreeLayerModel m, Object k,int c) throws IOException{
        if(c>0){
            
            for(int i=0;i<c;i++){

        struct(m,m.getChild(k, i),m.getChildCount(m.getChild(k, i)));
        
            }
             System.out.println("endofnode");
        }
        else{
            PsdDescriptor x= ((Layer)k).getProperties();

            PsdDescriptor d = ((Layer)k).geteffects();
            if(d!=null)
            {
               PsdDescriptor grfl = ((PsdDescriptor)d.getObjects().get("GrFl"));
              
               Double ang = null;
               String type = null;
               if(grfl!=null){
                  ang= getGradientAngle(grfl);
                  type = getGradientType(grfl);
               }
               
                System.out.println(ang+type);
            }
               
            PsdDescriptor vogk = ((Layer)k).getvogk();
            System.out.println(vogk);
               if(x!=null){
                  
                  Map properties1 =x.getObjects();
                   PsdTextData y= (PsdTextData)properties1.get("EngineData");
                   Map<String,Object> properties = y.getProperties();
      
                  String FontText= (properties1.get("Txt")).toString();
                  
                  String FontName = getFontName(properties);
                   Double FontSize = getFontSize(properties);
                   Integer FontSize1=FontSize.intValue();
                   ArrayList<Double> FontColor = getFontColor(properties);
                
       
                              float y1 = ((Layer)k).getX();
                              int y2 =(int) (y1/2.15f);
                              System.out.println(y2);
                              float y3 = ((Layer)k).getY();
                              int y4 =(int) (y3/2.15f);
                              System.out.println(y2);
                              float y5 = ((Layer)k).getWidth();
                              int y6 =(int) (y5/2.15f);
                              System.out.println(y2);
                              float y7 = ((Layer)k).getHeight();
                              int y8 =(int) (y7/2.15f);
                              System.out.println(y2);
                              
          int flag= check(k,FontText,y2,y4,y6,y8);                   
           if(flag!=0)                  
           createTextLayer(FontText,k,FontName,FontSize1,FontColor,y2,y4,y6,y8);
           
               //   System.out.println(properties1);
                 
               }
               else if(((Layer)k).getChannelsCount()==4){
         String name =((Layer)k).getName();
   
         String name1=name;          
        
         String reg = "[^a-zA-Z0-9]";
          
          if(Character.isDigit(name.charAt(0))){
           name1="_"+name;
           }
        
         name1=name1.replaceAll(reg, "_");
                   
                   
                  
          float y1 = ((Layer)k).getX();
          int y2 =(int) (y1/2.15f);
          System.out.println(y2);
          float y3 = ((Layer)k).getY();
           int y4 =(int) (y3/2.15f);
          System.out.println(y2);
          float y5 = ((Layer)k).getWidth();
          int y6 =(int) (y5/2.15f);
          System.out.println(y2);
          float y7 = ((Layer)k).getHeight();
          int y8 =(int) (y7/2.15f);
          System.out.println(y2);
          int flag= check1(k,y2,y4,y6,y8);                   
            if(flag!=0)                    
                   createImageLayer(k,name1,y2,y4,y6,y8);
                       System.out.println("image"); 
               }
               else{
                   String name =((Layer)k).getName();
   
           String name1=name;          
        
          String reg = "[^a-zA-Z0-9]";
          
          if(Character.isDigit(name.charAt(0))){
           name1="_"+name;
           
          }
        
         name1=name1.replaceAll(reg, "_");
                   
                   
                  
           
                      float y1 = ((Layer)k).getX();
                              int y2 =(int) (y1/2.15f);
                              System.out.println(y2);
                              float y3 = ((Layer)k).getY();
                              int y4 =(int) (y3/2.15f);
                              System.out.println(y2);
                              float y5 = ((Layer)k).getWidth();
                              int y6 =(int) (y5/2.15f);
                              System.out.println(y2);
                              float y7 = ((Layer)k).getHeight();
                              int y8 =(int) (y7/2.15f);
                              System.out.println(y2);
                 int flag= check1(k,y2,y4,y6,y8);                   
                if(flag!=0) 
                   createImageLayer1(k,name1,y2,y4,y6,y8);
           

               }
            System.out.println("end");
        }
        
    
  }   
    
    
     public static String getFontName(Map<String,Object> properties){
   
               return (String)((Map)((ArrayList)((Map)(properties.get("ResourceDict"))).get("FontSet")).get(0)).get("Name");
        }
       public static Double getFontSize(Map<String,Object> properties){
    return ((Double)( (Map)( (Map)( (Map)( (ArrayList)( (Map)((Map)(properties.get("EngineDict"))).get("StyleRun") ).get("RunArray") ).get(0) ).get("StyleSheet") ).get("StyleSheetData") ).get("FontSize")  );
        }
       public static ArrayList<Double> getFontColor(Map<String,Object> properties){
            ArrayList<Double> x=(( (ArrayList)((Map)( (Map)( (Map)( (Map)( (ArrayList)( (Map)((Map)(properties.get("EngineDict"))).get("StyleRun") ).get("RunArray") ).get(0) ).get("StyleSheet") ).get("StyleSheetData") ).get("FillColor")).get("Values")) );
           if(x.isEmpty()){return x;}
           Double e = x.remove(0);
           x.add(x.size(),e);
            
            return x;
        }

    public static void createRelativeLayout(Layer k, int x, int y)
    {
        String name =k.getName();
        String name1=name;
        String reg = "[^a-zA-Z0-9]";
        if(Character.isDigit(name.charAt(0))){
           name1="_"+name;
           
          }
        name1=name1.replaceAll(reg, "_");
        
         
        try {
            out.write(("<RelativeLayout "+"android:id= "+"\""+"@+id/"+name1.toLowerCase()+"_"+counter+"\"\n\t").getBytes());
           out.write(("android:layout_marginStart= "+"\""+x+"dp"+"\"\n\t").getBytes());
            out.write(("android:layout_marginTop= "+"\""+y+"dp"+"\"\n\t").getBytes());
            out.write(("android:layout_width= "+"\""+"wrap_content"+"\"\n\t").getBytes());
            out.write(("android:layout_height= "+"\""+"wrap_content"+"\"\n\t").getBytes());
            out.write((">\n\t").getBytes());
            
            
            
            
        } catch (IOException ex) {
            Logger.getLogger(CreateAndroid.class.getName()).log(Level.SEVERE, null, ex);
        }
        counter++;
    }
            

    public static void createTextLayer(String FontText,Object k, String FontName, Integer FontSize, ArrayList<Double> FontColor,int x,int y,int width,int height) {
        
        try {
                    String name =((Layer)k).getName();
   
           String name1=name; 
          
        
          String reg = "[^a-zA-Z0-9]";
          
          if(Character.isDigit(name.charAt(0))){
           name1="_"+name;
           
          }
        
         name1=name1.replaceAll(reg, "_");
          String name2=name1;
         name1+="_color";
         Color color1;
            color1 = new Color(FontColor.get(0).floatValue(),FontColor.get(1).floatValue(), FontColor.get(2).floatValue());
            System.out.println(color1);
           String hex = (Integer.toHexString(color1.getRGB() & 0xffffff)).toUpperCase();
           hex="#"+hex;
           System.out.println("COLORCOLOR"+hex+"   "+name2.toLowerCase());
            color.write(("<color name="+"\""+name2.toLowerCase()+"_"+counter+"_color"+"\""+">"+hex+"</color>\n").getBytes());
            string.write(("<string name="+"\""+name2.toLowerCase()+"_"+counter+"_string"+"\""+">"+FontText+"</string>\n").getBytes());

         
            out.write(("<TextView "+"android:id= "+"\""+"@+id/"+name2.toLowerCase()+"_"+counter+"\"\n\t").getBytes());
            out.write(("android:text= "+"\""+"@string/"+name2.toLowerCase()+"_"+counter+"_string"+"\"\n\t").getBytes());
            out.write(("android:fontFamily= "+"\""+FontName+"\"\n\t").getBytes());
            out.write(("android:textSize="+"\""+((FontSize/3)+2)+"sp"+"\"\n\t").getBytes());
            out.write(("android:layout_marginStart= "+"\""+x+"dp"+"\"\n\t").getBytes());
            out.write(("android:layout_marginTop= "+"\""+y+"dp"+"\"\n\t").getBytes());
            out.write(("android:layout_width= "+"\""+width+"dp"+"\"\n\t").getBytes());
            out.write(("android:layout_height= "+"\""+"wrap_content"+"\"\n\t").getBytes());
            out.write(("android:textColor= "+"\""+"@color/"+name2.toLowerCase()+"_"+counter+"_color"+"\"").getBytes());



            out.write(("/>\n").getBytes());
            

            
            counter++;
          
            
        } catch (IOException ex) {
           
        }


        
    }
    
    
    private static void createImageLayer(Object k,String name,int x,int y,int width,int height) throws IOException {
         BufferedImage image = ((Layer)k).getImage();
                   File srcfolder=new File(dir+"/src/psd/parser/android/drawable/"+name.toLowerCase()+"_"+counter+".png");
                   srcfolder.mkdirs();
                   ImageIO.write(image, "png", srcfolder);
                   
                    out.write(("<ImageView "+"android:id= "+"\""+"@+id/"+name.toLowerCase()+"_"+counter+"\"\n\t").getBytes());
                    out.write(("android:src= "+"\""+"@drawable/"+name.toLowerCase()+"_"+counter+"\"\n\t").getBytes());
                    out.write(("android:layout_marginStart= "+"\""+x+"dp"+"\"\n\t").getBytes());
                    out.write(("android:layout_marginTop= "+"\""+y+"dp"+"\"\n\t").getBytes());
                    out.write(("android:layout_width= "+"\""+width+"dp"+"\"\n\t").getBytes());
                    out.write(("android:layout_height= "+"\""+height+"dp"+"\"\n\t").getBytes());
                    

                    out.write(("/>\n").getBytes());

           
           counter++;
           
        //   System.out.println(srcfolder+ name+ ".png");
    }



    private static void createImageLayer1(Object k,String name1, int i, int y2, int i0, int i1) {
 
        try {
             BufferedImage image = ((Layer)k).getImage();
                   File srcfolder=new File(dir+"/src/psd/parser/android/drawable/"+name1.toLowerCase()+"_"+counter+".png");
                   srcfolder.mkdirs();
                   ImageIO.write(image, "png", srcfolder);
                   
                   
            out.write(("<View "+"android:id= "+"\""+"@+id/"+name1.toLowerCase()+"_"+counter+"\"\n\t").getBytes());
            out.write(("android:background= "+"\""+"@drawable/"+name1.toLowerCase()+"_"+counter+"\"\n\t").getBytes());
             out.write(("android:layout_marginStart= "+"\""+i+"dp"+"\"\n\t").getBytes());
                    out.write(("android:layout_marginTop= "+"\""+y2+"dp"+"\"\n\t").getBytes());
                    out.write(("android:layout_width= "+"\""+i0+"dp"+"\"\n\t").getBytes());
                    out.write(("android:layout_height= "+"\""+i1+"dp"+"\"\n\t").getBytes());
                    

                    out.write(("/>\n").getBytes());
        } catch (IOException ex) {
            Logger.getLogger(CreateAndroid.class.getName()).log(Level.SEVERE, null, ex);
        }
                   counter++;

    }

    private static int check(Object k, String FontText, int x, int y, int width, int height) {
        String name = ((Layer)k).getName();
        if(name.equals("Button")){

            createButtonLayer(k, FontText, x,  y, width, height);
            return 0;
        }
        return 1;
    }
    
    private static int check1(Object k,  int x, int y, int width, int height) throws IOException {
        String name = ((Layer)k).getName();
        if(name.equals("Button")){

            createButtonLayer1(k, name, x,  y, width, height);
            return 0;
        }
          if(name.equals("EditText")){

            createEditTextLayer1(k, name, x,  y, width, height);
            return 0;
        }
        return 1;
    }

    private static void createButtonLayer(Object k, String FontText, int x, int y, int width, int height) {
        
             String name =((Layer)k).getName();
   
           String name1=name; 
          
        
          String reg = "[^a-zA-Z0-9]";
          
          if(Character.isDigit(name.charAt(0))){
           name1="_"+name;
           
          }
        
         name1=name1.replaceAll(reg, "_");
        
        try {
            string.write(("<string name="+"\""+name1.toLowerCase()+"_"+counter+"_string"+"\""+">"+FontText+"</string>\n").getBytes());

         
            
            out.write(("<Button "+"android:id= "+"\""+"@+id/"+name1.toLowerCase()+"_"+counter+"\"\n\t").getBytes());
            out.write(("android:text= "+"\""+"@string/"+name1.toLowerCase()+"_"+counter+"_string"+"\"\n\t").getBytes());
            out.write(("android:layout_marginStart= "+"\""+x+"dp"+"\"\n\t").getBytes());
            out.write(("android:layout_marginTop= "+"\""+y+"dp"+"\"\n\t").getBytes());
            out.write(("android:layout_width= "+"\""+width+"dp"+"\"\n\t").getBytes());
            out.write(("android:layout_height= "+"\""+"wrap_content"+"\"\n\t").getBytes());
           // out.write(("android:textcolor= "+"\""+"@color/"+name1+"\"").getBytes());



            out.write(("/>\n").getBytes());
        } catch (IOException ex) {
            Logger.getLogger(CreateAndroid.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            
            counter++;
        
    }
    
    private static void createButtonLayer1(Object k,String name,int x,int y,int width,int height) throws IOException{
    
         String name1=name; 
          
        
          String reg = "[^a-zA-Z0-9]";
          
          if(Character.isDigit(name.charAt(0))){
           name1="_"+name;
           
          }
        
         name1=name1.replaceAll(reg, "_");
    BufferedImage image = ((Layer)k).getImage();
                   File srcfolder=new File(dir+"/src/psd/parser/android/drawable/"+name1.toLowerCase()+"_"+counter+".png");
                   srcfolder.mkdirs();
                   ImageIO.write(image, "png", srcfolder);
                   
                    out.write(("<Button "+"android:id= "+"\""+"@+id/"+name1.toLowerCase()+"_"+counter+"\"\n\t").getBytes());
                    out.write(("android:background= "+"\""+"@drawable/"+name1.toLowerCase()+"_"+counter+"\"\n\t").getBytes());
                    out.write(("android:layout_marginStart= "+"\""+x+"dp"+"\"\n\t").getBytes());
                    out.write(("android:layout_marginTop= "+"\""+y+"dp"+"\"\n\t").getBytes());
                    out.write(("android:layout_width= "+"\""+width+"dp"+"\"\n\t").getBytes());
                    out.write(("android:layout_height= "+"\""+height+"dp"+"\"\n\t").getBytes());
                    

                    out.write(("/>\n").getBytes());
                    counter++;
    }

    private static void createEditTextLayer1(Object k, String name, int x, int y, int width, int height) {
          String name1=name; 
          
        
          String reg = "[^a-zA-Z0-9]";
          
          if(Character.isDigit(name.charAt(0))){
           name1="_"+name;
           
          }
        
         name1=name1.replaceAll(reg, "_");
    BufferedImage image = ((Layer)k).getImage();
                   File srcfolder=new File(dir+"/src/psd/parser/android/drawable/"+name1.toLowerCase()+"_"+counter+".png");
                   srcfolder.mkdirs();
        try {
            ImageIO.write(image, "png", srcfolder);
            
            
                    out.write(("<EditText "+"android:id= "+"\""+"@+id/"+name1.toLowerCase()+"_"+counter+"\"\n\t").getBytes());
                    out.write(("android:background= "+"\""+"@drawable/"+name1.toLowerCase()+"_"+counter+"\"\n\t").getBytes());
                    out.write(("android:layout_marginStart= "+"\""+x+"dp"+"\"\n\t").getBytes());
                    out.write(("android:layout_marginTop= "+"\""+y+"dp"+"\"\n\t").getBytes());
                    out.write(("android:layout_width= "+"\""+width+"dp"+"\"\n\t").getBytes());
                    out.write(("android:layout_height= "+"\""+height+"dp"+"\"\n\t").getBytes());
                    

                    out.write(("/>\n").getBytes());
        } catch (IOException ex) {
            Logger.getLogger(CreateAndroid.class.getName()).log(Level.SEVERE, null, ex);
        }
                
                    counter++;
    }

    private static Double getGradientAngle(PsdDescriptor grfl) {
            Map grfl1=null,grfl2=null;
               Double ang = null;
              grfl1 = grfl.getObjects();
                ang = ((PsdUnitFloat)(grfl1.get("Angl"))).getValue() ;
                return ang;
    }

    private static String getGradientType(PsdDescriptor grfl) {
            Map grfl1=null;
              String type = null;
              grfl1 = grfl.getObjects();
                type = ((PsdEnum)(grfl1.get("Type"))).getValue() ;
                return type;
    }
}
