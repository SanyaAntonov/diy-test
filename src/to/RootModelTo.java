package to;

import java.util.List;

// Группа технологий со своим кодом
public class RootModelTo {
    public RootModelTo(String rootCode) {
        this.rootCode = rootCode;
    }

    public String rootCode;
    public List<TechModelTo> techList;
}
