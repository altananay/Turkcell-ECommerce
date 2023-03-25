package kodlama.io.ecommerce.business.validators;

import kodlama.io.ecommerce.entities.Product;

public class ProductValidator {
    public static void checkIfUnitPriceValid(Product product) {
        if (product.getPrice() <= 0) {
            throw new IllegalArgumentException("Price cannot be less than or equal to zero.");
        }
    }

    public static void checkIfQuantityValid(Product product) {
        if (product.getQuantity() < 0) {
            throw new IllegalArgumentException("Quantity cannot be less than zero");
        }
    }

    public static void checkIfDescriptionLengthValid(Product product) {
        if (product.getDescription().length() < 10 || product.getDescription().length() > 50) {
            System.out.println("test");
            throw new IllegalArgumentException("Description length must be between 10 and 50 characters.");
        }
    }

}