package aboutyou.parser;

import java.util.List;

public interface Strategy {
    List<Item> getItems(String searchString);

}
