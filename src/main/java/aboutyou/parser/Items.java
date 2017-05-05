package aboutyou.parser;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "offers")
public class Items {
    private List<Item> itemList;

    public List<Item> getItemList() {
        return itemList;
    }

    @XmlElement(name = "offer")
    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }
}
