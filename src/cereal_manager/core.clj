(ns cereal-manager.core
  (:require
   [clojure.data.csv :as csv] ;; for handling csv files
   [clojure.java.io :as io])) ;; for file reading

;; load the data into a hashmap
(defn load-data
  "takes a file path to a .csv file -> returns a hashmap of the data"
  [file-path]
  (with-open [reader (io/reader file-path)]
    (let [rows (doall (csv/read-csv reader))
          headers (map keyword (first rows))
          data (rest rows)]
      (map #(zipmap headers %) data))))

;; test sort by on the loaded data
(println "sort by calories: \n"
         (map #(select-keys % [:name :calories])
               (sort-by #(Integer/parseInt (% :calories)) (load-data "resources/cereal.csv"))))