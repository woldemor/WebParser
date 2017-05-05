package aboutyou.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class AboutYouStrategy implements Strategy{
    private static final String URL_FORMAT = "https://www.aboutyou.de/suche?category=20202&term=%s&page=%d";
    private long httpRequestsCount = 0;
    private long prodAmountCount = 0;
    private ExecutorService executor = null;

    @Override
    public List<Item> getItems(String searchString) {
        List<Item> itemList = new ArrayList<>();

        try {
            int page = 1;
            Document doc = getDocument(searchString, page);
            Elements elements = doc.select("#pl-pager-bottom");
            int lastPage = getLastPage(elements);

            executor = Executors.newFixedThreadPool(10);
            List<Callable<List<Item>>> taskList = new ArrayList<>();
            for (int i = 1; i < lastPage + 1; i++) {
                Task task = new Task(searchString, i);
                taskList.add(task);
            }
            List<Future<List<Item>>> results = executor.invokeAll(taskList);
            for (Future<List<Item>> result : results) {
                itemList.addAll(result.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (executor != null) {
                executor.shutdown();
            }
        }
        System.out.println("Amount of triggered HTTP request: " + httpRequestsCount);
        System.out.println("Amount of extracted products: " + prodAmountCount);
        return itemList;
    }

    private int getLastPage(Elements elements){
        int lastPage;

        if (elements.size() != 0) {
            Elements elements1 = elements.select(".gt9 a");
            if (elements1.size() != 0) {
                lastPage = Integer.parseInt(elements1.first().html());
            } else {
                Elements pages = elements.get(0).select(".page a");
                if (pages.size() == 0) {
                    lastPage = 1;
                } else {
                    lastPage = Integer.parseInt(pages.last().html());
                }
            }
        } else {
            lastPage = 1;
        }
        return lastPage;
    }

    private Document getDocument(String searchString, int page) throws IOException {
        String url = String.format(URL_FORMAT, searchString, page);
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36";
        Document doc = Jsoup.connect(url).userAgent(userAgent).referrer("http://google.com").get();
        httpRequestsCount++;
        return doc;
    }

    class Task implements Callable<List<Item>> {
        private String searchString;
        private int page;

        Task(String searchString, int page) {
            this.searchString = searchString;
            this.page = page;
        }

        @Override
        public List<Item> call() throws Exception {
            ArrayList<Item> items = new ArrayList<>();
            try {
                Document doc = getDocument(searchString, page);

                Elements itemsList = doc.getElementsByClass("row list-wrapper product-image-list");
                Elements elements = itemsList.get(0).getElementsByAttributeValue("itemprop", "itemListElement");
                for (Element element : elements) {
                    Item item = new Item();
                    item.setName(element.getElementsByAttributeValue("itemprop", "name").text());
                    item.setBrand(element.select(".js-product-item-brand").text());
                    Elements prices = element.getElementsByAttributeValue("itemprop", "price");
                    item.setInitialPrice(prices.get(0).select("span").attr("data-price"));
                    if (prices.size() != 1) {
                        item.setPrice(prices.get(1).select("span").attr("data-price"));
                    }
                    item.setArticleId(element.select("article").attr("data-product-id"));
                    items.add(item);
                    prodAmountCount++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return items;
        }
    }
}

