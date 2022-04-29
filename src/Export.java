import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Export {
    int screenWidth;
    int screenHeight;
    public Export(int screenWidth, int screenHeight){
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
    }
    public void export(int[] points,String path){
        try(FileWriter writer = new FileWriter(path,false)){
            String text = Files.readString(Paths.get("template.svg"));
            String[] parts = text.split("#");

            String insert = createPointsSVG(points);

            writer.write(parts[0]+insert+parts[1]);
        }catch(IOException e){
            System.out.println("IOException");
        }
    }

    private String createPointsSVG(int[] points) {
        StringBuilder builder = new StringBuilder(0+","+points[0]);

        //each 5 for practical use
        for (int x = 5; x < screenWidth-5; x=x+5) {
            builder.append(" ");
            builder.append(x);
            builder.append(",");
            builder.append(points[x]);
        }

        return builder.toString();
    }
}
