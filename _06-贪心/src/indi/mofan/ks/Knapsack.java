package indi.mofan.ks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @author mofan
 * @date 2022/11/12 16:04
 */
public class Knapsack {
    public static void main(String[] args) {
        select("价值主导", (o1, o2) -> o2.value - o1.value);
        select("重量主导", Comparator.comparingInt(o -> o.weight));
        select("价值密度主导", (o1, o2) -> Double.compare(o2.valueDensity, o1.valueDensity));
    }

    static void select(String title, Comparator<Article> comparator) {
        Article[] articles = new Article[] {
                new Article(35, 10), new Article(30, 40),
                new Article(60, 30), new Article(50, 50),
                new Article(40, 35), new Article(10, 40),
                new Article(25, 30)
        };
        Arrays.sort(articles, comparator);

        int capacity = 150, weight = 0, value = 0;
        List<Article> selectedArticles = new ArrayList<>();

        for (int i = 0; i < articles.length && weight < capacity; i++) {
            int newWeight = weight + articles[i].weight;
            if (newWeight <= capacity) {
                weight = newWeight;
                value += articles[i].value;
                selectedArticles.add(articles[i]);
            }
        }

        System.out.println("[" + title + "]");
        System.out.println("总价值为: " + value);
        selectedArticles.forEach(System.out::println);
        System.out.println("----------------------------------");
    }
}
