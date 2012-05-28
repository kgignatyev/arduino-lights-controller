package kgi.cblt.christmas_light_show;

import org.apache.commons.io.IOUtils;

import java.io.FileReader;
import java.util.List;

/**
 * Created by
 * User: kgignatyev
 */
public class LightShapesReader {


    public static LightShape[] readShapes(String shapesFileName) throws Exception {
        LightShape[] res = new LightShape[14];
        List<String> lines = IOUtils.readLines(new FileReader(shapesFileName));
        int shapeIndex = 0;
        LightShape buf = null;
        for (String line : lines) {
            if (!line.isEmpty() && !line.startsWith("//")) {
                String[] parts = line.split(",");
                if (parts.length == 1) {
                    String colorName = parts[0];
                    if (buf != null) {
                        res[shapeIndex] = buf;
                        shapeIndex = shapeIndex + 1;
                    }
                    buf = new LightShape();
                    buf.colorName = colorName;
                } else if (parts.length == 4) {
                   buf.lines.add( new Line(new Point( Integer.parseInt(parts[0].trim()),Integer.parseInt(parts[1].trim())) ,
                           new Point( Integer.parseInt(parts[2].trim()),Integer.parseInt(parts[3].trim())) ));
                }
            }
        }
        res[shapeIndex] = buf;
        return res;
    }
}
