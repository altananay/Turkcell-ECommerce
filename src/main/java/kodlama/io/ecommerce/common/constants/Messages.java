package kodlama.io.ecommerce.common.constants;

public class Messages {
    public static class Product
    {
        public static final String NotExists = "Product not exists";
        public static final String OutOfStock = "Product out of stock";
        public static final String IsNotActive = "Product isn't active";
    }

    public static class Brand
    {
        public static final String NotExists = "BRAND_NOT_EXISTS";
        public static final String Exists = "BRAND_ALREADY_EXISTS";
    }

    public static class Category
    {
        public static final String NotExists = "Category not exists";
    }

    public static class Payment
    {
        public static final String NotFound = "PAYMENT_NOT_FOUND";
        public static final String CardNumberAlreadyExists = "CARD_NUMBER_ALREADY_EXISTS";
        public static final String NotEnoughMoney = "NOT_ENOUGH_MONEY";
        public static final String NotAValidPayment = "NOT_A_VALID_PAYMENT";
        public static final String Failed = "PAYMENT_FAILED";
    }

    public static class Invoice
    {
        public static final String NotFound = "INVOICE_NOT_FOUND";
    }
}