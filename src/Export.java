import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Export {
    public static void export(int[] points,String path){
        try(FileWriter writer = new FileWriter(path,false)){
            String text = Files.readString(Paths.get("template.svg"));
            String[] parts = text.split("#");

            String insert = createPointsSVG(points);

            writer.write(parts[0]+insert+parts[1]);
        }catch(IOException e){
            System.out.println("IOException");
        }
    }

    private static String createPointsSVG(int[] points) {
        StringBuilder builder = new StringBuilder(0+","+points[0]);

        //each 5 for practical use
        for (int x = 5; x < Helper.ScreenWidth-5; x=x+5) {
            builder.append(" ");
            builder.append(x);
            builder.append(",");
            builder.append(points[x]);
        }

        return builder.toString();
    }
}
