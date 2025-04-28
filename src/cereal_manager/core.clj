(ns cereal-manager.core
  (:require
   [clojure.data.csv :as csv] ;; for handling csv files
   [clojure.java.io :as io])) ;; for file reading


;; ~~~~~~~~~~~~~~~~~~~~~~~~~~DATA HANDLING~~~~~~~~~~~~~~~~~~~~~~~~~~
;; load the data into a hashmap
(defn load-data
  "takes a file path to a .csv file -> returns a hashmap of the data"
  [file-path]
  (with-open [reader (io/reader file-path)]
    (let [rows (doall (csv/read-csv reader))
          headers (map keyword (first rows))
          data (rest rows)]
      (map #(zipmap headers %) data))))

;; hashmap to use throughout the program
(def cereal 
  (load-data "resources/cereal.csv"))

;; output data to a new csv file
(defn output-data
  "takes a file path and data -> writes the data to a .csv file"
  [file-path data]
  (with-open [writer (io/writer file-path)]
    (csv/write-csv writer (cons (keys (first data)) data))))



;; ~~~~~~~~~~~~~~~~~~~~~SORTING DATA FUNCTIONS~~~~~~~~~~~~~~~~~~~~~~
;; test sort by on the loaded data
;; (println "sort by calories: \n"
;;          (map #(select-keys % [:name :calories])
;;                (sort-by #(Integer/parseInt (% :calories)) cereal)))

;; generic sort-by function
(defn sort-cereal-by
  "takes a key and a collection -> returns the collection sorted by the key"
  [key coll]
  (sort-by #(Integer/parseInt (% key)) coll))

;; average calories by a single mfr
(defn avg-cal-by-mfr
  "takes a mfr and a collection -> returns the average calories for that mfr, rounded to 2 decimal places"
  [mfr coll]
  (let [mfr-cereal (filter #(= (% :mfr) mfr) coll)
        avg-calories (/ (reduce + (map #(Integer/parseInt (% :calories)) mfr-cereal))
                        (count mfr-cereal))]
    (Double/parseDouble (format "%.2f" (double avg-calories)))))



;; ~~~~~~~~~~~~~~~~~~~~~~~~~~REPL TESTING~~~~~~~~~~~~~~~~~~~~~~~~~~
;; sort-cereal-by calories
(println "sort by calories: ")
(doseq [cereal (map #(select-keys % [:name :calories])
              (sort-cereal-by :calories cereal))]
  (println cereal))

;; println avg calories by Kellogg's
(println "avg calories by Kellogg's: \n"
         (avg-cal-by-mfr "K" cereal))



;; ~~~~~~~~~~~~~~~~~~~~~~~~~~EXERCISES~~~~~~~~~~~~~~~~~~~~~~~~~~
;; zipmap

;; exercise 1
(def students ["Ed" "Fred" "Ted"])
(def grades [90 80 70])

;; solution 1
(println "exercise 1: \n"
         (zipmap students grades) "\n")

;; exercise 2
(def soda ["Coke" "Pepsi" "Sprite"])
(def calories [140 150])

;; solution 2
(println "exercise 2: \n"
         (zipmap soda calories) "\n")


;; select-keys

;; Explanation: select-keys returns a map of the keys inputted provided they exist in the dataset

;; exercise 3
(def cereal-data
  [{:name "Cocoa Puffs" :calories 110 :sugars 15 :cost 6.99}
   {:name "Fruit Loops" :calories 110 :sugars 12 :cost 5.99}
   {:name "Honey Bunches of Oats" :calories 110 :sugars 9 :cost 4.99}
   {:name "Lucky Charms" :calories 110 :sugars 10 :cost 7.99}
   {:name "Raisin Bran" :calories 120 :sugars 8 :cost 3.99}
   {:name "Special K" :calories 120 :sugars 4 :cost 4.49}])

;;(def empty-data [])

;; examples

(println (map #(select-keys % [:sugars]) cereal-data) "\n")
(println "Example use select-keys: "(select-keys {:name "Todd" :age 22 :height 150} [:name :age]))

;; Use select-keys to get a all the cereal names in the collection.
;; (You will need to use the map function as well)

;; solution 3
(println "exercise 3: \n"
         (map #(select-keys % [:name]) cereal-data) "\n")

;; exercise 4
;; Use select-keys to get a all the cost of the cereals in the collection, with their names as well

;; solution 4
(println "exercise 4: \n"
         (map #(select-keys % [:name :cost]) cereal-data) "\n")



