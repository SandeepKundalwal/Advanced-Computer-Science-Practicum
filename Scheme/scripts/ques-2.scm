;merge the two sorted list
(define (merge left_list right_list)
  (cond
    [(null? left_list) right_list]
    [(null? right_list) left_list]
    [(< (car left_list) (car right_list)) (cons (car left_list) (merge (cdr left_list) right_list))] ;if left_list have smaller element, add it to the final list
    [(= (car left_list) (car right_list)) (cons (car left_list) (merge (cdr left_list) (cdr right_list)))] ;removing duplicate elements from the list
    [else (cons (car right_list) (merge left_list (cdr right_list)))]
  )
)

;to find the middle element and split the list into two equal parts
(define (splitIntoTwoList n first_lst second_lst)
  (cond
    [(or (= n 0) (null? second_lst)) (cons (reverse first_lst) second_lst)]
    [else (splitIntoTwoList (- n 1) (cons (car second_lst) first_lst) (cdr second_lst))]
  )
)


;to split the list
(define (split lst)
  (splitIntoTwoList (quotient (length lst) 2) '() lst)
)

;to sort the given list in O(nlogn) in every case using merge-sort
(define (merge-sort unsorted_list)
  (cond
    [(or (null? unsorted_list) (null? (cdr unsorted_list))) unsorted_list] ;empty list OR single element list is already sorted
    [else (let ((halves (split unsorted_list))) ;splitting the list in two almost equal halves
            (merge (merge-sort (car halves)) (merge-sort (cdr halves))) ;recursive calls on both the lists
          )
    ]
  )
)
  
  
  