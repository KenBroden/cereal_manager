(ns cereal-manager.core-test
  (:require [clojure.test :refer [deftest is testing]]
            [cereal-manager.core :refer [top-rank 
                                         avg-cal-by-mfr
                                         lowest-avg-cal 
                                         sort-cereal-by
                                         bottom-rank]]))

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

;; Format of the input data
;; ({:mfr N, :calories 70, :shelf 3, :name 100% Bran, :potass 280, :type C, :fat 1, :fiber 10, :carbo 5, :vitamins 25, :weight 1, :sodium 130, :cups 0.33, :sugars 6, :protein 4, :rating 68.402973} 
;;  {:mfr Q, :calories 120, :shelf 3, :name 100% Natural Bran, :potass 135, :type C, :fat 5, :fiber 2, :carbo 8, :vitamins 0, :weight 1, :sodium 15, :cups 1, :sugars 8, :protein 3, :rating 33.983679} 
;;  {:mfr K, :calories 70, :shelf 3, :name All-Bran, :potass 320, :type C, :fat 1, :fiber 9, :carbo 7, :vitamins 25, :weight 1, :sodium 260, :cups 0.33, :sugars 5, :protein 4, :rating 59.425505} 
;;  {:mfr K, :calories 50, :shelf 3, :name All-Bran with Extra Fiber, :potass 330, :type C, :fat 0, :fiber 14, :carbo 8, :vitamins 25, :weight 1, :sodium 140, :cups 0.5, :sugars 0, :protein 4, :rating 93.704912} 
;;  {:mfr R, :calories 110, :shelf 3, :name Almond Delight, :potass -1, :type C, :fat 2, :fiber 1, :carbo 14, :vitamins 25, :weight 1, :sodium 200, :cups 0.75, :sugars 8, :protein 2, :rating 34.384843} 
;;  {:mfr G, :calories 110, :shelf 1, :name Apple Cinnamon Cheerios, :potass 70, :type C, :fat 2, :fiber 1.5, :carbo 10.5, :vitamins 25, :weight 1, :sodium 180, :cups 0.75, :sugars 10, :protein 2, :rating 29.509541} 
;;  {:mfr K, :calories 110, :shelf 2, :name Apple Jacks, :potass 30, :type C, :fat 0, :fiber 1, :carbo 11, :vitamins 25, :weight 1, :sodium 125, :cups 1, :sugars 14, :protein 2, :rating 33.174094} 
;;  {:mfr G, :calories 130, :shelf 3, :name Basic 4, :potass 100, :type C, :fat 2, :fiber 2, :carbo 18, :vitamins 25, :weight 1.33, :sodium 210, :cups 0.75, :sugars 8, :protein 3, :rating 37.038562} 
;;  {:mfr R, :calories 90, :shelf 1, :name Bran Chex, :potass 125, :type C, :fat 1, :fiber 4, :carbo 15, :vitamins 25, :weight 1, :sodium 200, :cups 0.67, :sugars 6, :protein 2, :rating 49.120253} 
;;  {:mfr P, :calories 90, :shelf 3, :name Bran Flakes, :potass 190, :type C, :fat 0, :fiber 5, :carbo 13, :vitamins 25, :weight 1, :sodium 210, :cups 0.67, :sugars 5, :protein 3, :rating 53.313813})

(deftest test-avg-cal-by-mfr
  (testing "Testing the avg-cal-by-mfr function"
    (is (= 100.0 ;;function parses the string to a double
           (avg-cal-by-mfr "K" [{:mfr "K" :calories "150"}
                                {:mfr "K" :calories "50"}])))
    (is (= 80.0
           (avg-cal-by-mfr "R" [{:mfr "R" :calories "100"}
                                {:mfr "R" :calories "60"}
                                {:mfr "R" :calories "80"}])))))

(deftest test-lowest-avg-cal
  (testing "Testing the lowest-avg-cal function"
    (is (= {:mfr "R", :avg-cal 80.0}
           (lowest-avg-cal [{:mfr "K" :calories "150"}
                            {:mfr "K" :calories "50"}
                            {:mfr "R" :calories "100"}
                            {:mfr "R" :calories "60"}
                            {:mfr "R" :calories "80"}])))
    (is (= {:mfr "R", :avg-cal 100.0}
           (lowest-avg-cal [{:mfr "K" :calories "150"}
                            {:mfr "R" :calories "100"}])))))

(deftest test-sort-cereal-by
  (testing "Testing the sort-cereal-by function"
    (is (= [{:name "Cereal A" :calories "50"}
            {:name "Cereal B" :calories "70"}
            {:name "Cereal C" :calories "90"}]
           (sort-cereal-by :calories
                           [{:name "Cereal A" :calories "50"}
                            {:name "Cereal B" :calories "70"}
                            {:name "Cereal C" :calories "90"}])))
    (is (= [{:name "Cereal C" :calories "90"}
            {:name "Cereal B" :calories "70"}
            {:name "Cereal A" :calories "50"}]
           (sort-cereal-by :calories > ;; function can switch the order of the sort
                           [{:name "Cereal A" :calories "50"}
                            {:name "Cereal B" :calories "70"}
                            {:name "Cereal C" :calories "90"}])))))

(deftest test-bottom-rank
  (testing "Testing the bottom-rank function"
    (is (= [{:name "Cereal A" :rating "50.0"}
            {:name "Cereal B" :rating "70.0"}]
           (bottom-rank :rating
                        [{:name "Cereal A" :rating "50.0"}
                         {:name "Cereal B" :rating "70.0"}
                         {:name "Cereal C" :rating "90.0"}]
                        2))) ;; return the lowest 2 rankings

    ;; more elements than available
    (is (= [{:name "Cereal A" :rating "50.0"}
            {:name "Cereal B" :rating "70.0"}
            {:name "Cereal C" :rating "90.0"}]
           (bottom-rank :rating
                        [{:name "Cereal A" :rating "50.0"}
                         {:name "Cereal B" :rating "70.0"}
                         {:name "Cereal C" :rating "90.0"}]
                        5))))) ;; take 5, only 3 available