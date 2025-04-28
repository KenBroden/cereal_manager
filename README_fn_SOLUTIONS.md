# Project: Function Exercises

- *Created by Cameron Walker & Ken Broden*

## `zipmap`

The `zipmap` function takes two sequences. One for keys and one for values. It returns a map where each key is associated with the corresponding value. If the sequences are of unequal lengths, the resulting map will only include as many key-value pairs as the shorter sequence.

**Examples:**

1) Basic:

    ```clojure
    (zipmap [:a :b :c] [1 2 3])
    ;; => {:a 1, :b 2, :c 3}
    ```

2) Unequal Lengths:

    ```clojure
    (zipmap [:a :b :c] [1 2 3])
    ;; => {:a 1, :b 2, :c 3}
    ```

3) Empty Sequences:

    ```clojure
    (zipmap [] [])
    ;; => {}
    ```

4) `zipmap` with `range`

    ```clojure
    (zipmap (range 5) (map #(* % %) (range 5)))
    ;; => {0 0, 1 1, 2 4, 3 9, 4 16}
    ```

**Exercise:**

Write a function `inventory-builder that takes two sequences:

- A sequence of item names (strings).
- A sequence of quantities (integers).

The function should return a map where each item name is associated with its quantity. If the sequences are of unequal lengths, the extra elements in the longer sequence should be ignored.

**Test Cases:**

1) Basic Case:

    ```clojure
    (inventory-builder ["Apples" "Bananas" "Cherries"] [10 20 30])
    ;; => {"Apples" 10, "Bananas" 20, "Cherries" 30}
    ```

2) Unequal Lengths:

    ```clojure
    (inventory-builder ["Apples" "Bananas"] [10 20 30])
    ;; => {"Apples" 10, "Bananas" 20}
    ```

3) Empty Sequences:

    ```clojure
    (inventory-builder [] [])
    ;; => {}
    ```

4) One Empty Sequence

    ```clojure
    (inventory-builder ["Apples" "Bananas"] [])
    ;; => {}
    ```

**Tests Cases in `core_test.clj`:**

```clojure
(deftest test-inventory-builder
  (testing "Testing the inventory-builder function"
    (is (= {"Apples" 10, "Bananas" 20, "Cherries" 30} ;; expected
           (inventory-builder ["Apples" "Bananas" "Cherries"] [10 20 30])))
    (is (= {"Apples" 10, "Bananas" 20} ;; expected
           (inventory-builder ["Apples" "Bananas"] [10 20 30])))
    (is (= {} ;; expected
           (inventory-builder [] [])))
    (is (= {} ;; expected
           (inventory-builder ["Apples" "Bananas"] [])))))
```

**Solution:**

```clojure
(defn inventory-builder
  "Takes a sequence of item names and a sequence of quantities, and returns a map of items to quantities."
  [items quantities]
  (zipmap items quantities))
```

## `select-keys`

The `select-keys` function takes a map and a sequence of keys. It returns a new map containing only the key-value pairs for the specified keys, provided they exist in the original map.

**Examples:**

1) Basic:

    ```clojure
    (select-keys {:name "Todd" :age 22 :height 150} [:name :age])
    ;; => {:name "Todd", :age 22}
    ```

2) Missing Keys:

    ```clojure
    (select-keys {:name "Todd" :age 22} [:name :height])
    ;; => {:name "Todd"}
    ```

3) Empty Map:

    ```clojure
    (select-keys {} [:name :age])
    ;; => {}
    ```

4) Empty Keys:

    ```clojure
    (select-keys {:name "Todd" :age 22} [])
    ;; => {}
    ```

**Exercise:**

Write a function `filter-and-select` that combines filtering and key selection. The function should:

1. Filter a collection of maps based on a predicate.
2. Extract specific keys from the filtered maps.

**Data:**

```clojure
(def cereal-data
  [{:name "Cocoa Puffs" :calories 110 :sugars 15 :cost 6.99}
   {:name "Fruit Loops" :calories 110 :sugars 12 :cost 5.99}
   {:name "Honey Bunches of Oats" :calories 110 :sugars 9 :cost 4.99}
   {:name "Lucky Charms" :calories 110 :sugars 10 :cost 7.99}
   {:name "Raisin Bran" :calories 120 :sugars 8 :cost 3.99}
   {:name "Special K" :calories 120 :sugars 4 :cost 4.49}])
```

**Test Cases:**

1) Filter cereals with `calories <= 110` and extract all cereal names:

    ```clojure
    (filter-and-select #(<= (:calories %) 110) [:name] cereal-data)
    ;; => ({:name "Cocoa Puffs"} {:name "Fruit Loops"} {:name "Honey Bunches of Oats"} {:name "Lucky Charms"} {:name "Special K"})
    ```

2) Filter cereals with `cost > 5.00` and extract cereal names and costs:

    ```clojure
    (filter-and-select #(> (:cost %) 5.00) [:name :cost] cereal-data)
    ;; => ({:name "Cocoa Puffs", :cost 6.99} {:name "Fruit Loops", :cost 5.99} {:name "Lucky Charms", :cost 7.99})
    ```

3) Edge Case: Empty Dataset:

    ```clojure
    (filter-and-select #(<= (:calories %) 110) [:name] [])
    ;; => ()
    ```

**Tests Cases in `core_test.clj`:**

```clojure
(deftest test-filter-and-select
  (testing "Testing filter-and-select with cereal data"
    (is (= [{:name "Cocoa Puffs"} {:name "Fruit Loops"} {:name "Honey Bunches of Oats"} {:name "Lucky Charms"}] ;; expected
           (filter-and-select #(<= (:calories %) 110) [:name] cereal-data)))
    (is (= [{:name "Cocoa Puffs", :cost 6.99} {:name "Fruit Loops", :cost 5.99} {:name "Lucky Charms", :cost 7.99}] ;; expected
           (filter-and-select #(> (:cost %) 5.00) [:name :cost] cereal-data)))
    (is (= [] ;; expected
           (filter-and-select #(<= (:calories %) 110) [:name] [])))))
```

**Solution:**

```clojure
(defn filter-and-select
  "Filters a collection of maps based on a predicate and extracts specific keys from the filtered maps."
  [pred keys coll]
  (map #(select-keys % keys) (filter pred coll)))
```
