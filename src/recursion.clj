(ns recursion)

(defn product [coll]
  (if (empty? coll)
  1
  (* (first coll) (product (rest coll)))))

(defn singleton? [coll]
  (if (empty? coll)
    false
  (empty? (rest coll))))

(defn my-last [coll]
  (if (empty? coll)
    nil
  (if (singleton? coll)
    (first coll)
   (my-last (rest coll)))))

(defn max-element [a-seq]
  (if (< (count a-seq) 2)
    (my-last a-seq)
    (max (first a-seq) (max-element (rest a-seq)))))

(defn seq-max [seq-1 seq-2]
  (if (> (count seq-1) (count seq-2))
    seq-1
    seq-2))

(defn longest-sequence [a-seq]
  (if (< (count a-seq) 2)
    (my-last a-seq)
    (seq-max (first a-seq) (longest-sequence (rest a-seq)))))

(defn my-map [f a-seq]
  (if (empty? a-seq)
    a-seq
    (cons (f (first a-seq))
          (my-map f (rest a-seq)))))

(defn my-filter [pred? a-seq]
  (let [fst (first a-seq) rst (rest a-seq)]
  (cond (empty? a-seq) a-seq
        (pred? fst) (cons fst (my-filter pred? rst))
        :else (my-filter pred? rst))))

(defn sequence-contains? [elem a-seq]
  (cond (empty? a-seq) false
        (= elem (first a-seq)) true
        :else (sequence-contains? elem (rest a-seq))))

(defn my-take-while [pred? a-seq]
  (cond (empty? a-seq) '()
        (pred? (first a-seq)) (cons (first a-seq) (my-take-while pred? (rest a-seq)))
        :else '()))

(defn my-drop-while [pred? a-seq]
  (cond (empty? a-seq) '()
        (pred? (first a-seq)) (my-drop-while pred? (rest a-seq))
        :else a-seq))

(defn seq= [a-seq b-seq]
  (cond (or (empty? a-seq) (empty? b-seq)) (and (empty? a-seq) (empty? b-seq))
        (= (first a-seq) (first b-seq)) (seq= (rest a-seq) (rest b-seq))
        :else false))

(defn my-map [f seq-1 seq-2]
  (cond (or (empty? seq-1) (empty? seq-2)) '()
        :else (cons (f (first seq-1) (first seq-2)) (my-map f (rest seq-1) (rest seq-2)))
        ))

(defn power [n k]
  (cond (zero? k) 1
        :else (* n (power n (dec k)))))

(defn fib [n]
  (cond (= 0 n) 0
        (= 1 n) 1
        :else (+ (fib (dec n)) (fib (- n 2)))))

(defn my-repeat [how-many-times what-to-repeat]
  (cond (<= how-many-times 0) '()
        :else (cons what-to-repeat (my-repeat (dec how-many-times) what-to-repeat))))

(defn my-range [up-to]
  (cond (< 1 up-to) '()
        :else (cons (dec up-to) (my-range (dec up-to)))))

(defn tails [a-seq]
  (cond (empty? a-seq) '(())
        :else (cons (seq a-seq) (tails (rest a-seq)))))

(defn inits [a-seq]
  (reverse (map reverse (tails (reverse a-seq)))))

(defn rotations [a-seq]
  (cond (empty? a-seq) '(())
        :else (rest (map concat (tails a-seq) (inits a-seq)))))

(defn my-frequencies-helper [freqs a-seq]
  (if (empty? a-seq)
    freqs
    (let [fst (first a-seq)
          cnt (inc (get freqs fst 0))]
      (my-frequencies-helper (assoc freqs fst cnt) (rest a-seq)))))

(defn my-frequencies [a-seq]
  (my-frequencies-helper {} a-seq))

(defn un-frequencies-helper [unfreqs a-map]
  (if (empty? a-map)
    unfreqs
    (let [freq-pair (first a-map)
          seq (repeat (get freq-pair 1) (get freq-pair 0))]
      (un-frequencies-helper (concat unfreqs seq) (rest a-map)))))

(defn un-frequencies [a-map]
  (un-frequencies-helper '() a-map))

(defn my-take [n coll]
  (if (or (<= n 0) (empty? coll))
    '()
    (cons (first coll) (my-take (dec n) (rest coll)))))

(defn my-drop [n coll]
  (if (or (<= n 0) (empty? coll))
    (seq coll)
    (my-drop (dec n) (rest coll))))

(defn halve [a-seq]
  (let [i (int (/ (count a-seq) 2))]
    (cons (my-take i a-seq) (cons (my-drop i a-seq) '()))))

(defn seq-merge [a-seq b-seq]
  (cond (empty? a-seq) b-seq
        (empty? b-seq) a-seq
        :else (let [a (first a-seq) b (first b-seq)]
                (if (<= a b)
                  (cons a (seq-merge (rest a-seq) b-seq))
                  (cons b (seq-merge a-seq (rest b-seq)))))))

(defn merge-sort [a-seq]
  (if (<= (count a-seq) 1)
    a-seq
    (let [[seq1 seq2] (halve a-seq)]
      (seq-merge (merge-sort seq1) (merge-sort seq2)))))

(defn monotonic? [a-seq]
  (if (empty? a-seq)
    true
    (or (apply <= a-seq) (apply >= a-seq))))

(defn split-into-monotonics [a-seq]
  (if (empty? a-seq)
    '()
    (let [monotonic (last (take-while monotonic? (inits a-seq)))]
      (cons monotonic (split-into-monotonics (drop (count monotonic) a-seq))))))

(defn permutations [a-set]
  (if (>= 1 (count a-set))
    (list (into '() a-set))
    (for [head a-set
          tail (permutations (disj (set a-set) head))]
      (do (cons head tail)))))

(defn powerset [a-set]
  (if (empty? a-set)
    #{#{}}
    (let [first-set (conj #{} (first a-set))
          tail (powerset (rest a-set))]
      (clojure.set/union (map #(clojure.set/union first-set %) tail) tail))))
