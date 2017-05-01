/**
 * 
 */
package psd.parser;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import psd.parser.layer.LayersSectionParser;
import psd.parser.layer.LayerParser;
import psdtool.TreeLayerModel;
import psd.model.Psd;
import psd.model.Layer;
import psd.parser.object.PsdDescriptor;
import psd.parser.object.PsdObject;
import psd.parser.object.PsdText;
import psd.parser.object.PsdTextData;

import java.io.File;
import java.io.FileOutputStream;
import psd.parser.object.PsdEnum;
import psd.parser.object.PsdUnitFloat;



/**
 * 
 * 
 */
public class CreateStoryboard {

   
   public static FileOutputStream out;
   public static FileOutputStream outSwift;

   public static int counter=0;

   public static String dir= System.getProperty("user.dir");
    
    public static void create(Psd psd) throws IOException {
        
  //swift
  File outputSwift= new File(dir+"/src/psd/parser/storyboard/output.xml");
outputSwift.getParentFile().mkdirs();
outputSwift.createNewFile();
outSwift=new FileOutputStream(outputSwift);



       
        
        TreeLayerModel tree = new TreeLayerModel();
        
        tree.setPsd(psd);

      try{        


          
          //swift
          String swiftup= "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
"<document type=\"com.apple.InterfaceBuilder3.CocoaTouch.Storyboard.XIB\" version=\"3.0\" toolsVersion=\"12118\" systemVersion=\"16E195\" targetRuntime=\"iOS.CocoaTouch\" propertyAccessControl=\"none\" useAutolayout=\"YES\" useTraitCollections=\"YES\" colorMatched=\"YES\" initialViewController=\"BYZ-38-t0r\">\n" +
"    <device id=\"retina4_7\" orientation=\"portrait\">\n" +
"        <adaptation id=\"fullscreen\"/>\n" +
"    </device>\n" +
"    <dependencies>\n" +
"        <deployment identifier=\"iOS\"/>\n" +
"        <plugIn identifier=\"com.apple.InterfaceBuilder.IBCocoaTouchPlugin\" version=\"12086\"/>\n" +
"        <capability name=\"documents saved in the Xcode 8 format\" minToolsVersion=\"8.0\"/>\n" +
"    </dependencies>\n" +
"    <scenes>\n" +
"        <!--View Controller-->\n" +
"        <scene sceneID=\"tne-QT-ifu\">\n" +
"            <objects>\n" +
"                <viewController id=\"BYZ-38-t0r\" customClass=\"ViewController\" customModule=\"ping\" customModuleProvider=\"target\" sceneMemberID=\"viewController\">\n" +
"                    <layoutGuides>\n" +
"                        <viewControllerLayoutGuide type=\"top\" id=\"y3c-jy-aDJ\"/>\n" +
"                        <viewControllerLayoutGuide type=\"bottom\" id=\"wfy-db-euE\"/>\n" +
"                    </layoutGuides>\n" +
"                    <view key=\"view\" contentMode=\"scaleToFill\" id=\"8bC-Xf-vdC\">\n" +
"                        <rect key=\"frame\" x=\"0.0\" y=\"0.0\" width=\"375\" height=\"667\"/>\n" +
"                        <autoresizingMask key=\"autoresizingMask\" widthSizable=\"YES\" heightSizable=\"YES\"/>\n" +
"                        \n" +
"                        <subviews>\n";
          outSwift.write(swiftup.getBytes());
          
          
         
         
      }catch(Exception e){
          e.printStackTrace();
      }      
  
        struct(tree, tree.getRoot(),tree.getChildCount(tree.getRoot()));
        
        //swift
        String swiftend="                        </subviews>\n" +
"                        \n" +
"                        <color key=\"backgroundColor\" red=\"1\" green=\"1\" blue=\"1\" alpha=\"1\" colorSpace=\"custom\" customColorSpace=\"sRGB\"/>\n" +
"                    </view>\n" +
"                </viewController>\n" +
"                <placeholder placeholderIdentifier=\"IBFirstResponder\" id=\"dkx-z0-nzr\" sceneMemberID=\"firstResponder\"/>\n" +
"            </objects>\n" +
"            <point key=\"canvasLocation\" x=\"32.799999999999997\" y=\"38.23088455772114\"/>\n" +
"        </scene>\n" +
"    </scenes>\n" +
"</document> ";
        outSwift.write(swiftend.getBytes());
        
        
     outSwift.close();
        
       
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
                              int y2 =(int) (y1/2.0f);
                              System.out.println(y2);
                              float y3 = ((Layer)k).getY();
                              int y4 =(int) (y3/2.0f);
                              System.out.println(y2);
                              float y5 = ((Layer)k).getWidth();
                              int y6 =(int) (y5/2.0f);
                              System.out.println(y2);
                              float y7 = ((Layer)k).getHeight();
                              int y8 =(int) (y7/2.0f);
                              System.out.println(y2);
                              
                     
                          
         //  createTextLayer(FontText,k,FontName,FontSize1,FontColor,y2,y4,y6,y8);
          
                   createTextLayerSwift(FontText,k,FontName,FontSize1/3,FontColor,y2,y4,y6,y8);
 
           

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
          int y2 =(int) (y1/2.0f);
          System.out.println(y2);
          float y3 = ((Layer)k).getY();
           int y4 =(int) (y3/2.0f);
          System.out.println(y2);
          float y5 = ((Layer)k).getWidth();
          int y6 =(int) (y5/2.0f);
          System.out.println(y2);
          float y7 = ((Layer)k).getHeight();
          int y8 =(int) (y7/2.0f);
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
                              int y2 =(int) (y1/2.0f);
                              System.out.println(y2);
                              float y3 = ((Layer)k).getY();
                              int y4 =(int) (y3/2.0f);
                              System.out.println(y2);
                              float y5 = ((Layer)k).getWidth();
                              int y6 =(int) (y5/2.0f);
                              System.out.println(y2);
                              float y7 = ((Layer)k).getHeight();
                              int y8 =(int) (y7/2.0f);
                              System.out.println(y2);
                 int flag= check1(k,y2,y4,y6,y8);                   
                if(flag!=0) 
                  createImageLayer(k,name1,y2,y4,y6,y8);
           
           
                   
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
  
            
    
        public static void createTextLayerSwift(String FontText,Object k, String FontName, Integer FontSize, ArrayList<Double> FontColor,int x,int y,int width,int height) {
        
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

           float alpha= (float)(((Layer)k).getAlpha());
           alpha = (alpha/255.0f);
                
                String textSwift="                            <textView clipsSubviews=\"YES\" multipleTouchEnabled=\"YES\""+ " alpha=\""+alpha+"\" contentMode=\"scaleToFill\" fixedFrame=\"YES\" scrollEnabled=\"NO\" editable=\"NO\" text=\""+FontText+"\" textAlignment=\"natural\" selectable=\"NO\" translatesAutoresizingMaskIntoConstraints=\"NO\" id=\"eG6-Lb-Ac"+counter+"\">\n" +
"                            <rect key=\"frame\" x=\""+x+"\" y=\""+(y-8)+"\" width=\""+width+"\" height=\""+(height+12)+"\"/>\n" +
"                            <autoresizingMask key=\"autoresizingMask\" flexibleMaxX=\"YES\" flexibleMaxY=\"YES\"/>\n" +
                        "                            <color key=\"textColor\" red=\""+FontColor.get(0).floatValue()+"\" green=\""+FontColor.get(1).floatValue()+"\" blue=\""+FontColor.get(2).floatValue()+"\" alpha=\""+FontColor.get(3).floatValue()+"\" colorSpace=\"custom\" customColorSpace=\"sRGB\"/>\n"+
"                            <fontDescription key=\"fontDescription\" name=\""+FontName+"\" pointSize=\""+FontSize+"\"/>\n" +
"                            <textInputTraits key=\"textInputTraits\" autocapitalizationType=\"sentences\"/>\n" +
"                            </textView>\n\n";
                
                
                outSwift.write((textSwift.getBytes()));
                
            
            
            counter++;
          
            
        } catch (IOException ex) {
           
        }
        
    }
    

