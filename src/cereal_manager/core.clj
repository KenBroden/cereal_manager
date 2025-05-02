(ns cereal-manager.core
  (:require
   [clojure.data.csv :as csv] ;; for handling csv files
   [clojure.java.io :as io])) ;; for file reading

;; ~~~~~~~~~~~~~~~~~~~~~~~~~~Cereal Manager~~~~~~~~~~~~~~~~~~~~~~~~~~
;; This program is a cereal manager that loads a csv file of cereal data
;; and allows the user to perform various operations on the data.

;; Completed by Cameron Walker and Ken Broden 

;; ~~~~~~~~~~~~~~~~~~~~~~~~~~DATA HANDLING~~~~~~~~~~~~~~~~~~~~~~~~~~
;; load the data into a collection of hashmaps
(defn load-data
  "takes a file path to a .csv file -> returns a sequence of hashmaps of the data"
  [file-path]
  (with-open [reader (io/reader file-path)]
    (let [rows (doall (csv/read-csv reader))
          headers (map keyword (first rows))
          data (rest rows)]
      (map #(zipmap headers %) data))))

;; do all forces the lazy seq not to be lazy.  loads everything into memory
;; each map represents a row in the csv file
;; the keys are the column headers
;; the values are the data in each row
;; everything is turned into a string, so we need will need to parse
;; "zip"map is derived from the word zipper, which sequentially
;; combines two sides of a zipper

;; upside of this function: all data is in a single collection
;; downside: we are gonna be passing a bunch of data thru our 
;; functions which is not used at all. but having it promotes flexibility.

;; hashmap to use throughout the program
(def cereal
  (load-data "resources/cereal.csv"))

;; see the data file structure here:
(println "\n First 10 rows of data after loading in: "
         (take 10 cereal) "\n")

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

;; uses higher order filter to return just cereals by one manufacturer
;; then uses reduce to add up the calories and then divides by the count of the cereals
;; Needs to parseInt because the load function returns strings

;;**insert Cameron's code

;; ~~~~~~~~~~~~~~~~~~~Implementation~~~~~~~~~~~~~~~~~~~~

;; println avg calories by Kellogg's
(println "\navg calories by Kellogg's: \n"
         (avg-cal-by-mfr "K" cereal))

;; QUESTION: What are the sugar contents of the cereals? sort from least to most.
;; ~~~~~~~~~~~~~~~~~~~~~FUNCTIONS~~~~~~~~~~~~~~~~~~~~~~

(defn sort-cereal-by
  "takes a key and a collection, and a optional compare operator -> 
   returns the collection sorted by the key"
  ([key coll] ;; 2 arguments
   (sort-by #(Double/parseDouble (% key)) coll))

  ([key comparator coll] ;; second option, 3 arguments
   (sort-by #(Double/parseDouble (% key)) comparator coll)))

;; essential just a specialized version of the higher order sort-by function
;; which takes a keyword and a collection and sorts the collection by the key
;; The specialized version parses the key as an int, since the data is loaded as a string

;; ~~~~~~~~~~~~~~~~~~~Implementation~~~~~~~~~~~~~~~~~~~~

;; sort-cereal-by calories
(println "\nWhat are the sugar contents of the cereals? sort from least to most.")
(println "sort by calories: ")
(doseq [cereal (map #(select-keys % [:name :calories])
                    (sort-cereal-by :calories cereal))]
  (println cereal))
;; doseq here just iterates over the collection, it allows us to 
;; print each return value on a new line.

;; QUESTION: What are the 10 highest-rated cereals based on Consumer Reports?
;; ~~~~~~~~~~~~~~~~~~~~~FUNCTIONS~~~~~~~~~~~~~~~~~~~~~~

;; top 10 of a sorting key
(defn top-rank
  "takes a key, a collection, and a number n for how many data points to return
   -> returns the top n elements of the collection sorted by the key"
  [key coll n]
  (take n (sort-cereal-by key > coll))) ;; uses the comparator to sort the collection

;; bottom 10 of a sorting key
(defn bottom-rank
  "takes a key, a collection, and a number n for how many data points to return
   -> returns the bottom n elements of the collection sorted by the key"
  [key coll n]
  (take n (sort-cereal-by key < coll))) ;; uses the comparator to sort the collection

;; this question wasn't asked, but it was an easy one to add, and interesting results.


;; ~~~~~~~~~~~~~~~~~~~Implementation~~~~~~~~~~~~~~~~~~~~
(println "\nWhat are the 10 highest-rated cereals based on Consumer Reports?")
(println "top 10 cereals by rating: ")
(doseq [[index cereal] (map-indexed (fn [i cereal] [(inc i) cereal]) ;; Increment index
                                    (map #(select-keys % [:name :rating]) ;; only show name and rating
                                         (top-rank :rating cereal 10)))]
  (println (str index ". " cereal))) ;; adds index with a period to front.

;; map-indexed is a higher order function that takes a function (which we 
;; defined here as increment i and cereal) and a collection, and returns a
;; new collection with the function applied to each element of the collection

(println "\nbottom 10 cereals by rating: ")
(doseq [[index cereal] (map-indexed (fn [i cereal] [(inc i) cereal]) ;; Increment index
                                    (map #(select-keys % [:name :rating])
                                         (bottom-rank :rating cereal 10)))]
  (println (str index ". " cereal))) ;; adds index with a period to front.