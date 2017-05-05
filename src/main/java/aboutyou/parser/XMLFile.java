package aboutyou.parser;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.List;

class XMLFile {
    private static final String FILE_NAME = "./items.xml";

    void write(List<Item> itemList) {
        try {
            Items items = new Items();
            items.setItemList(itemList);

            File file = new File(FILE_NAME);
            JAXBContext jaxbContext = JAXBContext.newInstance(Items.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.marshal(items, file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
