package models;

import com.google.gson.GsonBuilder;
import java.util.ArrayList;

/**
 * Параметры создания заказа.
 */
@SuppressWarnings("FieldCanBeLocal")
public class OrderParameters {
    private final ArrayList<String> ingredients;

    public OrderParameters(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String toJson() {
        return new GsonBuilder().serializeNulls().create().toJson(this);
    }
}
