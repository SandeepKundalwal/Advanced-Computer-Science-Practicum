;ques-1-(a)
(define (inc x) (+ x 1))
(define (twice func) (lambda(x) (func (func x))))

;ques-1-(b)
(define (inc x) (+ x 1))
(define (square x) (* x x))
(define (comp f g) (lambda(x) (f (g x))))

;ques-1-(c)
(define (inc x) (+ x 1))
(define (twice t) (compose t t))
