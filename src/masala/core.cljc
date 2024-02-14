(ns masala.core
  "Namespaces hides forbidden macros.
  Mix functions together to your heart's content.
  Optionally, serve with rice")

(defmacro curry
  "Curry a body of expressions as if it were a fn f
  (f x y) => z becomes g(x) => (h(y) => z)"
  [name args & body]
  (let [num-args  (count args)
        first-arg (if (= '& (first args))
                    (second args)
                    (first args))]
    (if (zero? num-args) ;; this is the last curry do body
      `(do ~@body)
      `(fn ~(symbol (str name "-" num-args))
         ~(vector first-arg)
         (curry ~name ~(vec (if (= '& (first args))
                              (rest (rest args))
                              (rest args))) ~@body)))))

(defmacro defcurried
  "Like defn, but creates a curried function var `name`
  where (f x y) => z becomes g(x) => (h(y) => z) etc."
  [name doc args & body]
  `(def ~(with-meta name {:arglists `'~(list args)})
     ~doc
     (curry ~name ~args ~@body)))

(defn do-curry
  "Computes a curried function `f` created with `defcurried` or `curry`
  by calling the successive functions with `args` from left-to-right."
  [f & args]
  (let [arg (first args)]
    (if (zero? (count args))
      f
      (recur (f arg) (rest args)))))

(defmacro uncurry
  "Experimental. Uncurries a curried function var created with `defcurried`
  or `curry`. Returns a fn that takes the originial curried args."
  [f]
  (let [{:keys [arglists]} (meta (resolve f))]
    `(fn ~(symbol (str f "-uncurried"))
       ~(first arglists)
       (apply do-curry ~f ~(first arglists)))))

(defn partial-fn
  "Takes a curried function `f` and fewer than expected `args` and returns
  a partial function application."
  [f & args]
  (fn [& xs]
    (apply do-curry f (into (vec args) (vec xs)))))
