package ru.geekbrains.junior.lesson1.task2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Корзина
 * @param <T> Еда
 */
public class Cart<T extends Food> {

    //region Поля

    /**
     * Товары в магазине
     */
    private final ArrayList<T> foodstuffs;
    private final UMarket market;
    private final Class<T> clazz;

    //endregion

    //region Конструкторы

    /**
     * Создание нового экземпляра корзины
     * @param market принадлежность к магазину
     */
    public Cart(Class<T> clazz, UMarket market)
    {
        this.clazz = clazz;
        this.market = market;
        foodstuffs = new ArrayList<>();
    }

    //endregion

    /**
     * Балансировка корзины
     */
    public void cardBalancing()
    {
        Map<String, Boolean> pfc = new HashMap<>(Map.of(
                "proteins", foodstuffs.stream().anyMatch(Food::getProteins),
                "fats", foodstuffs.stream().anyMatch(Food::getFats),
                "carbohydrates", foodstuffs.stream().anyMatch(Food::getCarbohydrates)));

        if (!pfc.containsValue(false))
        {
            System.out.println("Корзина уже сбалансирована по БЖУ.");
            return;
        }

        market.getThings(clazz).stream().filter(thing -> !pfc.get("proteins") && thing.getProteins())
                .findFirst().ifPresent(food -> {
                    pfc.put("proteins", true);
                    foodstuffs.add(food);
                });

        market.getThings(clazz).stream().filter(thing -> !pfc.get("fats") && thing.getFats())
                .findFirst().ifPresent(food -> {
                    pfc.put("fats", true);
                    foodstuffs.add(food);
                });

        market.getThings(clazz).stream().filter(thing -> !pfc.get("carbohydrates") && thing.getCarbohydrates())
                .findFirst().ifPresent(food -> {
                    pfc.put("carbohydrates", true);
                    foodstuffs.add(food);
                });

        if (!pfc.containsValue(false))
            System.out.println("Корзина сбалансирована по БЖУ.");
        else
            System.out.println("Невозможно сбалансировать корзину по БЖУ.");
    }

    public Collection<T> getFoodstuffs() {
        return foodstuffs;
    }

    /**
     * Распечатать список продуктов в корзине
     */
    public void printFoodstuffs(){
        /*int index = 1;
        for (var food : foodstuffs)
            System.out.printf("[%d] %s (Белки: %s Жиры: %s Углеводы: %s)\n", index++, food.getName(), food.getProteins() ? "Да" : "Нет",
                    food.getFats() ? "Да" : "Нет", food.getCarbohydrates() ? "Да" : "Нет");
         */
        AtomicInteger index = new AtomicInteger(1);
        foodstuffs.forEach(food -> System.out.printf("[%d] %s (Белки: %s Жиры: %s Углеводы: %s)\n",
                index.getAndIncrement(), food.getName(),
                food.getProteins() ? "Да" : "Нет",
                food.getFats() ? "Да" : "Нет",
                food.getCarbohydrates() ? "Да" : "Нет"));

    }

}
