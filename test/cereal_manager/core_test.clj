(ns cereal-manager.core-test
  (:require [clojure.test :refer :all]
            [cereal-manager.core :refer :all]))

(deftest test-element-counts
  (testing "Testing the element-counts function"
    (is (= {"Ed" 90 "Fred" 80 "Ted" 70} (report-card-builder ["Ed" "Fred" "Ted"] [90 80 70])))))

;; (deftest test-vals-set
;;   (testing "Test the vals-set function"
;;     (is (= #{1 2} (vals-set #{:a :b} {:a 1, :b 2, :c 3})))
;;     (is (= #{} (vals-set #{} {:a 1, :b 2})))
;;     (is (= #{} (vals-set #{:x :y} {:a 1, :b 2})))
;;     (is (= #{} (vals-set #{:a :b} {})))
;;     (is (= #{1 2} (vals-set #{:a :b} {:a 1, :b 2, :c 3})))))

;; (deftest test-first-element
;;   (testing "Testing the first_element function"
;;     (is (= 1 (first-element [1 2 3])))
;;     (is (= :not-found (first-element [])))
;;     (is (= "a" (first-element '("a" "b" "c"))))
;;     (is (= \T (first-element "Tennessee")))
;;     (is (= :not-found (first-element "")))
;;     (is (= :key (first-element [:key :value])))
;;     (is (= :not-found (first-element #{:not-found 5})))))