    private static void createImageLayer(Object k,String name,int x,int y,int width,int height) throws IOException {
         BufferedImage image = ((Layer)k).getImage();
                   File srcfolder=new File(dir+"/src/psd/parser/storyboard/drawable/"+name.toLowerCase()+"_"+counter+".png");
                   srcfolder.mkdirs();
                   ImageIO.write(image, "png", srcfolder);
                   float alpha= (float)(((Layer)k).getAlpha());
           alpha = (alpha/255.0f);


           String imageView="                            <imageView userInteractionEnabled=\"NO\" alpha=\""+alpha+"\" contentMode=\"scaleToFill\" horizontalHuggingPriority=\"251\" verticalHuggingPriority=\"251\" fixedFrame=\"YES\" image=\""+name.toLowerCase()+"_"+counter+".png"+"\" translatesAutoresizingMaskIntoConstraints=\"NO\" id=\"ol8-ru-gc"+counter+"\">\n" +
"                                <rect key=\"frame\" x=\""+x+"\" y=\""+y+"\" width=\""+width+"\" height=\""+height+"\"/>\n" +
"                                <autoresizingMask key=\"autoresizingMask\" flexibleMaxX=\"YES\" flexibleMaxY=\"YES\"/>\n" +
"                            </imageView>\n\n";
           
           outSwift.write(imageView.getBytes());
         
                    
           counter++;
           
        //   System.out.println(srcfolder+ name+ ".png");
    }


    
    
