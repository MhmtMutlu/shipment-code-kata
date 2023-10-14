package com.trendyol.shipment;

import java.util.*;
import java.util.stream.Collectors;

public class BasketV2 {
    private List<Product> products;
    private static final int THRESHOLD_FOR_COUNT = 3;

    public ShipmentSize getShipmentSize() {
        final var productsList = getProducts();
        final var shipmentSizesAsGroupByCount = productsList.stream().collect(Collectors.groupingBy(Product::getSize, Collectors.counting()));
        final var shipmentSizeOfProducts = new ArrayList<>(shipmentSizesAsGroupByCount.keySet());
        final var highestShipmentSizeOfProducts = getHighestShipmentSizeOfProducts(shipmentSizeOfProducts);

        if (productsList.size() < THRESHOLD_FOR_COUNT) {
            return  highestShipmentSizeOfProducts;
        }

        return shipmentSizesAsGroupByCount.entrySet().stream()
                .filter(shipmentSizeType -> shipmentSizeType.getValue() >= THRESHOLD_FOR_COUNT)
                .filter(shipmentSizeLongEntry -> shipmentSizeLongEntry.getKey().isNotLast())
                .findFirst()
                .map(shipmentSizeType -> shipmentSizeType.getKey().next())
                .orElse(highestShipmentSizeOfProducts);
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    private ShipmentSize getHighestShipmentSizeOfProducts(List<ShipmentSize> shipmentSizes) {
        return shipmentSizes.stream().max(Comparator.comparing(Enum::ordinal)).orElse(ShipmentSize.SMALL);
    }
}
