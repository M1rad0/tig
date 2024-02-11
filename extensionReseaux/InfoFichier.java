package transfer;

import java.io.Serializable;

public class InfoFichier implements Serializable{
    String relativePath;
    String type;

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }
    
    public void setType(String type) {
        this.type = type;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public String getType() {
        return type;
    }

    public InfoFichier(String relativePath,String type) {
        setRelativePath(relativePath);
        setType(type);
    }

    public String getName(){
        String[] tabRel=relativePath.split("\\");
        return tabRel[tabRel.length-1];
    }

    public boolean isDirectory(){
        return !type.equals("file");
    }
}
