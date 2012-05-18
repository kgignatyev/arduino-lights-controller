package kgi.cblt.christmas_light_show;

import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: max
 * Date: 12/28/10
 * Time: 9:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class ArduinoProgramWriter {

    static int state = -1;
    static int counter = 0;

    public static void writeAduinoProgram( Reader in, Writer out) throws IOException {

        BufferedReader r = new BufferedReader(in);
        String line = null;
        out.append("int i = 0;\n" +
                "int control;\n" +
                "void setup() {                \n" +
                "  // initialize the digital pin as an output.\n" +
                "  // Pin 13 has an LED connected on most Arduino boards:\n" +
                "  pinMode(13, OUTPUT);    \n" +
                "  pinMode(12, OUTPUT); \n" +
                "  pinMode(11, OUTPUT); \n" +
                "  pinMode(10, OUTPUT); \n" +
                "  pinMode(9, OUTPUT); \n" +
                "  pinMode(8, OUTPUT); \n" +
                "  pinMode(7, OUTPUT); \n" +
                "  pinMode(6, OUTPUT); \n" +
                "  pinMode(5, OUTPUT); \n" +
                "  pinMode(4, OUTPUT); \n" +
                "  pinMode(3, OUTPUT); \n" +
                "  pinMode(2, OUTPUT); \n" +
                "  pinMode(1, OUTPUT); \n" +
                "  pinMode(0, OUTPUT); \n" +
                "\n" +
                "   \n" +
                "}\n" +
                "\n" +
                "void loop() {\n" +
                "     \n"
                 );
              while ( (line = r.readLine())!= null){
                 int newState = Integer.parseInt(line);
                 if( newState!= state){
                     state = newState;
                     if(counter > 0){
                         out.append("delay("+(100*counter)+");\n");
                     }
                     out.append("flipPins("+ newState +");\n");
                     counter = 1;
                 }
                 if( newState == state){
                     counter = counter +1;
                 }

              }


             out.append(   "}\n" +
                "\n" +
                "void flipPins( int control ){\n" +
                "       flip(  control, 8192,13  );\n" +
                "       flip(  control, 4096,12  );\n" +
                "       flip(  control, 2048,11  );\n" +
                "       flip(  control, 1024,10  );\n" +
                "       flip(  control, 512,9  );\n" +
                "       flip(  control, 256,8  );\n" +
                "       flip(  control, 128,7  );\n" +
                "       flip(  control, 64,6  );\n" +
                "       flip(  control, 32,5  );\n" +
                "       flip(  control, 16,4  );\n" +
                "       flip(  control, 8,3  );\n" +
                "       flip(  control, 4,2  );\n" +
                "       flip(  control, 2,1  );\n" +
                "       flip(  control, 1,0  );\n" +
                "}\n" +
                "\n" +
                "void flip( int c, int check, int p) {\n" +
                "       //Serial.print(\"pin: \");\n" +
                "       //Serial.println(p, DEC);\n" +
                "      // Serial.println(check, DEC);\n" +
                "      if(  (c & check) > 0  ){\n" +
                "         digitalWrite( p, HIGH);\n" +
                "          //Serial.print(\"H\");\n" +
                "       }else{\n" +
                "         digitalWrite( p, LOW);\n" +
                "         //Serial.print(\"L\");\n" +
                "       }\n" +
                "}\n" +
                "\n" +
                "int readInput(){\n" +
                "  Serial.available();\n" +
                "  return Serial.read();\n" +
                "}\n" +
                "\n" +
                "int toInt( int c, int m ){\n" +
                "   return (c-48)*m;\n" +
                "}");
         out.flush();
        out.close();

    }

    public static void main(String[] args) {
        try {
            File lightShowFile = new File(args[0]);
            String pdeBaseName = args[1];
            File outDir = new File(lightShowFile.getParentFile(),pdeBaseName);
            if(! outDir.exists()){
                outDir.mkdirs();
            }
            File outFile = new File( outDir, outDir.getName() + ".pde");
            FileReader is = new FileReader(lightShowFile);
            writeAduinoProgram( is, new FileWriter(outFile));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
