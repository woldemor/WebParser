package aboutyou.parser;

import java.util.List;

public class Aggregator {

    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();
        long usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Used Memory before: " + usedMemoryBefore/1000000 + "MB");

        long startTime = System.currentTimeMillis();
        String term = args[0];

        AboutYouStrategy aboutYouStrategy = new AboutYouStrategy();
        List<Item> itemList = aboutYouStrategy.getItems(term);
        XMLFile xmlFile = new XMLFile();
        xmlFile.write(itemList);

        long runTime = (System.currentTimeMillis() - startTime);
        System.out.println("Run-time: " + runTime + "ms");
        long usedMemoryAfter = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Memory increased: " + (usedMemoryAfter-usedMemoryBefore)/1000000 + "MB");
    }
}
