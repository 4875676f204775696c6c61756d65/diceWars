package Graphical;

import javax.swing.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Comparator;

public class ComparatorCasesTerritoires implements Comparator<JButton>, Serializable {

    @Serial
    private static final long serialVersionUID = 558557735862L;

    @Override
    public int compare(JButton o1, JButton o2) {
        Integer id1 = Integer.parseInt(o1.getActionCommand().substring(o1.getActionCommand().indexOf("[") + 1, o1.getActionCommand().indexOf("]")));
        Integer id2 = Integer.parseInt(o2.getActionCommand().substring(o2.getActionCommand().indexOf("[") + 1, o2.getActionCommand().indexOf("]")));
        return id1.compareTo(id2);
    }
}
