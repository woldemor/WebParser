package aboutyou.parser;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlSeeAlso({Items.class})
@XmlType(propOrder = {"name", "brand", "color", "price", "initialPrice", "description", "articleId", "shippingCosts"})
public class Item {
    private String name;
    private String brand;
    private String color;
    private String price;
    private String initialPrice;
    private String description;
    private String articleId;
    private String shippingCosts;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (!name.equals(item.name)) return false;
        if (!brand.equals(item.brand)) return false;
        if (!color.equals(item.color)) return false;
        if (!price.equals(item.price)) return false;
        if (!initialPrice.equals(item.initialPrice)) return false;
        if (!description.equals(item.description)) return false;
        if (!articleId.equals(item.articleId)) return false;
        return shippingCosts.equals(item.shippingCosts);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + brand.hashCode();
        result = 31 * result + color.hashCode();
        result = 31 * result + price.hashCode();
        result = 31 * result + initialPrice.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + articleId.hashCode();
        result = 31 * result + shippingCosts.hashCode();
        return result;
    }

    public String getName() {
        return name;
    }
    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }
    @XmlElement
    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }
    @XmlElement
    public void setColor(String color) {
        this.color = color;
    }

    public String getPrice() {
        return price;
    }
    @XmlElement
    public void setPrice(String price) {
        this.price = price;
    }

    public String getInitialPrice() {
        return initialPrice;
    }
    @XmlElement
    public void setInitialPrice(String initialPrice) {
        this.initialPrice = initialPrice;
    }

    public String getDescription() {
        return description;
    }
    @XmlElement
    public void setDescription(String description) {
        this.description = description;
    }

    public String getArticleId() {
        return articleId;
    }
    @XmlElement
    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getShippingCosts() {
        return shippingCosts;
    }
    @XmlElement
    public void setShippingCosts(String shippingCosts) {
        this.shippingCosts = shippingCosts;
    }
}
