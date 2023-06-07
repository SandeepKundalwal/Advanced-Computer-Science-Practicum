;cube-root function to calculate cuberoot of a number using Newton-Rapson Method
(define (cube-root x) (cube-root-iterator 1.0 x))

(define (cube-root-iterator guess x)
  (cond
    [(goodenough? guess x) guess]
    [else (cube-root-iterator (improveguess guess x) x)]
  )
)

(define (goodenough? guess x)
  (< (/ (abs (- (cube guess) x)) guess) 0.0001)
)

(define (improveguess y x)
  (/ (+ (/ x (* y y)) (* 2 y)) 3)
)

(define (cube x) (* x x x))