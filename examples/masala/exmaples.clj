(ns masala.exmaples
  (:require [masala.core :refer [defcurried
                                 do-curry
                                 uncurry
                                 partial-fn]]))

(defcurried hello-curried
  "Hello world for currying in Clojure"
  [msg warning]
  (println msg warning))

(defcurried multiply
  "multiplies four values together"
  [w x y z]
  (* w x y z))

(defcurried crazy
  "lots"
  [x & ys]
  (apply * x ys))

(defcurried destructuring
  ""
  [{:keys [x y]} z]
  (* x y z))

;; Using a curried function
;; ((((multiply 1) 2) 3) 4)
;; (do-curry multiply 1 2 3 4)

(def uncurry-multiply (uncurry multiply))
;; (uncurry-multiply 1 2 3 4)

(def multiply-by-6 (partial-fn multiply 1 2 3))
;; (multiply-by-6 4)
;; (multiply-by-6 6)
