public class Main {
    public static void main(String[] args) {
    ProductRepository productRepository = new ProductRepository();

    productRepository.saveProduct(new Product(0, "Apple Airpods Pro 3", 16999.99, 30, 1));
    }
}