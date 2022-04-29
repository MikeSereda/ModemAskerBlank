import java.util.Timer;

public class ModemTimer extends Timer {
    private final String markup;

    public ModemTimer(String markup){
        this.markup = markup;

    }

    public String getMarkup() {
        return markup;
    }
}
