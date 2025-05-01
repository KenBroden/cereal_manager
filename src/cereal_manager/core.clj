(ns cereal-manager.core
  (:require
   [clojure.data.csv :as csv] ;; for handling csv files
   [clojure.java.io :as io])) ;; for file reading


;; ~~~~~~~~~~~~~~~~~~~~~~~~~~DATA HANDLING~~~~~~~~~~~~~~~~~~~~~~~~~~
;; load the data into a collection of hashmaps
(defn load-data
  "takes a file path to a .csv file -> returns a hashmap of the data"
  [file-path]
  (with-open [reader (io/reader file-path)]
    (let [rows (doall (csv/read-csv reader))
          headers (map keyword (first rows))
          data (rest rows)]
      (map #(zipmap headers %) data))))

;; each map represents a row in the csv file
;; the keys are the column headers
;; the values are the data in each row

;; upside of this function: all data is in a single collection
;; downside: we are gonna be passing a bunch of data thru our 
;; functions which is not used at all. but having it promotes flexibility.

;; hashmap to use throughout the program
(def cereal
  (load-data "resources/cereal.csv"))

;; output data to a new csv file
(defn output-data
  "takes a file path and data -> writes the data to a .csv file"
  [file-path data]
  (with-open [writer (io/writer file-path)]
    (csv/write-csv writer (cons (keys (first data)) data))))


;; QUESTION: On average, which cereal manufacturer’s cereal has the fewest calories per serving?
;; ~~~~~~~~~~~~~~~~~~~~~FUNCTIONS~~~~~~~~~~~~~~~~~~~~~~

;; average calories by a single mfr
(defn avg-cal-by-mfr
  "takes a mfr and a collection -> returns the average calories for that mfr, rounded to 2 decimal places"
  [mfr coll]
  (let [mfr-cereal (filter #(= (% :mfr) mfr) coll)
        avg-calories (/ (reduce + (map #(Integer/parseInt (% :calories)) mfr-cereal))
                        (count mfr-cereal))]
    (Double/parseDouble (format "%.2f" (double avg-calories)))))

;;**insert Cameron's code

;; ~~~~~~~~~~~~~~~~~~~Implementation~~~~~~~~~~~~~~~~~~~~

;; println avg calories by Kellogg's
(println "avg calories by Kellogg's: \n"
         (avg-cal-by-mfr "K" cereal))

;; QUESTION: What are the sugar contents of the cereals? sort from least to most.
;; ~~~~~~~~~~~~~~~~~~~~~FUNCTIONS~~~~~~~~~~~~~~~~~~~~~~

(defn sort-cereal-by
  "takes a key and a collection -> returns the collection sorted by the key"
  [key coll]
  (sort-by #(Integer/parseInt (% key)) coll))

;; ~~~~~~~~~~~~~~~~~~~Implementation~~~~~~~~~~~~~~~~~~~~

;; sort-cereal-by calories
(println "sort by calories: ")
(doseq [cereal (map #(select-keys % [:name :calories])
                    (sort-cereal-by :calories cereal))]
  (println cereal))

;; QUESTION: What are the 10 highest-rated cereals based on Consumer Reports?
;; ~~~~~~~~~~~~~~~~~~~~~FUNCTIONS~~~~~~~~~~~~~~~~~~~~~~



;; ~~~~~~~~~~~~~~~~~~~Implementation~~~~~~~~~~~~~~~~~~~~