    private static int check1(Object k,  int x, int y, int width, int height) throws IOException {
        String name = ((Layer)k).getName();
      
        if(name.equals("Button")){

            createButtonLayer1(k, name, x,  y, width, height);
            return 0;
        }
          if(name.equals("EditText")){
              
//System.out.println("11111111111111111111111111111111111111"+name);
            createEditTextLayer1(k, name, x,  y, width, height);
            return 0;
        }
        return 1;
    }

    
    private static void createButtonLayer1(Object k,String name,int x,int y,int width,int height) throws IOException{
    
         String name1=name; 
          
        
          String reg = "[^a-zA-Z0-9]";
          
          if(Character.isDigit(name.charAt(0))){
           name1="_"+name;
           
          }
        
         name1=name1.replaceAll(reg, "_");
    BufferedImage image = ((Layer)k).getImage();
                   File srcfolder=new File(dir+"/src/psd/parser/storyboard/drawable/"+name1.toLowerCase()+"_"+counter+".png");
                   srcfolder.mkdirs();
                   ImageIO.write(image, "png", srcfolder);

                    
                 String buttonimg= "                            <button opaque=\"NO\" contentMode=\"scaleToFill\" fixedFrame=\"YES\" contentHorizontalAlignment=\"center\" contentVerticalAlignment=\"center\" lineBreakMode=\"middleTruncation\" translatesAutoresizingMaskIntoConstraints=\"NO\" id=\"2qi-o5-khx\">\n" +
"                                <rect key=\"frame\" x=\"167\" y=\"370\" width=\"81\" height=\"22\"/>\n" +
"                                <autoresizingMask key=\"autoresizingMask\" flexibleMaxX=\"YES\" flexibleMaxY=\"YES\"/>\n" +
"                                <state key=\"normal\" title=\"Button\" backgroundImage=\""+name1.toLowerCase()+"_"+counter+".png"+"\"/>\n" +
"                            </button>\n\n";
                   outSwift.write(buttonimg.getBytes());
                   
                    
                    counter++;
    }

    private static void createEditTextLayer1(Object k, String name, int x, int y, int width, int height) throws IOException {
  
   
                   String textSwift="                            <textView clipsSubviews=\"YES\" multipleTouchEnabled=\"YES\" contentMode=\"scaleToFill\" fixedFrame=\"YES\" scrollEnabled=\"NO\" editable=\"YES\" text=\""+"placeholder"+"\" textAlignment=\"natural\" selectable=\"NO\" translatesAutoresizingMaskIntoConstraints=\"NO\" id=\"eG6-Lb-Ac"+counter+"\">\n" +
                           "                            <rect key=\"frame\" x=\""+x+"\" y=\""+y+"\" width=\""+width+"\" height=\""+height+"\"/>\n" +
                           "                            <autoresizingMask key=\"autoresizingMask\" flexibleMaxX=\"YES\" flexibleMaxY=\"YES\"/>\n" +
                           "                            <color key=\"backgroundColor\" white=\"1\" alpha=\"1\" colorSpace=\"calibratedWhite\"/>\n" +
                           "                            <textInputTraits key=\"textInputTraits\" autocapitalizationType=\"sentences\"/>\n" +
                           "                            </textView>\n\n";
                   
                   outSwift.write(textSwift.getBytes());
                   
                
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


