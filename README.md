# cereal_manager

- *Project by Ken Broden, Cameron Walker*

A Clojure library designed to analyze and perform operations on the 80 Cereals dataset.

## Project Overview

This project is part of CSci 2601 and focuses on analyzing a dataset of 80 cereals. The goal is to answer specific questions about the dataset using Clojure functions while exploring advanced features of the language. The project includes writing tutorials, exercises, and presenting findings.

## Questions Answered

1. **On average, which cereal manufacturer’s cereal has the fewest calories per serving?**
2. **Display the cereals based on sugar content, from least to most.**
3. **What are the 10 highest-rated cereals based on Consumer Reports?**

## Features

- Utilizes at least 5 pre-defined Clojure functions not covered in class.
  - `with-open` - Evaluates body in a try expression with names bound to the values of the inits, and a finally clause that calls (.close name) on each name in reverse order.
  - `zipmap` - Returns a map with the keys mapped to the corresponding vals.
  - `parseInt` - Parses string as a integer.
  - `select-keys` - Returns a map containing only those entries in map whose key is in keys
  - `doall` - Walks through the successive nexts of the seq, retains the head and returns it, thus causing the entire seq to reside in memory at one time.
  - `doseq` - Repeatedly executes body (presumably for side-effects) with bindings and filtering as provided by "for".  Does not retain the head of the sequence. Returns nil.
  - `map-indexed` - Returns a lazy seq of the items in coll, where each item is a vector of the index and the item.
  - `format` - In general, if you want to use floating-point formats %e, %f, %g, or %a with format or printf, and you don't know whether the values you want to format are floats or doubles you should convert them:

    ```console
    user=> (format "%.3f" (double 2))
    "2.000"
    ```

## Usage

This library provides functions to load the dataset and perform the above operations. To get started, ensure you have the dataset downloaded from [Kaggle](https://www.kaggle.com/datasets/crawford/80-cereals?resource=download) and follow the instructions in the source code.
