# masala
Currying in Clojure for fun and learning.

``` clojure
user> (defcurried multiply
        "multiplies four values together"
        [w x y z]
        (* w x y z))
#'user/multiply
user> multiply
#function[user/multiply-4--7434]

;; Using a curried function
user> ((((multiply 1) 2) 3) 4)
24
;; or
user>  (do-curry multiply 1 2 3 4)
24

;; It's also possible to uncurry a function created with defcurried
user> (def uncurry-multiply (uncurry multiply))
#'user/uncurry-multiply
user> (uncurry-multiply 1 2 3 4)
24

;; partial function applications also available.
;; Probably not as useful as clojure.core/partial tbh.
user> (def multiply-by-6 (partial-fn multiply 1 2 3))
#'user/multipyl-by-6
user> (multiply-by-6 4)
24
user> (multiply-by-6 6)
36
```
