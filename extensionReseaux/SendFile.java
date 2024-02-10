package transfer;
import java.util.ArrayList;

public class SendFile extends Demande {
    ArrayList<FileData> toSend;

    public void setToSend(ArrayList<FileData> toSend){
        this.toSend=toSend;
    }

    public ArrayList<FileData> getToSend(){
        return toSend;
    }

    public SendFile(String path,ArrayList<FileData> toSend){
        super("send",path);
        setToSend(toSend);
    }
}
