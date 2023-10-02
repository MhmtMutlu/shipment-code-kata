package com.trendyol.shipment;

import java.util.*;

public class Basket {

    private List<Product> products;
    private static final int THRESHOLD_FOR_COUNT = 3;

    public ShipmentSize getShipmentSize() {
        EnumMap<ShipmentSize, Integer> shipmentSizeMap = new EnumMap<>(ShipmentSize.class);

        products.forEach(product -> shipmentSizeMap.put(product.getSize(), shipmentSizeMap.getOrDefault(product.getSize(), 0) +1));

        if (products.size() < THRESHOLD_FOR_COUNT) {
            return getMaxShipmentSize(shipmentSizeMap);
        }

        for (Map.Entry<ShipmentSize, Integer> entry : shipmentSizeMap.entrySet()) {
            ShipmentSize shipmentSize = entry.getKey();
            Integer count = entry.getValue();

            if (count >= THRESHOLD_FOR_COUNT) {
                return getNextShipmentSize(shipmentSize);
            }
        }

        return getMaxShipmentSize(shipmentSizeMap);
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    private ShipmentSize getMaxShipmentSize(EnumMap<ShipmentSize, Integer> shipmentSizeMap) {
        List<ShipmentSize> reversedSizes = Arrays.asList(ShipmentSize.values());
        Collections.reverse(reversedSizes);

        return reversedSizes.stream()
                .filter(shipmentSizeMap::containsKey)
                .findFirst()
                .orElse(ShipmentSize.SMALL);
    }

    private ShipmentSize getNextShipmentSize(ShipmentSize currentShipmentSize) {
        return switch (currentShipmentSize) {
            case SMALL -> ShipmentSize.MEDIUM;
            case MEDIUM -> ShipmentSize.LARGE;
            case LARGE, X_LARGE -> ShipmentSize.X_LARGE;
        };
    }
}
