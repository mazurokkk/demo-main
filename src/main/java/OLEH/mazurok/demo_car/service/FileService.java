package alla.verkhohliadova.demo_car.service;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

@Component
public class FileService {

    public static final String IMG_DIR =
            //System.getProperty("C:\\Users\\Alla\\images\\dealer-images\\");
            System.getProperty("user.home")
                    //+ File.separator + "Users"
                    //+ File.separator + "Alla"
                    + File.separator + "images"
                    + File.separator + "dealer-images"
                    + File.separator;
//            System.getProperty("user.home") + File.separator + "dealer-images" + File.separator;//

    //public

    public File multipartFileConvertToFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String prefix = fileName.substring(fileName.lastIndexOf("."));
        File fi = null;
            fi = File.createTempFile(fileName, prefix);
            file.transferTo(fi);
        return fi;
    }

    public String fileEncodeBase64(File file){
        String format = "";
        String f = file.getName();
        if(f.lastIndexOf(".")!=-1 && f.lastIndexOf(".")!=0) {
            format = f.substring(f.lastIndexOf(".") + 1);
        }
        //System.out.println("\n format - " + f);
        String dataStart = "data:image/";
        String dataFin = ";base64,";
        String encodefile = "";
        try {
            byte[] imageData = new byte[(int) file.length()];
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(imageData);
            encodefile = dataStart + format + dataFin + Base64.getEncoder().encodeToString(imageData);

        } catch (FileNotFoundException e){
            System.err.println("Image not found" + e);
        } catch (IOException e){
            System.err.println("Exception while reading the Image " + e);;
        }
        return encodefile;
    }

    public String saveFile(String img) throws IOException {
        createDir(IMG_DIR);//create folder if not exists

        String[] data = img.split(",");
        String metaInfo = data[0];
        String base64File = data[1];

        String fileName = createFileName(null, getFileExtensionFromMetaInfo(metaInfo)); //null

        Files.write(
                Paths.get(IMG_DIR, fileName),
                Base64.getDecoder().decode(base64File.getBytes())
        );
        return fileName;
    }

    private String createFileName(String fileName, String fileExtension) {
        if (fileName == null) {
            fileName = UUID.randomUUID().toString();
        }
        return String.format("%s.%s", fileName, fileExtension);
    }
    //data:image/jpeg;base64
    private String getFileExtensionFromMetaInfo(String metaInfo) {
        return metaInfo.split("/")[1].split(";")[0];
    }

    private void createDir(String dir) {
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
}
