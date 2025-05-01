(ns cereal-manager.core-test
  (:require [clojure.test :refer [deftest is testing]]
            [cereal-manager.core :refer [inventory-builder filter-and-select cereal-data]]))

;; (deftest test-filter-and-select
;;   (testing "Testing filter-and-select with cereal data"
;;     (is (= [{:name "Cocoa Puffs"} {:name "Fruit Loops"} {:name "Honey Bunches of Oats"} {:name "Lucky Charms"}] ;; expected
;;            (filter-and-select #(<= (:calories %) 110) [:name] cereal-data)))
;;     (is (= [{:name "Cocoa Puffs", :cost 6.99} {:name "Fruit Loops", :cost 5.99} {:name "Lucky Charms", :cost 7.99}] ;; expected
;;            (filter-and-select #(> (:cost %) 5.00) [:name :cost] cereal-data)))
;;     (is (= [] ;; expected
;;            (filter-and-select #(<= (:calories %) 110) [:name] [])))))

;; (deftest test-inventory-builder
;;   (testing "Testing the inventory-builder function"
;;     (is (= {"Apples" 10, "Bananas" 20, "Cherries" 30} ;; expected
;;            (inventory-builder ["Apples" "Bananas" "Cherries"] [10 20 30])))
;;     (is (= {"Apples" 10, "Bananas" 20} ;; expected
;;            (inventory-builder ["Apples" "Bananas"] [10 20 30])))
;;     (is (= {} ;; expected
;;            (inventory-builder [] [])))
;;     (is (= {} ;; expected
;;            (inventory-builder ["Apples" "Bananas"] [])))))
