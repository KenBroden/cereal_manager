(ns cereal-manager.core-test
  (:require [clojure.test :refer [deftest is testing]]
            [cereal-manager.core :refer [top-rank]]))

(deftest test-top-rank
  (testing "Testing the top-n function"
    ;; basic case
    (is (= [{:name "Cereal A" :rating "90.5"}
            {:name "Cereal B" :rating "85.3"}]
           (top-rank :rating
                     [{:name "Cereal A" :rating "90.5"}
                      {:name "Cereal B" :rating "85.3"}
                      {:name "Cereal C" :rating "70.2"}]
                     2)))

    ;; more elements than available
    (is (= [{:name "Cereal A" :rating "90.5"}
            {:name "Cereal B" :rating "85.3"}
            {:name "Cereal C" :rating "70.2"}]
           (top-rank :rating
                     [{:name "Cereal A" :rating "90.5"}
                      {:name "Cereal B" :rating "85.3"}
                      {:name "Cereal C" :rating "70.2"}]
                     5)))

    ;; empty coll
    (is (= []
           (top-rank :rating [] 3)))

    ;; n = 0
    (is (= []
           (top-rank :rating
                     [{:name "Cereal A" :rating "90.5"}
                      {:name "Cereal B" :rating "85.3"}]
                     0)))))
